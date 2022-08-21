package com.mjubus.server.repository;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.BusTimeTable;
import com.mjubus.server.domain.RouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BusTimeTableRepository extends JpaRepository<BusTimeTable, String> {
    Optional<BusTimeTable> findBusTimeTableByBus_IdAndBusCalendar_Id(Long bus_id, Long busCalendar_id);

    Optional<BusTimeTable> findBusTimeTableByBusTimeTableInfo_IdAndBusCalendar_Id(Long busTimeTableInfo_id, Long busCalendar_id);

    Optional<List<BusTimeTable>> findBusTimeTablesByBusCalendar_Id(Long busCalendar_id);

    @Query(value = "SELECT DISTINCT bus_id FROM bus_timetable WHERE bus_calendar_id = :busCalendar", nativeQuery = true)
    Optional<List<Integer>> findDistinctBusByBusCalendar_Id(@Param("busCalendar") Long busCalendar_id);

}