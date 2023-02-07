package com.mjubus.server.service.chatting;

import com.mjubus.server.dto.request.ChattingRoomQuitRequest;
import com.mjubus.server.dto.request.TaxiPartyQuitRequest;
import com.mjubus.server.exception.chatting.RoomIdNotFoundExcption;
import com.mjubus.server.exception.chatting.SessionIdNotFoundExcption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class RedisMessageServiceImpl implements RedisMessageService {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisMessageServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    @Override
    public String chattingRoomQuit(TaxiPartyQuitRequest taxiPartyQuitRequest, ChattingRoomQuitRequest chattingRoomQuitRequest) {
        Optional<Object> sessionIdGet = Optional.ofNullable(redisTemplate.opsForHash().get("session-matching", chattingRoomQuitRequest.getSessionMatchingHashKey()));
        if (sessionIdGet.isEmpty()) throw new SessionIdNotFoundExcption("해당하는 hash key가 존재하지 않습니다.");
        String sessionId = (String) sessionIdGet.get();

        String hashName = "room-" + taxiPartyQuitRequest.getGroupId() + "-subscription";
        if (!redisTemplate.hasKey(hashName)) throw new RoomIdNotFoundExcption("해당하는 roomId가 존재하지 않습니다.");

        redisTemplate.opsForHash().delete(hashName, sessionId);
        redisTemplate.opsForHash().delete("session-matching", chattingRoomQuitRequest.getSessionMatchingHashKey());
        return "success";
    }
}
