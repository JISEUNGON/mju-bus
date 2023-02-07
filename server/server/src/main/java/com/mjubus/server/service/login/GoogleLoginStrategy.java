package com.mjubus.server.service.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.login.GoogleAuthTokenDto;
import com.mjubus.server.dto.login.GoogleLoginDto;
import com.mjubus.server.dto.login.KakaoLoginDto;
import com.mjubus.server.dto.response.LoginResponse;
import com.mjubus.server.enums.LoginStrategyName;
import com.mjubus.server.service.member.MemberService;
import com.mjubus.server.service.member.MemberServiceImpl;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class GoogleLoginStrategy implements LoginStrategy {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MemberService memberService;
    @Value("${external.google.client-id}")
    private String clientId;

    @Value("${external.google.client-secret}")
    private String clientSecret;

    @Value("${external.google.token-uri}")
    private String tokenUri;

    public GoogleLoginStrategy(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    @Override
    public LoginResponse login(String encryptedData) {
        try {
            // 1. 클라이언트로부터 받은 기본 인증 정보 Base64 Decode
            GoogleLoginDto googleLoginDto = objectMapper.readValue(new String(Base64.getDecoder().decode(encryptedData)), GoogleLoginDto.class);

            // 2. Token 정보 검증 및 유효성 검사
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    "https://oauth2.googleapis.com/token",
                    clientId,
                    clientSecret,
                    googleLoginDto.getServerAuthCode(),
                    "").execute();

            if (tokenResponse.getRefreshToken() == null) {
                throw new RuntimeException("Google Token Response is null");
            }

            // 3. Token 정보를 통해 유저 정보 조회
            // Google 또한 refreshToken 만료일이 없다.
            GoogleAuthTokenDto googleAuthTokenDto = GoogleAuthTokenDto.builder()
                    .refreshToken(tokenResponse.getRefreshToken())
                    .userId(googleLoginDto.getId())
                    .refreshTokenExpiresAt(DateHandler.getToday().plusYears(100))
                    .build();

            Member member = memberService.saveOrGetGoogleMember(googleAuthTokenDto);

            return LoginResponse.of(member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoginStrategyName getStrategyName() {
        return LoginStrategyName.GOOGLE;
    }
}
