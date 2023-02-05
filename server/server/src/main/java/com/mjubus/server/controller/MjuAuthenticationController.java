package com.mjubus.server.controller;

import com.mjubus.server.dto.request.MjuAuthInfoRequest;
import com.mjubus.server.dto.request.MjuAuthRequest;
import com.mjubus.server.dto.response.MjuAuthInfoResponse;
import com.mjubus.server.service.authentication.mju.MjuAuthService;
import com.mjubus.server.service.authentication.mju.MjuAuthServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/mju")
@Api(tags = {"명지대학교 학생 여부 인증 api"})
public class MjuAuthenticationController {

    private MjuAuthService mjuAuthService;

    @Autowired
    public MjuAuthenticationController(MjuAuthServiceImpl mjuAuthService) {
        this.mjuAuthService = mjuAuthService;
    }

    @GetMapping("/find-info")
    @ApiOperation("명지대학교 SSO 아이디 찾기 호출 결과를 받아온다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 사용자가 없음")
    })
    public ResponseEntity<MjuAuthInfoResponse> findAuthInfo(MjuAuthInfoRequest request) {
        return ResponseEntity.ok(mjuAuthService.getAuthInfo(request));
    }

    @PostMapping("/change-auth-status/users/{userId}")
    @ApiOperation("명지대학교 학생이면 사용자의 ROLE을 변경하고, 아니면 변경하지 않는다. 변경 여부를 받아온다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "400", description = "해당 사용자가 GUEST 권한이 아님"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없거나 해당 정보를 가진 명지대학교 학생이 없어 변경하지 않음")
    })
    public ResponseEntity<String> findAndRoleChange(@PathVariable(value = "userId") MjuAuthRequest authRequest, @RequestBody MjuAuthInfoRequest request) {
        MjuAuthInfoResponse authInfoResponse = mjuAuthService.getAuthInfo(request);
        return ResponseEntity.ok(mjuAuthService.changeUserRoleWithId(authRequest, authInfoResponse));
    }
}
