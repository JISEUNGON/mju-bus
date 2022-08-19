package com.mjubus.busserver.repository;

import com.mjubus.busserver.domain.BusTimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusTimeTableRepository extends JpaRepository<BusTimeTable, Long> {

    BusTimeTable findBusTimeTableByBus_IdAndBusCalendar_Id(Long bus_id, Long busCalendar_id);
}