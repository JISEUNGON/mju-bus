package com.mjubus.server.repository;

import com.mjubus.server.domain.Route;
import com.mjubus.server.domain.RouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, String> {
    Optional<Route> findRouteByBus_IdAndBusCalendar_Id(Long bus_id, Long busCalendar_id);
}