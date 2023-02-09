package com.mjubus.server.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaxiPartyJoinResponse {

    @ApiModelProperty(value = "멤버 추가 성공 여부", example = "success")
    private String isAdded;
}
