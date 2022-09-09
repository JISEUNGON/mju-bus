package com.mjubus.server.repository;

import com.mjubus.server.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, String> {
    Optional<Route> findRouteByBus_IdAndBusCalendar_Id(Long bus_id, Long busCalendar_id);

    Optional<Route> findRouteByRouteInfo_IdAndBus_Id(Long routeInfo_id, Long bus_id);

    @Query(value = "SELECT *" +
            "       FROM route" +
            "       WHERE bus_calendar_id = :busCalendar" +
            "       LIMIT 1",nativeQuery = true)
    Optional<Route> findRouteByBusCalendar_Id(@Param(value = "busCalendar") Long busCalendar_id);
}