package com.mjubus.server.exception.Route;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.BusCalendar;
import com.mjubus.server.domain.RouteInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class RouteNotFoundException extends RuntimeException {
    private String message;

    public RouteNotFoundException(Bus bus, RouteInfo routeInfo, BusCalendar busCalendar) {
        super("RouteNotFoundException");
        StringBuilder msgBuilder = new StringBuilder();
        if (bus != null) {
            msgBuilder.append("<Bus> ID : " + bus.getId() + " || ");
        }
        if (routeInfo != null) {
            msgBuilder.append("<RouteInfo> ID : " + routeInfo.getId() + " || ");
        }
        if (busCalendar != null) {
            msgBuilder.append("<BusCalendar> Id : " + busCalendar.getId() + " || ");
        }
        msgBuilder.append("Not Found.");

        this.message = msgBuilder.toString();
    }

}