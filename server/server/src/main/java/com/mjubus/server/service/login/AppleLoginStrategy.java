package com.mjubus.server.service.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.login.AppleAuthTokenDto;
import com.mjubus.server.dto.response.LoginResponse;
import com.mjubus.server.enums.LoginStrategyName;
import com.mjubus.server.enums.MemberRole;
import com.mjubus.server.service.member.MemberService;
import com.mjubus.server.util.DateHandler;
import com.mjubus.server.dto.login.AppleLoginDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.*;

@Component
public class AppleLoginStrategy implements LoginStrategy {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${external.apple.client-id}")
    private String clientId;

    @Value("${external.apple.token-uri}")
    private String tokenUri;

    @Value("${external.apple.bundle-id}")
    private String bundleId;

    @Value("${external.apple.audience-uri}")
    private String audienceUri;

    @Value("${external.apple.team-id}")
    private String teamId;

    @Value("${external.apple.private-key}")
    private String appleSignKey;

    private MemberService memberService;

    public AppleLoginStrategy(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public LoginResponse login(String encryptedData) {
       try {
           // 1. 클라이언트로부터 받은 기본 인증 정보 Base64 Decode
           AppleLoginDto appleLoginDto = objectMapper.readValue(new String(Base64.getDecoder().decode(encryptedData), StandardCharsets.UTF_8), AppleLoginDto.class);

           // 2. 기본 정보로 JWT 생성 및 refreshToken 발급
           AppleAuthTokenDto appleAuthTokenDto = getAppleAuthTokenDto(appleLoginDto);
           appleAuthTokenDto.setUser_id(appleLoginDto.getUser());

           // 3. 멤버 조회 및 저장
           Member member = memberService.saveOrGetAppleMember(appleAuthTokenDto);

           return LoginResponse.of(member);
       } catch (Exception e) {
           e.printStackTrace();
          throw new RuntimeException("애플 로그인 실패");
       }
    }

    @Override
    public LoginStrategyName getStrategyName() {
        return LoginStrategyName.APPLE;
    }

    public String createJwt() throws IOException {
        Date expirationDate = DateHandler.toDate(DateHandler.getToday().plusDays(30));
        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("kid", clientId);
        jwtHeader.put("alg", "ES256");

        return Jwts.builder()
                .setHeaderParams(jwtHeader)
                .setIssuer(teamId)
                .setIssuedAt(DateHandler.toDate(DateHandler.getToday())) // 발행 시간 - UNIX 시간
                .setExpiration(expirationDate) // 만료 시간
                .setAudience(audienceUri)
                .setSubject(bundleId)
                .signWith(SignatureAlgorithm.ES256, getPrivateKey())
                .compact();
    }

    public PrivateKey getPrivateKey() throws IOException {
        // PRIVATE_KEY 렌더링
        appleSignKey = appleSignKey.replace(' ', '\n');
        String SSH_SUFFIX = "\n-----END PRIVATE KEY-----";
        String SSH_PREFIX = "-----BEGIN PRIVATE KEY-----\n";
        appleSignKey = SSH_PREFIX + appleSignKey + SSH_SUFFIX;

        Reader pemReader = new StringReader(appleSignKey);
        PEMParser pemParser = new PEMParser(pemReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
        return converter.getPrivateKey(object);
    }

    public AppleAuthTokenDto getAppleAuthTokenDto(AppleLoginDto appleLoginDto) throws IOException {
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", appleLoginDto.getAuthorizationCode());
        params.add("client_id", bundleId);
        params.add("client_secret", createJwt());
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<AppleAuthTokenDto> response = restTemplate.postForEntity(tokenUri, httpEntity, AppleAuthTokenDto.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("Apple Auth Token Error");
        }
    }
}

