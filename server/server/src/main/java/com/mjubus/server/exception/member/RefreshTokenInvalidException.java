package com.mjubus.server.exception.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RefreshTokenInvalidException extends RuntimeException{
    private String message;
    public RefreshTokenInvalidException(String message) {
        super(message);
        this.message = message;
    }
}
