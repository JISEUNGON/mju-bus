package com.mjubus.server.service.chatting;

import com.mjubus.server.repository.ChattingRoomRepository;
import com.mjubus.server.vo.ChattingMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisMessagePubServiceImpl implements RedisMessagePubService {

    private RedisTemplate<String, Object> redisTemplate;
    private RedisMessageListenerContainer redisMessageListenerContainer;

    @Autowired
    private RedisMessageSubServiceImpl redisMessageSubService;

    @Autowired
    private ChattingRoomRepository chattingRoomRepository;

    @Autowired
    public RedisMessagePubServiceImpl(RedisTemplate<String, Object> redisTemplate, RedisMessageListenerContainer redisMessageListenerContainer) {
        this.redisTemplate = redisTemplate;
        this.redisMessageListenerContainer = redisMessageListenerContainer;
    }

    @Override
    public void publish(ChannelTopic topic, ChattingMessage chattingMessage) {
        chattingRoomRepository.put(chattingMessage.getRoomId(), topic);
        redisMessageListenerContainer.addMessageListener(redisMessageSubService, topic);
        redisTemplate.convertAndSend(topic.getTopic(), chattingMessage);
    }
}
