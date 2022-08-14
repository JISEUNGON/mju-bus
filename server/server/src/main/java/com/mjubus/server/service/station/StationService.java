package com.mjubus.server.service.station;

import com.mjubus.server.domain.Station;
import com.mjubus.server.exception.Station.StationNotFoundException;
import com.mjubus.server.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StationService implements StationServiceInterface{
  @Autowired
  private StationRepository stationRepository;

  @Override
  public Station findStationById(Long id) {
     Optional<Station> optionalStation = stationRepository.findStationById(id);
     return optionalStation.orElseThrow(() -> new StationNotFoundException(id));
  }
}
