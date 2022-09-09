package com.mjubus.server.service.busArrival;

import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.busArrival.BusArrivalResponse;

public interface BusArrivalInterface {

    BusArrivalResponse findBusArrivalRemainByStation(Station srcStation, Station destStation, Boolean toSchool, Boolean redBus);
}
