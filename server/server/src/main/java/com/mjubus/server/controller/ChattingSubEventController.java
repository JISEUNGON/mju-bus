package com.mjubus.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Slf4j
@Controller
public class ChattingSubEventController implements ApplicationListener<SessionSubscribeEvent> {
    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        log.info("[subscribe] | command: " + event.getMessage().getHeaders().get("stompCommand"));
    }
}
