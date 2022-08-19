package com.mjubus.server.dto.busArrival;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.enums.BusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusRemainDto {

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

    @ApiModelProperty(example = "버스 종류 : 1(시내버스), 2(시외버스), 3(빨간버스)")
    private int type;

    @ApiModelProperty(example = "남은 시간(초)")
    private int remains;

    public void setId(Long id) {
        this.id = id;
        if (id < 100) {
            this.type = BusEnum.시내버스.getValue();
        } else if (id < 200) {
            this.type = BusEnum.시외버스.getValue();
        } else {
            this.type = BusEnum.빨간버스.getValue();
        }
    }

    public void setBus(Bus bus) {
        setId(bus.getId());
        setName(bus.getName());
        setCharge(bus.getCharge());
    }
}
