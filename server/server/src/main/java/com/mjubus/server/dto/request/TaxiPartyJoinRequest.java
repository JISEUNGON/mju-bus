package com.mjubus.server.dto.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxiPartyJoinRequest {
    @ApiModelProperty(value = "멤버 ID", example = "1")
    private Long memberId;

    @JsonIgnore
    private String ignored;

    public static TaxiPartyJoinRequest of(Long memberId) {
        return TaxiPartyJoinRequest.builder()
                .memberId(memberId)
                .build();
    }
}
