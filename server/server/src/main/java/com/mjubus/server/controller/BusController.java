package com.mjubus.server.controller;


import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.BusStatusDto;
import com.mjubus.server.dto.StationDTO;
import com.mjubus.server.exception.BusNotFoundExcpetion;
import com.mjubus.server.repository.BusRepository;
import com.mjubus.server.service.bus.BusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bus")
@Api(tags = {"버스 정보 조회 API"})
public class BusController {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusService busService;

    @GetMapping("/{busID}")
    @ApiOperation(value = "버스에 대한 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "버스 ID 찾지 못하는 경")
    })
    @ResponseBody
    public Bus info(@PathVariable(value = "busID") String id) throws ChangeSetPersister.NotFoundException {
        int type = Integer.parseInt(id);
        Optional<Bus> targetBus = busService.getBusByType(type);
        return targetBus.orElseThrow(() -> new BusNotFoundExcpetion(id));

    }

    @GetMapping("/{busID}/status")
    @ApiOperation(value = "버스 운행 여부를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public BusStatusDto status(@PathVariable(value = "busID") String id) {
        int type = Integer.parseInt(id);
        return busService.getBusStatus(type);
    }

    @GetMapping("/{busID}/route")
    @ApiOperation(value = "버스가 경유하는 정류장을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public List<StationDTO> stationList(@PathVariable(value = "busID") String id) {
        int type = Integer.parseInt(id);
        List<StationDTO> bus = busService.getBusStations(type);
        return bus;
    }
}
