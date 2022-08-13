package com.mjubus.server.controller;

import com.mjubus.server.domain.Station;
import com.mjubus.server.exception.Bus.BusNotFoundException;
import com.mjubus.server.service.station.StationService;
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
@RequestMapping("/station")
@Api(tags = {"정류장 정보 조회 API"})
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/{stationID}")
    @ApiOperation(value = "정류장에 대한 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public Station info(@PathVariable(value = "stationID") Long id) {
        Optional<Station> targetStation = stationService.findStationById(id);

        return targetStation.orElseThrow(() -> new BusNotFoundException(id));
    }

    @GetMapping("/{stationID}/bus-remain")
    @ApiOperation(value = "정류장에 도착하는 버스 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public String remains(@PathVariable(value = "stationID") String id) {
        return "{\"sid\":\"e0f62712-cd5d-456b-ac3a-c92337f3384c\",\"name\":\"진입로(명지대행)\",\"bus\":[{\"sid\":\"e75a9d3a-e08b-4221-b527-20e4c792b2d2\",\"name\":\"5000B\",\"charge\":\"0\",\"type\":\"100\",\"remain\":\"98\"},{\"sid\":\"da8397e8-7065-45e2-f1b1-b44341bef840\",\"name\":\"5001-1\",\"charge\":\"0\",\"type\":\"102\",\"remain\":\"136\"},{\"sid\":\"2914b958-c13e-4475-c15c-2de651d374f6\",\"name\":\"5005\",\"charge\":\"0\",\"type\":\"106\",\"remain\":\"235\"},{\"sid\":\"ee1d5094-9a6b-4c04-95cb-b4d1f29ba303\",\"name\":\"명지대역\",\"charge\":\"0\",\"type\":\"1\",\"remain\":\"356\"}]}";
    }
}