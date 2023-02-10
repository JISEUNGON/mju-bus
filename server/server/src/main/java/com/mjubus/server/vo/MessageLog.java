package com.mjubus.server.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class MessageLog implements Serializable {
    private String time;
    private String sender;
    private String message;
    private String memberId;

    public void decodeSenderAndMessage() {
        sender = new String(Base64.getDecoder().decode(sender.getBytes()));
        message = new String(Base64.getDecoder().decode(message.getBytes()));
    }
}
