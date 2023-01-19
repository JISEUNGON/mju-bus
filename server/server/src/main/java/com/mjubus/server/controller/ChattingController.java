package com.mjubus.server.controller;

import com.mjubus.server.vo.ChattingMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChattingController {

    @MessageMapping("/chatting-service")
    public void getChattingMessage(ChattingMessage chattingMessage) {
        // TOOD: 메시지 처리 구현
    }

}
