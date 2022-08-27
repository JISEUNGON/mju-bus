package com.mjubus.server.util;

import java.time.*;

public class DateHandler {
    private static ZonedDateTime zonedDateTime;
    private static ZonedDateTime stopAt;

    public static void setZonedDateTime(LocalDateTime localDateTime) {
        zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Seoul"));
        stopAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }
    private static ZonedDateTime getZonedDateTime() {
        if (zonedDateTime == null) {
            return ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        }

        Duration duration = Duration.between(stopAt, ZonedDateTime.now(ZoneId.of("Asia/Seoul")));
        return zonedDateTime.plus(duration);
    }

    public static LocalDateTime getToday() {
        return getZonedDateTime().toLocalDateTime();
    }

    public static LocalTime getTodayTime() {
        return getZonedDateTime().toLocalTime();
    }

    public static LocalDate getTodayDate() {
        return getZonedDateTime().toLocalDate();
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

    public static int getDayOfWeek(LocalDateTime date) {
        DayOfWeek day = date.getDayOfWeek();
        return (int) Math.pow(2, day.getValue() - 1);
    }
    public static boolean isWeekend(LocalDateTime date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    /**
     * l1 - l2 를 초단위로 돌려줍니다.
     * @param l1 expected
     * @param l2 현재 시간
     * @return l1 - l2 as sec
     */
    public static int minus_LocalTime(LocalTime l1, LocalTime l2) {
        int diff_hour = l1.getHour() - l2.getHour();
        int diff_minute = l1.getMinute() - l2.getMinute();
        int diff_sec = l1.getSecond() - l2.getSecond();
        return (diff_hour * 3600) + (diff_minute * 60) + diff_sec;
    }

    public static int minus_LocalTime(LocalDateTime l1, LocalDateTime l2) {
        int diff_hour = l1.getHour() - l2.getHour();
        int diff_minute = l1.getMinute() - l2.getMinute();
        int diff_sec = l1.getSecond() - l2.getSecond();
        return (diff_hour * 3600) + (diff_minute * 60) + diff_sec;
    }

    public static void reset() {
        zonedDateTime = null;
    }
}
