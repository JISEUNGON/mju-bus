package com.mjubus.server.repository;

import com.mjubus.server.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, String> {
  Optional<Station> findStationByType(int type);
}