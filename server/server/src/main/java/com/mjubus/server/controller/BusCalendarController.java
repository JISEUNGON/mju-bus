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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
