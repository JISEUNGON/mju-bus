package com.mjubus.server.service.route;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.station.StationDTO;
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
public class RouteServiceImpl implements RouteService {

    private final BusCalendarService busCalendarService;

    private final RouteRepository routeRepository;
    private final RouteDetailRepository routeDetailRepository;

    @Autowired
    public RouteServiceImpl(BusCalendarService busCalendarService, RouteRepository routeRepository, RouteDetailRepository routeDetailRepository) {
        this.busCalendarService = busCalendarService;
        this.routeRepository = routeRepository;
        this.routeDetailRepository = routeDetailRepository;
    }

    /**
     * 버스로 노선 검색
     * @param bus 버스
     * @return 노선
     */
    @Override
    public Route findByBus(Bus bus) {
        LocalDateTime now = DateHandler.getToday();
        BusCalendar busCalendar =  busCalendarService.findByDate(now);
        Optional<Route> routeOptional = routeRepository.findRouteByBus_IdAndBusCalendar_Id(bus.getId(), busCalendar.getId());
        return  routeOptional.orElseThrow(() -> new RouteNotFoundException(bus, null, busCalendar));
    }

    /**
     * 노선정보로 노선 검색
     * @param routeInfo 노선정보
     * @return 노선
     */
    @Override
    public Route findByRouteInfo(RouteInfo routeInfo) {
        LocalDateTime now = DateHandler.getToday();
        BusCalendar busCalendar =  busCalendarService.findByDate(now);
        Optional<Route> routeOptional = routeRepository.findRouteByRouteInfo_IdAndBus_Id(routeInfo.getId(), busCalendar.getId());
        return  routeOptional.orElseThrow(() -> new RouteNotFoundException(null, routeInfo, busCalendar));
    }

    /**
     * 노선정로 노선상의 정류장 검색
     * @param routeInfo 노선정보
     * @return 노선상의 정류장
     */
    @Override
    public List<StationDTO> findStationsByRouteInfo(RouteInfo routeInfo) {
        Optional<List<StationDTO>> optionalStationDTOS = routeDetailRepository.findStationsByRouteInfo_Id(routeInfo.getId());
        return optionalStationDTOS.orElseThrow(() -> new RouteInfoNotFoundException(routeInfo));
    }

    /**
     * 버스로 노선상의 정류장 검색
     * @param bus 버스
     * @return 노선상의 정류장
     */
    @Override
    public List<StationDTO> findStationsByBus(Bus bus) {
        BusCalendar busCalendar = busCalendarService.findByDate(DateHandler.getToday());

        Optional<Route> optionalRoute = routeRepository.findRouteByBus_IdAndBusCalendar_Id(bus.getId(), busCalendar.getId());
        Route route = optionalRoute.orElseThrow(() -> new RouteNotFoundException(bus, null, busCalendar));
        return findStationsByRouteInfo(route.getRouteInfo());
    }

    /**
     * 노선정보로 노선상의 정류장 순서
     * @param routeInfo 노선정보
     * @return 노선상의 정류장 순서
     */
    @Override
    public List<RouteDetail> findRouteDetailByRouteInfo(RouteInfo routeInfo) {
        Optional<List<RouteDetail>> optionalRouteDetailList = routeDetailRepository.findRouteDetailsByRouteInfo_IdOrderByOrder(routeInfo.getId());
        return optionalRouteDetailList.orElseThrow(() -> new RouteInfoNotFoundException(routeInfo));
    }

    /**
     * 버스로 노선 정보 검색
     * @param bus 버스
     * @return 노선 정보
     */
    @Override
    public RouteInfo findRouteInfoByBus(Bus bus) {
        Route route = findByBus(bus);

        return route.getRouteInfo();
    }

    /**
     *  정류장으로 노선상의 정류장 검색
     * @param station 정류장
     * @return 노선상의 정류장
     */
    @Override
    public List<RouteDetail> findRouteDetailByStation(Station station) {
        Optional<List<RouteDetail>> optionalRouteDetails = routeDetailRepository.findRouteDetailsByStation_Id(station.getId());
        return optionalRouteDetails.orElseThrow(() -> new StationNotFoundException(station.getId()));
    }

    /**
     * 일정으로 아무 노선 검색
     * @param busCalendar 일정
     * @return 노선
     */
    @Override
    public Route findAnyRouteByBusCalendar(BusCalendar busCalendar) {
        Optional<Route> optionalRoute = routeRepository.findRouteByBusCalendar_Id(busCalendar.getId());

        return optionalRoute.orElseThrow(() -> new RouteNotFoundException(null, null, busCalendar));
    }
}
