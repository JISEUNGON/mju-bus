package com.mjubus.server.service.authentication.mju;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.request.MjuAuthInfoRequest;
import com.mjubus.server.dto.request.MjuAuthRequest;
import com.mjubus.server.dto.response.MjuAuthInfoResponse;
import com.mjubus.server.enums.MemberRole;
import com.mjubus.server.exception.auth.MjuUserNotFoundException;
import com.mjubus.server.exception.auth.RoleUpdateTargetMisMatchException;
import com.mjubus.server.exception.member.MemberNotFoundException;
import com.mjubus.server.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class MjuAuthServiceImpl implements MjuAuthService {
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private MemberRepository memberRepository;

    @Autowired
    public MjuAuthServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper, MemberRepository memberRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.memberRepository = memberRepository;
    }

    @Override
    public MjuAuthInfoResponse getAuthInfo(MjuAuthInfoRequest request) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("nm", request.getName());
        map.add("birthday", request.getBirthday());

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
        headers.add("Referer", "https://sso1.mju.ac.kr/login.do");
        HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://sso1.mju.ac.kr/mju/findId.do", httpRequest, String.class);

        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            if (!jsonNode.get("error").toString().replace("\"", "").equals("0000")) {
                throw new MjuUserNotFoundException("no mju user found");
            }
            return MjuAuthInfoResponse.builder().isMjuUser("yes").build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public String changeUserRole(MjuAuthInfoResponse response) {
        if (response.getIsMjuUser().equals("yes")) {
            // TODO: User role change event
            return "success";
        }
        return null;
    }

    @Transactional
    @Override
    public String changeUserRoleWithId(MjuAuthRequest mjuAuthRequest, MjuAuthInfoResponse authInfoResponse) {
        if (authInfoResponse.getIsMjuUser().equals("yes")) {
            Optional<Member> findResult = memberRepository.findById(mjuAuthRequest.getId());
            Member targetMember = findResult.orElseThrow(() -> new MemberNotFoundException("해당 아이디를 가진 사용자가 존재하지 않습니다."));

            if (targetMember.getRole() != MemberRole.GUEST) {
                throw new RoleUpdateTargetMisMatchException("해당 사용자는 GUEST 권한이 아닙니다.");
            }
            targetMember.upgradeRoleFromGuestToUser();
            return "success";
        }
        return "fail";
    }
}
