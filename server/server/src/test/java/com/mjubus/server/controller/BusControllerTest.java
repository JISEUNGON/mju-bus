package com.mjubus.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BusControllerTest {

  @Autowired
  private BusController busController;

  @Autowired
  private BusCalendarController busCalendarController;

//  @Test
//  void 해당_날짜에_운행하는지_확인() {
//    busCalendarController.setDate("2023-01-01 23:00");
//
//    List<BusList> result = busController.busTimeTable();
//
//    for(int i = 0; i < result.size(); i++) {
//      for(int j = 0; j < result.get(i).getBusList().size(); j++) {
//        System.out.println(result.get(i).getBusList().get(j).getName());
//      }
//    }
//    DateHandler.reset();
//
//  }
//
//  @Test
//  void 버스리스트_확인() {
//    BusTimeTableResponseDto result = busController.busList(20L);
//
//    System.out.println(result.getName() + " " + result.getId() + " " + result.getStations().get(0).getTimeList().size());
//  }
//
//  @Test
//  void 정보가_올바른지() {
//    Bus result = busController.info(10L);
//
//    assertThat(result.getName()).isEqualTo("명지대역");
//
//    System.out.println(result.getName());
//  }
//
//  @Test
//  void 운행_확인() {
//    busCalendarController.setDate("2022-09-02 10:00");
//
//    BusStatusDto result = busController.status(20L);
//
//    switch (result.getStatus()) {
//      case 1:
//        System.out.println("운행 전");
//        break;
//      case 2:
//        System.out.println("운행 중");
//        break;
//      case 3:
//        System.out.println("운행 종료");
//        break;
//    }
//    DateHandler.reset();
//
//  }
//
//  @Test
//  void stationList() {
//    BusRouteDto result = busController.stationList(20L);
//
//    assertThat(result.getName()).isEqualTo("시내");
//  }
}