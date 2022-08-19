package com.mjubus.server.dto.busArrival;

import com.mjubus.server.domain.Station;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BusArrivalDto {

    @ApiModelProperty(example = "정류장 정보")
    private Station station;
    
    @ApiModelProperty(reference = "BusRemainDto")
    private List<BusRemainDto> busList;
}
