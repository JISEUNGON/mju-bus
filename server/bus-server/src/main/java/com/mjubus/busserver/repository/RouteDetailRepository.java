package com.mjubus.busserver.repository;

import com.mjubus.busserver.domain.RouteDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteDetailRepository extends JpaRepository<RouteDetail, Long> {
    List<RouteDetail> findRouteDetailByRouteInfo_Id(Long routeInfo_id);
}