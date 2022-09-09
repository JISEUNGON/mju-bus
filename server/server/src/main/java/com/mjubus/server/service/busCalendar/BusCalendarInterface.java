package com.mjubus.server.service.busCalendar;

import com.mjubus.server.domain.BusCalendar;

import java.time.LocalDateTime;

public interface BusCalendarInterface {
    BusCalendar findByDate(LocalDateTime date);
}
