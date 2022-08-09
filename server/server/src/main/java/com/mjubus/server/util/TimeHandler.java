package com.mjubus.server.util;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class TimeHandler {

    public static boolean isNightTime() {
        ZonedDateTime localDate = DateHandler.getToday();
        return localDate.getHour() >= 20;
    }
}
