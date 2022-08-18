package com.mjubus.busserver.scheduler;

import com.mjubus.busserver.domain.Bus;
import com.mjubus.busserver.domain.BusArrival;
import com.mjubus.busserver.domain.Station;
import com.mjubus.busserver.repository.BusArrivalRepository;
import com.mjubus.busserver.repository.BusRepository;
import com.mjubus.busserver.repository.StationRepository;
import com.mjubus.busserver.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RedBusScheduler {
    @Autowired
    private BusArrivalRepository busArrivalRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private StationRepository stationRepository;

    @Scheduled(cron = "10 * * * * *")
    public void printDate() {

        // 테스트 데이터
        Bus bus = busRepository.getReferenceById(200L);
        Station station = stationRepository.getReferenceById(4L);

        BusArrival test =  BusArrival.builder()
                            .bus(bus)
                            .station(station)
                            .expected(DateHandler.getToday())
                            .build();

        busArrivalRepository.save(test);
        System.out.println("Saved !");
    }
}
