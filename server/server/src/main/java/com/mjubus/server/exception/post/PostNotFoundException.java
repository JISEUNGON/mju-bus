package com.mjubus.server.exception.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class PostNotFoundException extends RuntimeException {
    private String postId;

    public PostNotFoundException(Integer id) {
        super(id.toString());

        postId = id.toString();
    }
}
