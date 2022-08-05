package com.mjubus.server.service.bus;

import com.mjubus.server.domain.Bus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BusServiceInterface {
    Optional<Bus> getBus(int type);

    boolean isActivate(int type);
    boolean isActivate(String sid);

}
