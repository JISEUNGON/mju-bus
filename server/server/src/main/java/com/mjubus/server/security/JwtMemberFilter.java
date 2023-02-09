package com.mjubus.server.security;

import com.mjubus.server.domain.Member;
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
            log.error("JWT Token does not begin with Bearer String");
            filterChain.doFilter(request, response);
            return;
        }

        // Token 꺼내기
        String token = authorizationHeader.split(" ")[1];

        // Token 검증
        if (!JwtUtil.isKeyValid(token)) {
            log.error("JWT Token is not valid");
            filterChain.doFilter(request, response);
            return;
        }

        // Token 만료 체크
        if (JwtUtil.isExpired(token)) {
            log.error("JWT Token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        // 유저 식별
        Member member = JwtUtil.getMember(token);
        String role = member.getRole().getKey();
        log.info("member: {}", member);

        String[] path = request.getServletPath().split("/");
        // 그룹에 대한 요청인 경우
        if (path.length > 2 && path[1].equals("taxi") && path[2].matches("[0-9]+")) {
            String groupId = path[2];
            if (memberService.hasGroupAuthority(member.getId(), groupId)) {
                if (memberService.isGroupAdminister(member.getId(), groupId)) {
                    role = "GROUP_ADMIN";
                } else {
                    role = "GROUP_MEMBER";
                }
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
