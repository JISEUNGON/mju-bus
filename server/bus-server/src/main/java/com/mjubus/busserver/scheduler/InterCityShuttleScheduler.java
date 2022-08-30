package com.mjubus.busserver.scheduler;

import com.mjubus.busserver.domain.*;
import com.mjubus.busserver.repository.*;
import com.mjubus.busserver.util.DateHandler;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
@Component
// 시내, 방학(공휴일) 셔틀버스
public class InterCityShuttleScheduler {
    @Autowired
    private BusArrivalRepository busArrivalRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusCalendarRepository busCalendarRepository;
    @Autowired
    private RouteDetailRepository routeDetailRepository;

    private BusCalendar getBusCalendar() {
        LocalDateTime today = DateHandler.getToday();
        return busCalendarRepository.findBusCalendarByDate(today.toLocalDate(), today.toLocalTime(), DateHandler.getDayOfWeek(today)).get();
    }
    private List<Station> getBusStationList() {
        Long route_info_id = 21L;
        List<RouteDetail> detailList = routeDetailRepository.findRouteDetailByRouteInfo_IdOrderByOrder(route_info_id);

        List<Station> stations = new LinkedList<>();
        for(RouteDetail detail: detailList)
            stations.add(detail.getStation());
        return stations;
    }

    private Bus getBus() {
        return busRepository.getReferenceById(20L);
    }


    @Scheduled(cron = "20 * * * * *")
    public void run() throws IOException, ParseException {
        List<Station> stationList = getBusStationList();
        LocalDateTime expected = DateHandler.getToday();
        Bus bus = getBus();
        Long duration;

        for(int i = 0; i < stationList.size() - 1; i++) {
            Station src = stationList.get(i);
            Station dest = stationList.get(i + 1);

            duration = NaverHandler.getDuration(src, dest);

            BusArrival busArrival = new BusArrival();
            busArrival.setBus(bus);
            busArrival.setStation(dest);
            expected = expected.plusSeconds(duration);
            busArrival.setExpected(expected);
            busArrivalRepository.save(busArrival);
        }
    }
}
