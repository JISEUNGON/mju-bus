package com.mjubus.busserver.repository.prod;

import com.mjubus.busserver.domain.BusTimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusTimeTableRepository extends JpaRepository<BusTimeTable, Long> {

    BusTimeTable findBusTimeTableByBus_IdAndBusCalendar_Id(Long bus_id, Long busCalendar_id);

    List<BusTimeTable> findBusTimeTablesByBusCalendar_Id(Long busCalendar_id);
}