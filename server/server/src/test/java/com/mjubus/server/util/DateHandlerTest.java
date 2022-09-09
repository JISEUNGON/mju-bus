package com.mjubus.server.util;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class DateHandlerTest {

    @Before
    public void reset() {
        DateHandler.reset();
    }

    @Test
    public void 테스트_한국시간() {
        LocalDateTime today = DateHandler.getToday().truncatedTo(ChronoUnit.HOURS);
        LocalDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().truncatedTo(ChronoUnit.HOURS);

        Assertions.assertEquals(today, now);
    }

    @Test
    public void 테스트_날짜변경() {
        LocalDateTime dateWith = DateHandler.getDateWith(3, 3, 3, 33);

        Assertions.assertEquals(dateWith.getMonth().getValue(), 3);
        Assertions.assertEquals(dateWith.getDayOfMonth(), 3);
        Assertions.assertEquals(dateWith.getHour(), 3);
        Assertions.assertEquals(dateWith.getMinute(), 33);
    }

    @Test
    public void 테스트_시간변경() {
        LocalDateTime todayWith = DateHandler.getTodayWith(3, 33);

        Assertions.assertEquals(todayWith.getHour(), 3);
        Assertions.assertEquals(todayWith.getMinute(), 33);
    }

    @Test
    public void 테스트_단일_비트마스크() {
        // 2022년 9월 5일(월) ~ 9일(금), 10(토), 11(일)
        LocalDateTime now;

        // 월요일 테스트
        now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().with(LocalDate.of(2022, 9, 5));
        Assertions.assertEquals(DateHandler.getDayOfWeek(now), 1);

        // 화요일 테스트
        now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().with(LocalDate.of(2022, 9, 6));
        Assertions.assertEquals(DateHandler.getDayOfWeek(now), 2);

        // 수요일 테스트
        now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().with(LocalDate.of(2022, 9, 7));
        Assertions.assertEquals(DateHandler.getDayOfWeek(now), 4);

        // 목요일 테스트
        now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().with(LocalDate.of(2022, 9, 8));
        Assertions.assertEquals(DateHandler.getDayOfWeek(now), 8);

        // 금요일 테스트
        now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().with(LocalDate.of(2022, 9, 9));
        Assertions.assertEquals(DateHandler.getDayOfWeek(now), 16);

        // 토요일 테스트
        now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().with(LocalDate.of(2022, 9, 10));
        Assertions.assertEquals(DateHandler.getDayOfWeek(now), 32);

        // 일요일 테스트
        now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().with(LocalDate.of(2022, 9, 11));
        Assertions.assertEquals(DateHandler.getDayOfWeek(now), 64);

    }

}