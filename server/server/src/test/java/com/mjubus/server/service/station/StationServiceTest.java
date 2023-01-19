package com.mjubus.server.service.station;

import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.request.StationRequest;
import com.mjubus.server.dto.response.StationResponse;
import com.mjubus.server.exception.Station.StationNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StationServiceTest {
    private StationService stationService;

    @Autowired
    public StationServiceTest(StationService stationService) {
        this.stationService = stationService;
    }

    @Test
    @DisplayName("[Service][findStationById] 정상적으로 역을 찾는다.")
    void 테스트_findStationById() {
        Station stationById = stationService.findStationById(1L);

        Assertions.assertNotNull(stationById);
        Assertions.assertEquals(stationById.getId(), 1L);
    }

    @Test
    @DisplayName("[Service][findStationById] 존재하지 않는 역을 찾을 때 예외를 던진다.")
    void 테스트_StationNotFoundException() {
        Assertions.assertThrows(StationNotFoundException.class, () -> {
            stationService.findStationById(999L);
        });
    }

    @Test
    @DisplayName("[Service][findStation] 정상적으로 역을 찾는다.")
    void 테스트_findStation() {
        StationResponse station = stationService.findStation(StationRequest.of("1"));

        Assertions.assertNotNull(station);
        Assertions.assertEquals(station.getId(), 1);
    }

    @Test
    @DisplayName("[Service][findStation] 존재하지 않는 역을 찾을 때 예외를 던진다.")
    void 테스트_findStation_StationNotFoundException() {
        Assertions.assertThrows(StationNotFoundException.class, () -> {
            stationService.findStation(StationRequest.of("999"));
        });
    }
}
