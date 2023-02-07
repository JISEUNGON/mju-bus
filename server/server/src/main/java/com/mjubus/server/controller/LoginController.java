package com.mjubus.server.controller;

import com.mjubus.server.dto.response.LoginResponse;
import com.mjubus.server.service.login.AppleLoginStrategy;
import com.mjubus.server.service.login.KakaoLoginStrategy;
import com.mjubus.server.service.login.LoginStrategy;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
@Api(tags = {"로그인 API"})
public class LoginController {

    private final LoginStrategy appleLoginStrategy;
    private final LoginStrategy kakaoLoginStrategy;

    @Autowired
    public LoginController(AppleLoginStrategy appleLoginStrategy, KakaoLoginStrategy kakaoLoginStrategy) {
        this.appleLoginStrategy = appleLoginStrategy;
        this.kakaoLoginStrategy = kakaoLoginStrategy;
    }

    @GetMapping("/apple")
    public ResponseEntity<LoginResponse> appleLogin(@RequestParam String data) {
        return ResponseEntity.ok(
               appleLoginStrategy.login(data)
        );
    }

    @GetMapping("/google")
    public ResponseEntity<LoginResponse> googleLogin(@RequestParam String data) {
        return ResponseEntity.ok(
                kakaoLoginStrategy.login(data)
        );
    }

    @GetMapping("/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam String data) {
        return ResponseEntity.ok(
                kakaoLoginStrategy.login(data)
        );
    }
}