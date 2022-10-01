package com.mjubus.busserver.repository.staging;

import com.mjubus.busserver.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationStagingRepository extends JpaRepository<Station, Long> {
}