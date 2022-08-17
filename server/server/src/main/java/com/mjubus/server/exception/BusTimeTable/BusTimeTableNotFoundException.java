package com.mjubus.server.exception.BusTimeTable;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.BusCalendar;
import com.mjubus.server.domain.BusTimeTableInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class BusTimeTableNotFoundException extends RuntimeException {
    private String message;
    public BusTimeTableNotFoundException(Bus bus, BusCalendar busCalendar, BusTimeTableInfo busTimeTableInfo) {
        super("BusTimeTableNotFoundExcpetion");
        StringBuilder msgBuilder = new StringBuilder();
        if (bus != null) {
            msgBuilder.append("<Bus> ID : " + bus.getId() + " || ");
        }
        if (busTimeTableInfo != null) {
            msgBuilder.append("<RouteInfo> ID : " + busTimeTableInfo.getId() + " || ");
        }
        if (busCalendar != null) {
            msgBuilder.append("<BusCalendar> Id : " + busCalendar.getId() + " || ");
        }
        msgBuilder.append("Not Found.");

        this.message = msgBuilder.toString();
    }

}
