package com.mjubus.busserver.repository.prod;

import com.mjubus.busserver.domain.RouteDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteDetailRepository extends JpaRepository<RouteDetail, Long> {
    List<RouteDetail> findRouteDetailByRouteInfo_IdOrderByOrder(Long routeInfo_id);
}