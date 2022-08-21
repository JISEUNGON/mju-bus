package com.mjubus.server.service.busTimeTable;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.BusTimeTableResponseDto;
import com.mjubus.server.dto.BusTimeTableStationDto;
import com.mjubus.server.dto.BusTimeTableTimeDto;
import com.mjubus.server.dto.StationDTO;
import com.mjubus.server.exception.BusCalenderNotFoundException;
import com.mjubus.server.exception.BusTimeTable.BusTimeTableDetailNotFoundException;
import com.mjubus.server.exception.BusTimeTable.BusTimeTableNotFoundException;
import com.mjubus.server.repository.BusTimeTableDetailRepository;
import com.mjubus.server.repository.BusTimeTableRepository;
import com.mjubus.server.service.bus.BusService;
import com.mjubus.server.service.mjuCalendar.BusCalendarService;
import com.mjubus.server.service.route.RouteService;
import com.mjubus.server.service.station.StationService;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class BusTimeTableService implements BusTimeTableInterface {

    @Autowired
    private BusTimeTableDetailRepository busTimeTableDetailRepository;

    @Autowired
    private BusTimeTableRepository busTimeTableRepository;

    @Autowired
    private BusCalendarService busCalendarService;

    @Autowired
    private RouteService routeService;

    private final BusService busService;


    public BusTimeTableService(BusService busService) {
        this.busService = busService;
    }

    @Override
    public BusTimeTable findBusTimeTableByBus(Bus bus) {
        BusCalendar busCalendar = busCalendarService.findByDate(DateHandler.getToday());

        Optional<BusTimeTable> optionalBusTimeTable = busTimeTableRepository.findBusTimeTableByBus_IdAndBusCalendar_Id(bus.getId(), busCalendar.getId());

        return optionalBusTimeTable.orElseThrow(() -> new BusTimeTableNotFoundException(bus, busCalendar, null));
    }

    @Override
    public BusTimeTable findBusTimeTableByBusTimeTableInfo(BusTimeTableInfo busTimeTableInfo) {
        BusCalendar busCalendar = busCalendarService.findByDate(DateHandler.getToday());

        Optional<BusTimeTable> optionalBusTimeTable = busTimeTableRepository.findBusTimeTableByBusTimeTableInfo_IdAndBusCalendar_Id(busTimeTableInfo.getId(), busCalendar.getId());

        return optionalBusTimeTable.orElseThrow(() -> new BusTimeTableNotFoundException(null, busCalendar, busTimeTableInfo));
    }

    @Override
    public List<BusTimeTable> findBusTimeTableListByCalendar(BusCalendar busCalendar) {
        Optional<List<BusTimeTable>> optionalBusTimeTableList = busTimeTableRepository.findBusTimeTablesByBusCalendar_Id(busCalendar.getId());
        return optionalBusTimeTableList.orElseThrow(() -> new BusTimeTableNotFoundException(null, busCalendar, null));
    }

    @Override
    public BusTimeTableResponseDto makeBusTimeTableByBusId(Long busId) {
        BusTimeTableResponseDto result = new BusTimeTableResponseDto();

        // Bus 정보 삽입
        Bus bus = busService.findBusByBusId(busId);
        result.setBus(bus);

        if (bus.getId() < 100) {
            // 시내 셔틀버스
            RouteInfo routeInfoByBus = routeService.findRouteInfoByBus(bus);

            // 주요 정류장 + 정류장 도착 예정 시간
            int minRequired = routeInfoByBus.getMinute();
            String stationName = routeInfoByBus.getStation().getName();

            // Stations
            List<BusTimeTableStationDto> busTimeTableStationDto = new LinkedList<>();

            // Station
            BusTimeTableStationDto station = new BusTimeTableStationDto();
            station.setName(stationName);

            // Time
            List<BusTimeTableTimeDto> timeList = new LinkedList<>();

            BusTimeTable busTimeTable = findBusTimeTableByBus(bus);
            List<BusTimeTableDetail> tableDetailList = findBusTimeTableDetailByInfo(busTimeTable.getBusTimeTableInfo());
            for(BusTimeTableDetail detail : tableDetailList) {
                BusTimeTableTimeDto temp = new BusTimeTableTimeDto();

                // 명지대 출발 시각
                LocalTime depart_at = detail.getDepart();

                // 주요 정류장 도착 시각
                LocalTime arrive_at = detail.getDepart();
                arrive_at = LocalTime.from(DateHandler.getTodayWith(arrive_at.getHour(), arrive_at.getMinute()));
                arrive_at = arrive_at.plusMinutes(minRequired);

                // 조립
                temp.setDepart_at(depart_at);
                temp.setArrive_at(arrive_at);
                timeList.add(temp);
            }
            station.setTimeList(timeList);
            busTimeTableStationDto.add(station);
            result.setStations(busTimeTableStationDto);
        } else {
            // 시외 셔틀버스
            // TODO : 모든 시간을 넣어야 됨.
            BusTimeTable busTimeTable = findBusTimeTableByBus(bus);
            Route route = routeService.findByBus(bus);

            List<BusTimeTableDetail> tableDetailList = findBusTimeTableDetailByInfo(busTimeTable.getBusTimeTableInfo());
            List<RouteDetail> routeDetailList = routeService.findRouteDetailByRouteInfo(route.getRouteInfo());

            List<BusTimeTableStationDto> busTimeTableStationDtoList = new LinkedList<>();
            for (int i = 0; i < routeDetailList.size(); i++) {
                RouteDetail routeDetail = routeDetailList.get(i);
                BusTimeTableDetail busTimeTableDetail = tableDetailList.get(i);

                BusTimeTableStationDto temp = new BusTimeTableStationDto();
                temp.setName(routeDetail.getStation().getName());

                // 시간표 추가
                List<BusTimeTableTimeDto> timeList = new LinkedList<>();
                BusTimeTableTimeDto temp_time = new BusTimeTableTimeDto();
                temp_time.setArrive_at(busTimeTableDetail.getDepart());
                temp_time.setDepart_at(busTimeTableDetail.getDepart());
                timeList.add(temp_time);

                temp.setTimeList(timeList);

                busTimeTableStationDtoList.add(temp);
            }

            result.setStations(busTimeTableStationDtoList);
        }

        return result;
    }

    @Override
    public List<Integer> findBusListByDate(LocalDateTime date) {
        BusCalendar busCalendar = busCalendarService.findByDate(date);

        Optional<List<Integer>> optionalBusList = busTimeTableRepository.findDistinctBusByBusCalendar_Id(busCalendar.getId());
        return optionalBusList.orElseThrow(() -> new BusCalenderNotFoundException(date));
    }

    @Override
    public List<BusTimeTableDetail> findBusTimeTableDetailByInfo(BusTimeTableInfo busTimeTableInfo) {

        Optional<List<BusTimeTableDetail>> optionalBusTimeTableDetails = busTimeTableDetailRepository.findBusTimeTableDetailsByBusTimeTableInfo_IdOrderByDepart(busTimeTableInfo.getId());

        return optionalBusTimeTableDetails.orElseThrow(() -> new BusTimeTableDetailNotFoundException(busTimeTableInfo));
    }

    @Override
    public LocalDateTime getFirstBus(List<BusTimeTableDetail> busTimeTableDetails) {
        BusTimeTableDetail first = busTimeTableDetails.get(0);
        LocalTime depart = first.getDepart();

        return DateHandler.getTodayWith(depart.getHour(), depart.getMinute());
    }

    @Override
    public LocalDateTime getLastBus(List<BusTimeTableDetail> busTimeTableDetails) {
        BusTimeTableDetail last = busTimeTableDetails.get(busTimeTableDetails.size() - 1);
        LocalTime depart = last.getDepart();

        return DateHandler.getTodayWith(depart.getHour(), depart.getMinute());
    }


}
