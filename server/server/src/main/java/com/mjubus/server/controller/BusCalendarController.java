package com.mjubus.server.controller;


import com.mjubus.server.dto.request.BusCalendarSetDateRequest;
import com.mjubus.server.dto.response.BusCalendarGetDateResponse;
import com.mjubus.server.dto.response.BusCalendarResponse;
import com.mjubus.server.service.busCalendar.BusCalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResponseEntity<BusCalendarResponse> info() {
        return ResponseEntity.ok(
            busCalendarService.findBusCalendar()
        );
    }


    @GetMapping("/set/{date}")
    @ApiOperation(value = "서버의 날짜를 변경한다 : 2022-08-01 22:00")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public ResponseEntity<BusCalendarGetDateResponse> setDate(@PathVariable BusCalendarSetDateRequest date) {
        busCalendarService.setDate(date);
        return ResponseEntity.ok(
            busCalendarService.getDate()
        );
    }

    @GetMapping("/set/today")
    @ApiOperation(value = "서버의 날짜를 현재 시각으로 복귀한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public ResponseEntity<BusCalendarGetDateResponse> setDateToday() {
        busCalendarService.resetDate();
        return ResponseEntity.ok(
            busCalendarService.getDate()
        );
    }

}
