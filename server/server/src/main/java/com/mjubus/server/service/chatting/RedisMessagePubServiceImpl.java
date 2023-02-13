package com.mjubus.server.service.chatting;

import com.mjubus.server.repository.ChattingRoomRepository;
import com.mjubus.server.vo.ChattingMessage;
import com.mjubus.server.vo.MessageLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Service
public class RedisMessagePubServiceImpl implements RedisMessagePubService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisMessageSubService redisMessageSubService;
    private final ChattingRoomRepository chattingRoomRepository;


    @Autowired
    public RedisMessagePubServiceImpl(RedisTemplate<String, Object> redisTemplate, RedisMessageListenerContainer redisMessageListenerContainer, RedisMessageSubService redisMessageSubService, ChattingRoomRepository chattingRoomRepository) {
        this.redisTemplate = redisTemplate;
        this.redisMessageListenerContainer = redisMessageListenerContainer;
        this.redisMessageSubService = redisMessageSubService;
        this.chattingRoomRepository = chattingRoomRepository;
    }

    @Override
    public void publish(ChattingMessage chattingMessage) {
        ChannelTopic topic = new ChannelTopic(chattingMessage.getRoomId());
        chattingRoomRepository.newChattingRoom(chattingMessage.getRoomId(), topic);
        redisMessageListenerContainer.addMessageListener(redisMessageSubService, topic);
        redisTemplate.convertAndSend(topic.getTopic(), chattingMessage);
    }

    @Override
    public void publishForFCM(ChattingMessage chattingMessage) {
        String hashName = "room-" + chattingMessage.getRoomId() + "-subscription";
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(hashName);
        entries.forEach((key, value) -> {
            String flag = (String) value;
            String session = (String) key;
            if (flag.equals(RedisMessageSubServiceImpl.RedisHashFlag.OFF)) {
                log.info("session " + session  +": false");
                //TODO: FCM Actions
            }
        });
    }

    @Override
    public void saveMessage(ChattingMessage chattingMessage) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));

        MessageLog messageLog = MessageLog.builder()
                .time(dateTime)
                .sender(chattingMessage.getSender())
                .message(chattingMessage.getMessage())
                .memberId(chattingMessage.getMemberId())
                .build();

        redisTemplate.opsForList().rightPush(chattingMessage.getRoomId(), messageLog);
    }

    @Override
    public void saveSessionHash(ChattingMessage chattingMessage) {
        String hashName = "room-" + chattingMessage.getRoomId() + "-subscription";
        if (!redisTemplate.hasKey(hashName)) {
            redisTemplate.opsForHash().put(hashName, "init", "init");
        }
    }
}
