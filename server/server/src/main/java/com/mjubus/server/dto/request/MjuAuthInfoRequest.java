package com.mjubus.server.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MjuAuthInfoRequest {

    @ApiModelProperty(name = "name", value = "이름")
    private String name;

    @ApiModelProperty(name = "birthday", value = "생년월일 6자리")
    private String birthday;

    public static MjuAuthInfoRequest of(String name, String birthday) {
        return new MjuAuthInfoRequest(name, birthday);
    }
}
