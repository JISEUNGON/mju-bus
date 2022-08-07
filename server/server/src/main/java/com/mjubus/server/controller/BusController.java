package com.mjubus.server.controller;


import com.mjubus.server.domain.Bus;
import com.mjubus.server.exception.BusNotFoundExcpetion;
import com.mjubus.server.repository.BusRepository;
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

import java.util.Optional;

@Controller
@RequestMapping("/bus")
@Api(tags = {"버스 정보 조회 API"})
public class BusController {

    @Autowired
    private BusRepository busRepository;
    @GetMapping("/{busID}")
    @ApiOperation(value = "버스에 대한 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public Bus info(@PathVariable(value = "busID") String id) throws ChangeSetPersister.NotFoundException {
        int type = Integer.parseInt(id);
        Optional<Bus> targetBus = busRepository.findByType(type);
        return targetBus.orElseThrow(() -> new BusNotFoundExcpetion(id));

    }



    @GetMapping("/{busID}/status")
    @ApiOperation(value = "버스 운행 여부를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public String status(@PathVariable(value = "busID") String id) {
        return "{\"sid\":\"ee1d5094-9a6b-4c04-95cb-b4d1f29ba303,\",\"name\":\"명지대역,\",\"status\":\"1\"}";
    }

    @GetMapping("/{busID}/route")
    @ApiOperation(value = "버스가 경유하는 정류장을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    @ResponseBody
    public String stationList(@PathVariable(value = "busID") String id) {
        return "{\"sid\":\"ee1d5094-9a6b-4c04-95cb-b4d1f29ba303,\",\"name\":\"명지대역,\",\"routes\":[{\"sid\":\"cafe5065-8db9-450a-b4d1-83dc9c5fbbf9\",\"name\":\"명지대버스관리사무소\",\"order\":\"1\"},{\"sid\":\"a5bccfc2-f93c-456d-8164-db9dfacb1249,\",\"name\":\"상공회의소,\",\"order\":\"2\"},{\"sid\":\"8bb7245b-3ee2-4d87-b5e1-1aab2ab02850,\",\"name\":\"진입로(명지대역행),\",\"order\":\"3\"},{\"sid\":\"f05a3600-cbdf-4c38-ab74-146b2dac9e9d,\",\"name\":\"명지대역,\",\"order\":\"4\"},{\"sid\":\"e0f62712-cd5d-456b-ac3a-c92337f3384c,\",\"name\":\"진입로(명지대행),\",\"order\":\"5\"},{\"sid\":\"008d03ca-9409-4546-9ac3-ea4b038abb6a,\",\"name\":\"이마트,\",\"order\":\"6\"},{\"sid\":\"19cb0c7f-6993-4507-8d3c-539eb34effb3,\",\"name\":\"명진당,\",\"order\":\"7\"},{\"sid\":\"25963aa3-29c5-4372-9893-921e35428a58,\",\"name\":\"제3공학관,\",\"order\":\"8\"}]}";
    }
}
