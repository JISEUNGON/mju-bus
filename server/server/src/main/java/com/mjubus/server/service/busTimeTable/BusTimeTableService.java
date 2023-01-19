package com.mjubus.server.service.busTimeTable;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.request.BusTimeTableRequest;
import com.mjubus.server.dto.response.BusTimeTableResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface BusTimeTableService {

    /**
     * bus_timetable 테이블 관련 Service
     */
    BusTimeTable findBusTimeTableByBus(Bus bus);
    BusTimeTable findBusTimeTableByBusTimeTableInfo(BusTimeTableInfo busTimeTableInfo);
    List<BusTimeTable> findBusTimeTableListByCalendar(BusCalendar busCalendar);
    BusTimeTableResponse makeBusTimeTableByBusId(BusTimeTableRequest req);
    List<Integer> findBusListByDate(LocalDateTime date);

    BusTimeTable findAnyBusTimeTableByBusCalendar(BusCalendar busCalendar);

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
}
