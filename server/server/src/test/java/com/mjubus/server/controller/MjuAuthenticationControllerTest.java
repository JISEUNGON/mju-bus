package com.mjubus.server.controller;

import com.mjubus.server.dto.request.MjuAuthInfoRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}