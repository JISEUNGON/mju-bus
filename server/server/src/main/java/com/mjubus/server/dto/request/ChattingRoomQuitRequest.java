package com.mjubus.server.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChattingRoomQuitRequest {
    private String sessionMatchingHashKey;
    public static ChattingRoomQuitRequest of(String sessionMatchingHashKey) {
        return new ChattingRoomQuitRequest(sessionMatchingHashKey);
    }
}
