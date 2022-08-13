package com.mjubus.server.service.bus;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.BusResponseDto;
import com.mjubus.server.dto.BusStatusDto;
import com.mjubus.server.dto.StationDTO;
import com.mjubus.server.exception.Bus.BusNotFoundException;
import com.mjubus.server.exception.BusCalenderNotFoundException;
import com.mjubus.server.exception.BusTimeTable.BusTimeTableNotFoundException;
import com.mjubus.server.repository.*;
import com.mjubus.server.service.busTimeTable.BusTimeTableService;
import com.mjubus.server.service.mjuCalendar.BusCalendarService;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
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
    private BusTimeTableService busTimeTableService;
    @Autowired
    private RouteRepository routeRepository;

    @Override
    public Optional<Bus> findBusById(Long id) {
      return busRepository.findById(id);
    }

    @Override
    public BusStatusDto getBusStatus(Long id) {
        Optional<BusCalendar> busCalendarOptional = busCalendarService.findByDate(DateHandler.getToday());
        Optional<Bus> busOptional = findBusById(id);

        // Bus, BusCalender 검증
        Bus bus = busOptional.orElseThrow(() -> new BusNotFoundException(id));
        BusCalendar busCalendar = busCalendarOptional.orElseThrow(() -> new BusCalenderNotFoundException(DateHandler.getToday()));

        // BusTimeTable 검증
        Optional<BusTimeTable> busTimeTableOptional = busTimeTableRepository.findBusTimeTableByBus_IdAndBusCalendar_Id(bus.getId(), busCalendar.getId());
        BusTimeTable busTimeTable = busTimeTableOptional.orElseThrow(() ->  new BusTimeTableNotFoundException("<getBusStatus> Bus : " +  bus.getName() + " / BusCalendar : " + busCalendar.getName()));


        // Response Obj
        BusStatusDto busStatusDto = new BusStatusDto(bus);

        // 오늘
        LocalDateTime now = DateHandler.getToday();

        // 버스 시간표
        List<BusTimeTableDetail> timeTableDetails = busTimeTableService.findByInfoId(busTimeTable.getBusTimeTableInfo().getId());

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
    public List<Bus> getBusByDate(LocalDateTime date) {
        Optional<BusCalendar> busCalendarOptional = busCalendarService.findByDate(date);

        // BusCalendar 검증
        BusCalendar busCalendar = busCalendarOptional.orElseThrow(() -> new BusCalenderNotFoundException(date));

        // bus-id 로 리스트 가져오기
        List<BusTimeTable> busTimeTableList = busTimeTableService.findListById(busCalendar.getId());

        // 이동
        List<Bus> result = new LinkedList<>();
        busTimeTableList.forEach((busTimeTable -> result.add(busTimeTable.getBus())));

        return result;
    }


    public List<StationDTO> getBusStations(Long type) {
        BusCalendar busCalendar = busCalendarService.findByDate(DateHandler.getToday()).get();
        Bus bus = findBusById(type).get();
        Route route = routeRepository.findRouteByBus_IdAndBusCalendar_Id(bus.getId(), busCalendar.getId()).get();
        List<StationDTO> station = routeDetailRepository.findStationsByRouteInfo_Id(route.getRouteInfo().getId());
        return station;
    }

}
