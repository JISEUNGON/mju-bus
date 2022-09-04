package com.mjubus.server.controller;

import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.busArrival.BusArrivalResponse;
import com.mjubus.server.util.DateHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class StationControllerTest {

  @Autowired
  private StationController stationController;

  @Autowired
  BusCalendarController busCalendarController;

  @Test
  void 정보조회() {
    Station result = stationController.info(20L);
    assertThat(result.getName()).isEqualTo("동부경찰서");

    System.out.println(result.getName() + " " + result.getLatitude() + " " + result.getLatitude());
  }

  @Test
  void 도착_버스틀_조회() {
    BusArrivalResponse result = stationController.busRemains(3L);

    System.out.println("=============");

    busCalendarController.setDate("2022-09-01 09:00");

    for(int i = 0; i < result.getBusList().size(); i++) {
      System.out.println(result.getBusList().size());
    }

    System.out.println("=============");
  }
}