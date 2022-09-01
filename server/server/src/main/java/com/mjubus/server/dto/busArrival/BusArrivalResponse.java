package com.mjubus.server.dto.busArrival;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusArrivalResponse {

    @ApiModelProperty(example = "4", dataType = "int")
    private Long id;

    @ApiModelProperty(example = "진입로(명지대행)")
    private String name;

    @ApiModelProperty(example = "정류장 위도")
    private Double latitude;

    @ApiModelProperty(example = "정류장 위도")
    private Double longitude;

    @ApiModelProperty(example = "2022-08-26 17:06:52")
    private LocalDateTime response_at;

    private List<? extends BusRemainInterface> busList;
}
