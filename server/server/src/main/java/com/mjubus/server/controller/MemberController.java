package com.mjubus.server.controller;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.member.MemberPrincipalDto;
import com.mjubus.server.dto.request.JwtResponse;
import com.mjubus.server.dto.request.MjuAuthInfoRequest;
import com.mjubus.server.dto.request.MjuAuthRequest;
import com.mjubus.server.dto.response.MemberResponse;
import com.mjubus.server.dto.response.MjuAuthInfoResponse;
import com.mjubus.server.service.member.MemberService;
import com.nimbusds.oauth2.sdk.TokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/member")
@Api(tags = {"멤버 정보 조회 API"})
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/")
    @ApiOperation("사용자의 정보를 받아온다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<MemberResponse> member(@ApiIgnore Authentication authentication) {
        return ResponseEntity.ok(
                memberService.findMemberByMemberPrincipal((MemberPrincipalDto) authentication.getPrincipal())
        );
    }

    @PostMapping("/auth/mju")
    @ApiOperation("명지대학교 학생이면 사용자의 ROLE을 변경하고, 아니면 변경하지 않는다. 변경 여부를 받아온다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "400", description = "해당 사용자가 GUEST 권한이 아님"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없거나 해당 정보를 가진 명지대학교 학생이 없어 변경하지 않음")
    })
    public ResponseEntity<MemberResponse> findAndRoleChange(@ApiIgnore Authentication authentication, @RequestBody MjuAuthInfoRequest request) {
        return ResponseEntity.ok(
                memberService.authMjuStudent((MemberPrincipalDto) authentication.getPrincipal(), request)
        );
    }

    @PostMapping("/token")
    @ApiOperation("토큰을 재발급 받는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "400", description = "토큰이 만료되지 않았거나, 잘못된 토큰임"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음")
    })
    public ResponseEntity<JwtResponse> generateToken(@ApiIgnore Authentication authentication, @RequestBody String refresh_token) {
        return ResponseEntity.ok(
                memberService.generateToken((MemberPrincipalDto) authentication.getPrincipal(), refresh_token)
        );
    }
}
