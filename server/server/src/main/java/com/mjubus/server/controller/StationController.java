package com.mjubus.server.controller;

import com.mjubus.server.dto.request.StationRemainRequest;
import com.mjubus.server.dto.request.StationRequest;
import com.mjubus.server.dto.response.BusArrivalResponse;
import com.mjubus.server.dto.response.StationResponse;
import com.mjubus.server.service.busArrival.BusArrivalService;
import com.mjubus.server.service.station.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/station")
@Api(tags = {"정류장 정보 조회 API"})
public class StationController {

    private final StationService stationService;
    private final BusArrivalService busArrivalService;

    @Autowired
    public StationController(StationService stationService, BusArrivalService busArrivalService) {
        this.stationService = stationService;
        this.busArrivalService = busArrivalService;
    }

    @GetMapping("/{stationID}")
    @ApiOperation(value = "정류장에 대한 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public ResponseEntity<StationResponse> info(@PathVariable(value = "stationID") StationRequest req) {
        return ResponseEntity.ok(
                stationService.findStation(req)
        );
    }

    @GetMapping("/{stationID}/bus-arrival")
    @ApiOperation(value = "정류장에 도착하는 버스 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 버스 정보 없음")
    })
    @ResponseBody
    public ResponseEntity<BusArrivalResponse> busRemains(@PathVariable(value = "stationID") StationRequest req, @ModelAttribute StationRemainRequest remainReq) {
        return ResponseEntity.ok(
                busArrivalService.findBusArrivalRemain(req, remainReq)
        );
    }
}
