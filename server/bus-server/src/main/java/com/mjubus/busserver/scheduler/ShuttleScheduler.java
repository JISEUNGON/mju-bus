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
import java.util.List;
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
    private BusArrivalRepository busArrivalRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ShuttleBusHandler shuttleBusHandler;

    public BusCalendar findCalendarByDate(LocalDateTime date) {
            return busCalendarRepository.findBusCalendarByDate(date.toLocalDate(), date.toLocalTime(), DateHandler.getDayOfWeek(date)).get();
    }

    public List<BusTimeTableDetail> findBusTimeTableDetailListByInfo(BusTimeTableInfo busTimeTableInfo) {
        return busTimeTableDetailRepository.findBusTimeTableDetailsByBusTimeTableInfo_Id(busTimeTableInfo.getId());
    }

    public List<BusTimeTable> findBusTimeTableByBusCalendar(BusCalendar busCalendar) {
        return busTimeTableRepository.findBusTimeTablesByBusCalendar_Id(busCalendar.getId());
    }

    public List<BusArrival> findBusArrivalByDate(LocalDateTime date) {
        return busArrivalRepository.findBusArrivalsByExpected(date);
    }

    @Scheduled(cron = "0 0 * * * *")
    // 매일 0 0시에 당일 시간표를 생성한다.
    public void makeBusTimeTable() {
        BusCalendar busCalendar = findCalendarByDate(DateHandler.getToday());
        List<BusTimeTable> timeTableList = findBusTimeTableByBusCalendar(busCalendar);

        for(BusTimeTable busTimeTable: timeTableList) {
            List<BusTimeTableDetail> busTimeTableDetailList = findBusTimeTableDetailListByInfo(busTimeTable.getBusTimeTableInfo());
            for (BusTimeTableDetail busTimeTableDetail: busTimeTableDetailList) {
                UUID uuid = UUID.randomUUID();
                busArrivalRepository.save(
                        BusArrival.builder()
                                .bus(busTimeTable.getBus())
                                .station(stationRepository.getReferenceById(1L))
                                .expected(DateHandler.getTodayWith(busTimeTableDetail.getDepart()))
                                .sid(uuid.toString())
                                .preSid(uuid.toString())
                                .build()
                        );
            }
        }
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void dispatcher() throws IOException, ParseException {
        List<BusArrival> arrivalList = findBusArrivalByDate(DateHandler.getToday());

        if (arrivalList.isEmpty()) return;
        for(BusArrival busArrival: arrivalList) {
            shuttleBusHandler.predict(busArrival.getBus(), busArrival.getStation(), busArrival.getPreSid());
        }
    }
}
