package com.mjubus.server.service.bus;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.BusResponseDto;
import com.mjubus.server.dto.BusStatusDto;
import com.mjubus.server.dto.StationDTO;
import com.mjubus.server.dto.busListDto.BusList;
import com.mjubus.server.dto.busListDto.BusListDto;
import com.mjubus.server.dto.busRoute.BusRouteDto;
import com.mjubus.server.dto.busRoute.RouteOrderDto;
import com.mjubus.server.enums.BusEnum;
import com.mjubus.server.exception.Bus.BusNotFoundException;
import com.mjubus.server.exception.BusCalenderNotFoundException;
import com.mjubus.server.exception.BusTimeTable.BusTimeTableNotFoundException;
import com.mjubus.server.repository.*;
import com.mjubus.server.service.busTimeTable.BusTimeTableService;
import com.mjubus.server.service.mjuCalendar.BusCalendarService;
import com.mjubus.server.service.route.RouteService;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class BusService implements BusServiceInterface {
    private final BusRepository busRepository;

    private final BusCalendarService busCalendarService;

    private final BusTimeTableService busTimeTableService;

    private final RouteService routeService;

    public BusService(@Lazy BusTimeTableService busTimeTableService, @Lazy BusRepository busRepository, @Lazy BusCalendarService busCalendarService, @Lazy RouteService routeService) {
        this.busTimeTableService = busTimeTableService;
        this.busRepository = busRepository;
        this.busCalendarService = busCalendarService;
        this.routeService = routeService;
    }

    @Override
    public Bus findBusByBusId(Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        return  bus.orElseThrow(() -> new BusNotFoundException(id));
    }

    @Override
    public BusStatusDto getBusStatusByBusId(Long id) {
        // Bus, BusCalender 검증
        Bus bus = findBusByBusId(id);

        // BusTimeTable
        BusTimeTable busTimeTable = busTimeTableService.findBusTimeTableByBus(bus);

        // Response Obj
        BusStatusDto busStatusDto = new BusStatusDto(bus);

        // 오늘
        LocalDateTime now = DateHandler.getToday();

        // 버스 시간표
        List<BusTimeTableDetail> timeTableDetails = busTimeTableService.findBusTimeTableDetailByInfo(busTimeTable.getBusTimeTableInfo());

        // 첫차
        LocalDateTime first_bus = busTimeTableService.getFirstBus(timeTableDetails);

        // 막차
        LocalDateTime last_bus = busTimeTableService.getLastBus(timeTableDetails);

        if (now.isBefore(first_bus)) { // 첫차 전
            busStatusDto.setStatus(BusStatusDto.BEFORE_RUNNING);
        } else if (now.isAfter(last_bus)) { // 막차 후
            busStatusDto.setStatus(BusStatusDto.FINISH_RUNNING);
        } else { // 운행 중
            busStatusDto.setStatus(BusStatusDto.RUNNING);
        }

        return busStatusDto;
    }

    @Override
    public BusRouteDto getRouteByBusId(Long busId) {
        Bus bus = findBusByBusId(busId);
        Route route = routeService.findByBus(bus);

        RouteInfo routeInfo = route.getRouteInfo();
        List<RouteDetail> routeDetailList = routeService.findRouteDetailByRouteInfo(routeInfo);

        List<RouteOrderDto> routeOrderList = new LinkedList<>();
        for(RouteDetail routeDetail: routeDetailList) {
            RouteOrderDto temp = new RouteOrderDto();
            temp.setStation(routeDetail.getStation());
            temp.setRoute_order(routeDetail.getOrder());

            routeOrderList.add(temp);
        }

        BusRouteDto result = new BusRouteDto();
        result.setBus(bus);
        result.setStations(routeOrderList);
        return result;
    }

    @Override
    public List<BusList> getBusListByDate(LocalDateTime date) {
        // BusCalendar
        BusCalendar busCalendar = busCalendarService.findByDate(date);

        // 현재 일정으로 운행중인 버스 운행표
        List<BusTimeTable> busTimeTableList = busTimeTableService.findBusTimeTableListByCalendar(busCalendar);

        // 시내버스
        BusList busList_IN = new BusList();
        busList_IN.setType(BusEnum.시내버스.getValue());
        List<Bus> busListInner = new LinkedList<>();

        // 시외버스
        BusList busList_OUT = new BusList();
        busList_OUT.setType(BusEnum.시외버스.getValue());
        List<Bus> busListOuter = new LinkedList<>();

        // 시내/시외 분류
        for(BusTimeTable busTimeTable : busTimeTableList) {
            Bus bus = busTimeTable.getBus();
            if (bus.getId() < 100) {
                busListInner.add(bus);
            } else {
                busListOuter.add(bus);
            }
        }

        // 결과 값 생성
        List<BusList> busLists = new LinkedList<>();

        busList_IN.setBusList(busListInner);
        busList_OUT.setBusList(busListOuter);

        busLists.add(busList_IN);
        busLists.add(busList_OUT);

        return busLists;
    }
}
