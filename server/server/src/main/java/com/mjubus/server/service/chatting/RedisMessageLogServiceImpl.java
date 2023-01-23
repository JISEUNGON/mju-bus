package com.mjubus.server.service.chatting;

import com.mjubus.server.exception.chatting.RoomIdNotFoundExcption;
import com.mjubus.server.vo.MessageLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RedisMessageLogServiceImpl implements RedisMessageLogService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisMessageLogServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<MessageLog> findMessageLog(final String roomId) {
        List<Object> list = redisTemplate.opsForList().range(roomId, 0, redisTemplate.opsForList().size(roomId));
        List<MessageLog> messageLogList = new ArrayList<>();

        if(list.size() == 0) {
            throw new RoomIdNotFoundExcption(roomId);
        }

        list.forEach(c -> {
            messageLogList.add((MessageLog) c);
        });

        return messageLogList;
    }
}
