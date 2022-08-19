package com.mjubus.busserver.repository;

import com.mjubus.busserver.domain.BusCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BusCalendarRepository extends JpaRepository<BusCalendar, Long> {

    @Query(value = "SELECT * from bus_calendar bc WHERE bc.start_at <= :date and :date <= bc.end_at and bc.weekend = 1 ORDER BY priority DESC LIMIT 1", nativeQuery = true)
    BusCalendar findBusCalendarByDateOnWeekend(@Param("date") LocalDateTime date);

    @Query(value = "SELECT * from bus_calendar bc WHERE bc.start_at <= :date and :date <= bc.end_at and bc.weekend = 0 ORDER BY priority DESC LIMIT 1", nativeQuery = true)
    BusCalendar findBusCalendarByDateOnWeekday(@Param("date")LocalDateTime date);


}