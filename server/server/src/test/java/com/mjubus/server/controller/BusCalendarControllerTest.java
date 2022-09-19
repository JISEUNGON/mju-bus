package com.mjubus.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BusCalendarControllerTest {

  @Autowired
  private BusCalendarController busCalendarController;
}