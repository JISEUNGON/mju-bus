package com.mjubus.server.controller;

import com.mjubus.server.service.busCalendar.BusCalendarService;
import com.mjubus.server.service.busCalendar.BusCalendarServiceImpl;
import com.mjubus.server.util.DateHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class BusCalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private static BusCalendarService busCalendarService;

    @BeforeAll
    static void setUp() {
        busCalendarService = new BusCalendarServiceImpl();
    }

    @DisplayName("[API][GET][/calendar/] 일정 조회 테스트")
    @Test
    public void testGetBusCalendar() throws Exception {
        // given

        // when & then
        mockMvc.perform(get("/calendar/"))
            .andExpect(status().isOk())
            .andReturn();
    }

    @DisplayName("[API][GET][/calendar/set/{date}] 일정 설정 테스트")
    @Test
    public void testSetBusCalendar() throws Exception {
        // given

        // when & then
        mockMvc.perform(get("/calendar/set/2022-08-01 22:00"))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("2022-08-01T22:00")))
            .andReturn();
    }

    @DisplayName("[API][GET][/calendar/set/{date}] 일정 설정 포맷 테스트")
    @Test
    public void testSetBusCalenda2r() throws Exception {
        // given

        // when & then
        mockMvc.perform(get("/calendar/set/비정상어쩌고저쩌고"))
            .andExpect(status().is4xxClientError())
            .andReturn();
    }

    @DisplayName("[API][GET][/calendar/set/today] 일정 리셋 테스트")
    @Test
    public void testResetBusCalendar() throws Exception {
        // given
        LocalDateTime today = DateHandler.getToday();
        // when & then
        mockMvc.perform(get("/calendar/set/today"))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString(today.toString().substring(0, 12))))
            .andReturn();
    }
}
