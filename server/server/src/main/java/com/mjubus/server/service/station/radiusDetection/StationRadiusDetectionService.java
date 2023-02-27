package com.mjubus.server.service.station.radiusDetection;

import com.mjubus.server.dto.request.StationRadiusDetectedNameRequest;
import com.mjubus.server.dto.response.StationRadiusDetectedNameResponse;
import org.springframework.stereotype.Service;


public interface StationRadiusDetectionService {
    public StationRadiusDetectedNameResponse detectAndGetName(StationRadiusDetectedNameRequest request);
}
