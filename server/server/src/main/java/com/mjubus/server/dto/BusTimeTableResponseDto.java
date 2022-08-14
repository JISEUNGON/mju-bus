package com.mjubus.server.dto;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.enums.BusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class BusTimeTableResponseDto {

    @ApiModelProperty(example = "10")
    private Long id;

    @ApiModelProperty(example = "명지대역")
    private String busName;

    @ApiModelProperty(example = "1", dataType = "int")
    private int type;

    @ApiModelProperty(example = "1", dataType = "int")
    private List<BusTimeTableDetailDto> busTimeTableDetailDto;


    public BusTimeTableResponseDto() {
        this.id = -1L;
        this.busName = "";
        this.type = -1;
        this.busTimeTableDetailDto = null;
    }

    public void setBus(Bus bus) {
        this.id = bus.getId();
        this.busName = bus.getName();
        if (id < 100) {
            this.type = BusEnum.시내버스.getValue();
        } else {
            this.type = BusEnum.시외버스.getValue();
        }
    }
}
