package com.mjubus.server.service.busArrival;

import com.mjubus.server.domain.BusArrival;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.busArrival.BusArrivalResponse;
import com.mjubus.server.dto.busArrival.BusRemainAsSecond;
import com.mjubus.server.exception.BusArrival.BusArrivalNotFoundException;
import com.mjubus.server.repository.BusArrivalRepository;
import com.mjubus.server.service.station.StationService;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class BusArrivalService implements BusArrivalInterface {
    @Autowired
    private StationService stationService;

    @Autowired
    private BusArrivalRepository busArrivalRepository;
    @Override
    public BusArrivalResponse findBusArrivalRemainByStation(Station station) {
        Optional<List<BusArrival>> optionalBusArrivalList = busArrivalRepository.findBusArrivalByStationId(station.getId());
        List<BusArrival> busArrivalList = optionalBusArrivalList.orElseThrow(() -> new BusArrivalNotFoundException(station, null));
        LocalDateTime now = DateHandler.getToday();

        List<BusRemainAsSecond> busRemainAsSecondList = new LinkedList<>();
        for(BusArrival busArrival: busArrivalList) {
            BusRemainAsSecond remainAsSecond =  BusRemainAsSecond.builder()
                    .id(busArrival.getBus().getId())
                    .name(busArrival.getBus().getName())
                    .remains(DateHandler.minus_LocalTime(busArrival.getExpected(), now))
                    .build();
            busRemainAsSecondList.add(remainAsSecond);
        }

        BusArrivalResponse response = new BusArrivalResponse();
        response.setResponse_at(now);
        response.setId(station.getId());
        response.setName(station.getName());
        response.setLongitude(station.getLongitude());
        response.setLatitude(station.getLatitude());
        response.setBusList(busRemainAsSecondList);
        return response;
    }
}
