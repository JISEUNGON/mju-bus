package com.mjubus.server.service.route;

import com.mjubus.server.controller.BusCalendarController;
import com.mjubus.server.domain.Route;
import com.mjubus.server.domain.RouteInfo;
import com.mjubus.server.dto.StationDTO;
import com.mjubus.server.service.bus.BusService;
import com.mjubus.server.util.DateHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RouteServiceTest {

  @Autowired
  RouteService routeService;

  @Autowired
  BusService busService;

  BusCalendarController busCalendarController = new BusCalendarController();

  @Test
  void findByBus() {
    Route result = routeService.findByBus(busService.findBusByBusId(20L));

    assertThat(result.getBus().getId()).isEqualTo(20L);
  }

  @Test
  void findByRouteInfo() {
  }

  @Test
  void findStationsByRouteInfo() {
    busCalendarController.setDate("2022-09-01 09:09");
    DateHandler.reset();

  }

  @Test
  void findStationsByBus() {
    List<StationDTO> stationsByBus = routeService.findStationsByBus(busService.findBusByBusId(20L));

    System.out.println(stationsByBus.size());
  }

  @Test
  void findRouteDetailByRouteInfo() {
  }

  @Test
  void findRouteInfoByBus() {
    LocalDateTime today = busCalendarController.setDate("2022-11-01 09:00");
    RouteInfo result = routeService.findRouteInfoByBus(busService.findBusByBusId(20L));
    DateHandler.reset();

    assertThat(result.getName()).isEqualTo("시내 노선도");
  }
}