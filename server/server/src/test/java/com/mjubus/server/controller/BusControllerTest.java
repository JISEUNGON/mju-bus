package com.mjubus.server.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class BusControllerTest {

    private final BusController busController;
    private final BusCalendarController busCalendarController;
    private final MockMvc mockMvc;

    @Autowired
    public BusControllerTest(BusController busController, BusCalendarController busCalendarController, MockMvc mockMvc) {
        this.busController = busController;
        this.busCalendarController = busCalendarController;
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("[API][/bus/{busId}] 버스 정보 조회")
    public void findBusInfo() throws Exception {
        // given
        String busId = "20";

        // when & then
        mockMvc.perform(get("/bus/" + busId))
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    @DisplayName("[API][/bus/{busId}] 버스 정보 조회 - 실패")
    public void findBusInfo2() throws Exception {
        // given
        String busId = "999";

        // when & then
        mockMvc.perform(get("/bus/" + busId))
            .andExpect(status().is4xxClientError())
            .andReturn();
    }

    @Test
    @DisplayName("[API][/bus/{busId}/path] 버스 노선 경로 조회 - 성공")
    public void findBusPath() throws Exception {
        // given
        String busId = "20";

        // when & then
        mockMvc.perform(get("/bus/" + busId + "path"))
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    @DisplayName("[API][/bus/{busId}/path] 버스 노선 경로 조회 - 실패")
    public void findBusPath2() throws Exception {
        // given
        String busId = "200";

        // when & then
        mockMvc.perform(get("/bus/" + busId + "path"))
            .andExpect(status().isOk())
            .andReturn();
    }
}
