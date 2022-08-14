package com.mjubus.server.exception.Station;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class StationNotFoundException extends RuntimeException {
    private String busId;
    public StationNotFoundException(Long id) {
        super(id.toString());
        this.busId = id.toString();
    }

}

