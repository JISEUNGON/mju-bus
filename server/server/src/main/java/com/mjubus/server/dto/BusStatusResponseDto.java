package com.mjubus.server.dto;

import lombok.Getter;

@Getter
public class BusStatusResponseDto {
    private String sid;
    private String name;
    private boolean status;

    public BusStatusResponseDto(String sid, String name, boolean status) {
        this.sid = sid;
        this.name = name;
        this.status = status;
    }
}
