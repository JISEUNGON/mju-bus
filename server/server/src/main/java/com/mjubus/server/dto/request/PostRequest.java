package com.mjubus.server.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Builder
@Getter
public class PostRequest {
    @ApiModelProperty(example = "1", dataType = "int", value = "고유 식별 ID")
    private int id;

    public static PostRequest of(String id) {
        return new PostRequest(Integer.parseInt(id));
    }

    public static PostRequest of(int id) {
        return new PostRequest(id);
    }
}
