package com.mjubus.busserver.repository;

import com.mjubus.busserver.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Route findRouteByBus_IdAndBusCalendar_Id(Long bus_id, Long busCalendar_id);

    Route findRouteByRouteInfo_IdAndBus_Id(Long routeInfo_id, Long bus_id);
}