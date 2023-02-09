package com.mjubus.server.repository;

import com.mjubus.server.domain.TaxiDestination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiDestinationRepository extends JpaRepository<TaxiDestination, Long> {
}
