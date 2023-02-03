package com.mjubus.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.request.MjuAuthInfoRequest;
import com.mjubus.server.enums.MemberRole;
import com.mjubus.server.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class MjuAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("[API][/auth/mju/find-info] 명지대학생인지 조회 - 성공")
    public void 테스트_명지대학생인지_조회_성공() throws Exception {
        String birthday = "991020";
        String name = "박재한";

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("birthday", birthday);
        request.add("name", name);
        mockMvc.perform(get("/auth/mju/find-info").params(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isMjuUser", is("yes")));
    }

    @Test
    @DisplayName("[API][/auth/mju/find-info] 명지대학생인지 조회 - 실패")
    public void 테스트_명지대학생인지_조회_실패() throws Exception {
        String birthday = "991099";
        String name = "박재한";

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("birthday", birthday);
        request.add("name", name);
        mockMvc.perform(get("/auth/mju/find-info").params(request))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName("[API][/auth/mju/change-auth-status/users/{userId}] 명지대학생 아닌 사람 ROLE업데이트 - 실패")
    public void 테스트_명지대학생_아닌_사람_ROLE_업데이트_실패() throws Exception {
        String birthday = "991029";
        String name = "박재한";
        int testId = 4;

        String url = "/auth/mju/change-auth-status/users/" + testId;

        MjuAuthInfoRequest mjuAuthInfoRequest = MjuAuthInfoRequest.of(name, birthday);
        String content = objectMapper.writeValueAsString(mjuAuthInfoRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        mockMvc.perform(post(url).content(content).headers(headers))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("[API][/auth/mju/change-auth-status/users/{userId}] 이미 USER인 사람 ROLE업데이트 - 실패")
    public void 테스트_이미_ROLE_USER_인_사람_업데이트_실패() throws Exception {
        String birthday = "991020";
        String name = "박재한";
        int testId = 5;

        String url = "/auth/mju/change-auth-status/users/" + testId;

        MjuAuthInfoRequest mjuAuthInfoRequest = MjuAuthInfoRequest.of(name, birthday);
        String content = objectMapper.writeValueAsString(mjuAuthInfoRequest);

        Optional<Member> findResult = memberRepository.findById(Long.valueOf(testId));
        assertNotEquals(findResult.get().getRole(), MemberRole.GUEST);
        assertEquals(findResult.get().getRole(), MemberRole.USER);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        mockMvc.perform(post(url).content(content).headers(headers))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Transactional
    @Test
    @DisplayName("[API][/auth/mju/change-auth-status/users/{userId}] ROLE업데이트 - 성공")
    public void 테스트_명지대학생인_사람_ROLE_업데이트_성공() throws Exception {
        String birthday = "991020";
        String name = "박재한";
        int testId = 4;

        String url = "/auth/mju/change-auth-status/users/" + testId;

        MjuAuthInfoRequest mjuAuthInfoRequest = MjuAuthInfoRequest.of(name, birthday);
        String content = objectMapper.writeValueAsString(mjuAuthInfoRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        mockMvc.perform(post(url).content(content).headers(headers))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Optional<Member> findResult = memberRepository.findById(Long.valueOf(testId));
        assertNotEquals(findResult.get().getRole(), MemberRole.GUEST);
        assertEquals(findResult.get().getRole(), MemberRole.USER);
    }


}