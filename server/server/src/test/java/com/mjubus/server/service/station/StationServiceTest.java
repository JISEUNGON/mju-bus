package com.mjubus.server.service.station;

import com.mjubus.server.domain.Station;
import com.mjubus.server.exception.Station.StationNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StationServiceTest {

  @Autowired
  private StationService stationService;

  @Test
  void 테스트_findStationById() {
    Station stationById = stationService.findStationById(1L);

    Assertions.assertNotNull(stationById);
    Assertions.assertEquals(stationById.getId(), 1L);
  }

  @Test()
  void 테스트_StationNotFoundException() {
    Assertions.assertThrows(StationNotFoundException.class, () -> {
      stationService.findStationById(999L);
    });
  }
}