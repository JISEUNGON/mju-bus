package com.mjubus.busserver.scheduler;


import com.mjubus.busserver.domain.*;
import com.mjubus.busserver.repository.*;
import com.mjubus.busserver.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

public class ShuttleScheduler {

    @Autowired
    private BusCalendarRepository busCalendarRepository;

    @Autowired
    private BusTimeTableDetailRepository busTimeTableDetailRepository;

    @Autowired
    private BusTimeTableRepository busTimeTableRepository;

    @Autowired
    private BusArrivalRepository busArrivalRepository;

    @Autowired
    private StationRepository stationRepository;

    public BusCalendar findCalendarByDate(LocalDateTime date) {
            return busCalendarRepository.findBusCalendarByDate(date.toLocalDate(), date.toLocalTime(), DateHandler.getDayOfWeek(date)).get();
    }

    public List<BusTimeTableDetail> findBusTimeTableDetailListByInfo(BusTimeTableInfo busTimeTableInfo) {
        return busTimeTableDetailRepository.findShuttleBusTimeTableDetails();
    }

    public List<BusTimeTable> findBusTimeTableByBusCalendar(BusCalendar busCalendar) {
        return busTimeTableRepository.findBusTimeTablesByBusCalendar_Id(busCalendar.getId());
    }

    @Scheduled(cron = "0 0/1 * * * *") // 매일 00시 마다
    public void run() {
        BusCalendar busCalendar = findCalendarByDate(DateHandler.getToday());
        List<BusTimeTable> timeTableList = findBusTimeTableByBusCalendar(busCalendar);

        for(BusTimeTable busTimeTable: timeTableList) {
            List<BusTimeTableDetail> busTimeTableDetailList = findBusTimeTableDetailListByInfo(busTimeTable.getBusTimeTableInfo());
            for (BusTimeTableDetail busTimeTableDetail: busTimeTableDetailList) {
                busArrivalRepository.save(
                        BusArrival.builder()
                                .bus(busTimeTable.getBus())
                                .station(stationRepository.getReferenceById(1L))
                                .expected(DateHandler.getTodayWith(busTimeTableDetail.getDepart()))
                                .preId()
                                .build()
                        );
            }
        }
    }
}
