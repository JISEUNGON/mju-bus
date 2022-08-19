package com.mjubus.server.repository;

import com.mjubus.server.domain.RouteDetail;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.StationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface RouteDetailRepository extends JpaRepository<RouteDetail, String> {

    Optional<List<RouteDetail>> findRouteDetailsByRouteInfo_IdOrderByOrder(Long routeInfo_id);

    @Query(value = "SELECT s.*, rd.route_order FROM route_detail rd INNER JOIN station s ON s.id = rd.station_id WHERE rd.route_info_id = ?1 ORDER BY rd.route_order", nativeQuery = true)
    Optional<List<StationDTO>> findStationsByRouteInfo_Id(Long routeInfoId);

}
