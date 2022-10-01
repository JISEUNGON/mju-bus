package com.mjubus.busserver.repository.staging;

import com.mjubus.busserver.domain.BusTimeTableDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusTimeTableDetailStagingRepository extends JpaRepository<BusTimeTableDetail, Long> {
    List<BusTimeTableDetail> findBusTimeTableDetailByBusTimeTableInfo_Id(Long busTimeTableInfo_id);

    List<BusTimeTableDetail> findBusTimeTableDetailsByBusTimeTableInfo_Id(Long busTimeTableInfo_id);
}