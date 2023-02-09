package com.mjubus.server.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxiPartyQuitRequest {

    @ApiModelProperty(value = "멤버 ID", example = "1")
    private Long memberId;

    public static TaxiPartyQuitRequest of(String memberId) {
        return new TaxiPartyQuitRequest(Long.parseLong(memberId));
    }
}
