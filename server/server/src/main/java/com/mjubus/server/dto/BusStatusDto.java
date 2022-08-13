package com.mjubus.server.dto;

import com.mjubus.server.domain.Bus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BusStatusDto {
    public static final int BEFORE_RUNNING = 1;
    public static final int RUNNING = 2;
    public static final int FINISH_RUNNING = 3;

    @ApiModelProperty(example = "61883118-be22-48fa-d3f5-44f5a450f9d3")
    private Long id;

    @ApiModelProperty(example = "시내")
    private String name;

    @ApiModelProperty(example = "3")
    private int status;

    public BusStatusDto(Bus bus) {
        this.id = bus.getId();
        this.name = bus.getName();
        this.status = FINISH_RUNNING;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
