package com.mjubus.server.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RoleUpdateTargetMisMatchException extends RuntimeException {
    private String message;

    public RoleUpdateTargetMisMatchException(String message) {
        super(message);
        this.message = message;
    }
}
