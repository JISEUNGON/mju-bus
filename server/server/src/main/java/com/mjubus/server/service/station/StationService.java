package com.mjubus.server.service.station;

import com.mjubus.server.domain.Station;
import com.mjubus.server.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StationService implements StationServiceInterface{
  @Autowired
  private StationRepository stationRepository;

  @Override
  public Optional<Station> findStation(int id) {
    return stationRepository.findStationByType(id);
  }
}
