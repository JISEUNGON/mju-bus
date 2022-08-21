package com.mjubus.busserver.repository;

import com.mjubus.busserver.domain.BusArrival;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusArrivalRepository extends JpaRepository<BusArrival, Long> {

}