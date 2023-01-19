package com.mjubus.server.service.station;

import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.request.StationRequest;
import com.mjubus.server.dto.response.StationResponse;

public interface StationService {

    /**
     * Controller
     */
    StationResponse findStation(StationRequest req);

    /**
     * Service
     */
    Station findStationById(Long id);
}
