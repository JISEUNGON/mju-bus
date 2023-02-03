package com.mjubus.server.exception.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends RuntimeException {
    private String message;
    public MemberNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
