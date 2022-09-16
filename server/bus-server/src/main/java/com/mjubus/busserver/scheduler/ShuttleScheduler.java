package com.mjubus.busserver.scheduler;


import com.mjubus.busserver.domain.*;
import com.mjubus.busserver.repository.*;
import com.mjubus.busserver.util.DateHandler;
import com.mjubus.busserver.util.ShuttleBusHandler;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ShuttleScheduler {

    @Autowired
    private BusCalendarRepository busCalendarRepository;

    @Autowired
    private BusTimeTableDetailRepository busTimeTableDetailRepository;

    @Autowired
    private BusTimeTableRepository busTimeTableRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusArrivalRepository busArrivalRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ShuttleBusHandler shuttleBusHandler;

    @Autowired
    private RouteDetailRepository routeDetailRepository;

    public BusCalendar findCalendarByDate(LocalDateTime date) {
            return busCalendarRepository.findBusCalendarByDate(date.toLocalDate(), date.toLocalTime(), DateHandler.getDayOfWeek(date)).get();
    }

    public List<BusTimeTableDetail> findBusTimeTableDetailListByInfo(BusTimeTableInfo busTimeTableInfo) {
        return busTimeTableDetailRepository.findBusTimeTableDetailsByBusTimeTableInfo_Id(busTimeTableInfo.getId());
    }

    public List<BusTimeTable> findTodayBusTimeTable() {
        LocalDateTime now = DateHandler.getToday();
        BusCalendar busCalendar = busCalendarRepository.findBusCalendarByDate(now.toLocalDate(), now.toLocalTime(), DateHandler.getDayOfWeek(now)).get();

        return busTimeTableRepository.findBusTimeTablesByBusCalendar_Id(busCalendar.getId());
    }

    public Route findRouteByBus(Bus bus) {
        LocalDateTime now = DateHandler.getToday();
        BusCalendar busCalendar = busCalendarRepository.findBusCalendarByDate(now.toLocalDate(), now.toLocalTime(), DateHandler.getDayOfWeek(now)).get();

        return routeRepository.findRouteByBus_IdAndBusCalendar_Id(bus.getId(), busCalendar.getId());
    }

    public List<RouteDetail> findRouteDetailByRouteInfo(RouteInfo routeInfo) {
        return routeDetailRepository.findRouteDetailByRouteInfo_IdOrderByOrder(routeInfo.getId());
    }

    public List<BusArrival> findBusArrivalByDate(LocalDateTime date) {
        return busArrivalRepository.findBusArrivalsByExpectedShuttleBus(date.truncatedTo(ChronoUnit.MINUTES));
    }

    @Scheduled(cron = "0 0 0 * * *")
    // 매일 0 0시에 당일 시간표를 생성한다.
    public void makeBusTimeTable() {
        List<BusTimeTable> timeTableList = findTodayBusTimeTable();

        for(BusTimeTable busTimeTable: timeTableList) {
            // 첫 정류장 알아내기
            Bus bus = busTimeTable.getBus();
            Route route = findRouteByBus(bus);
            List<RouteDetail> routeDetails = findRouteDetailByRouteInfo(route.getRouteInfo());
            Station startStation = routeDetails.get(0).getStation();

            List<BusTimeTableDetail> busTimeTableDetailList = findBusTimeTableDetailListByInfo(busTimeTable.getBusTimeTableInfo());
            for (BusTimeTableDetail busTimeTableDetail: busTimeTableDetailList) {
                UUID uuid = UUID.randomUUID();
                busArrivalRepository.save(
                        BusArrival.builder()
                                .bus(busTimeTable.getBus())
                                .station(startStation)
                                .expected(DateHandler.getTodayWith(busTimeTableDetail.getDepart()))
                                .sid(uuid.toString())
                                .preSid(uuid.toString())
                                .build()
                        );
            }
        }
    }

    @Scheduled(cron = "10 * * * * *") // 1분 마다
    public void dispatcher() throws IOException, ParseException {
        List<BusArrival> arrivalList = findBusArrivalByDate(DateHandler.getToday());
        System.out.println("Dispatcher : Started! ");
        System.out.println("Dispatcher : " + arrivalList);
        System.out.println(DateHandler.getToday().truncatedTo(ChronoUnit.MINUTES));
        if (arrivalList.isEmpty()) return;

        for(BusArrival busArrival: arrivalList) {
            System.out.println(busArrival.getExpected());
            shuttleBusHandler.predict(busArrival);
        }
    }
}
