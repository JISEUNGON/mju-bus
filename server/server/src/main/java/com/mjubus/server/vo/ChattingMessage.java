package com.mjubus.server.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Base64;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ChattingMessage implements Serializable {
    private String roomId;
    private String sender;
    private String message;
    private String imgUrl;
    private String memberId;

    public void encodeSenderAndMessage() {
        sender = Base64.getEncoder().encodeToString(sender.getBytes());
        message = Base64.getEncoder().encodeToString(message.getBytes());
    }
}
