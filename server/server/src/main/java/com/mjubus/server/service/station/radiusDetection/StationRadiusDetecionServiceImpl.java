package com.mjubus.server.service.station.radiusDetection;

import com.mjubus.server.domain.StationLocation;
import com.mjubus.server.dto.request.StationRadiusDetectedNameRequest;
import com.mjubus.server.dto.response.StationRadiusDetectedNameResponse;
import com.mjubus.server.repository.StationLocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class StationRadiusDetecionServiceImpl implements StationRadiusDetectionService {

    private final StationLocationRepository stationLocationRepository;

    public StationRadiusDetecionServiceImpl(StationLocationRepository stationLocationRepository) {
        this.stationLocationRepository = stationLocationRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public StationRadiusDetectedNameResponse detectAndGetName(StationRadiusDetectedNameRequest request) {
        List<StationLocation> all = stationLocationRepository.findAll();
        all.forEach(stationLocation -> {
            log.info("lat: {}, lon: {}", stationLocation.getLatitude(), stationLocation.getLongitude());
        });
        return null;
    }
}
