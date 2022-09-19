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
    public BusArrivalResponse findBusArrivalRemainByStation(Station srcStation, Station destStation, Boolean toSchool, Boolean redBus) {
        List<BusArrivalDto> busArrivalList;
        LocalDateTime now = DateHandler.getToday();
        List<BusRemainAsSecond> busRemainAsSecondList = new LinkedList<>();

        if (toSchool && redBus)  busArrivalList = busArrivalRepository.findRedBusArrivalByStationId(srcStation.getId()); // 학교로 + 빨간버스만
        else if (destStation != null && !toSchool && !redBus)  busArrivalList = getBusArrivalListBetween(srcStation, destStation); // 출발지-목적지를 모두 경유하는 버스 리스트
        else busArrivalList = busArrivalRepository.findBusArrivalByStationId(srcStation.getId()); // 현 정류장에 도착하는 모든 버스

        for (BusArrivalDto busArrivalDto: busArrivalList) {
            Bus bus = busService.findBusByBusId(busArrivalDto.getBusId());

            BusRemainAsSecond busRemainAsSecond = new BusRemainAsSecond();
            busRemainAsSecond.setId(bus.getId());
            busRemainAsSecond.setName(bus.getName());
            busRemainAsSecond.setRemains(DateHandler.minus_LocalTime(busArrivalDto.getExpectedAt(), now));
            busRemainAsSecond.setDepart_at(LocalTime.from(busArrivalDto.getExpectedAt()));

            // 학교로 / 학교에서 출발
            if (toSchool) busRemainAsSecond.setArrive_at(getExpectedTimeFrom(bus, srcStation, busArrivalDto.getExpectedAt()));
            else busRemainAsSecond.setArrive_at(getExpectedTimeTo(bus, destStation, busArrivalDto.getExpectedAt()));

            busRemainAsSecondList.add(busRemainAsSecond);
        }

        BusArrivalResponse response = new BusArrivalResponse();
        response.setResponse_at(now);
        response.setId(srcStation.getId());
        response.setName(srcStation.getName());
        response.setBusList(busRemainAsSecondList);
        return response;
    }

    /**
     * srcStation 부터 destStation 까지 경유하는 노선 탐색
     * @param srcStation 출발지
     * @param destStation 목적지
     * @return 노선 리스트
     */
    private List<BusArrivalDto> getBusArrivalListBetween(Station srcStation, Station destStation) {
        List<BusArrivalDto> busArrivalList = busArrivalRepository.findBusArrivalFromSchool(srcStation.getId()); // 출발지에 도착하는 버스
        List<BusArrivalDto> candidates = new LinkedList<>(); // 최종 후보군

        for(BusArrivalDto busArrivalDto : busArrivalList) {
            Bus bus = busService.findBusByBusId(busArrivalDto.getBusId());
            List<StationDTO> stationList = routeService.findStationsByBus(bus);

            // 목적지가 해당 노선에 있는 지 확인
            for(StationDTO stationDTO: stationList) {
                if (stationDTO.getId().equals(destStation.getId())) {
                    candidates.add(busArrivalDto);
                    break;
                }
            }

        }

        return candidates;
    }

    /**
     * 현 정류장으로부터, 종점까지 걸리는 시간을 측정한다.
     * @param bus 버스
     * @param currentStation 출발 정류장
     * @param depart_at 출발 시각
     * @return 출발 정류장 ~ 종점까지 걸리는 시간
     */
    private LocalTime getExpectedTimeFrom(Bus bus, Station currentStation, LocalDateTime depart_at) {
        try {
            if (bus.getId() >= 200) { // 빨간버스 로직 별도로, DB에 값이 연결되어 있지 않음.
                Station destStation = stationService.findStationById(201L);
                return LocalTime.from(depart_at.plusSeconds(NaverHandler.getDuration(currentStation, destStation)));
            }

            // 노선에 포함되어 있는 정류장 리스트
            List<StationDTO> stationDTOList = routeService.findStationsByBus(bus);

            // 출발 정류장 탐색
            int start = -1;
            for(int i = 0; i < stationDTOList.size(); i++) {
                if (stationDTOList.get(i).getId().equals(currentStation.getId())) {
                    start = i;
                    break;
                }
            }

            // 노선이 정류장을 거치지 않는 경우 pass
            if (start == -1) return LocalTime.from(DateHandler.getToday());

            for(int i = start; i < stationDTOList.size() - 1; i++) {
                Station srcStation = stationService.findStationById(stationDTOList.get(i).getId());
                Station destStation = stationService.findStationById(stationDTOList.get(i + 1).getId());

                // 네이버 API를 통한 예상시간 계산
                Long duration = NaverHandler.getDuration(srcStation, destStation);
                depart_at = depart_at.plusSeconds(duration);
            }
            return LocalTime.from(depart_at);
        } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
        }
    }

    /**
     * 시작 노선부터 targetStation 까지의 소요 시간을 계산
     *
     * @param bus           버스 노선
     * @param targetStation 목표 정류장
     * @param depart_at     예상 출발 시각
     * @return 예상 출발 시각 + (목표 정류장까지 걸리는 시간)
     */
    private LocalTime getExpectedTimeTo(Bus bus, Station targetStation, LocalDateTime depart_at) {
        try {
            // 노선에 포함되어 있는 정류장 리스트
            List<StationDTO> stationDTOList = routeService.findStationsByBus(bus);

            // 예상 시간
            LocalDateTime expected = depart_at;

            // 시간 계산
            for(int i = 0 ; i < stationDTOList.size() - 1; i++) {
                Station curr = stationService.findStationById(stationDTOList.get(i).getId());

                if (curr.getId().equals(targetStation.getId())) break;
                Station dest = stationService.findStationById(stationDTOList.get(i + 1).getId());
                expected = expected.plusSeconds(NaverHandler.getDuration(curr, dest));
            }

            return LocalTime.from(expected);
        } catch (IOException | ParseException e) {
            throw  new RuntimeException(e);
        }
    }
}
