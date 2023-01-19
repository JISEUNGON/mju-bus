package com.mjubus.server.service.chatting;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public interface RedisMessageSubService extends MessageListener {
    @Override
    void onMessage(Message message, byte[] pattern);
}
