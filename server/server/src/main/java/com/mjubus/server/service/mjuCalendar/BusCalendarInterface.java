package com.mjubus.server.service.mjuCalendar;

import com.mjubus.server.domain.BusCalendar;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BusCalendarInterface {
    BusCalendar findByDate(LocalDateTime date);
}
