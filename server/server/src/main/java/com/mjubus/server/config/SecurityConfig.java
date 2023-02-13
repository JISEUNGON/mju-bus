package com.mjubus.server.config;

import com.mjubus.server.service.member.MemberService;
import com.mjubus.server.service.taxiPartyMembers.TaxiPartyMembersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // 해당 애노테이션을 붙인 필터(현재 클래스)를 스프링 필터체인에 등록.
public class SecurityConfig {

    private MemberService memberService;

    private TaxiPartyMembersService taxiPartyMembersService;;

    @Value("${external.jwt.secret}")
    private String secretKey;

    public SecurityConfig(MemberService memberService, TaxiPartyMembersService taxiPartyMembersService) {
        this.memberService = memberService;
        this.taxiPartyMembersService = taxiPartyMembersService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .authorizeRequests().antMatchers("**").permitAll().and().build();
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .addFilterBefore(new JwtMemberFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/login/**").permitAll()
//                .antMatchers("/taxi/**/delete").hasAnyAuthority("ROLE_ADMIN", "GROUP_ADMIN")
//                .antMatchers("/taxi/**/chatting/**").hasAnyAuthority("ROLE_ADMIN", "GROUP_ADMIN", "GROUP_MEMBER")
//                .antMatchers(HttpMethod.DELETE, "/taxi/**").hasAnyAuthority("ROLE_ADMIN", "GROUP_ADMIN", "GROUP_MEMBER")
//                .antMatchers("/member/**", "/taxi/**").hasAnyAuthority("ROLE_MEMBER", "ROLE_ADMIN", "GROUP_ADMIN", "GROUP_MEMBER")
//                .and().build();
    }

}
