package com.mjubus.server.service.busArrival;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.busArrival.BusArrivalDto;
import com.mjubus.server.dto.request.StationRemainRequest;
import com.mjubus.server.dto.request.StationRequest;
import com.mjubus.server.dto.response.BusArrivalResponse;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface BusArrivalService {

    BusArrivalResponse findBusArrivalRemainByStation(Station srcStation, Station destStation, Boolean toSchool, Boolean redBus);
    BusArrivalResponse findBusArrivalRemain(StationRequest req, StationRemainRequest remainReq);
    List<BusArrivalDto> getBusArrivalListBetween(Station srcStation, Station destStation);
    LocalTime getExpectedTimeFrom(Bus bus, Station currentStation, LocalDateTime depart_at);
    LocalTime getExpectedTimeTo(Bus bus, Station targetStation, LocalDateTime depart_at);
    List<BusArrivalDto> findBusArrivalByStation(Station station);
}
