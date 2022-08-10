package com.mjubus.server.dto;

import com.mjubus.server.domain.Bus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BusStatusDto {
    @ApiModelProperty(example = "61883118-be22-48fa-d3f5-44f5a450f9d3")
    private String sid;

    @ApiModelProperty(example = "시내")
    private String name;

    @ApiModelProperty(example = "true", dataType = "boolean")
    private boolean status;

    public BusStatusDto(Bus bus) {
        this.sid = bus.getSid();
        this.name = bus.getName();
        this.status = false;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
