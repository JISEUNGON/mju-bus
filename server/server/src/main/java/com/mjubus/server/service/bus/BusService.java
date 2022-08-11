package com.mjubus.server.service.bus;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.BusStatusDto;
import com.mjubus.server.dto.StationDTO;
import com.mjubus.server.repository.BusRepository;
import com.mjubus.server.repository.BusTimeTableRepository;
import com.mjubus.server.repository.RouteDetailRepository;
import com.mjubus.server.repository.RouteRepository;
import com.mjubus.server.service.mjuCalendar.BusCalendarService;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService implements BusServiceInterface {
    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusCalendarService busCalendarService;

    @Autowired
    private BusTimeTableRepository busTimeTableRepository;

    @Autowired
    private RouteDetailRepository routeDetailRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public Optional<Bus> getBusByType(int type) {
      return busRepository.findByType(type);
    }

    @Override
    public BusStatusDto getBusStatus(int type) {
        BusCalendar busCalendar = busCalendarService.findByDate(DateHandler.getToday()).get();
        Bus bus = getBusByType(type).get();
        Optional<Route> route = routeRepository.findRouteByBus_SidAndAndBusCalendar_Sid(bus.getSid(), busCalendar.getSid());
        BusStatusDto busStatusDto = new BusStatusDto(bus);
        if (route.isPresent())
            busStatusDto.setStatus(true);
        else
            busStatusDto.setStatus(false);
        return busStatusDto;
    }


    public List<StationDTO> getBusStations(int type) {
        BusCalendar busCalendar = busCalendarService.findByDate(DateHandler.getToday()).get();
        Bus bus = getBusByType(type).get();
        Route route = routeRepository.findRouteByBus_SidAndAndBusCalendar_Sid(bus.getSid(), busCalendar.getSid()).get();
        List<StationDTO> station = routeDetailRepository.findStationsByRouteInfo_Sid(route.getRouteInfo().getSid());
        return station;
    }

}