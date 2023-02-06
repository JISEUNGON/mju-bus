package com.mjubus.server.controller;

import com.mjubus.server.service.chatting.RedisMessageSubService;
import com.mjubus.server.service.chatting.RedisMessageSubServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Slf4j
@Controller
public class ChattingUnsubEventController implements ApplicationListener<SessionUnsubscribeEvent> {
    private RedisMessageSubService redisMessageSubService;

    public ChattingUnsubEventController(RedisMessageSubService redisMessageSubService) {
        this.redisMessageSubService = redisMessageSubService;
    }

    @Override
    public void onApplicationEvent(SessionUnsubscribeEvent event) {
        redisMessageSubService.updateSessionHash(event);
    }
}
