package com.mjubus.server.repository;

import com.mjubus.server.domain.PathInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PathInfoRepository extends JpaRepository<PathInfo, Long> {

    Optional<PathInfo> findPathInfoByStationFrom_IdAndStationAt_Id(Long stationFrom_id, Long stationAt_id);
}