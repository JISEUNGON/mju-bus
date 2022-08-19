package com.mjubus.server.service.route;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.StationDTO;

import java.util.List;
import java.util.Optional;

public interface RouteInterface {
    Route findByBus(Bus bus);

    Route findByRouteInfo(RouteInfo routeInfo);

    List<StationDTO> findStationsByRouteInfo(RouteInfo routeInfo);
    List<StationDTO> findStationsByBus(Bus bus);

    List<RouteDetail> findRouteDetailByRouteInfo(RouteInfo routeInfo);
    RouteInfo findRouteInfoByBus(Bus bus);
}

