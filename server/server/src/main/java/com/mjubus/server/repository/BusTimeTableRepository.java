package com.mjubus.server.repository;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.BusTimeTable;
import com.mjubus.server.domain.MjuCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusTimeTableRepository extends JpaRepository<BusTimeTable, String> {
    Optional<List<BusTimeTable>> findBusTimeTablesByBusAndMjuCalendar(Bus bus, MjuCalendar mjuCalendar);
}