package com.mjubus.server.controller;

import com.mjubus.server.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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
  
}