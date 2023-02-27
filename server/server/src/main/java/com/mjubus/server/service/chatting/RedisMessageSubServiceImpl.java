package com.mjubus.server.service.chatting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjubus.server.dto.request.UpdateChattingSessionHashRequest;
import com.mjubus.server.exception.chatting.IllegalHeaderArgumentsException;
import com.mjubus.server.exception.chatting.RoomIdNotFoundExcption;
import com.mjubus.server.exception.chatting.SessionIdNotFoundExcption;
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
import java.util.regex.Pattern;

@Slf4j
@Service
public class RedisMessageSubServiceImpl implements RedisMessageSubService {

    private RedisTemplate<String, Object> redisTemplate;
    private SimpMessageSendingOperations simpMessageSendingOperations;
    private ObjectMapper objectMapper;

    public interface RedisHashFlag {
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
    public void updateSessionHash(SessionSubscribeEvent subscribeEvent) {
        MessageHeaders messageHeaders = subscribeEvent.getMessage().getHeaders();
        String simpSubscriptionId = (String) messageHeaders.get("simpSubscriptionId"); // sub-{memberId}

        String simpDestination = (String) messageHeaders.get("simpDestination"); // /sub/{roomId}

        if (!Pattern.matches("^(sub-)[0-9]+$", simpSubscriptionId)) {
            throw new IllegalHeaderArgumentsException("올바르지 않은 형식을 가진 STOMP header 값입니다. id: " + simpSubscriptionId);
        }
        String roomId = (simpDestination.split("/"))[2];
        String hashName = "room-" + roomId + "-subscription";

        redisTemplate.opsForHash().put(hashName, simpSubscriptionId, RedisHashFlag.ON);
    }

    @Override
    public void updateSessionHash(SessionUnsubscribeEvent unsubscribeEvent) {
        MessageHeaders messageHeaders = unsubscribeEvent.getMessage().getHeaders();

        String simpDestination = (String) messageHeaders.get("simpSubscriptionId"); // {roomId}/sub-{memberId}
        if (!Pattern.matches("^[0-9]*(\\/sub-)[0-9]+$", simpDestination)) {
            throw new IllegalHeaderArgumentsException("올바르지 않은 형식을 가진 STOMP header 값입니다. id: " + simpDestination);
        }


        String roomId = (simpDestination.split("/"))[0];
        String memberId = (((simpDestination.split("/"))[1]).split("-"))[1];
        String hashName = "room-" + roomId + "-subscription";

        redisTemplate.opsForHash().put(hashName, "sub-" + memberId, RedisHashFlag.OFF);
    }

    @Override
    public String updateSessionHash(UpdateChattingSessionHashRequest updateChattingSessionHashRequest) {
        String hashName = "room-" + updateChattingSessionHashRequest.getRoomId() + "-subscription";
        if (!redisTemplate.hasKey(hashName)) throw new RoomIdNotFoundExcption("해당하는 roomId가 존재하지 않습니다.");

        String simpSubscriptionId = "sub-" + updateChattingSessionHashRequest.getMemberId();
        if (updateChattingSessionHashRequest.isUpdateFlag()) {
            redisTemplate.opsForHash().put(hashName, simpSubscriptionId, RedisHashFlag.ON);
        } else {
            redisTemplate.opsForHash().put(hashName, simpSubscriptionId, RedisHashFlag.OFF);
        }

        return "success";
    }
}
