package com.mjubus.server.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MjuUserNotFoundException extends RuntimeException {
    private String message;

    public MjuUserNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
