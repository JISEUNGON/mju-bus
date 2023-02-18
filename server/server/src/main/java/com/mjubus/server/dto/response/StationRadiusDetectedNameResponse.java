package com.mjubus.server.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StationRadiusDetectedNameResponse {

    @ApiModelProperty(value = "위도경도와 가까운 장소명", example = "진입로")
    private String name;
}
