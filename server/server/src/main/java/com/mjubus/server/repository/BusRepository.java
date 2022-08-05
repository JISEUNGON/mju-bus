package com.mjubus.server.repository;

import com.mjubus.server.domain.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus, String> {
    Optional<Bus> findByType(int type);
}