package com.mjubus.server.util;

import java.time.*;

public class DateHandler {

    private static ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public static LocalDateTime getToday() {
        return getZonedDateTime().toLocalDateTime();
    }

    public static LocalDateTime getTodayWith(int hour, int minute) {
        ZonedDateTime zonedDateTime = getZonedDateTime();
        zonedDateTime = zonedDateTime.with(LocalTime.of(hour, minute));
        return zonedDateTime.toLocalDateTime();
    }

    public static LocalDateTime getDateWith(int month, int dayOfMonth, int hour, int minute) {
        ZonedDateTime zonedDateTime = getZonedDateTime();
        zonedDateTime = zonedDateTime.with(LocalDateTime.of(LocalDate.of(2022, month, dayOfMonth), LocalTime.of(hour, minute)));
        return zonedDateTime.toLocalDateTime();
    }

    public static boolean isWeekend(LocalDateTime date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}
