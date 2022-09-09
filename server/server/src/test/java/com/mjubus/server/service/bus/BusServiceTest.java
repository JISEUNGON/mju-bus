package com.mjubus.server.service.bus;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.BusStatusDto;
import com.mjubus.server.dto.StationDTO;
import com.mjubus.server.dto.busListDto.BusList;
import com.mjubus.server.dto.busRoute.BusRouteDto;
import com.mjubus.server.dto.busRoute.RouteOrderDto;
import com.mjubus.server.exception.Bus.BusNotFoundException;
import com.mjubus.server.service.busCalendar.BusCalendarService;
import com.mjubus.server.service.busTimeTable.BusTimeTableService;
import com.mjubus.server.service.route.RouteService;
import com.mjubus.server.util.DateHandler;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@Transactional
public class BusServiceTest {

  @Autowired
  private BusService busService;

  @Autowired
  private BusCalendarService busCalendarService;

  @Autowired
  private BusTimeTableService busTimeTableService;

  @Autowired
  private RouteService routeService;

  @After
  public void reset() {
    DateHandler.reset();
  }

  @Test
  public void 테스트_findBusByBusId() {
    Bus bus = busService.findBusByBusId(10L);

    Assertions.assertEquals(10L, bus.getId());
  }

  @Test
  public void 테스트_findBusByBusId_BusNotFoundException() {
    Assertions.assertThrows(BusNotFoundException.class, () -> busService.findBusByBusId(999L));
  }

  @Test
  public void 테스트_getBusStatusByBusId_운행전() {
    // 0시 ~ 첫차 출발 전
    LocalDateTime time;
    BusCalendar busCalendar;
    Bus bus;
    Route route;
    BusStatusDto busStatusDto;

    time = DateHandler.getTodayWith(3, 30);
    DateHandler.setZonedDateTime(time);
    busCalendar = busCalendarService.findByDate(DateHandler.getToday());
    route = routeService.findAnyRouteByBusCalendar(busCalendar);
    bus = route.getBus();
    busStatusDto = busService.getBusStatusByBusId(bus.getId());

    Assertions.assertEquals(BusStatusDto.BEFORE_RUNNING, busStatusDto.getStatus());
  }

  @Test
  public void 테스트_getBusStatusByBusId_운행중() {
    // 첫차에 대해서 검증
    BusCalendar busCalendar;
    Bus bus;
    BusTimeTable busTimeTable;
    BusTimeTableInfo busTimeTableInfo;
    BusStatusDto busStatusDto;
    List<BusTimeTableDetail> detailList;

    busCalendar = busCalendarService.findByDate(DateHandler.getToday());
    busTimeTable = busTimeTableService.findAnyBusTimeTableByBusCalendar(busCalendar);
    bus = busTimeTable.getBus();
    busTimeTableInfo = busTimeTable.getBusTimeTableInfo();
    detailList = busTimeTableService.findBusTimeTableDetailByInfo(busTimeTableInfo);

    // 첫차 시간으로 시간 설정
    LocalTime firstTime = detailList.get(0).getDepart();
    DateHandler.setZonedDateTime(DateHandler.getTodayWith(firstTime.getHour(), firstTime.getMinute()));
    busStatusDto = busService.getBusStatusByBusId(bus.getId());

    Assertions.assertEquals(BusStatusDto.RUNNING, busStatusDto.getStatus());
  }

  @Test
  public void 테스트_getBusStatusByBusId_운행중2() {
    // 첫차~막차 사이 시간에 대해서 검증
    BusCalendar busCalendar;
    Bus bus;
    BusTimeTable busTimeTable;
    BusTimeTableInfo busTimeTableInfo;
    BusStatusDto busStatusDto;
    List<BusTimeTableDetail> detailList;

    busCalendar = busCalendarService.findByDate(DateHandler.getToday());
    busTimeTable = busTimeTableService.findAnyBusTimeTableByBusCalendar(busCalendar);
    bus = busTimeTable.getBus();
    busTimeTableInfo = busTimeTable.getBusTimeTableInfo();
    detailList = busTimeTableService.findBusTimeTableDetailByInfo(busTimeTableInfo);

    // 첫차~막차 사이 시간으로 시간 설정
    LocalTime randomAfterFirst = detailList.get(0).getDepart().plusMinutes((long) (Math.random() * 100));
    DateHandler.setZonedDateTime(DateHandler.getTodayWith(randomAfterFirst.getHour(), randomAfterFirst.getMinute()));
    busStatusDto = busService.getBusStatusByBusId(bus.getId());

    Assertions.assertEquals(BusStatusDto.RUNNING, busStatusDto.getStatus());
  }

