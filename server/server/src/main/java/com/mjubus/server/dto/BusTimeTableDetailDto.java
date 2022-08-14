package com.mjubus.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class BusTimeTableDetailDto {

    @ApiModelProperty(example = "진입로")
    private String mainStationName;

    @ApiModelProperty(example = "08:20")
    private LocalTime depart_at;

    @ApiModelProperty(example = "08:45")
    private LocalTime arrive_at;

    public BusTimeTableDetailDto() {
        this.mainStationName = "";
        this.depart_at = null;
        this.arrive_at = null;
    }
}
