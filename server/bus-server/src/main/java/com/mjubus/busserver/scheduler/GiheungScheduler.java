package com.mjubus.busserver.scheduler;

import com.mjubus.busserver.domain.*;
import com.mjubus.busserver.repository.*;
import com.mjubus.busserver.util.DateHandler;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class GiheungScheduler {
    @Autowired
    BusCalendarRepository busCalendarRepository;

    @Autowired
    BusRepository busRepository;

    @Autowired
    RouteDetailRepository routeDetailRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    BusArrivalRepository busArrivalRepository;

    private BusCalendar getBusCalendar() {
        LocalDateTime today = DateHandler.getToday();
        return busCalendarRepository.findBusCalendarByDate(today.toLocalDate(), today.toLocalTime(), DateHandler.getDayOfWeek(today)).get();
    }

    private Bus getBus() {
        Bus bus = busRepository.findById(30L).get();
        return bus;
    }

    private Route getRoute(Bus bus, BusCalendar busCalendar) {
        return routeRepository.findRouteByBus_IdAndBusCalendar_Id(bus.getId(), busCalendar.getId());
    }

    @Scheduled(cron = "* 0/10 * * * *") // 10분 마다
    public void run() throws IOException, ParseException {
        Bus bus = getBus();
        BusCalendar busCalendar = getBusCalendar();
        Route route = getRoute(bus, busCalendar);
        LocalDateTime now = DateHandler.getToday();

        List<RouteDetail> detailList = routeDetailRepository.findRouteDetailByRouteInfo_IdOrderByOrder(route.getRouteInfo().getId());
        List<Station> stationList = new LinkedList<>();

        for(RouteDetail routeDetail: detailList)
                stationList.add(routeDetail.getStation());

        for(int i = 0; i < stationList.size() - 1; i++) {
            Station src = stationList.get(i);
            Station dest = stationList.get(i + 1);

            Long duration = NaverHandler.getDuration(src, dest);
            now = now.plusSeconds(duration);

            BusArrival busArrival = BusArrival.builder()
                    .bus(bus)
                    .station(dest)
                    .expected(now)
                    .build();
            busArrivalRepository.save(busArrival);
        }
    }
}
