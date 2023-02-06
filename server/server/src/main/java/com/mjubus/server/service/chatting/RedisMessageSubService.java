package com.mjubus.server.service.chatting;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

public interface RedisMessageSubService extends MessageListener {
    @Override
    void onMessage(Message message, byte[] pattern);
    public void sessionMatching(SessionSubscribeEvent subscribeEvent);
}
