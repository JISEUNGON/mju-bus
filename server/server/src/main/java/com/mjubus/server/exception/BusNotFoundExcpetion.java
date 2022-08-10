package com.mjubus.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class BusNotFoundExcpetion extends RuntimeException {
    private String message;
    public BusNotFoundExcpetion(String message) {
        super(message);
        this.message = message;
    }

}
