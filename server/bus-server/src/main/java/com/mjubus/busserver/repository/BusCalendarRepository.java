package com.mjubus.busserver.repository;

import com.mjubus.busserver.domain.BusCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public interface BusCalendarRepository extends JpaRepository<BusCalendar, Long> {

    @Query(value = "SELECT * from bus_calendar bc WHERE bc.start_at <= :date and :date <= bc.end_at and bc.start_time <= :time and :time <= bc.end_time and  bc.day_of_week & :day > 0 ORDER BY priority DESC LIMIT 1", nativeQuery = true)
    Optional<BusCalendar> findBusCalendarByDate(@Param("date") LocalDate date, @Param("time") LocalTime time, @Param("day") int day);


}