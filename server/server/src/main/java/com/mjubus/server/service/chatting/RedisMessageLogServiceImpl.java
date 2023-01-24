package com.mjubus.server.service.chatting;

import com.mjubus.server.dto.request.MessageLogRequest;
import com.mjubus.server.dto.response.MessageLogResponse;
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
    public List<MessageLogResponse> findMessageLog(final MessageLogRequest messageLogRequest) {
        List<Object> list = redisTemplate.opsForList().range(messageLogRequest.getRoomId(), 0, redisTemplate.opsForList().size(messageLogRequest.getRoomId()));
        List<MessageLogResponse> messageLogList = new ArrayList<>();

        if(list.size() == 0) {
            throw new RoomIdNotFoundExcption(messageLogRequest.getRoomId());
        }

        list.forEach(objectTypeElement -> {
            MessageLog messageLogVo = (MessageLog) objectTypeElement;
            MessageLogResponse messageLogResponse = MessageLogResponse.builder().build();
            messageLogResponse.setMessageLogVo(messageLogVo);
            messageLogList.add(messageLogResponse);
        });

        return messageLogList;
    }
}
