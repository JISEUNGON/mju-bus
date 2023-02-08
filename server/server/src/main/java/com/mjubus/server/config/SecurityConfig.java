package com.mjubus.server.config;

import com.mjubus.server.security.JwtMemberFilter;
import com.mjubus.server.security.JwtPartyFilter;
import com.mjubus.server.service.member.MemberService;
import com.mjubus.server.service.taxiParty.TaxiPartyService;
import com.mjubus.server.service.taxiPartyMembers.TaxiPartyMembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  // 해당 애노테이션을 붙인 필터(현재 클래스)를 스프링 필터체인에 등록.
@RequiredArgsConstructor
public class SecurityConfig {

    private MemberService memberService;

    private TaxiPartyMembersService taxiPartyMembersService;;

    @Value("${external.jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/member/**").authenticated()
                .and().addFilterBefore(new JwtMemberFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/taxi/**").authenticated().and().addFilterBefore(new JwtPartyFilter(taxiPartyMembersService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
