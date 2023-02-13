package com.mjubus.server.controller;

import com.mjubus.server.dto.request.AppleLoginRequest;
import com.mjubus.server.dto.request.GoogleLoginRequest;
import com.mjubus.server.dto.request.KakaoLoginRequest;
import com.mjubus.server.dto.response.LoginResponse;
import com.mjubus.server.service.login.AppleLoginService;
import com.mjubus.server.service.login.GoogleLoginService;
import com.mjubus.server.service.login.GuestLoginService;
import com.mjubus.server.service.login.KakaoLoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
@Api(tags = {"로그인 API"})
public class LoginController {

    private final KakaoLoginService kakaoLoginService;
    private final GoogleLoginService googleLoginService;
    private final AppleLoginService appleLoginService;
    private final GuestLoginService guestLoginService;

    @Autowired
    public LoginController(KakaoLoginService kakaoLoginService, GoogleLoginService googleLoginService, AppleLoginService appleLoginService, GuestLoginService guestLoginService) {
        this.kakaoLoginService = kakaoLoginService;
        this.googleLoginService = googleLoginService;
        this.appleLoginService = appleLoginService;
        this.guestLoginService = guestLoginService;
    }

    @PostMapping("/")
    public ResponseEntity<LoginResponse> guestLogin() {
        return ResponseEntity.ok(
                guestLoginService.login()
        );
    }

    @PostMapping("/apple")
    public ResponseEntity<LoginResponse> appleLogin(@RequestBody AppleLoginRequest appleLoginRequest) {
        return ResponseEntity.ok(
                appleLoginService.login(appleLoginRequest)
        );
    }

    @PostMapping("/google")
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody GoogleLoginRequest googleLoginRequest) {
        return ResponseEntity.ok(
                googleLoginService.login(googleLoginRequest)
        );
    }

    @PostMapping("/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestBody KakaoLoginRequest kakaoLoginRequest) {
        return ResponseEntity.ok(
                kakaoLoginService.login(kakaoLoginRequest)
        );
    }
}