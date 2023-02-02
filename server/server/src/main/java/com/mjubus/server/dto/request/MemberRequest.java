package com.mjubus.server.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRequest {

    @ApiModelProperty(example = "1", dataType = "int", value = "고유 식별 ID")
    private Long id;

    public static MemberRequest of(String id) {
        return new MemberRequest(Long.parseLong(id));
    }

    public static MemberRequest of(Long id) {
        return new MemberRequest(id);
    }
}
