package com.mjubus.server.service.busArrival;

import com.mjubus.server.service.station.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BusArrivalServiceTest {

  @Autowired
  BusArrivalService busArrivalService;

  @Autowired
  StationService stationService;

}