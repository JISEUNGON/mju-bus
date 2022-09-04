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
import java.time.LocalTime;
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
    public BusArrivalResponse findBusArrivalRemainByStation(Station station, Station dest) {
        // TODO : 시간 측정
        List<BusArrivalDto> busArrivalList;
        LocalDateTime now = DateHandler.getToday();
        List<BusRemainAsSecond> busRemainAsSecondList = new LinkedList<>();

        if (dest == null) {  // 버스 도착지와 무관한, 현 정류장에 오는 버스
            busArrivalList = busArrivalRepository.findBusArrivalByStationId(station.getId());
        } else {  // TODO : 좀 더 일반화된 함수 작성
            busArrivalList = busArrivalRepository.findRedBusArrivalByStationId(station.getId()); // 빨간 버스만 가져오기
        }

        for(BusArrivalDto busArrival: busArrivalList) {
            Bus bus = busService.findBusByBusId(busArrival.getBusId());
            BusRemainAsSecond remainAsSecond = BusRemainAsSecond.builder()
                    .id(bus.getId())
                    .name(bus.getName())
                    .remains(DateHandler.minus_LocalTime(busArrival.getExpectedAt(), now))
                    .depart_at(LocalTime.from(busArrival.getExpectedAt()))
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
