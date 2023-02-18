package com.mjubus.server.controller;


import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.busListDto.BusList;
import com.mjubus.server.dto.request.BusTimeTableRequest;
import com.mjubus.server.dto.response.*;
import com.mjubus.server.dto.stationPath.PathDto;
import com.mjubus.server.service.bus.BusService;
import com.mjubus.server.service.busTimeTable.BusTimeTableService;
import com.mjubus.server.service.path.PathService;
import com.mjubus.server.service.station.StationService;
import com.mjubus.server.util.DateHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bus")
@Api(tags = {"버스 정보 조회 API"})
public class BusController {
    private final BusService busService;
    private final BusTimeTableService busTimeTableService;
    private final PathService pathService;
    private final StationService stationService;

    @Autowired
    public BusController(BusService busService, BusTimeTableService busTimeTableService, PathService pathService, StationService stationService) {
        this.busService = busService;
        this.busTimeTableService = busTimeTableService;
        this.pathService = pathService;
        this.stationService = stationService;
    }

    @GetMapping("/list")
    @ApiOperation(value = "운행중인 버스 리스트를 받는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "요청한 type이 다른 경우")
    })
    @ResponseBody
    public ResponseEntity<BusListResponse> busTimeTable() {
        return ResponseEntity.ok(busService.getBusListByDate(DateHandler.getToday()));
    }

    @GetMapping("/{busID}/timeTable")
    @ApiOperation(value = "운행중인 버스들의 시간표를 받는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "버스 ID가 정상적이지 않은 경")
    })
    @ResponseBody
    public ResponseEntity<BusTimeTableResponse> busList(@PathVariable(value = "busID") BusTimeTableRequest req) {
        return ResponseEntity.ok(busTimeTableService.makeBusTimeTableByBusId(req));
    }

    @GetMapping("/{busID}")
    @ApiOperation(value = "버스에 대한 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "버스 ID 찾지 못하는 경우")
    })
    @ResponseBody
    public ResponseEntity<BusResponse> info(@PathVariable(value = "busID") BusTimeTableRequest req) {
        return ResponseEntity.ok(busService.findBus(req));
    }

    @GetMapping("/{busID}/status")
    @ApiOperation(value = "버스 운행 여부를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "버스 ID를 찾지 못하는 경우")
    })
    @ResponseBody
    public ResponseEntity<BusStatusResponse> status(@PathVariable(value = "busID") BusTimeTableRequest req) {
        return ResponseEntity.ok(busService.getBusStatus(req));
    }


    @GetMapping("/{busID}/route")
    @ApiOperation(value = "버스가 경유하는 정류장을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public ResponseEntity<BusRouteResponse> stationList(@PathVariable(value = "busID") BusTimeTableRequest req) {
        return ResponseEntity.ok(busService.getBusRoute(req));
    }

    @GetMapping("/{busId}/path")
    @ApiOperation(value = "출발지와 정류장 사이의 경로를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "Station Id가 정확하지 않은 경우"),
            @ApiResponse(responseCode = "500", description = "RequestParam 개수가 일치하지 않는 경우")
    })
    @ResponseBody
    public List<PathDto> getPathBetweenStation(@PathVariable(value = "busId")Long busId, @RequestParam(value = "station")Long stationId, @RequestParam(value = "toSchool")Boolean toSchool) {
        Bus bus = busService.findBusByBusId(busId);
        Station station = stationService.findStationById(stationId);
        return pathService.findPathList(bus, station, toSchool);
    }
}
