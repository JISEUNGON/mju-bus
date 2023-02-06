package com.mjubus.server.service.chatting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjubus.server.vo.ChattingMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class RedisMessageSubServiceImpl implements RedisMessageSubService {

    private RedisTemplate<String, Object> redisTemplate;
    private SimpMessageSendingOperations simpMessageSendingOperations;
    private ObjectMapper objectMapper;

    private interface RedisHashFlag {
        String ON = "true";
        String OFF = "false";
    }

    @Autowired
    public RedisMessageSubServiceImpl(RedisTemplate<String, Object> redisTemplate, SimpMessageSendingOperations simpMessageSendingOperations, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());

        try {
            ChattingMessage chattingMessage = objectMapper.readValue(publishMessage, ChattingMessage.class);
            simpMessageSendingOperations.convertAndSend("/sub/" + chattingMessage.getRoomId(), chattingMessage);
        } catch (JsonProcessingException e) {
            log.error("[Redis Pub/Sub] | Json Convert Error. publishMessage: [" + publishMessage + "]");
            log.error(e.getMessage());
        }
    }

    @Override
    public void sessionMatching(SessionSubscribeEvent subscribeEvent) {
        MessageHeaders messageHeaders = subscribeEvent.getMessage().getHeaders();
        String simpSubscriptionId = (String) messageHeaders.get("simpSubscriptionId");
        String simpSessionId = (String) messageHeaders.get("simpSessionId");
        redisTemplate.opsForHash().putIfAbsent("session-matching", simpSubscriptionId, simpSessionId);
    }

    @Override
    public void saveSessionHash(SessionSubscribeEvent subscribeEvent) {
        MessageHeaders messageHeaders = subscribeEvent.getMessage().getHeaders();
        String simpSessionId = (String) messageHeaders.get("simpSessionId");

        String simpDestination = (String) messageHeaders.get("simpDestination");
        String hashName = "room-" + (simpDestination.split("/"))[2] + "-subscription";

        redisTemplate.opsForHash().put(hashName, simpSessionId, RedisHashFlag.ON);
    }

    @Override
    public void updateSessionHash(SessionUnsubscribeEvent unsubscribeEvent) {
        MessageHeaders messageHeaders = unsubscribeEvent.getMessage().getHeaders();
        String simpSessionId = (String) messageHeaders.get("simpSessionId");
        log.info("[EVENT] | message: " + unsubscribeEvent.getMessage());
        //sub dest를 id로 받아와 판단
        String simpDestination = (String) messageHeaders.get("simpSubscriptionId");
        String hashName = "room-" + (simpDestination.split("/"))[2] + "-subscription";

        redisTemplate.opsForHash().put(hashName, simpSessionId, RedisHashFlag.OFF);
    }
}
