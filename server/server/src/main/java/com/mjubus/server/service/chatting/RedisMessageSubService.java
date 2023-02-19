package com.mjubus.server.service.chatting;

import com.mjubus.server.dto.request.UpdateChattingSessionHashRequest;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

public interface RedisMessageSubService extends MessageListener {
    @Override
    void onMessage(Message message, byte[] pattern);
    public void updateSessionHash(SessionSubscribeEvent subscribeEvent);
    public void updateSessionHash(SessionUnsubscribeEvent unsubscribeEvent);
    public String updateSessionHash(UpdateChattingSessionHashRequest updateChattingSessionHashRequest);
}
