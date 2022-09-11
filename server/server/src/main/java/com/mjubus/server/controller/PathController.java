package com.mjubus.server.controller;


import com.mjubus.server.domain.PathDetail;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.stationPath.PathDto;
import com.mjubus.server.service.path.PathService;
import com.mjubus.server.service.station.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/path")
@Api(tags = {"정류장 경로 조회 API"})
public class PathController {

    @Autowired
    private StationService stationService;

    @Autowired
    private PathService pathService;

    @GetMapping("/")
    @ApiOperation(value = "출발지와 정류장 사이의 경로를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "Station Id가 정확하지 않은 경우"),
            @ApiResponse(responseCode = "500", description = "RequestParam 개수가 일치하지 않는 경우")
    })
    @ResponseBody
    public List<PathDto> getPathBetweenStation(@RequestParam(value = "src")Long srcStationId, @RequestParam(value = "dest")Long destStationId) {
        Station src = stationService.findStationById(srcStationId);
        Station dest = stationService.findStationById(destStationId);

        return pathService.findPathListBetween(src, dest);
    }
}
