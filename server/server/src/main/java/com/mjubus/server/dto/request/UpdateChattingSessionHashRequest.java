package com.mjubus.server.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateChattingSessionHashRequest {
    private String roomId;
    private String simpSubscriptionId;
    private boolean updateFlag;

    public static UpdateChattingSessionHashRequest of(String roomId, String simpSubscriptionId, boolean updateFlag) {
        return new UpdateChattingSessionHashRequest(roomId, simpSubscriptionId, updateFlag);
    }
}
