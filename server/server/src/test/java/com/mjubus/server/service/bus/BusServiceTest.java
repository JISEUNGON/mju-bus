package com.mjubus.server.service.bus;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.busListDto.BusList;
import com.mjubus.server.dto.busRoute.RouteOrderDto;
import com.mjubus.server.dto.request.BusTimeTableRequest;
import com.mjubus.server.dto.response.BusRouteResponse;
import com.mjubus.server.dto.response.BusStatusResponse;
import com.mjubus.server.dto.station.StationDTO;
import com.mjubus.server.exception.Bus.BusNotFoundException;
import com.mjubus.server.service.busCalendar.BusCalendarService;
import com.mjubus.server.service.busTimeTable.BusTimeTableService;
import com.mjubus.server.service.route.RouteService;
import com.mjubus.server.util.DateHandler;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

    private BusService busService;
    private BusCalendarService busCalendarService;
    private BusTimeTableService busTimeTableService;
    private RouteService routeService;

    @Autowired
    public BusServiceTest(BusService busService, BusCalendarService busCalendarService, BusTimeTableService busTimeTableService, RouteService routeService) {
        this.busService = busService;
        this.busCalendarService = busCalendarService;
        this.busTimeTableService = busTimeTableService;
        this.routeService = routeService;
    }

    @After
    public void reset() {
        DateHandler.reset();
    }

    @Test
    @DisplayName("[Service][FindBusByBusId] 버스 아이디로 버스 찾기")
    public void 테스트_findBusByBusId() {
        Bus bus = busService.findBusByBusId(10L);

        Assertions.assertEquals(10L, bus.getId());
    }

    @Test
    @DisplayName("[Service][FindBusByBusId] 버스 아이디로 버스 찾기 - 버스 없음")
    public void 테스트_findBusByBusId_BusNotFoundException() {
        Assertions.assertThrows(BusNotFoundException.class, () -> busService.findBusByBusId(999L));
    }

    @Test
    @DisplayName("[Service][getBusStatus] 버스 상태 검사 - 운행 전")
    public void 테스트_getBusStatusByBusId_운행전() {
        // 0시 ~ 첫차 출발 전
        LocalDateTime time;
        BusCalendar busCalendar;
        Bus bus;
        Route route;
        BusStatusResponse busStatusDto;

        time = DateHandler.getTodayWith(3, 30);
        DateHandler.setZonedDateTime(time);
        busCalendar = busCalendarService.findByDate(DateHandler.getToday());
        route = routeService.findAnyRouteByBusCalendar(busCalendar);
        bus = route.getBus();
        busStatusDto = busService.getBusStatus(BusTimeTableRequest.of(bus));


        Assertions.assertEquals(BusStatusResponse.BEFORE_RUNNING, busStatusDto.getStatus());
    }

    @Test
    @DisplayName("[Service][getBusStatus] 버스 상태 검사 - 운행 중")
    public void 테스트_getBusStatusByBusId_운행중() {
        // 첫차에 대해서 검증
        BusCalendar busCalendar;
        Bus bus;
        BusTimeTable busTimeTable;
        BusTimeTableInfo busTimeTableInfo;
        BusStatusResponse busStatusDto;
        List<BusTimeTableDetail> detailList;

        busCalendar = busCalendarService.findByDate(DateHandler.getToday());
        busTimeTable = busTimeTableService.findAnyBusTimeTableByBusCalendar(busCalendar);
        bus = busTimeTable.getBus();
        busTimeTableInfo = busTimeTable.getBusTimeTableInfo();
        detailList = busTimeTableService.findBusTimeTableDetailByInfo(busTimeTableInfo);

        // 첫차 시간으로 시간 설정
        LocalTime firstTime = detailList.get(0).getDepart();
        DateHandler.setZonedDateTime(DateHandler.getTodayWith(firstTime.getHour(), firstTime.getMinute()));
        busStatusDto = busService.getBusStatus(BusTimeTableRequest.of(bus));

        Assertions.assertEquals(BusStatusResponse.RUNNING, busStatusDto.getStatus());
    }

    @Test
    @DisplayName("[Service][getBusStatus] 버스 상태 검사 - 운행 중 - 마지막 정류장")
    public void 테스트_getBusStatusByBusId_운행중2() {
        // 첫차~막차 사이 시간에 대해서 검증
        BusCalendar busCalendar;
        Bus bus;
        BusTimeTable busTimeTable;
        BusTimeTableInfo busTimeTableInfo;
        BusStatusResponse busStatusDto;
        List<BusTimeTableDetail> detailList;

        busCalendar = busCalendarService.findByDate(DateHandler.getToday());
        busTimeTable = busTimeTableService.findAnyBusTimeTableByBusCalendar(busCalendar);
        bus = busTimeTable.getBus();
        busTimeTableInfo = busTimeTable.getBusTimeTableInfo();
        detailList = busTimeTableService.findBusTimeTableDetailByInfo(busTimeTableInfo);

        // 첫차~막차 사이 시간으로 시간 설정
        LocalTime randomAfterFirst = detailList.get(0).getDepart().plusMinutes((long) (Math.random() * 100));
        DateHandler.setZonedDateTime(DateHandler.getTodayWith(randomAfterFirst.getHour(), randomAfterFirst.getMinute()));
        busStatusDto = busService.getBusStatus(BusTimeTableRequest.of(bus));


        Assertions.assertEquals(BusStatusResponse.RUNNING, busStatusDto.getStatus());
    }

    @Test
    @DisplayName("[Service][getBusStatus] 버스 상태 검사 - 운행 중 - 마지막 정류장 - 마지막 차")
    public void 테스트_getBusStatusByBusId_운행중3() {
        // 막차 시간에 대해서 검증
        BusCalendar busCalendar;
        Bus bus;
        BusTimeTable busTimeTable;
        BusTimeTableInfo busTimeTableInfo;
        BusStatusResponse busStatusDto;
        List<BusTimeTableDetail> detailList;

        busCalendar = busCalendarService.findByDate(DateHandler.getToday());
        busTimeTable = busTimeTableService.findAnyBusTimeTableByBusCalendar(busCalendar);
        bus = busTimeTable.getBus();
        busTimeTableInfo = busTimeTable.getBusTimeTableInfo();
        detailList = busTimeTableService.findBusTimeTableDetailByInfo(busTimeTableInfo);

        // 막차 시간으로 시간 설정
        LocalTime lastTime = LocalTime.from(busTimeTableService.getLastBus(detailList));
        DateHandler.setZonedDateTime(DateHandler.getTodayWith(lastTime.getHour(), lastTime.getMinute()));
        busStatusDto = busService.getBusStatus(BusTimeTableRequest.of(bus));


        Assertions.assertEquals(BusStatusResponse.RUNNING, busStatusDto.getStatus());
    }

    @Test
    @DisplayName("[Service][getBusStatus] 버스 상태 검사 - 운행 중 - 마지막 정류장 - 마지막 차 - 1분 후")
    public void 테스트_getBusStatusByBusId_운행중4() {
        // 막차 출발 + 15분에 대해서 검증
        BusCalendar busCalendar;
        Bus bus;
        BusTimeTable busTimeTable;
        BusTimeTableInfo busTimeTableInfo;
        BusStatusResponse busStatusDto;
        List<BusTimeTableDetail> detailList;

        busCalendar = busCalendarService.findByDate(DateHandler.getToday());
        busTimeTable = busTimeTableService.findAnyBusTimeTableByBusCalendar(busCalendar);
        bus = busTimeTable.getBus();
        busTimeTableInfo = busTimeTable.getBusTimeTableInfo();
        detailList = busTimeTableService.findBusTimeTableDetailByInfo(busTimeTableInfo);

        // (막차 + 15분) 시간으로 시간 설정
        LocalTime lastTime = LocalTime.from(busTimeTableService.getLastBus(detailList)).plusMinutes(14);
        DateHandler.setZonedDateTime(DateHandler.getTodayWith(lastTime.getHour(), lastTime.getMinute()));
        busStatusDto = busService.getBusStatus(BusTimeTableRequest.of(bus));

        Assertions.assertEquals(BusStatusResponse.RUNNING, busStatusDto.getStatus());
    }

    @Test
    @DisplayName("[Service][getBusStatus] 버스 상태 검사 - 운행 종료 - 마지막 정류장 - 마지막 차 - 15분 후")
    public void 테스트_getBusStatusByBusId_운행종료() {
        // 막차 + 15 ~ 23:59
        LocalDateTime time;
        BusCalendar busCalendar;
        Bus bus;
        Route route;
        BusStatusResponse busStatusDto;

        time = DateHandler.getTodayWith(23, 30);
        DateHandler.setZonedDateTime(time);
        busCalendar = busCalendarService.findByDate(DateHandler.getToday());
        route = routeService.findAnyRouteByBusCalendar(busCalendar);
        bus = route.getBus();
        busStatusDto = busService.getBusStatus(BusTimeTableRequest.of(bus));

        Assertions.assertEquals(BusStatusResponse.FINISH_RUNNING, busStatusDto.getStatus());
    }

    @Test
    @DisplayName("[Service][getBusRoute] 버스ID로 버스 노선 조회 - 성공")
    void 테스트_getRouteByBusId_버스명() {
        BusRouteResponse busRoute = busService.getBusRoute(BusTimeTableRequest.of("20"));
        Bus bus = busService.findBusByBusId(20L);

        Assertions.assertEquals(bus.getName(), busRoute.getName());
    }

    @Test
    @DisplayName("[Service][findStationsByBus] 버스ID로 버스 노선의 정류장 조회 - 성공")
    void 테스트_getRouteByBusId_버스경로() {
        BusRouteResponse busRoute = busService.getBusRoute(BusTimeTableRequest.of("20"));
        Bus bus = busService.findBusByBusId(20L);

        List<StationDTO> stationDTOList = routeService.findStationsByBus(bus);
        List<RouteOrderDto> stations = busRoute.getStations();

        Assertions.assertEquals(stations.size(), stationDTOList.size());
        for (int i = 0; i < stations.size(); i++) {
            Assertions.assertEquals(stations.get(i).getId(),
                stationDTOList.get(i).getId());
        }
    }

    @Test
    @DisplayName("[Service][findBusByBusId] 버스ID로 버스 노선의 정류장 조회 - 실패 - 존재하지 않는 버스ID")
    void 테스트_getRouteByBusId_BusNotFoundException() {
        Assertions.assertThrows(BusNotFoundException.class, () -> busService.findBusByBusId(999L));
    }

    @Test
    @DisplayName("[Service][getBusListByDate] 현재 운행하는 버스가 없는 경우")
    void 테스트_getBusListByDate() {
        LocalDateTime now = DateHandler.getToday();
        List<BusList> busListByDate = busService.getBusListByDate(now);

        Assertions.assertNotNull(busListByDate);
    }


    @Test
    @DisplayName("[Service][getBusRoute] 셔틀버스 노선 조회 - 성공")
    void 테스트_getBusListByDate_셔틀버스() {
        LocalDateTime now = DateHandler.getToday();
        List<BusList> busListByDate = busService.getBusListByDate(now);

        Assertions.assertNotNull(busListByDate);
        BusList inner = busListByDate.get(0);
        List<Bus> busList = inner.getBusList();
        for (Bus bus : busList) {
            Assertions.assertTrue(bus.getId() < 100);
        }
    }

    @Test
    @DisplayName("[Service][getBusRoute] 일반버스 노선 조회 - 성공")
    void 테스트_getBusListByDate_빨간버스() {
        LocalDateTime now = DateHandler.getToday();
        List<BusList> busListByDate = busService.getBusListByDate(now);

        Assertions.assertNotNull(busListByDate);
        BusList outer = busListByDate.get(1);
        List<Bus> busList = outer.getBusList();
        for (Bus bus : busList) {
            Assertions.assertTrue(bus.getId() >= 200);
        }
    }

    @Test
    @DisplayName("[Service][findBus] 버스ID로 버스 조회 - 성공")
    void 테스트_findBusByBusId2() {
        Bus bus = busService.findBusByBusId(20L);

        Assertions.assertNotNull(bus);
    }

    @Test
    @DisplayName("[Service][findBus] 버스ID로 버스 조회 - 실패 - 존재하지 않는 버스ID")
    void 테스트_findBusByBusId2_BusNotFoundException() {
        Assertions.assertThrows(BusNotFoundException.class, () -> busService.findBusByBusId(999L));
    }

    @Test
    @DisplayName("[Service][getBusRoute] 버스ID로 버스 노선 조회 - 성공")
    void 테스트_getRouteByBusId_버스명2() {
        BusRouteResponse busRoute = busService.getBusRoute(BusTimeTableRequest.of("20"));
        Bus bus = busService.findBusByBusId(20L);

        Assertions.assertEquals(bus.getName(), busRoute.getName());
    }

    @Test
    @DisplayName("[Service][getBusRoute] 버스ID로 버스 노선 조회 - 실패")
    void 테스트_getRouteByBusId_BusNotFoundException2() {
        Assertions.assertThrows(BusNotFoundException.class, () -> busService.getBusRoute(BusTimeTableRequest.of("999")));
    }
}
