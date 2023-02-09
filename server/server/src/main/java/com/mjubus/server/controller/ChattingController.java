package com.mjubus.server.controller;

import com.mjubus.server.dto.request.MessageLogRequest;
import com.mjubus.server.dto.request.UpdateChattingSessionHashRequest;
import com.mjubus.server.dto.response.MessageLogResponse;
import com.mjubus.server.service.chatting.RedisMessageLogService;
import com.mjubus.server.service.chatting.RedisMessagePubService;
import com.mjubus.server.service.chatting.RedisMessageSubService;
import com.mjubus.server.vo.ChattingMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chatting")
@Api(tags = {"채팅 WebSocket API"})
public class ChattingController {

    private final RedisMessagePubService redisMessagePubService;
    @Autowired
    public ChattingController(RedisMessagePubService redisMessagePubService) {
        this.redisMessagePubService = redisMessagePubService;
    }
    @MessageMapping("/chatting-service")
    public void getChattingMessage(ChattingMessage chattingMessage) {
        chattingMessage.encodeSenderAndMessage();
        redisMessagePubService.publish(chattingMessage);
        redisMessagePubService.publishForFCM(chattingMessage);
        redisMessagePubService.saveMessage(chattingMessage);
        redisMessagePubService.saveSessionHash(chattingMessage);
    }

}
