package com.mjubus.server.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaxiPartyQuitResponse {
    @ApiModelProperty(value = "파티 탈퇴 성공 여부", example = "success")
    private String isQuited;

}
