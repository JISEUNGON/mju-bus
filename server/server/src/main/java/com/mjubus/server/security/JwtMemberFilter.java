package com.mjubus.server.security;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.member.MemberPrincipalDto;
import com.mjubus.server.enums.MemberRole;
import com.mjubus.server.service.member.MemberService;
import com.mjubus.server.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JwtMemberFilter extends OncePerRequestFilter {

    private MemberService memberService;
    private String secretKey;

    public JwtMemberFilter(MemberService memberService, String secretKey) {
        this.memberService = memberService;
        this.secretKey = secretKey;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "JWT Token does not begin with Bearer String");
            filterChain.doFilter(request, response);
            return;
        }

        // Token 꺼내기
        String token = authorizationHeader.split(" ")[1];

        // Token 검증
        if (!JwtUtil.isKeyValid(token)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "JWT Token is not valid");
            filterChain.doFilter(request, response);
            return;
        }

        // Token 만료 체크
        if (JwtUtil.isExpired(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        // 유저 식별
        MemberPrincipalDto principalDto = JwtUtil.getMemberPrincipal(token);
        Optional<Member> optionalMember = memberService.findOptionalMemberByMemberId(principalDto.getId());

        if (optionalMember.isEmpty()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "JWT Token is not valid");
            filterChain.doFilter(request, response);
            return;
        }

        Member member = optionalMember.get();
        String role = "";
        if (member.getRole() == MemberRole.ADMIN) {
            role = "ADMIN";
        } else {
            String[] path = request.getServletPath().split("/");
            // 그룹에 대한 요청인 경우
            if (path.length > 2 && path[1].equals("taxi") && path[2].matches("[0-9]+")) { // 생성, 리스트 조회는 그룹에 대한 요청이 아님
                String groupId = path[2];
                if (memberService.hasGroupAuthority(member.getId(), groupId)) {
                    if (memberService.isGroupAdminister(member.getId(), groupId)) { // 그룹 관리자인 경우
                        role = "GROUP_ADMIN";
                    } else { // 그룹 멤버인 경우
                        role = "GROUP_MEMBER";
                    }
                } else { // 그룹에 속하지 않은 경우
                    role = "MEMBER";
                }
            } else { // 그룹에 대한 요청이 아닌 경우
                role = "MEMBER";
            }
        }



        log.warn("role: {}", role);
        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member, null, List.of(new SimpleGrantedAuthority(role)));

        // UserDetail을 통해 인증된 사용자 정보를 SecurityContext에 저장
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