  @Test
  public void 테스트_getBusStatusByBusId_운행중3() {
    // 막차 시간에 대해서 검증
    BusCalendar busCalendar;
    Bus bus;
    BusTimeTable busTimeTable;
    BusTimeTableInfo busTimeTableInfo;
    BusStatusDto busStatusDto;
    List<BusTimeTableDetail> detailList;

    busCalendar = busCalendarService.findByDate(DateHandler.getToday());
    busTimeTable = busTimeTableService.findAnyBusTimeTableByBusCalendar(busCalendar);
    bus = busTimeTable.getBus();
    busTimeTableInfo = busTimeTable.getBusTimeTableInfo();
    detailList = busTimeTableService.findBusTimeTableDetailByInfo(busTimeTableInfo);

    // 막차 시간으로 시간 설정
    LocalTime lastTime = LocalTime.from(busTimeTableService.getLastBus(detailList));
    DateHandler.setZonedDateTime(DateHandler.getTodayWith(lastTime.getHour(), lastTime.getMinute()));
    busStatusDto = busService.getBusStatusByBusId(bus.getId());

    Assertions.assertEquals(BusStatusDto.RUNNING, busStatusDto.getStatus());
  }

  @Test
  public void 테스트_getBusStatusByBusId_운행중4() {
    // 막차 출발 + 15분에 대해서 검증
    BusCalendar busCalendar;
    Bus bus;
    BusTimeTable busTimeTable;
    BusTimeTableInfo busTimeTableInfo;
    BusStatusDto busStatusDto;
    List<BusTimeTableDetail> detailList;

    busCalendar = busCalendarService.findByDate(DateHandler.getToday());
    busTimeTable = busTimeTableService.findAnyBusTimeTableByBusCalendar(busCalendar);
    bus = busTimeTable.getBus();
    busTimeTableInfo = busTimeTable.getBusTimeTableInfo();
    detailList = busTimeTableService.findBusTimeTableDetailByInfo(busTimeTableInfo);

    // (막차 + 15분) 시간으로 시간 설정
    LocalTime lastTime = LocalTime.from(busTimeTableService.getLastBus(detailList)).plusMinutes(14);
    DateHandler.setZonedDateTime(DateHandler.getTodayWith(lastTime.getHour(), lastTime.getMinute()));
    busStatusDto = busService.getBusStatusByBusId(bus.getId());

    Assertions.assertEquals( BusStatusDto.RUNNING, busStatusDto.getStatus());
  }

  @Test
  public void 테스트_getBusStatusByBusId_운행종료() {
    // 막차 + 15 ~ 23:59
    LocalDateTime time;
    BusCalendar busCalendar;
    Bus bus;
    Route route;
    BusStatusDto busStatusDto;

    time = DateHandler.getTodayWith(23, 30);
    DateHandler.setZonedDateTime(time);
    busCalendar = busCalendarService.findByDate(DateHandler.getToday());
    route = routeService.findAnyRouteByBusCalendar(busCalendar);
    bus = route.getBus();
    busStatusDto = busService.getBusStatusByBusId(bus.getId());

    Assertions.assertEquals(BusStatusDto.FINISH_RUNNING, busStatusDto.getStatus());
  }
  @Test
  void 테스트_getRouteByBusId_버스명() {
    BusRouteDto busRouteDto = busService.getRouteByBusId(20L);
    Bus bus = busService.findBusByBusId(20L);

    Assertions.assertEquals(bus.getName(), busRouteDto.getName());
  }

  @Test
  void 테스트_getRouteByBusId_버스경로() {
    BusRouteDto busRouteDto = busService.getRouteByBusId(20L);
    Bus bus = busService.findBusByBusId(20L);

    List<StationDTO> stationDTOList = routeService.findStationsByBus(bus);
    List<RouteOrderDto> stations = busRouteDto.getStations();

    Assertions.assertEquals(stations.size(), stationDTOList.size());
    for(int i = 0; i < stations.size(); i++) {
      Assertions.assertEquals(stations.get(i).getId(),
                              stationDTOList.get(i).getId());
    }
  }
  @Test
  void 테스트_getRouteByBusId_BusNotFoundException() {
    Assertions.assertThrows(BusNotFoundException.class, () -> busService.findBusByBusId(999L));
  }

  @Test
  void 테스트_getBusListByDate() {
    LocalDateTime now = DateHandler.getToday();
    List<BusList> busListByDate = busService.getBusListByDate(now);

    Assertions.assertNotNull(busListByDate);
  }

  @Test
  void 테스트_getBusListByDate_셔틀버스() {
    LocalDateTime now = DateHandler.getToday();
    List<BusList> busListByDate = busService.getBusListByDate(now);

    Assertions.assertNotNull(busListByDate);
    BusList inner = busListByDate.get(0);
    List<Bus> busList = inner.getBusList();
    for (Bus bus: busList) {
      Assertions.assertTrue(bus.getId() < 100);
    }
  }

  @Test
  void 테스트_getBusListByDate_빨간버스() {
    LocalDateTime now = DateHandler.getToday();
    List<BusList> busListByDate = busService.getBusListByDate(now);

    Assertions.assertNotNull(busListByDate);
    BusList outer = busListByDate.get(1);
    List<Bus> busList = outer.getBusList();
    for (Bus bus: busList) {
      Assertions.assertTrue(bus.getId() >= 200);
    }
  }
}