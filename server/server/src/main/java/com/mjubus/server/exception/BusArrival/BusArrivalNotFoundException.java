package com.mjubus.server.exception.BusArrival;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.Station;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class BusArrivalNotFoundException extends RuntimeException {
    private String message;
    public BusArrivalNotFoundException(Station station, Bus bus) {
        super("BusArrivalNotFoundExcepetion");
        StringBuilder msg = new StringBuilder();

        if (station != null) {
            msg.append("|| StationID : " + station.getId());
        }

        if (bus != null) {
            msg.append("|| BusID : " + bus.getId());
        }
    }

}