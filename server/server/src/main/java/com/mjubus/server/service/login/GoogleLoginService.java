package com.mjubus.server.service.login;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.login.GoogleAuthTokenDto;
import com.mjubus.server.dto.request.GoogleLoginRequest;
import com.mjubus.server.dto.response.LoginResponse;
import com.mjubus.server.service.member.MemberService;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class GoogleLoginService {
    private final MemberService memberService;
    @Value("${external.google.client-id}")
    private String clientId;

    @Value("${external.google.client-secret}")
    private String clientSecret;

    @Value("${external.google.token-uri}")
    private String tokenUri;

    public GoogleLoginService(MemberService memberService) {
        this.memberService = memberService;
    }

    public LoginResponse login(GoogleLoginRequest googleLoginRequest) {
        try {
            // 1. Token 정보 검증 및 유효성 검사
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    "https://oauth2.googleapis.com/token",
                    clientId,
                    clientSecret,
                    googleLoginRequest.getServerAuthCode(),
                    "").execute();

            if (tokenResponse.getRefreshToken() == null) {
                throw new RuntimeException("Google Token Response is null");
            }

            // 2. Token 정보를 통해 유저 정보 조회
            // Google 또한 refreshToken 만료일이 없다.
            GoogleAuthTokenDto googleAuthTokenDto = GoogleAuthTokenDto.builder()
                    .refreshToken(tokenResponse.getRefreshToken())
                    .userId(googleLoginRequest.getId())
                    .fcmToken(googleLoginRequest.getFcmToken())
                    .refreshTokenExpiresAt(DateHandler.getToday().plusYears(100))
                    .build();

            Member member = memberService.saveOrGetGoogleMember(googleAuthTokenDto);

            return LoginResponse.of(member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
