package com.mjubus.server.service.login;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.login.KaKaoAuthTokenDto;
import com.mjubus.server.dto.request.KakaoLoginRequest;
import com.mjubus.server.dto.response.LoginResponse;
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

@Service
public class KakaoLoginService {

    private final MemberService memberService;

    @Value("${external.kakao.validate-uri}")
    private String validateUri;

    public KakaoLoginService(MemberService memberService) {
        this.memberService = memberService;
    }

    public LoginResponse login(KakaoLoginRequest kakaoLoginRequest) {
        try {
            // 1. Token 정보 검증 및 유효성 검사
            KaKaoAuthTokenDto kaKaoAuthTokenDto = validateToken(kakaoLoginRequest);
            if (kaKaoAuthTokenDto == null) {
                throw new IllegalArgumentException("Token 정보가 유효하지 않습니다.");
            }

            // 날짜 변환 및 값 주입
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime expiredAt = LocalDateTime.parse(kakaoLoginRequest.getRefreshTokenExpiresAt(), formatter);
            kaKaoAuthTokenDto.setRefreshToken(kakaoLoginRequest.getRefreshToken());
            kaKaoAuthTokenDto.setRefreshTokenExpiresAt(expiredAt);

            // 2. 유저 정보 조회 및 저장
            Member member = memberService.saveOrGetKakaoMember(kaKaoAuthTokenDto);
            return LoginResponse.of(member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("카카오 로그인에 실패하였습니다.");
        }

    }

    public KaKaoAuthTokenDto validateToken(KakaoLoginRequest kakaoLoginDto) {
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
}

