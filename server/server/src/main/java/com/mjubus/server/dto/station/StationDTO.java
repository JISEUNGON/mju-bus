package com.mjubus.server.dto.station;

import io.swagger.annotations.ApiModelProperty;

public interface StationDTO {

    @ApiModelProperty(example = "458d6218-7ca1-49a5-9575-13da00d14550")
    Long getId();

    @ApiModelProperty(example = "생활관(명현관)")
    String getName();

    @ApiModelProperty(example = "위도")
    Double getLatitude();

    @ApiModelProperty(example = "경도")
    Double getLongitude();

    @ApiModelProperty(example = "1-based 정류장 경유 순서 ")
    int getRoute_order();
}
