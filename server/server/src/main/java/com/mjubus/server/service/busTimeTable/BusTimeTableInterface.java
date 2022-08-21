package com.mjubus.server.service.busTimeTable;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.BusTimeTableResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BusTimeTableInterface {

    /**
     * bus_timetable 테이블 관련 Service
     */
    BusTimeTable findBusTimeTableByBus(Bus bus);
    BusTimeTable findBusTimeTableByBusTimeTableInfo(BusTimeTableInfo busTimeTableInfo);
    List<BusTimeTable> findBusTimeTableListByCalendar(BusCalendar busCalendar);
    BusTimeTableResponseDto makeBusTimeTableByBusId(Long busId);
    List<Integer> findBusListByDate(LocalDateTime date);

    /**
     * bus_timetable_info 테이블 관련 Service
     */



    /**
     * bus_timetable_detail 테이블 관련 Service
     */
    List<BusTimeTableDetail> findBusTimeTableDetailByInfo(BusTimeTableInfo busTimeTableInfo);

    /**
     * 기타 ?...
     */
    LocalDateTime getFirstBus(List<BusTimeTableDetail> busTimeTableDetails);
    LocalDateTime getLastBus(List<BusTimeTableDetail> busTimeTableDetails);
//    BusTimeTableResponseDto getBusTimeTableResponseDtoById(Long id);
}
