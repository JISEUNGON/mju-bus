package com.mjubus.server.repository;

import com.mjubus.server.domain.RouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteInfoRepository extends JpaRepository<RouteInfo, String> {
}