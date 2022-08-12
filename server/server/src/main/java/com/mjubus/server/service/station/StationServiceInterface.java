package com.mjubus.server.service.station;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.Station;

import java.util.List;
import java.util.Optional;

public interface StationServiceInterface {
  public Optional<Station> findStation(int id);
}
