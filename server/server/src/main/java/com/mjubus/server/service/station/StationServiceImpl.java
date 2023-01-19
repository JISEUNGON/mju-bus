package com.mjubus.server.service.station;

import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.request.StationRequest;
import com.mjubus.server.dto.response.StationResponse;
import com.mjubus.server.exception.Station.StationNotFoundException;
import com.mjubus.server.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StationServiceImpl implements StationService {

  private StationRepository stationRepository;

  @Autowired
  public StationServiceImpl(StationRepository stationRepository) {
    this.stationRepository = stationRepository;
  }

    @Override
    public StationResponse findStation(StationRequest req) {
        Station station = findStationById(req.getId());
        return StationResponse.of(station);
    }

    @Override
    public Station findStationById(Long id) {
        Optional<Station> optionalStation = stationRepository.findStationById(id);
        return optionalStation.orElseThrow(() -> new StationNotFoundException(id));
    }
}
