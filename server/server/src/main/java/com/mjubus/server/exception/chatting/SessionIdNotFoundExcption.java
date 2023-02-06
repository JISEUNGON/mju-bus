package com.mjubus.server.exception.chatting;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class SessionIdNotFoundExcption extends RuntimeException {
    private String roomId;

    public SessionIdNotFoundExcption(String roomId) {
        super(roomId);

        this.roomId = roomId;
    }
}