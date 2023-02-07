package com.mjubus.server.controller;

import com.mjubus.server.service.chatting.RedisMessageSubService;
import com.mjubus.server.service.chatting.RedisMessageSubServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Slf4j
@Controller
public class ChattingSubEventController implements ApplicationListener<SessionSubscribeEvent> {

    private RedisMessageSubService redisMessageSubService;

    @Autowired
    public ChattingSubEventController(RedisMessageSubService redisMessageSubService) {
        this.redisMessageSubService = redisMessageSubService;
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        redisMessageSubService.sessionMatching(event);
        redisMessageSubService.updateSessionHash(event);
    }
}
