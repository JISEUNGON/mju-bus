package com.mjubus.server.dto.busArrival;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BusArrivalDto {

    @ApiModelProperty(example = "200", dataType = "int")
    private String id;

    @ApiModelProperty(example = "5001")
    private String name;

    @ApiModelProperty(example = "2022-08-26 18:00:03")
    private LocalDateTime expected_at;
}
