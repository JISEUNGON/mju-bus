package com.mjubus.server.controller;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.response.MemberResponse;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@Api(tags = {"멤버 정보 조회 API"})
public class MemberController {

    @GetMapping("/")
    public ResponseEntity<MemberResponse> member(Authentication authentication) {
        return ResponseEntity.ok(
                MemberResponse.of((Member) authentication.getPrincipal())
        );
    }
}
