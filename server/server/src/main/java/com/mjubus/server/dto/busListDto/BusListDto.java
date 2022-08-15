package com.mjubus.server.dto.busListDto;

import lombok.Getter;

import java.util.List;

@Getter
public class BusListDto {
    private List<BusList> list;

    public BusListDto(List<BusList> busLists) {
        this.list = busLists;
    }

    public void setBusList(List<BusList> busList) {
        this.list = busList;
    }
}
