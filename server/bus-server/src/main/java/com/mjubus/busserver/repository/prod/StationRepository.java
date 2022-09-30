package com.mjubus.busserver.repository.prod;

import com.mjubus.busserver.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}