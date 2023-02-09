package com.mjubus.server.dto.response;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaxiPartyCreateResponse {
    @ApiModelProperty(value = "파티 생성 성공 여부", example = "success")
    private String isCreated;
}
