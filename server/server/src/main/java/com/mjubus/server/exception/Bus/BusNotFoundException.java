package com.mjubus.server.exception.Bus;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class BusNotFoundException extends RuntimeException {
    private String busId;
    public BusNotFoundException(Long id) {
        super(id.toString());
        this.busId = id.toString();
    }

}
