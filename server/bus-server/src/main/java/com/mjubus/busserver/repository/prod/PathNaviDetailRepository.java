package com.mjubus.busserver.repository.prod;

import com.mjubus.busserver.domain.PathInfo;
import com.mjubus.busserver.domain.PathNaviDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PathNaviDetailRepository extends JpaRepository<PathNaviDetail, PathInfo> {

    List<PathNaviDetail> findPathNaviDetailsByStationFrom_IdAndStationAt_IdOrderByNaviOrder(Long stationFrom_id, Long stationAt_id);

    List<PathNaviDetail> findPathNaviDetailsByPathInfo_Id(Long pathInfo_id);
}