package com.mjubus.server.controller;

import com.mjubus.server.service.chatting.RedisMessagePubService;
import com.mjubus.server.vo.ChattingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChattingController {

    private final RedisMessagePubService redisMessagePubService;

    @Autowired
    public ChattingController(RedisMessagePubService redisMessagePubService) {
        this.redisMessagePubService = redisMessagePubService;
    }

    @MessageMapping("/chatting-service")
    public void getChattingMessage(ChattingMessage chattingMessage) {
        redisMessagePubService.publish(chattingMessage);
    }

}
