package com.mjubus.busserver.scheduler;

import com.mjubus.busserver.domain.*;
import com.mjubus.busserver.enums.BusEnum;
import com.mjubus.busserver.repository.*;
import com.mjubus.busserver.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Component
public class InterCityShuttleScheduler {
    @Autowired
    private BusArrivalRepository busArrivalRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private BusTimeTableRepository busTimeTableRepository;

    @Autowired
    private BusCalendarRepository busCalendarRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteDetailRepository routeDetailRepository;

    @Autowired
    private BusTimeTableDetailRepository busTimeTableDetailRepository;

//    @Scheduled(cron = "0 0 0 * * 2022")
    @Scheduled(cron = "10 * * * * *") // 테스트
    public void insertData() {

        // 시외 셔틀버스 데이터
        LinkedList<Integer> busIdList = new LinkedList<>();
        busIdList.add(BusEnum.합정_영등포.getValue());
        busIdList.add(BusEnum.노원_구리.getValue());
        busIdList.add(BusEnum.인천.getValue());
        busIdList.add(BusEnum.송내.getValue());
        busIdList.add(BusEnum.안산.getValue());
        busIdList.add(BusEnum.금정_범계.getValue());
        busIdList.add(BusEnum.분당.getValue());

        // 6 : 학기 중
        BusCalendar busCalendar = busCalendarRepository.getReferenceById(6L);

        for (Integer busId : busIdList) {
            BusTimeTable busTimeTable = busTimeTableRepository.findBusTimeTableByBus_IdAndBusCalendar_Id(Long.valueOf(busId), busCalendar.getId());
            Route route = routeRepository.findRouteByBus_IdAndBusCalendar_Id(Long.valueOf(busId), busCalendar.getId());

            // 노선도 / 시간표 가져옴, 2개는 반드시 같은 개수를 가져야 함.
            List<BusTimeTableDetail> busTimeTableDetailList = busTimeTableDetailRepository.findBusTimeTableDetailByBusTimeTableInfo_Id(busTimeTable.getBusTimeTableInfo().getId());
            List<RouteDetail> routeDetailList = routeDetailRepository.findRouteDetailByRouteInfo_Id(route.getRouteInfo().getId());

            // 등교는 대응이 되는데, 하교는 또 로직이 다름. 
            // 진짜 환장하네
            int N = routeDetailList.size() - 1;
            for(int i = 0 ; i < N; i++) {
                BusTimeTableDetail busTimeTableDetail = busTimeTableDetailList.get(i);
                RouteDetail routeDetail = routeDetailList.get(i);

                Station station = stationRepository.getReferenceById(routeDetail.getStation().getId());
                Bus bus = busRepository.getReferenceById(Long.valueOf(busId));
                LocalDateTime expected = DateHandler.getTodayWith(busTimeTableDetail.getDepart());

                BusArrival busArrival = new BusArrival();
                busArrival.setBus(bus);
                busArrival.setStation(station);
                busArrival.setExpected(expected);

                busArrivalRepository.save(busArrival);
            }
        }
        System.out.println("Saved !");
    }
}
