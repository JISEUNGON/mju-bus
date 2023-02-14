package com.mjubus.server.controller;

import com.mjubus.server.service.chatting.*;
import com.mjubus.server.vo.ChattingMessage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chatting")
@Api(tags = {"채팅 WebSocket API"})
public class ChattingController {

    private final RedisMessagePubService redisMessagePubService;
    private final DynamoDbMessageService dynamoDbMessageService;

    @Autowired
    public ChattingController(RedisMessagePubService redisMessagePubService, DynamoDbMessageService dynamoDbMessageService) {
        this.redisMessagePubService = redisMessagePubService;
        this.dynamoDbMessageService = dynamoDbMessageService;
    }

    @MessageMapping("/chatting-service")
    public void getChattingMessage(ChattingMessage chattingMessage) {
        redisMessagePubService.publish(chattingMessage);
        redisMessagePubService.publishForFCM(chattingMessage);
        dynamoDbMessageService.saveMessage(chattingMessage);
        redisMessagePubService.saveSessionHash(chattingMessage);
    }

}
