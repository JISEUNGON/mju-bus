package com.mjubus.server.config;

import com.mjubus.server.security.JwtMemberFilter;
import com.mjubus.server.service.member.MemberService;
import com.mjubus.server.service.taxiPartyMembers.TaxiPartyMembersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  // 해당 애노테이션을 붙인 필터(현재 클래스)를 스프링 필터체인에 등록.
public class SecurityConfig {

    private final MemberService memberService;


    @Value("${external.jwt.secret}")
    private String secretKey;

    public SecurityConfig(MemberService memberService) {
        this.memberService = memberService;
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web.ignoring().antMatchers(
                        "/",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/chat",
                        "/login/**",
                        "/member/refresh",
                        "/bus/**",
                        "/station/**",
                        "/health/**",
                        "/post/**",
                        "/calendar/**");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(new JwtMemberFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/taxi/**/delete").hasAnyAuthority("ADMIN", "GROUP_ADMIN")
                .antMatchers("/taxi/**/chatting/**").hasAnyAuthority("ADMIN", "GROUP_ADMIN", "GROUP_MEMBER")
                .antMatchers(HttpMethod.DELETE, "/taxi/**").hasAnyAuthority("ADMIN", "GROUP_ADMIN", "GROUP_MEMBER")
                .antMatchers("/member/**").hasAnyAuthority("GUEST", "MEMBER", "ADMIN")
                .antMatchers("/taxi/**").hasAnyAuthority("MEMBER", "ADMIN", "GROUP_ADMIN", "GROUP_MEMBER")
                .and().build();
    }

}
