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
    @ApiModelProperty(example = "1", dataType = "int", value = "Member ID")
    private Long memberId;

    @JsonIgnore
    private String ignored;

    public static TaxiPartyJoinRequest of(Long memberId) {
        return TaxiPartyJoinRequest.builder()
                .memberId(memberId)
                .build();
    }
}
