package com.mjubus.server.service.busArrival;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.BusArrivalDto;
import com.mjubus.server.dto.busArrival.BusArrivalResponse;
import com.mjubus.server.dto.busArrival.BusRemainAsSecond;
import com.mjubus.server.repository.BusArrivalRepository;
import com.mjubus.server.service.bus.BusService;
import com.mjubus.server.service.station.StationService;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class BusArrivalService implements BusArrivalInterface {

    @Autowired
    private StationService stationService;

    @Autowired
    private BusService busService;

    @Autowired
    private BusArrivalRepository busArrivalRepository;
    @Override
    public BusArrivalResponse findBusArrivalRemainByStation(Station station) {
        List<BusArrivalDto> busArrivalList = busArrivalRepository.findBusArrivalByStationId(station.getId());
        LocalDateTime now = DateHandler.getToday();

        List<BusRemainAsSecond> busRemainAsSecondList = new LinkedList<>();
        for(BusArrivalDto busArrival: busArrivalList) {
            Bus bus = busService.findBusByBusId(busArrival.getBusId());
            BusRemainAsSecond remainAsSecond =  BusRemainAsSecond.builder()
                    .id(bus.getId())
                    .name(bus.getName())
                    .remains(DateHandler.minus_LocalTime(busArrival.getExpectedAt(), now))
                    .build();
            busRemainAsSecondList.add(remainAsSecond);
        }

        BusArrivalResponse response = new BusArrivalResponse();
        response.setResponse_at(now);
        response.setId(station.getId());
        response.setName(station.getName());
        response.setBusList(busRemainAsSecondList);
        return response;
    }
}
