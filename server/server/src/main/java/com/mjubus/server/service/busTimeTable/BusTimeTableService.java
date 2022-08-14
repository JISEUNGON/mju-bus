package com.mjubus.server.service.busTimeTable;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.BusTimeTableResponseDto;
import com.mjubus.server.exception.BusTimeTable.BusTimeTableDetailNotFoundException;
import com.mjubus.server.exception.BusTimeTable.BusTimeTableNotFoundException;
import com.mjubus.server.repository.BusTimeTableDetailRepository;
import com.mjubus.server.repository.BusTimeTableRepository;
import com.mjubus.server.service.bus.BusService;
import com.mjubus.server.service.mjuCalendar.BusCalendarService;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

//    @Override
//    public List<BusTimeTable> findBusTimeTableListByBus(Bus bus) {
//        BusCalendar busCalendar = busCalendarService.findByDate(DateHandler.getToday());
//
//        busTimeTableRepository.
//    }
//
    @Override
    public List<BusTimeTableDetail> findBusTimeTableDetailByInfo(BusTimeTableInfo busTimeTableInfo) {

        Optional<List<BusTimeTableDetail>> optionalBusTimeTableDetails = busTimeTableDetailRepository.findBusTimeTableDetailsByBusTimeTableInfo_Id(busTimeTableInfo.getId());

        return optionalBusTimeTableDetails.orElseThrow(() -> new BusTimeTableDetailNotFoundException(busTimeTableInfo));
    }

    @Override
    public LocalDateTime getFirstBus(List<BusTimeTableDetail> busTimeTableDetails) {
        BusTimeTableDetail first = busTimeTableDetails.get(0);
        LocalDateTime depart = first.getDepart();

        return DateHandler.getTodayWith(depart.getHour(), depart.getMinute());
    }

    @Override
    public LocalDateTime getLastBus(List<BusTimeTableDetail> busTimeTableDetails) {
        BusTimeTableDetail last = busTimeTableDetails.get(busTimeTableDetails.size() - 1);
        LocalDateTime depart = last.getDepart();

        return DateHandler.getTodayWith(depart.getHour(), depart.getMinute());
    }
//
//    @Override
//    public BusTimeTable findListByBus(Bus bus) {
//        BusCalendar busCalendar = busCalendarService.findByDate(DateHandler.getToday());
//
//        Optional<BusTimeTable> optionalBusTimeTable = busTimeTableRepository.findBusTimeTableByBus_IdAndBusCalendar_Id(bus.getId(), busCalendar.getId());
//        return optionalBusTimeTable.orElseThrow(() -> new BusTimeTableNotFoundException(bus, busCalendar, null));
//    }



//    @Override
    public BusTimeTableResponseDto getBusTimeTableResponseDtoById(Long id) {
        /**
         * # 2안
         * {
         *   "id" : 10,
         *   "stations" : [
         *      "name" : "진입로",
         *      "timeList" : [
         *         {
         *              "depart_at" : "08:00",
         *              "arrive_at" : "08:15"
         *         },
         *         ...
         *      ]
         *   }
         *   "busName" : "명지대역",
         *   "timeTableList" : [
         *     {
         *       "stationName": "진입로",
         *       "depart_at": "08:00",
         *       "arrive_at": "08:15"
         *     },
         *      {
         *       "stationName": "진입로",
         *       "depart_at": "08:10",
         *       "arrive_at": "08:25"
         *     }
         *    ]
         * }
         */
        /**
         // 1. 핵심 정류장을 반환하는 API
         *  2. 핵심 정류장에 대한 도착 예정 시간을 반환하는?
         */
        BusTimeTableResponseDto busTimeTableResponseDto = new BusTimeTableResponseDto();

        // 버스, 캘린더 검증
        BusTimeTable busTimeTable = findBusTimeTableByBus(null);
        Bus bus = busService.findBusByBusId(id);

        // 버스 시간표 Detail
//        BusTimeTableInfo busTimeTableInfo = busTimeTable.getBusTimeTableInfo();
//        Optional<List<BusTimeTableDetail>> optionalBusTimeTableDetailList = findDetailByInfoId(busTimeTableInfo.getId());
//
//        // Detail 검증
////        List<BusTimeTableDetail> busTimeTableDetailList = optionalBusTimeTableDetailList.orElseThrow(() -> new BusTimeTableNotFoundException(bus, busc));
//
//        // 데이터 생성
//        List<BusTimeTableDetailDto> busTimeTableDetailDtoList = new LinkedList<>();
//        for(int i = 0; i < busTimeTableDetailList.size(); i++) {
//            BusTimeTableDetailDto busTimeTableDetailDto = new BusTimeTableDetailDto();

//        }

        // 데이터 삽입
        busTimeTableResponseDto.setBus(bus);
        return null;
    }
}
