package com.mjubus.server.repository;

import com.mjubus.server.domain.MjuCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.Optional;

public interface MjuCalendarRepository extends JpaRepository<MjuCalendar, String> {
    @Query(value = "SELECT * from mju_calendar mc WHERE mc.start <= :date and :date <= mc.end", nativeQuery = true)
    Optional<MjuCalendar> findMjuCalendarByDate(@Param("date")Date date);
}