package com.mjubus.busserver.scheduler;

import com.mjubus.busserver.domain.*;
import com.mjubus.busserver.repository.*;
import com.mjubus.busserver.util.DateHandler;
import net.bytebuddy.asm.Advice;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Component
public class MjuStationScheduler {

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
        Bus bus = busRepository.findById(10L).get();
        return bus;
    }

    private Route getRoute(Bus bus, BusCalendar busCalendar) {
        return routeRepository.findRouteByBus_IdAndBusCalendar_Id(bus.getId(), busCalendar.getId());
    }

    // 명지대역 셔틀버스 예측
    @Scheduled(cron = "0/15 * * * * *")
    public void run() throws IOException, ParseException {
        Bus bus = getBus();
        BusCalendar busCalendar = getBusCalendar();
        Route route = getRoute(bus, busCalendar);
        List<RouteDetail> routeDetailList = routeDetailRepository.findRouteDetailByRouteInfo_IdOrderByOrder(route.getRouteInfo().getId());

        List<Station> stationList = new LinkedList<>();
        for(RouteDetail routeDetail: routeDetailList)
            stationList.add(routeDetail.getStation());

        LocalDateTime now = DateHandler.getToday();
        for(int i = 0; i < stationList.size() - 1; i++) {
            Station src = stationList.get(i);
            Station dest = stationList.get(i + 1);

            Long duration = NaverHandler.getDuration(src, dest);
            now = now.plusSeconds(duration);
            BusArrival busArrival = BusArrival
                    .builder()
                    .bus(bus)
                    .station(dest)
                    .expected(now)
                    .build();
            busArrivalRepository.save(busArrival);
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        MjuStationScheduler mjuStationScheduler = new MjuStationScheduler();
        mjuStationScheduler.run();
    }
}
