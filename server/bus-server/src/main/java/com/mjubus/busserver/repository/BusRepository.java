package com.mjubus.busserver.repository;

import com.mjubus.busserver.domain.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus, Long> {
    @Override
    Optional<Bus> findById(Long aLong);
}