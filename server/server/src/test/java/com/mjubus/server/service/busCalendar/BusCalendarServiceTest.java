package com.mjubus.server.service.busCalendar;

import com.mjubus.server.domain.BusCalendar;
import com.mjubus.server.util.DateHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class BusCalendarServiceTest {

  @Autowired
  BusCalendarService busCalendarService;

  @Before
  public void reset() {
    DateHandler.reset();
  }

  @Test
  public void 테스트_주말() {
    LocalDateTime weekend;
    BusCalendar busCalendar;

    // 9월 10일 08:00 (토) 오전
    weekend = DateHandler.getDateWith(9, 10, 8, 0);
    busCalendar = busCalendarService.findByDate(weekend);
    Assertions.assertEquals(busCalendar.getId(), 5);

    // 9월 10일 18:00 (토) 오후
    weekend = DateHandler.getDateWith(9, 10, 15, 0);
    busCalendar = busCalendarService.findByDate(weekend);
    Assertions.assertEquals(busCalendar.getId(), 5);

    // 9월 10일 20:00 (토) 저녁
    weekend = DateHandler.getDateWith(9, 10, 20, 0);
    busCalendar = busCalendarService.findByDate(weekend);
    Assertions.assertEquals(busCalendar.getId(), 5);

    Assertions.assertTrue((busCalendar.getDayOfWeek() & 96) > 0);
  }

  @Test
  public void 테스트_평일() {
    LocalDateTime weekday;
    BusCalendar busCalendar;

    // 9월 20일 08:00 (화) 오전
    weekday = DateHandler.getDateWith(9, 20, 8, 0);
    busCalendar = busCalendarService.findByDate(weekday);
    Assertions.assertEquals(busCalendar.getId(), 6);

    // 9월 20일 18:00 (화) 오후
    weekday = DateHandler.getDateWith(9, 20, 15, 0);
    busCalendar = busCalendarService.findByDate(weekday);
    Assertions.assertEquals(busCalendar.getId(), 6);

    // 9월 20일 20:00 (화) 저녁
    weekday = DateHandler.getDateWith(9, 20, 20, 0);
    busCalendar = busCalendarService.findByDate(weekday);
    Assertions.assertEquals(busCalendar.getId(), 6);

    Assertions.assertTrue((busCalendar.getDayOfWeek() & 31) > 0);
  }

  @Test
  public void 테스트_개천절() {
    LocalDateTime holiday;
    BusCalendar busCalendar;

    // 10월 3일 08:00 (월) 오전
    holiday = DateHandler.getDateWith(10, 3, 8, 0);
    busCalendar = busCalendarService.findByDate(holiday);
    Assertions.assertEquals(busCalendar.getId(), 3);

    // 10월 3일 18:00 (월) 오후
    holiday = DateHandler.getDateWith(10, 3, 15, 0);
    busCalendar = busCalendarService.findByDate(holiday);
    Assertions.assertEquals(busCalendar.getId(), 3);

    // 10월 3일 20:00 (월) 저녁
    holiday = DateHandler.getDateWith(10, 3, 20, 0);
    busCalendar = busCalendarService.findByDate(holiday);
    Assertions.assertEquals(busCalendar.getId(), 3);

    Assertions.assertTrue((busCalendar.getDayOfWeek() & 1) > 0);
  }

  @Test
  public void 테스트_한글날() {
    LocalDateTime hangul;
    BusCalendar busCalendar;

    // 10월 10일 08:00 (월) 오전
    hangul = DateHandler.getDateWith(10, 10, 8, 0);
    busCalendar = busCalendarService.findByDate(hangul);
    Assertions.assertEquals(busCalendar.getId(), 1);

    // 10월 10일 18:00 (월) 오후
    hangul = DateHandler.getDateWith(10, 10, 15, 0);
    busCalendar = busCalendarService.findByDate(hangul);
    Assertions.assertEquals(busCalendar.getId(), 1);

    // 10월 10일 20:00 (월) 저녁
    hangul = DateHandler.getDateWith(10, 10, 20, 0);
    busCalendar = busCalendarService.findByDate(hangul);
    Assertions.assertEquals(busCalendar.getId(), 1);

    Assertions.assertTrue((busCalendar.getDayOfWeek() & 1) > 0);
  }

  @Test
  public void 테스트_개교기념일() {
    LocalDateTime schoolBirth;
    BusCalendar busCalendar;

    // 9월 7일 08:00 (수) 오전
    schoolBirth = DateHandler.getDateWith(9, 7, 8, 0);
    busCalendar = busCalendarService.findByDate(schoolBirth);
    Assertions.assertEquals(busCalendar.getId(), 4);

    // 9월 7일 18:00 (수) 오후
    schoolBirth = DateHandler.getDateWith(9, 7, 15, 0);
    busCalendar = busCalendarService.findByDate(schoolBirth);
    Assertions.assertEquals(busCalendar.getId(), 4);

    // 9월 7일 20:00 (수) 저녁
    schoolBirth = DateHandler.getDateWith(9, 7, 20, 0);
    busCalendar = busCalendarService.findByDate(schoolBirth);
    Assertions.assertEquals(busCalendar.getId(), 4);

    Assertions.assertTrue((busCalendar.getDayOfWeek() & 4) > 0);
  }

  @Test
  public void 테스트_국군의날() {
    LocalDateTime soldierDay;
    BusCalendar busCalendar;

    // 10월 1일 08:00 (토) 오전
    soldierDay = DateHandler.getDateWith(10, 1, 8, 0);
    busCalendar = busCalendarService.findByDate(soldierDay);
    Assertions.assertEquals(busCalendar.getId(), 7);

    // 10월 1일 18:00 (토) 오후
    soldierDay = DateHandler.getDateWith(10, 1, 15, 0);
    busCalendar = busCalendarService.findByDate(soldierDay);
    Assertions.assertEquals(busCalendar.getId(), 7);

    // 10월 1일 20:00 (토) 저녁
    soldierDay = DateHandler.getDateWith(10, 1, 20, 0);
    busCalendar = busCalendarService.findByDate(soldierDay);
    Assertions.assertEquals(busCalendar.getId(), 7);

    Assertions.assertTrue((busCalendar.getDayOfWeek() & 32) > 0);
  }

  @After
  public void resetAfter() {
    DateHandler.reset();
  }
}