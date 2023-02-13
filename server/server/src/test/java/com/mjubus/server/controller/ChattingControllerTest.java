package com.mjubus.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjubus.server.service.chatting.RedisMessagePubService;
import com.mjubus.server.vo.ChattingMessage;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class ChattingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisMessagePubService redisMessagePubService;

    @Test
    @DisplayName("[API][/chatting/{roomId}] 채팅 기록 조회 - 실패")
    public void 테스트_채팅방_없음() throws Exception {
        mockMvc.perform(get("/chatting/testRoom"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName("[API][/chatting/{roomId}] 채팅 기록 조회 - 성공")
    public void 테스트_채팅방_조회성공() throws Exception {
        //given
        String channel = "testRoom";
        String requestUrl = "/chatting/history/testRoom";

        List<ChattingMessage> chattingMessages = new ArrayList<>();
        //chattingMessages.add(new ChattingMessage(channel, "gildong", "first message"));
        //chattingMessages.add(new ChattingMessage(channel, "chulsu", "second message"));

        chattingMessages.forEach(message -> {
            redisMessagePubService.saveMessage(message);
        });

        //when then
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].sender", is("gildong")))
                .andExpect(jsonPath("$.[0].message", is("first message")))
                .andExpect(jsonPath("$.[1].sender", is("chulsu")))
                .andExpect(jsonPath("$.[1].message", is("second message")))
                .andReturn();
    }
}