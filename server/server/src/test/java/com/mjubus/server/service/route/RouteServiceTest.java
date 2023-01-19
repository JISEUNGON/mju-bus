package com.mjubus.server.service.route;

import com.mjubus.server.domain.Route;
import com.mjubus.server.domain.RouteDetail;
import com.mjubus.server.service.bus.BusService;
import com.mjubus.server.service.station.StationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RouteServiceTest {

    RouteService routeService;
    StationService stationService;
    BusService busService;

    @Autowired
    public RouteServiceTest(RouteService routeService, BusService busService, StationService stationService) {
        this.routeService = routeService;
        this.busService = busService;
        this.stationService = stationService;
    }

    @Test
    @DisplayName("[Service][findByBus] 버스로 노선 검색")
    void findByBus() {
        Route result = routeService.findByBus(busService.findBusByBusId(20L));

        assertThat(result.getBus().getId()).isEqualTo(20L);
    }

    @Test
    @DisplayName("[Service][findRouteDetailByStation] 정류장으로 노선 상세 검색")
    void findRouteDetailByStation() {
        List<RouteDetail> result = routeService.findRouteDetailByStation(stationService.findStationById(20L));
        assertThat(result.stream().filter(routeDetail -> routeDetail.getStation().getId() == 20L).count()).isEqualTo(1L);
    }

    @Test
    void findRouteDetailByRouteInfo() {
    }
}
