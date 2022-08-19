package com.mjubus.server.repository;

import com.mjubus.server.domain.BusArrival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BusArrivalRepository extends JpaRepository<BusArrival, String> {
    @Query(value = "select * from bus_arrival where station_id = ?1 and created_at >= ?2", nativeQuery = true)
    Optional<List<BusArrival>> findAllByStationIdAndDate(Long stationId, LocalDateTime localDateTime);



    @Query(value = "select * from bus_arrival where bus_id = ?1 and created_at >= ?2 order by created_at desc limit 1", nativeQuery = true)
    BusArrival findBusArrivalByBus_idAndCreated(Long bus_id, LocalDateTime created);
}
