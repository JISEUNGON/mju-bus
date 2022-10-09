package com.mjubus.server.service.bus;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.dto.BusStatusDto;
import com.mjubus.server.dto.busListDto.BusList;
import com.mjubus.server.dto.busRoute.BusRouteDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface BusServiceInterface {
    Bus findBusByBusId(Long id);

    BusStatusDto getBusStatusByBusId(Long id);
    BusRouteDto getRouteByBusId(Long busId);
    List<BusList> getBusListByDate(LocalDateTime date);
}
