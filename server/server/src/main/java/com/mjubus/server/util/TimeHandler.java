package com.mjubus.server.util;

import java.time.LocalDateTime;

public class TimeHandler {

    public static boolean isNightTime() {
        LocalDateTime localDateTime = DateHandler.getToday();
        return localDateTime.getHour() >= 20;
    }
}
