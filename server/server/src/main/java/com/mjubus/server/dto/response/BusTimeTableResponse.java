package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.dto.busTimeTable.BusTimeTableStationDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BusTimeTableResponse {

    @ApiModelProperty(example = "10", value = "버스 식별 번호")
    private Long id;

    @ApiModelProperty(example = "명지대역", value = "버스 정류장 이름")
    private String name;

    @ApiModelProperty(example = "1", dataType = "int")
    private List<BusTimeTableStationDto> stations;

    public void setBus(Bus bus) {
        this.id = bus.getId();
        this.name = bus.getName();
    }
}
