package com.mjubus.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class NoMatchContentExcpetion extends ErrorResponse {
    private String message;
    public NoMatchContentExcpetion(String message, ErrorCode errorCode) {
        super(errorCode);
        this.message = message;
    }

}
