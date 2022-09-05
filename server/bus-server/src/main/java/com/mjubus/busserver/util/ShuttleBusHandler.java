package com.mjubus.busserver.util;

import com.mjubus.busserver.domain.*;
import com.mjubus.busserver.repository.*;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class ShuttleBusHandler {
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

    private Route getRoute(Bus bus, BusCalendar busCalendar) {
        return routeRepository.findRouteByBus_IdAndBusCalendar_Id(bus.getId(), busCalendar.getId());
    }

    private List<RouteDetail> getCurrentRoute(Bus bus) {
        BusCalendar busCalendar = getBusCalendar();
        Route route = getRoute(bus, busCalendar);

        return routeDetailRepository.findRouteDetailByRouteInfo_IdOrderByOrder(route.getRouteInfo().getId());
    }

    public void predict(Bus bus, Station start, String pre_busArrival_sid) throws IOException, ParseException {
        LocalDateTime now = DateHandler.getToday();

        // 현재 노선 정보 가져오기
        List<RouteDetail> detailList = getCurrentRoute(bus);

        // 정류장 리스트 추출
        List<Station> stationList = (List<Station>) detailList.stream().map((routeDetail -> routeDetail.getStation()));
        int i_start = stationList.indexOf(start); // 정류장 idx

        for(int i = i_start; i < stationList.size() - 1; i++ ) {
            Station src = stationList.get(i); // 출발지
            Station dest = stationList.get(i + 1); // 목적지

            Long duration = NaverHandler.getDuration(src, dest); // 예상 시간
            LocalDateTime expected = now.plusSeconds(duration);

            busArrivalRepository.updateBusArrivalByPreSid(pre_busArrival_sid, expected, dest.getId());
        }
    }
}
