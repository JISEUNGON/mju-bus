package com.mjubus.server.controller;


import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.BusCalendar;
import com.mjubus.server.exception.Bus.BusNotFoundException;
import com.mjubus.server.exception.BusCalenderNotFoundException;
import com.mjubus.server.service.mjuCalendar.BusCalendarService;
import com.mjubus.server.util.DateHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/calendar")
@Api(tags = {"일정 조회 API"})
public class BusCalendarController {

    @Autowired
    private BusCalendarService busCalendarService;

    @GetMapping("/")
    @ApiOperation(value = "오늘 해당되는 일정 정보를 확인한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public BusCalendar info() {
        return busCalendarService.findByDate(DateHandler.getToday());
    }


    @GetMapping("/set/{date}")
    @ApiOperation(value = "서버의 날짜를 변경한다 : 2022-08-01 22:00")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public LocalDateTime setDate(@PathVariable String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDate = LocalDateTime.parse(date, formatter);
        DateHandler.setZonedDateTime(localDate);
        return DateHandler.getToday();
    }

}
