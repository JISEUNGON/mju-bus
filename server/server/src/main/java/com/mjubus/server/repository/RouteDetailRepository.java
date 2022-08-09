package com.mjubus.server.repository;

import com.mjubus.server.domain.RouteDetail;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.StationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RouteDetailRepository extends JpaRepository<RouteDetail, String> {

    Optional<List<RouteDetail>> findRouteDetailsByRouteInfo_Sid(String RouteInfoSid);

    @Query(value = "SELECT s.*, rd.route_order FROM route_detail rd INNER JOIN station s ON s.sid = rd.station_sid WHERE rd.route_info_sid = ?1 ORDER BY rd.route_order", nativeQuery = true)
    List<StationDTO> findStationsByRouteInfo_Sid(String routeInfoSid);
}
