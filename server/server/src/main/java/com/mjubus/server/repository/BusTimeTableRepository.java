package com.mjubus.server.repository;

import com.mjubus.server.domain.BusTimeTable;
import com.mjubus.server.domain.RouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BusTimeTableRepository extends JpaRepository<BusTimeTable, String> {
}