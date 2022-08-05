package com.mjubus.server.service.mjuCalendar;

import com.mjubus.server.domain.MjuCalendar;

import java.sql.Date;
import java.util.Optional;

public interface MjuCalendarInterface {
    Optional<MjuCalendar> findByDate(Date date);
}
