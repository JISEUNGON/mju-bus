package com.mjubus.server.eventHandler;

import com.mjubus.server.service.chatting.RedisMessageSubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Slf4j
@Component
public class ChattingEventHandler {

    private final RedisMessageSubService redisMessageSubService;

    public ChattingEventHandler(RedisMessageSubService redisMessageSubService) {
        this.redisMessageSubService = redisMessageSubService;
    }

    @EventListener(SessionSubscribeEvent.class)
    public void sessionSubscribedEventHandler(SessionSubscribeEvent subEvent) {
        redisMessageSubService.updateSessionHash(subEvent);

    }

    @EventListener(SessionUnsubscribeEvent.class)
    public void sessionUnSubscribedEventHandler(SessionUnsubscribeEvent unSubEvent) {
        redisMessageSubService.updateSessionHash(unSubEvent);
    }
}
