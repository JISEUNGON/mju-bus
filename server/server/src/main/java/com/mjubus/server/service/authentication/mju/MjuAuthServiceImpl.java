package com.mjubus.server.service.authentication.mju;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjubus.server.dto.request.MjuAuthInfoRequest;
import com.mjubus.server.dto.response.MjuAuthInfoResponse;
import com.mjubus.server.exception.auth.MjuUserNotFoundException;
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

@Slf4j
@Service
public class MjuAuthServiceImpl implements MjuAuthService {
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public MjuAuthServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    @Override
    public MjuAuthInfoResponse getAuthInfo(MjuAuthInfoRequest request) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("nm", request.getName());
        map.add("birthday", request.getBirthday());
        HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<>(map, new HttpHeaders());
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
}
