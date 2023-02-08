package com.mjubus.server.security;

import com.mjubus.server.domain.Member;
import com.mjubus.server.enums.MemberRole;
import com.mjubus.server.service.member.MemberService;
import com.mjubus.server.service.taxiParty.TaxiPartyService;
import com.mjubus.server.service.taxiPartyMembers.TaxiPartyMembersService;
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
public class JwtPartyFilter extends OncePerRequestFilter {

    private TaxiPartyMembersService taxiPartyMembersService;
    private String secretKey;

    public JwtPartyFilter(TaxiPartyMembersService taxiPartyMembersService, String secretKey) {
        this.taxiPartyMembersService = taxiPartyMembersService;
        this.secretKey = secretKey;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorizationHeader: {}", authorizationHeader);

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

        // 유저 식별 ID
        Member member = JwtUtil.getMember(token);
        log.info("member: {}", member);

        if (member.getRole() == MemberRole.GUEST) {
            log.error("JWT Token is a GUEST");
            filterChain.doFilter(request, response);
            return;
        }

        String groupId = request.getParameter("group-id");
        if (groupId != null && taxiPartyMembersService.isMember(groupId, member)) {
            log.error("JWT Token is not a member of the party");
            filterChain.doFilter(request, response);
            return;
        }

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member, null, List.of(new SimpleGrantedAuthority("")));

        // UserDetail을 통해 인증된 사용자 정보를 SecurityContext에 저장
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
