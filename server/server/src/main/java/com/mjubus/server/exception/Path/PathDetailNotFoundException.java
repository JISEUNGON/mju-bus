package com.mjubus.server.exception.Path;

import com.mjubus.server.domain.PathInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class PathDetailNotFoundException extends RuntimeException {
    private String message;

    public PathDetailNotFoundException(PathInfo pathInfo) {
        super("PathDetailNotFoundException");

        if (pathInfo == null)
            message = "PathInfo is null";
        else
            message = "PathInfo not found, ID : " + pathInfo.getId();
    }
}

