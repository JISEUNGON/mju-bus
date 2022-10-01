package com.mjubus.busserver.repository.prod;

import com.mjubus.busserver.domain.BusTimeTable;
import com.mjubus.busserver.domain.BusTimeTableDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusTimeTableDetailRepository extends JpaRepository<BusTimeTableDetail, Long> {
    List<BusTimeTableDetail> findBusTimeTableDetailByBusTimeTableInfo_Id(Long busTimeTableInfo_id);

    List<BusTimeTableDetail> findBusTimeTableDetailsByBusTimeTableInfo_Id(Long busTimeTableInfo_id);
}