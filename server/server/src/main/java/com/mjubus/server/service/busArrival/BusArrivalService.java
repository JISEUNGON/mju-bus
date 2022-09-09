package com.mjubus.server.service.busArrival;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.BusArrivalDto;
import com.mjubus.server.dto.StationDTO;
import com.mjubus.server.dto.busArrival.BusArrivalResponse;
import com.mjubus.server.dto.busArrival.BusRemainAsSecond;
import com.mjubus.server.repository.BusArrivalRepository;
import com.mjubus.server.service.bus.BusService;
import com.mjubus.server.service.route.RouteService;
import com.mjubus.server.service.station.StationService;
import com.mjubus.server.util.DateHandler;
import com.mjubus.server.util.NaverHandler;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private RouteService routeService;
    @Autowired
    private BusArrivalRepository busArrivalRepository;

    @Override
    public BusArrivalResponse findBusArrivalRemainByStation(Station station, Boolean toSchool, Boolean redBus) {
        List<BusArrivalDto> busArrivalList;
        LocalDateTime now = DateHandler.getToday();
        List<BusRemainAsSecond> busRemainAsSecondList = new LinkedList<>();

        if (!toSchool) busArrivalList = busArrivalRepository.findShuttleBusFromStart(); // 학교에서
        else if (redBus) busArrivalList = busArrivalRepository.findRedBusArrivalByStationId(station.getId()); // 학교로 + 빨간버스만
        else busArrivalList = busArrivalRepository.findBusArrivalByStationId(station.getId()); // 학교로 + (빨간/셔틀버스)

        for (BusArrivalDto busArrivalDto: busArrivalList) {
            Bus bus = busService.findBusByBusId(busArrivalDto.getBusId());

            BusRemainAsSecond busRemainAsSecond = BusRemainAsSecond.builder()
                    .id(bus.getId())
                    .name(bus.getName())
                    .remains(DateHandler.minus_LocalTime(busArrivalDto.getExpectedAt(), now))
                    .depart_at(LocalTime.from(busArrivalDto.getExpectedAt()))
                    .arrive_at(LocalTime.from(getExpectedTime(bus, station, busArrivalDto.getExpectedAt())))
                    .build();
            busRemainAsSecondList.add(busRemainAsSecond);
        }

        BusArrivalResponse response = new BusArrivalResponse();
        response.setResponse_at(now);
        response.setId(station.getId());
        response.setName(station.getName());
        response.setBusList(busRemainAsSecondList);
        return response;
    }

    private LocalDateTime getExpectedTime(Bus bus, Station currentStation, LocalDateTime depart_at) {
        try {
            if (bus.getId() >= 200) { // 빨간버스 로직 별도로, DB에 값이 연결되어 있지 않음.
                Station destStation = stationService.findStationById(201L);
                return depart_at.plusSeconds(NaverHandler.getDuration(currentStation, destStation));
            }

            // 노선에 포함되어 있는 정류장 리스트
            List<StationDTO> stationDTOList = routeService.findStationsByBus(bus);

            // 목적지 정류장 탐색
            int destIndex = -1;
            for(int i = 0; i < stationDTOList.size(); i++) {
                if (stationDTOList.get(i).getId().equals(currentStation.getId())) {
                    destIndex = i;
                    break;
                }
            }

            // 노선이 정류장을 거치지 않는 경우 pass
            if (destIndex == -1) return DateHandler.getToday();

            for(int i = 0; i < destIndex; i++) {
                Station srcStation = stationService.findStationById(stationDTOList.get(i).getId());
                Station destStation = stationService.findStationById(stationDTOList.get(i + 1).getId());

                // 네이버 API를 통한 예상시간 계산
                Long duration = NaverHandler.getDuration(srcStation, destStation);
                depart_at = depart_at.plusSeconds(duration);
            }
            return depart_at;
        } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
        }
    }
}
