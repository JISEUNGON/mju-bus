package com.mjubus.server.exception.BusTimeTable;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class BusTimeTableNotFoundException extends RuntimeException {
    private String message;
    public BusTimeTableNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
