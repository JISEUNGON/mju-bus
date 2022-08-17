package com.mjubus.server.dto;

import com.mjubus.server.domain.Bus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class BusResponseDto {

    @ApiModelProperty(example = "고유 식별 ID")
    /**
     * `0` ~ `99` : 명지대학교 시내 셔틀버스
     * `100` ~ `199` :  명지대학교 시외 셔틀버스
     * `200` ~ `255` : 경기도 시외버스 (빨간버스)
     */
    private Long id;

    @ApiModelProperty(example = "명지대역")
    private String name;

    @ApiModelProperty(example = "0", dataType = "int")
    private Long charge;
    @ApiModelProperty(example = "1")
    private int type;

    public BusResponseDto(Bus bus) {
        this.id = bus.getId();
        this.name = bus.getName();
        this.charge = bus.getCharge();
    }

    public void setType(int type) {
        this.type = type;
    }


}
