package com.mjubus.server.repository;

import com.mjubus.server.domain.BusArrival;
import com.mjubus.server.dto.BusArrivalDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BusArrivalRepository extends JpaRepository<BusArrival, String> {
    @Query(value = "select * from bus_arrival where station_id = ?1 and created_at >= ?2", nativeQuery = true)
    Optional<List<BusArrival>> findAllByStationIdAndDate(Long stationId, LocalDateTime localDateTime);

    @Query(value = "SELECT bus_id as busId, station_id as stationId, MIN(expected_at) as expectedAt" +
            "       FROM bus_arrival" +
            "       WHERE station_id = :station_id" +
            "       AND expected_at > NOW()" +
            "       GROUP BY bus_id;", nativeQuery = true)
    List<BusArrivalDto> findBusArrivalFromSchool(@Param("station_id")Long stationId);

    @Query(value = "SELECT ba.station_id as stationId, ba.bus_id as busId, MIN(ba.expected_at) as expectedAt, ba.created_at as createdAt" +
            "       FROM bus_arrival ba" +
            "       INNER JOIN (" +
            "           SELECT bus_id, station_id, MAX(created_at) as lastest_created_at" +
            "           FROM bus_arrival" +
            "           WHERE station_id = :station_id " +
            "           GROUP BY bus_id " +
            "       ) temp ON temp.bus_id = ba.bus_id AND temp.station_id = ba.station_id AND temp.lastest_created_at = ba.created_at " +
            "       GROUP BY ba.station_id, ba.bus_id, ba.created_at " +
            "       HAVING expectedAt > NOW();", nativeQuery = true)
    List<BusArrivalDto> findBusArrivalByStationId(@Param("station_id")Long stationId);

    @Query(value = "SELECT ba.station_id as stationId, ba.bus_id as busId, MIN(ba.expected_at) as expectedAt, ba.created_at as createdAt" +
            "       FROM bus_arrival ba" +
            "       INNER JOIN (" +
            "           SELECT bus_id, station_id, MAX(created_at) as lastest_created_at" +
            "           FROM bus_arrival" +
            "           WHERE station_id = :station_id " +
            "           GROUP BY bus_id " +
            "       ) temp ON temp.bus_id = ba.bus_id AND temp.station_id = ba.station_id AND temp.lastest_created_at = ba.created_at" +
            "       WHERE ba.sid = ba.pre_bus_arrival_sid" +
            "       GROUP BY ba.station_id, ba.bus_id, ba.created_at " +
            "       HAVING expectedAt > NOW()" +
            "       AND ba.bus_id >= 200", nativeQuery = true)
    List<BusArrivalDto> findRedBusArrivalByStationId(@Param("station_id")Long stationId);

    @Query(value = "select * from bus_arrival where bus_id = ?1 and created_at >= ?2 order by created_at desc limit 1", nativeQuery = true)
    BusArrival findBusArrivalByBus_idAndCreated(Long bus_id, LocalDateTime created);

    @Query(value = "SELECT station_id as stationId, bus_id as busId, MIN(expected_at) as expectedAt" +
            "       FROM bus_arrival" +
            "       WHERE station_id = 1" +
            "       AND expected_at >= NOW()" +
            "       GROUP BY bus_id", nativeQuery = true)
    List<BusArrivalDto> findShuttleBusFromStart();
}
