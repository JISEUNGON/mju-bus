package com.mjubus.server.service.busTimeTable;

import com.mjubus.server.repository.BusTimeTableInfoRepository;
import com.mjubus.server.service.bus.BusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BusTimeTableServiceTest {

  @Autowired
  BusTimeTableService busTimeTableService;
  @Autowired
  BusService busService;
//  BusCalendarController busCalendarController = new BusCalendarController();
  @Autowired
  BusTimeTableInfoRepository busTimeTableInfo;
//
//  @Test
//  void findBusTimeTableByBus() {
//    LocalDateTime today = busCalendarController.setDate(BusCalendarSetDateRequest.of("2022-11-01 09:00"));
//
//    BusTimeTable result = busTimeTableService.findBusTimeTableByBus(busService.findBusByBusId(10L));
//
//    DateHandler.reset();
//
//    assertThat(result).isNotNull();
//  }
//
//  @Test
//  void findBusTimeTableByBusTimeTableInfo() {
//    LocalDateTime today = busCalendarController.setDate("2022-11-01 09:00");
//
//    BusTimeTableInfo info = busTimeTableInfo.findById(2L).get();
//
//    BusTimeTable result = busTimeTableService.findBusTimeTableByBusTimeTableInfo(info);
//
//    DateHandler.reset();
//
//    assertThat(result.getBus()).isEqualTo(busService.findBusByBusId(10L));
//  }

  @Test
  void findBusTimeTableListByCalendar() {
  }

  @Test
  void makeBusTimeTableByBusId() {
  }

  @Test
  void findBusListByDate() {
  }

  @Test
  void findBusTimeTableDetailByInfo() {
  }

  @Test
  void getFirstBus() {
  }

  @Test
  void getLastBus() {

  }
}
