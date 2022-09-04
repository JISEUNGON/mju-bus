package com.mjubus.server.controller;

import com.mjubus.server.domain.BusCalendar;
import com.mjubus.server.util.DateHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BusCalendarControllerTest {

  @Autowired
  private BusCalendarController busCalendarController;

  @Test
  void info() {
    BusCalendar info = busCalendarController.info();

    System.out.println(info.getDescription());
  }

  @Test
  void setDate() {
    LocalDateTime today = busCalendarController.setDate("2022-11-01 09:00");

    System.out.println("Today is " + today.getMonth());

  }

  @Test
  void setDateToday() {
    LocalDateTime today = busCalendarController.setDateToday();

    System.out.println("Today is " + today.getMonthValue());
  }
}