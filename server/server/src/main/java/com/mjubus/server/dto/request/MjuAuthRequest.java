package com.mjubus.server.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MjuAuthRequest {
    @ApiModelProperty(name = "id", value = "Member 아이디")
    private Long id;

    public static MjuAuthRequest of(Long id) {
        return new MjuAuthRequest(id);
    }

    public static MjuAuthRequest of(String id) {
        return new MjuAuthRequest(Long.parseLong(id));
    }
}
