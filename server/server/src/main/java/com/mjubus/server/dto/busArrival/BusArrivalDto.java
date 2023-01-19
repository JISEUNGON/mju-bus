package com.mjubus.server.dto.busArrival;

import java.time.LocalDateTime;
public interface BusArrivalDto {
    Long getStationId();
    Long getBusId();
    LocalDateTime getCreatedAt();
    LocalDateTime getExpectedAt();

}
