package com.mjubus.server.repository;

import com.mjubus.server.domain.BusArrival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BusArrivalRepository extends JpaRepository<BusArrival, String> {
    @Query(value = "select * from bus_arrival where station_id = ?1 and created_at >= ?2", nativeQuery = true)
    Optional<List<BusArrival>> findAllByStationIdAndDate(Long stationId, LocalDateTime localDateTime);

    @Query(value = "SELECT bus_arrival.* " +
            "       FROM bus_arrival " +
            "       INNER JOIN (" +
            "           SELECT bus_id, MAX(created_at) as lastest " +
            "           FROM bus_arrival " +
            "           GROUP BY bus_id" +
            "       ) ranked_arrival ON ranked_arrival.lastest = bus_arrival.created_at AND ranked_arrival.bus_id = bus_arrival.bus_id " +
            "       WHERE expected_at >= now() " +
            "       AND bus_arrival.station_id = :station_id", nativeQuery = true)
    Optional<List<BusArrival>> findBusArrivalByStationId(@Param("station_id")Long stationId);


    @Query(value = "select * from bus_arrival where bus_id = ?1 and created_at >= ?2 order by created_at desc limit 1", nativeQuery = true)
    BusArrival findBusArrivalByBus_idAndCreated(Long bus_id, LocalDateTime created);

}
