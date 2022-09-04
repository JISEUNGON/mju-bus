package com.mjubus.server.service.bus;

import com.mjubus.server.controller.BusCalendarController;
import com.mjubus.server.domain.Bus;
import com.mjubus.server.dto.BusStatusDto;
import com.mjubus.server.dto.busRoute.BusRouteDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BusServiceTest {

  @Autowired
  private BusService busService;

  @Test
  void findBusByBusId() {
    Bus result = busService.findBusByBusId(10L);

    assertThat(result.getName()).isEqualTo("명지대역");
  }

  @Test
  void getBusStatusByBusId() {
    BusStatusDto result = busService.getBusStatusByBusId(20L);

    assertThat(result.getStatus()).isEqualTo(3);
  }

  @Test
  void getRouteByBusId() {
    BusRouteDto result = busService.getRouteByBusId(20L);

    assertThat(result.getName()).isEqualTo("시내");
  }

  @Test
  void getBusListByDate() {
    BusCalendarController busCalendarController = new BusCalendarController();

    LocalDateTime today = busCalendarController.setDate("2022-11-01 23:00");

    System.out.println(busService.getBusListByDate(today).get(0).getBusList().size());
  }
}