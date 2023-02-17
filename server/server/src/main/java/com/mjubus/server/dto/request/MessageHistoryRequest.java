package com.mjubus.server.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageHistoryRequest {
    private Long roomId;

    public static MessageHistoryRequest of(String roomId) {
        return MessageHistoryRequest.builder().roomId(Long.parseLong(roomId)).build();
    }
}
