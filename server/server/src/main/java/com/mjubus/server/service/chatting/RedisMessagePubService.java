package com.mjubus.server.service.chatting;

import com.mjubus.server.vo.ChattingMessage;
import org.springframework.data.redis.listener.ChannelTopic;

public interface RedisMessagePubService {
    public void publish(ChattingMessage chattingMessage);
}
