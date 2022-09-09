package com.mjubus.server.service.mjuCalendar;

import com.mjubus.server.controller.BusCalendarController;
import com.mjubus.server.domain.BusCalendar;
import com.mjubus.server.util.DateHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional
class BusCalendarServiceTest {

  @Autowired
  BusCalendarService busCalendarService;

  BusCalendarController busCalendarController = new BusCalendarController();

  @Test
  void findByDate() {
    LocalDateTime today = busCalendarController.setDate("2022-11-01 09:00");

    BusCalendar now = busCalendarService.findByDate(today);
    DateHandler.reset();

    assertThat(now).isNotNull();
  }
}