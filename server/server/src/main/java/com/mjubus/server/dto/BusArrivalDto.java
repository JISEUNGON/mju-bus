package com.mjubus.server.dto;

import java.time.LocalDateTime;
public interface BusArrivalDto {
    Long getStationId();
    Long getBusId();
    LocalDateTime getCreatedAt();
    LocalDateTime getExpectedAt();

}
