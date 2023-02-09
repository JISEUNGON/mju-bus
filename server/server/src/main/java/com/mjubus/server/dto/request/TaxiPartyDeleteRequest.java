package com.mjubus.server.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxiPartyDeleteRequest {

    @ApiModelProperty(value = "택시 파티 ID", example = "1")
    private Long groupId;

    public static TaxiPartyDeleteRequest of(String groupId) {
        return new TaxiPartyDeleteRequest(Long.parseLong(groupId));
    }

}
