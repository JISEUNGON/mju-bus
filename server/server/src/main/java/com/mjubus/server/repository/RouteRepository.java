package com.mjubus.server.repository;

import com.mjubus.server.domain.Route;
import com.mjubus.server.domain.RouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, String> {
    @Query(value = "select * from route r where r.bus_sid = ?1 and r.bus_calendar_sid = ?2", nativeQuery = true)
    Optional<Route> findRouteByBus_SidAndAndBusCalendar_Sid(String bus_sid, String busCalendar_sid);
}