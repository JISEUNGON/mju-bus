package com.mjubus.server.service.busCalendar;

import com.mjubus.server.domain.BusCalendar;
import com.mjubus.server.dto.request.BusCalendarSetDateRequest;
import com.mjubus.server.util.DateHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class BusCalendarServiceTest {

    @Autowired
    private BusCalendarService busCalendarService;


    @AfterAll
    public static void resetAfter() {
        DateHandler.reset();
    }


    @Test
    @DisplayName("[Service][getDate] 서버의 날짜를 반환")
    public void getDate() {
        LocalDateTime today = DateHandler.getToday();
        LocalDateTime time = busCalendarService.getDate().getTime();;

        Assertions.assertEquals(today.getDayOfWeek(), time.getDayOfWeek());
        Assertions.assertEquals(today.getHour(), time.getHour());
        Assertions.assertEquals(today.getMinute(), time.getMinute());
        Assertions.assertEquals(today.getMonth(), time.getMonth());
        Assertions.assertEquals(today.getYear(), time.getYear());
    }

    @Test
    @DisplayName("[Service][resetDate] 서버의 날짜를 초기화")
    public void resetDate() {

        LocalDateTime today = DateHandler.getToday();
        LocalDateTime time = busCalendarService.resetDate().getTime();

        Assertions.assertEquals(today.getDayOfWeek(), time.getDayOfWeek());
        Assertions.assertEquals(today.getHour(), time.getHour());
        Assertions.assertEquals(today.getMinute(), time.getMinute());
        Assertions.assertEquals(today.getMonth(), time.getMonth());
        Assertions.assertEquals(today.getYear(), time.getYear());
    }

    @Test
    @DisplayName("[Service][setDate] 서버의 날짜를 변경")
    public void setDate() {
        LocalDateTime time = LocalDateTime.of(2020, 1, 1, 1, 1);
        busCalendarService.setDate(BusCalendarSetDateRequest.of(time));
        LocalDateTime setTime = busCalendarService.getDate().getTime();

        Assertions.assertEquals(time.getDayOfWeek(), setTime.getDayOfWeek());
        Assertions.assertEquals(time.getHour(), setTime.getHour());
        Assertions.assertEquals(time.getMinute(), setTime.getMinute());
        Assertions.assertEquals(time.getMonth(), setTime.getMonth());
        Assertions.assertEquals(time.getYear(), setTime.getYear());
    }

    @Test
    @DisplayName("[Service][findByDate] 여름방학/겨울방학 Calendar를 찾는다.")
    public void 테스트_주말() {
        LocalDateTime weekend;
        BusCalendar busCalendar;

        // 2023년 1월 7일 08:00 (토) 오전
        weekend = DateHandler.getDateWith(1, 7, 8, 0);
        busCalendar = busCalendarService.findByDate(weekend);
        Assertions.assertEquals(busCalendar.getId(), 2);

        // 2023년 1월 7일 08:00 (토) 오전
        weekend = DateHandler.getDateWith(1, 7, 15, 0);
        busCalendar = busCalendarService.findByDate(weekend);
        Assertions.assertEquals(busCalendar.getId(), 2);

        // 2023년 1월 7일 08:00 (토) 오전
        weekend = DateHandler.getDateWith(1, 7, 20, 0);
        busCalendar = busCalendarService.findByDate(weekend);
        Assertions.assertEquals(busCalendar.getId(), 2);

        Assertions.assertTrue((busCalendar.getDayOfWeek() & 96) > 0);
    }

    @Test
    @DisplayName("[Service][findByDate] 평일 Calendar를 찾는다.")
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
    @DisplayName("[Service][findByDate] 개천절 Calendar를 찾는다.")
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
    @DisplayName("[Service][findByDate] 한글날 Calendar를 찾는다.")
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
    @DisplayName("[Service][findByDate] 개교기념일 Calendar를 찾는다.")
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
    @DisplayName("[Service][findByDate] 국군의날 Calendar를 찾는다.")
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

}
