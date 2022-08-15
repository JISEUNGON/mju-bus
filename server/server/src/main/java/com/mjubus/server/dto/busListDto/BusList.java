package com.mjubus.server.dto.busListDto;

import com.mjubus.server.domain.Bus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class BusList {
    @ApiModelProperty(example = "1(시내), 2(시외)")
    private int type;

    @ApiModelProperty(example = "버스 리스트")
    private List<Bus> busList;

    public BusList() {
        type = 0;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setBusList(List<Bus> busList) {
        this.busList = busList;
    }
}
