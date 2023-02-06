package com.mjubus.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Slf4j
@Controller
public class ChattingUnsubEventController implements ApplicationListener<SessionUnsubscribeEvent> {
    @Override
    public void onApplicationEvent(SessionUnsubscribeEvent event) {
        log.info("[unsubscribe] | command: " + event.getMessage().getHeaders().get("stompCommand"));
    }
}
