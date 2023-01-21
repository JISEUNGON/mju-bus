package com.mjubus.server.service.chatting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjubus.server.vo.ChattingMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisMessageSubServiceImpl implements RedisMessageSubService {

    private RedisTemplate<String, Object> redisTemplate;
    private SimpMessageSendingOperations simpMessageSendingOperations;
    private ObjectMapper objectMapper;

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
}
