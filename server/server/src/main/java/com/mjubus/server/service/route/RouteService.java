package com.mjubus.server.service.route;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.StationDTO;
import com.mjubus.server.exception.Route.RouteInfoNotFoundException;
import com.mjubus.server.exception.Route.RouteNotFoundException;
import com.mjubus.server.exception.Station.StationNotFoundException;
import com.mjubus.server.repository.RouteDetailRepository;
import com.mjubus.server.repository.RouteRepository;
import com.mjubus.server.service.busCalendar.BusCalendarService;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService implements RouteInterface {

    @Autowired
    private BusCalendarService busCalendarService;


    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteDetailRepository routeDetailRepository;

    @Override
    public Route findByBus(Bus bus) {
        // 현재 시각
        LocalDateTime now = DateHandler.getToday();

        // 버스 일정
        BusCalendar busCalendar =  busCalendarService.findByDate(now);

        // 버스 일정 + 버스 정보로 Route 검색
        Optional<Route> routeOptional = routeRepository.findRouteByBus_IdAndBusCalendar_Id(bus.getId(), busCalendar.getId());


        return  routeOptional.orElseThrow(() -> new RouteNotFoundException(bus, null, busCalendar));
    }

    @Override
    public Route findByRouteInfo(RouteInfo routeInfo) {
        // 현재 시각
        LocalDateTime now = DateHandler.getToday();

        // 버스 일정
        BusCalendar busCalendar =  busCalendarService.findByDate(now);

        // RouteInfo + 버스 일정 정보로 Route 검색
        Optional<Route> routeOptional = routeRepository.findRouteByRouteInfo_IdAndBus_Id(routeInfo.getId(), busCalendar.getId());

        return  routeOptional.orElseThrow(() -> new RouteNotFoundException(null, routeInfo, busCalendar));
    }

    @Override
    public List<StationDTO> findStationsByRouteInfo(RouteInfo routeInfo) {
        Optional<List<StationDTO>> optionalStationDTOS = routeDetailRepository.findStationsByRouteInfo_Id(routeInfo.getId());
        return optionalStationDTOS.orElseThrow(() -> new RouteInfoNotFoundException(routeInfo));
    }

    @Override
    public List<StationDTO> findStationsByBus(Bus bus) {
        BusCalendar busCalendar = busCalendarService.findByDate(DateHandler.getToday());

        Optional<Route> optionalRoute = routeRepository.findRouteByBus_IdAndBusCalendar_Id(bus.getId(), busCalendar.getId());
        Route route = optionalRoute.orElseThrow(() -> new RouteNotFoundException(bus, null, busCalendar));
        return findStationsByRouteInfo(route.getRouteInfo());
    }

    @Override
    public List<RouteDetail> findRouteDetailByRouteInfo(RouteInfo routeInfo) {
        Optional<List<RouteDetail>> optionalRouteDetailList = routeDetailRepository.findRouteDetailsByRouteInfo_IdOrderByOrder(routeInfo.getId());
        return optionalRouteDetailList.orElseThrow(() -> new RouteInfoNotFoundException(routeInfo));
    }

    @Override
    public RouteInfo findRouteInfoByBus(Bus bus) {
        Route route = findByBus(bus);

        return route.getRouteInfo();
    }

    @Override
    public List<RouteDetail> findRouteDetailByStation(Station station) {
        Optional<List<RouteDetail>> optionalRouteDetails = routeDetailRepository.findRouteDetailsByStation_Id(station.getId());
        return optionalRouteDetails.orElseThrow(() -> new StationNotFoundException(station.getId()));
    }
}
