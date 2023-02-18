package com.mjubus.server.service.bus;

import com.mjubus.server.domain.*;
import com.mjubus.server.dto.busListDto.BusList;
import com.mjubus.server.dto.busRoute.RouteOrderDto;
import com.mjubus.server.dto.response.BusListResponse;
import com.mjubus.server.dto.response.BusResponse;
import com.mjubus.server.dto.response.BusRouteResponse;
import com.mjubus.server.dto.response.BusStatusResponse;
import com.mjubus.server.dto.request.BusTimeTableRequest;
import com.mjubus.server.enums.BusEnum;
import com.mjubus.server.exception.Bus.BusNotFoundException;
import com.mjubus.server.repository.BusRepository;
import com.mjubus.server.repository.BusTimeTableRepository;
import com.mjubus.server.service.busCalendar.BusCalendarService;
import com.mjubus.server.service.busTimeTable.BusTimeTableService;
import com.mjubus.server.service.route.RouteService;
import com.mjubus.server.util.DateHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements BusService {

    private final RouteService routeService;
    private final BusRepository busRepository;
    private final BusTimeTableRepository busTimeTableRepository;

    private final BusTimeTableService busTimeTableService;

    private final BusCalendarService busCalendarService;

    public BusServiceImpl(RouteService routeService, BusRepository busRepository, BusTimeTableRepository busTimeTableRepository, @Lazy BusTimeTableService busTimeTableService, BusCalendarService busCalendarService) {
        this.routeService = routeService;
        this.busRepository = busRepository;
        this.busTimeTableRepository = busTimeTableRepository;
        this.busTimeTableService = busTimeTableService;
        this.busCalendarService = busCalendarService;
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
     * 날짜로 운행중인 버스 리스트를 찾는 메소드
     * @param date 날짜
     * @return List<Bus>
     */
    @Override
    public List<Bus> findBusListByDate(LocalDateTime date) {
        BusCalendar busCalendar = busCalendarService.findBusCalendarByDate(date);
        List<BusTimeTable> busTimeTableList = busTimeTableRepository.findBusTimeTablesByBusCalendar_Id(busCalendar.getId()).orElseThrow(() -> new BusNotFoundException(busCalendar.getId()));
        return busTimeTableList.stream().map(BusTimeTable::getBus).collect(Collectors.toList());
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
    public BusListResponse getBusListByDate(LocalDateTime date) {
        // 현재 일정으로 운행중인 버스 운행표
        List<Bus> busList = findBusListByDate(date);

        // 시내버스
        List<Bus> sineBusList = busList.stream().filter(bus -> bus.getId() < 100).collect(Collectors.toList());

        // 시외버스
        List<Bus> siwaBusList = busList.stream().filter(bus -> bus.getId() >= 100).collect(Collectors.toList());

        return BusListResponse.of(sineBusList, siwaBusList);

    }
}
