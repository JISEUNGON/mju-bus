package com.mjubus.server.service.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.login.KaKaoAuthTokenDto;
import com.mjubus.server.dto.login.KakaoLoginDto;
import com.mjubus.server.dto.response.LoginResponse;
import com.mjubus.server.enums.LoginStrategyName;
import com.mjubus.server.service.member.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class KakaoLoginStrategy implements LoginStrategy {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MemberService memberService;

    @Value("${external.kakao.validate-uri}")
    private String validateUri;

    public KakaoLoginStrategy(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public LoginResponse login(String encryptedData) {
        try {
            // 1. 클라이언트로부터 받은 기본 인증 정보 Base64 Decode
            KakaoLoginDto kakaoLoginDto = objectMapper.readValue(new String(Base64.getDecoder().decode(encryptedData)), KakaoLoginDto.class);

            // 2. Token 정보 검증 및 유효성 검사
            KaKaoAuthTokenDto kaKaoAuthTokenDto = validateToken(kakaoLoginDto);
            if (kaKaoAuthTokenDto == null) {
                throw new IllegalArgumentException("Token 정보가 유효하지 않습니다.");
            }

            // 날짜 변환 및 값 주입
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime expiredAt = LocalDateTime.parse(kakaoLoginDto.getRefreshTokenExpiresAt(), formatter);
            kaKaoAuthTokenDto.setRefreshToken(kakaoLoginDto.getRefreshToken());
            kaKaoAuthTokenDto.setRefreshTokenExpiresAt(expiredAt);

            // 3. 유저 정보 조회 및 저장
            Member member = memberService.saveOrGetKakaoMember(kaKaoAuthTokenDto);
            return LoginResponse.of(member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("카카오 로그인에 실패하였습니다.");
        }

    }

    public KaKaoAuthTokenDto validateToken(KakaoLoginDto kakaoLoginDto) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoLoginDto.getAccessToken());
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KaKaoAuthTokenDto> response = restTemplate.exchange(validateUri, HttpMethod.GET, httpEntity, KaKaoAuthTokenDto.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("Apple Auth Token Error");
        }
    }

    @Override
    public LoginStrategyName getStrategyName() {
        return LoginStrategyName.KAKAO;
    }
}

