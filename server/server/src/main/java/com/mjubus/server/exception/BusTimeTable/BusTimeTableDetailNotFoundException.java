package com.mjubus.server.exception.BusTimeTable;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.BusCalendar;
import com.mjubus.server.domain.BusTimeTableInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class BusTimeTableDetailNotFoundException extends RuntimeException {
    private String message;

    public BusTimeTableDetailNotFoundException(BusTimeTableInfo busTimeTableInfo) {
        super("BusTimeTableDetailNotFoundException");

        this.message = busTimeTableInfo.getId().toString();
    }

}
