package com.mjubus.server.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateChattingSessionHashRequest {

    @ApiModelProperty(value = "파티 아이디", example = "1")
    private String roomId;
    @ApiModelProperty(value = "멤버 아이디", example = "1")
    private String memberId;

    @ApiModelProperty(example = "true")
    private boolean updateFlag;

    public static UpdateChattingSessionHashRequest of(String roomId, String simpSubscriptionId, boolean updateFlag) {
        return new UpdateChattingSessionHashRequest(roomId, simpSubscriptionId, updateFlag);
    }
}
