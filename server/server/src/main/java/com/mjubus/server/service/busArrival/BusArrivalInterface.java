package com.mjubus.server.service.busArrival;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.busArrival.BusArrivalDto;

import java.util.List;

public interface BusArrivalInterface {
    BusArrivalDto findBusArrivalByStationId(Long stationId);
}
