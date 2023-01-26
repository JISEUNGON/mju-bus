package com.mjubus.server.vo;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class MessageLog implements Serializable {
    private String time;
    private String sender;
    private String message;
}
