package com.mjubus.server.repository;

import com.mjubus.server.domain.BusTimeTableDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusTimeTableDetailRepository extends JpaRepository<BusTimeTableDetail, Long> {
    Optional<List<BusTimeTableDetail>> findBusTimeTableDetailsByBusTimeTableInfo_Id(Long busTimeTableInfo_id);
}