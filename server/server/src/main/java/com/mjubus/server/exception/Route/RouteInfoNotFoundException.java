package com.mjubus.server.exception.Route;

import com.mjubus.server.domain.RouteInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class RouteInfoNotFoundException extends RuntimeException {

    private String message;

    public RouteInfoNotFoundException(RouteInfo routeInfo) {
        super("RouteInfoNotFoundException");
        this.message = routeInfo.getId().toString();
    }
}
