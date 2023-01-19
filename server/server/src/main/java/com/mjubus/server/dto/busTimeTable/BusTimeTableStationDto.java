package com.mjubus.server.dto.busTimeTable;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusTimeTableStationDto {

    private String name;

    private List<BusTimeTableTimeDto> timeList;

    public BusTimeTableStationDto() {
        name = "";
        timeList = null;
    }
}
