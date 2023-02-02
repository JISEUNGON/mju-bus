package com.mjubus.server.dto.busTimeTable;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class BusTimeTableTimeDto {
    private LocalTime depart_at;
    private LocalTime arrive_at;

    public BusTimeTableTimeDto() {
        depart_at = null;
        arrive_at = null;
    }
}
