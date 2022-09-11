package com.mjubus.server.exception.Path;

import com.mjubus.server.domain.Station;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class PathInfoNotFoundException extends RuntimeException  {
    private String message;

    public PathInfoNotFoundException(Station src, Station dest) {
        super("PathInfoNotFoundException");

        if (src == null && dest == null)
            message = "src/dest are null";
        else if (src == null)
            message = "src is null";
        else if (dest == null)
            message = "dest is null";
        else
            message = "From " + src.getName() + ", To " + dest.getName();
    }
}
