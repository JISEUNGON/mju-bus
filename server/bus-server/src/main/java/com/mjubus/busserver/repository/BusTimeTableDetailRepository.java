package com.mjubus.busserver.repository;

import com.mjubus.busserver.domain.BusTimeTable;
import com.mjubus.busserver.domain.BusTimeTableDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusTimeTableDetailRepository extends JpaRepository<BusTimeTableDetail, Long> {
    List<BusTimeTableDetail> findBusTimeTableDetailByBusTimeTableInfo_Id(Long busTimeTableInfo_id);
}