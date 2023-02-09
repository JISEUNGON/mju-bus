package com.mjubus.server.controller;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.request.JwtResponse;
import com.mjubus.server.dto.response.MemberResponse;
import com.mjubus.server.service.member.MemberService;
import com.nimbusds.oauth2.sdk.TokenResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@Api(tags = {"멤버 정보 조회 API"})
@RequiredArgsConstructor
public class MemberController {
    private MemberService memberService;
    @GetMapping("/")
    public ResponseEntity<MemberResponse> member(Authentication authentication) {
        return ResponseEntity.ok(
                MemberResponse.of((Member) authentication.getPrincipal())
        );
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> generateToken(Authentication authentication, @RequestBody String refresh_token) {
        return ResponseEntity.ok(
                memberService.generateToken((Member) authentication.getPrincipal(), refresh_token)
        );
    }
}
