package com.mjubus.server.service.station;

import com.mjubus.server.domain.Station;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StationServiceTest {

  @Autowired
  StationService stationService;

  @Test
  void findStationById() {
    Station result = stationService.findStationById(2L);

    assertThat(result.getName()).isEqualTo("상공회의소");
  }
}