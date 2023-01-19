package com.mjubus.server.controller;

import com.mjubus.server.service.chatting.RedisMessagePubService;
import com.mjubus.server.service.chatting.RedisMessageSubService;
import com.mjubus.server.vo.ChattingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChattingController {

    private final RedisMessagePubService redisMessagePubService;
    private final RedisMessageSubService redisMessageSubService;

    @Autowired
    public ChattingController(RedisMessagePubService redisMessagePubService, RedisMessageSubService redisMessageSubService) {
        this.redisMessagePubService = redisMessagePubService;
        this.redisMessageSubService = redisMessageSubService;
    }

    @MessageMapping("/chatting-service")
    public void getChattingMessage(ChattingMessage chattingMessage) {
        ChannelTopic topic = new ChannelTopic(chattingMessage.getRoomId());
        redisMessagePubService.publish(topic, chattingMessage);
    }

}
