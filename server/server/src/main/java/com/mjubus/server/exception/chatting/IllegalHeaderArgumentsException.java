package com.mjubus.server.exception.chatting;

import lombok.Getter;

@Getter
public class IllegalHeaderArgumentsException extends RuntimeException {
    private String message;

    public IllegalHeaderArgumentsException(String message) {
        super(message);
        this.message = message;
    }
}
