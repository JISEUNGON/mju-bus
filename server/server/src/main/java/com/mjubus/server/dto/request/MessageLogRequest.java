package com.mjubus.server.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageLogRequest {
    private String roomId;

    public static MessageLogRequest of(String roomId) {
        return new MessageLogRequest(roomId);
    }
}
