package com.mjubus.server.repository;

import com.mjubus.server.domain.StationLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationLocationRepository extends JpaRepository<StationLocation, Long> {
}
