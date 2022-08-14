package com.mjubus.server.dto;

import com.mjubus.server.domain.Bus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusTimeTableResponseDto {

    @ApiModelProperty(example = "10")
    private Long id;

    @ApiModelProperty(example = "명지대역")
    private String name;


    @ApiModelProperty(example = "1", dataType = "int")
    private List<BusTimeTableStationDto> stations;


    public BusTimeTableResponseDto() {
        this.id = -1L;
        this.name = "";
        this.stations = null;
    }

    public void setBus(Bus bus) {
        this.id = bus.getId();
        this.name = bus.getName();
    }
}
