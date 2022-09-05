package com.mjubus.busserver.repository;

import com.mjubus.busserver.domain.BusTimeTable;
import com.mjubus.busserver.domain.BusTimeTableDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusTimeTableDetailRepository extends JpaRepository<BusTimeTableDetail, Long> {
    List<BusTimeTableDetail> findBusTimeTableDetailByBusTimeTableInfo_Id(Long busTimeTableInfo_id);

    @Query(value = "SELECT btd.*" +
            "       FROM bus_timetable_info bti " +
            "       INNER JOIN bus_timetable_detail btd ON bti.id = btd.bus_timetable_info_id" +
            "       WHERE bus_timetable_info_id = 1" +
            "       OR bus_timetable_info_id = 2" +
            "       OR bus_timetable_info_id = 3" +
            "       ORDER BY depart_at;", nativeQuery = true)
    List<BusTimeTableDetail> findShuttleBusTimeTableDetails();
}