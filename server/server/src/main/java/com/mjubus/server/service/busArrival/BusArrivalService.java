package com.mjubus.server.service.busArrival;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.BusArrival;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.busArrival.BusArrivalDto;
import com.mjubus.server.dto.busArrival.BusRemainDto;
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
    public BusArrivalDto findBusArrivalByStationId(Long stationId) {
        // 버스 후보군 선정
        LocalDateTime today = DateHandler.getTodayWith(0, 0);
        Station station = stationService.findStationById(stationId);
        Optional<List<BusArrival>> optionalBusArrivalList = busArrivalRepository.findAllByStationIdAndDate(stationId, today);
        List<BusArrival> busArrivalList = optionalBusArrivalList.orElseThrow(() -> new BusArrivalNotFoundException(station, null));

        List<Bus> busList = new LinkedList<>();
        for(BusArrival busArrival: busArrivalList) {
            if (!busList.contains(busArrival.getBus()))
                busList.add(busArrival.getBus());
        }

        // 버스 후보군 + 오늘 날짜로 최신 값 검색
        List<BusRemainDto> busArrivals = new LinkedList<>();
        for(Bus bus : busList) {
            BusArrival busArrival = busArrivalRepository.findBusArrivalByBus_idAndCreated(bus.getId(), today);

            BusRemainDto busRemainDto = new BusRemainDto();
            busRemainDto.setBus(busArrival.getBus());
            busRemainDto.setRemains(DateHandler.minus_LocalTime(busArrival.getExpected().toLocalTime(), DateHandler.getToday().toLocalTime()));

            busArrivals.add(busRemainDto);
        }

        return new BusArrivalDto(station, busArrivals);
    }
}
