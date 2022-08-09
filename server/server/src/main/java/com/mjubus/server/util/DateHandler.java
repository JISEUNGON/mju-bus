package com.mjubus.server.util;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateHandler {

    public static ZonedDateTime getToday() {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public static boolean isWeekend(ZonedDateTime date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}
