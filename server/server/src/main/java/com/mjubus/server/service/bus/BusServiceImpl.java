package com.mjubus.server.service.bus;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.busListDto.BusList;
import com.mjubus.server.dto.busRoute.RouteOrderDto;
import com.mjubus.server.dto.response.BusResponse;
import com.mjubus.server.dto.response.BusRouteResponse;
import com.mjubus.server.dto.response.BusStatusResponse;
import com.mjubus.server.dto.request.BusTimeTableRequest;
import com.mjubus.server.enums.BusEnum;
import com.mjubus.server.exception.Bus.BusNotFoundException;
import com.mjubus.server.repository.BusRepository;
import com.mjubus.server.service.busCalendar.BusCalendarService;
import com.mjubus.server.service.busTimeTable.BusTimeTableService;
import com.mjubus.server.service.route.RouteService;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class BusServiceImpl implements BusService {

    private final BusCalendarService busCalendarService;
    private final BusTimeTableService busTimeTableService;
    private final RouteService routeService;
    private final BusRepository busRepository;

    @Autowired
    public BusServiceImpl(@Lazy BusRepository busRepository, @Lazy BusCalendarService busCalendarService, @Lazy BusTimeTableService busTimeTableService, @Lazy RouteService routeService) {
        this.busRepository = busRepository;
        this.busCalendarService = busCalendarService;
        this.busTimeTableService = busTimeTableService;
        this.routeService = routeService;
    }

    /**
     * Controller 에서 호출되는 메소드
     * @param req BusTimeTableRequest
     * @return BusResponse
     */
    @Override
    public BusResponse findBus(BusTimeTableRequest req) {
        Bus bus = busRepository.findById(req.getId()).orElseThrow(() -> new BusNotFoundException(req.getId()));
        return BusResponse.of(bus);
    }

    /**
     * BusId 로 Bus 를 찾는 메소드
     * @param id BusId
     * @return Bus
     */
    @Override
    public Bus findBusByBusId(Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        return  bus.orElseThrow(() -> new BusNotFoundException(id));
    }

    /**
     * BusId 로 버스 운행 상태를 찾는 메소드
     * @param req BusId
     * @return BusStatusDto : 버스 운행 상태
     */
    @Override
    public BusStatusResponse getBusStatus(BusTimeTableRequest req) {
        // Bus, BusCalender 검증
        Bus bus = findBusByBusId(req.getId());

        // BusTimeTable
        BusTimeTable busTimeTable = busTimeTableService.findBusTimeTableByBus(bus);

        // 오늘
        LocalDateTime now = DateHandler.getToday();

        // 버스 시간표
        List<BusTimeTableDetail> timeTableDetails = busTimeTableService.findBusTimeTableDetailByInfo(busTimeTable.getBusTimeTableInfo());

        // 첫차
        LocalDateTime first_bus = busTimeTableService.getFirstBus(timeTableDetails);

        // 막차
        LocalDateTime last_bus = busTimeTableService.getLastBus(timeTableDetails).plusMinutes(15);

        // 오늘의 운행 일정
        if (now.isBefore(first_bus)) { // 첫차 전
            return BusStatusResponse.builder().status(BusStatusResponse.BEFORE_RUNNING)
                    .id(bus.getId())
                    .name(bus.getName())
                    .build();
        } else if (now.isAfter(last_bus)) { // 막차 후
            return BusStatusResponse.builder().status(BusStatusResponse.FINISH_RUNNING)
                    .id(bus.getId())
                    .name(bus.getName())
                    .build();
        } else { // 운행 중
            return BusStatusResponse.builder().status(BusStatusResponse.RUNNING)
                    .id(bus.getId())
                    .name(bus.getName())
                    .build();
        }
    }

    /**
     * BusId 로 버스 노선을 찾는 메소드
     * @param  req BusId
     */
    @Override
    public BusRouteResponse getBusRoute(BusTimeTableRequest req) {
        Bus bus = findBusByBusId(req.getId());
        Route route = routeService.findByBus(bus);

        RouteInfo routeInfo = route.getRouteInfo();
        List<RouteDetail> routeDetailList = routeService.findRouteDetailByRouteInfo(routeInfo);

        List<RouteOrderDto> routeOrderList = new LinkedList<>();
        for (RouteDetail routeDetail : routeDetailList) {
            RouteOrderDto temp = new RouteOrderDto();
            temp.setStation(routeDetail.getStation());
            temp.setRoute_order(routeDetail.getOrder());

            routeOrderList.add(temp);
        }


        return BusRouteResponse.builder()
            .id(bus.getId())
            .name(bus.getName())
            .stations(routeOrderList)
            .build();
    }

    /**
     * BusId 로 버스 운행 시간표를 찾는 메소드
     * @param date 날짜
     * @return BusTimeTableDto : 버스 운행 시간표
     */
    @Override
    public List<BusList> getBusListByDate(LocalDateTime date) {
        // 현재 일정으로 운행중인 버스 운행표
        List<Integer> busIdList = busTimeTableService.findBusListByDate(date);

        // 시내버스
        BusList busList_IN = new BusList();
        busList_IN.setType(BusEnum.시내버스.getValue());
        List<Bus> busListInner = new LinkedList<>();

        // 시외버스
        BusList busList_OUT = new BusList();
        busList_OUT.setType(BusEnum.시외버스.getValue());
        List<Bus> busListOuter = new LinkedList<>();

        // 시내/시외 분류
        for(Integer busId : busIdList) {
            Bus bus = findBusByBusId(Long.valueOf(busId));
            if (bus.getId() < 100) {
                busListInner.add(bus);
            } else {
                busListOuter.add(bus);
            }
        }

        // 결과 값 생성
        List<BusList> busLists = new LinkedList<>();

        busList_IN.setBusList(busListInner);
        busList_OUT.setBusList(busListOuter);

        busLists.add(busList_IN);
        busLists.add(busList_OUT);

        return busLists;
    }
}
