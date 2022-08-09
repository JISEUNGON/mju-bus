package com.mjubus.server.service.mjuCalendar;

import com.mjubus.server.domain.BusCalendar;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface BusCalendarInterface {
    Optional<BusCalendar> findByDate(ZonedDateTime date);
}
