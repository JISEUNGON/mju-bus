package com.mjubus.busserver.repository.prod;

import com.mjubus.busserver.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Route findRouteByBus_IdAndBusCalendar_Id(Long bus_id, Long busCalendar_id);

    List<Route> findRoutesByBusCalendar_Id(Long busCalendar_id);
}