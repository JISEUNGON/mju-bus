package com.mjubus.server.vo;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ChattingMessage implements Serializable {
    private String roomId;
    private String sender;
    private String message;
}
