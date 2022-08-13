package com.mjubus.server.dto;

import io.swagger.annotations.ApiModelProperty;

public interface BusResponseDto {
    @ApiModelProperty(example = "10", dataType = "int")
    Long getId();

    @ApiModelProperty(example = "명지대역")
    String getName();

    @ApiModelProperty(example = "0", dataType = "int")
    int getCharge();

}
