package com.mjubus.server.controller;

import com.mjubus.server.dto.request.MessageLogRequest;
import com.mjubus.server.dto.response.MessageLogResponse;
import com.mjubus.server.service.chatting.RedisMessageLogService;
import com.mjubus.server.service.chatting.RedisMessagePubService;
import com.mjubus.server.vo.ChattingMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/chatting")
@Api(tags = {"채팅 기록 조회 api"})
public class ChattingController {

    private final RedisMessagePubService redisMessagePubService;
    private final RedisMessageLogService redisMessageLogService;

    @Autowired
    public ChattingController(RedisMessagePubService redisMessagePubService, RedisMessageLogService redisMessageLogService) {
        this.redisMessagePubService = redisMessagePubService;
        this.redisMessageLogService = redisMessageLogService;
    }

    @MessageMapping("/chatting-service")
    public void getChattingMessage(ChattingMessage chattingMessage) {
        chattingMessage.encodeSenderAndMessage();
        redisMessagePubService.saveMessage(chattingMessage);
        redisMessagePubService.publish(chattingMessage);
    }

    @GetMapping("/history/{roomId}")
    @ApiOperation(value = "채팅 기록을 얻는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "Room ID가 정상적이지 않은 경우")
    })
    @ResponseBody
    public ResponseEntity<List<MessageLogResponse>> findMessageHistory(@PathVariable(value = "roomId") MessageLogRequest req) {
        return ResponseEntity.ok(redisMessageLogService.findMessageLog(req));
    }
}
