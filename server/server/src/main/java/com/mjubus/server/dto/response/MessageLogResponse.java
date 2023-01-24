package com.mjubus.server.dto.response;

import com.mjubus.server.vo.MessageLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageLogResponse {
    @ApiModelProperty(value = "채팅 저장 시간")
    private String time;

    @ApiModelProperty(value = "메시지를 보낸 사람")
    private String sender;

    @ApiModelProperty(value = "채팅 메시지")
    private String message;

    public void setMessageLogVo(MessageLog messageLogVo) {
        this.time = messageLogVo.getTime();
        this.sender = messageLogVo.getSender();
        this.message = messageLogVo.getMessage();
    }
}
