package com.mjubus.server.service.station.radiusDetection;

import com.mjubus.server.domain.StationLocation;
import com.mjubus.server.dto.request.StationRadiusDetectedNameRequest;
import com.mjubus.server.dto.response.StationRadiusDetectedNameResponse;
import com.mjubus.server.repository.StationLocationRepository;
import com.mjubus.server.util.HaversineUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        Optional<StationLocation> matchs = all.stream().
                filter(stationLocation -> {
                    return HaversineUtil.distance(request.getLatitude(),
                            request.getLongitude(),
                            stationLocation.getLatitude(),
                            stationLocation.getLongitude()
                    ) < stationLocation.getRange();
                })
                .sorted(Comparator.comparing(StationLocation::getPriority))
                .findFirst();

        StationLocation matchLocation = matchs.orElseThrow(() -> new IllegalStateException("없는 장소"));

        return StationRadiusDetectedNameResponse.builder()
                .name(matchLocation.getName())
                .build();
    }
}
