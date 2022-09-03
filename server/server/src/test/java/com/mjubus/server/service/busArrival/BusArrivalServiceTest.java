package com.mjubus.server.service.busArrival;

import com.mjubus.server.controller.BusCalendarController;
import com.mjubus.server.dto.busArrival.BusArrivalResponse;
import com.mjubus.server.service.station.StationService;
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
class BusArrivalServiceTest {

  @Autowired
  BusArrivalService busArrivalService;

  @Autowired
  StationService stationService;

  @Test
  void findBusArrivalRemainByStation() {
    BusCalendarController busCalendarController = new BusCalendarController();

    LocalDateTime today = busCalendarController.setDate("2022-11-01 03:00");

    BusArrivalResponse result = busArrivalService.findBusArrivalRemainByStation(stationService.findStationById(2L));

    assertThat(result).isNotNull();
  }
}