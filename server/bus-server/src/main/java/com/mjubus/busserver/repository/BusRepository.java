package com.mjubus.busserver.repository;

import com.mjubus.busserver.domain.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
}