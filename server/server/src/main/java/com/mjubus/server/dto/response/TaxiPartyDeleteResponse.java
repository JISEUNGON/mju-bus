package com.mjubus.server.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaxiPartyDeleteResponse {
    @ApiModelProperty(value = "택시 파티 삭제 성공 여부", example = "success")
    private String isDeleted;

}
