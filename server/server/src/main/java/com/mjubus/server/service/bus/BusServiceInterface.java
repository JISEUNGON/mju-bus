package com.mjubus.server.service.bus;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.dto.BusStatusDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BusServiceInterface {
    Optional<Bus> getBusByType(int type);

    BusStatusDto getBusStatus(int type);

}
