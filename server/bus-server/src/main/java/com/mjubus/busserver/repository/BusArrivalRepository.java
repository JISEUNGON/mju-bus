package com.mjubus.busserver.repository;

import com.mjubus.busserver.domain.BusArrival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BusArrivalRepository extends JpaRepository<BusArrival, Long> {



    @Query(value = "SELECT *" +
            "       FROM bus_arrival" +
            "       WHERE expected_at = :datetime" +
            "       AND bus_id < 100", nativeQuery = true)
    List<BusArrival> findBusArrivalsByExpectedShuttleBus(@Param(value = "datetime") LocalDateTime expected);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE bus_arrival SET bus_arrival.expected_at = :expectedAt WHERE bus_arrival.pre_bus_arrival_sid = :pre_busArrival_sid AND bus_arrival.station_id = :stationId", nativeQuery = true)
    void updateBusArrivalByPreSid(@Param(value = "pre_busArrival_sid") String pre_busArrival_sid, @Param("expectedAt") LocalDateTime expectedAt, @Param("stationId") Long StationId);
}