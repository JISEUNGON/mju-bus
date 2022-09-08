package com.mjubus.server.controller;

import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.busArrival.BusArrivalResponse;
import com.mjubus.server.service.busArrival.BusArrivalService;
import com.mjubus.server.service.station.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/station")
@Api(tags = {"정류장 정보 조회 API"})
public class StationController {

    @Autowired
    private StationService stationService;

    @Autowired
    private BusArrivalService busArrivalService;

    @GetMapping("/{stationID}")
    @ApiOperation(value = "정류장에 대한 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public Station info(@PathVariable(value = "stationID") Long id) {
        return stationService.findStationById(id);
    }

    @GetMapping("/{stationID}/bus-arrival")
    @ApiOperation(value = "정류장에 도착하는 버스 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 버스 정보 없음")
    })
    @ResponseBody
    public BusArrivalResponse busRemains(@PathVariable(value = "stationID") Long stationId, @RequestParam(value = "toSchool") Boolean toSchool, @RequestParam(value = "redBus", required = false) Boolean redBus) {
        Station station = stationService.findStationById(stationId);
        return busArrivalService.findBusArrivalRemainByStation(station, toSchool, redBus != null && redBus);
    }
}
