package com.mjubus.server.controller;

import com.mjubus.server.dto.request.StationRadiusDetectedNameRequest;
import com.mjubus.server.dto.response.StationRadiusDetectedNameResponse;
import com.mjubus.server.service.station.radiusDetection.StationRadiusDetectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/radius")
@Api(tags = {"정류장(장소) 반경 감지 API"})
public class StationRadiusDetectionController {

    private final StationRadiusDetectionService stationRadiusDetectionService;

    @Autowired
    public StationRadiusDetectionController(StationRadiusDetectionService stationRadiusDetectionService) {
        this.stationRadiusDetectionService = stationRadiusDetectionService;
    }

    @GetMapping("detect")
    @ApiOperation(value = "특정 위도 경도에 대한 정류장(장소)를 알려준다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당하는 위도경도에 대한 지정된 장소가 없음")
    })
    public ResponseEntity<StationRadiusDetectedNameResponse> getName(@RequestParam Double latitude, @RequestParam Double longitude) {
        return ResponseEntity.ok(stationRadiusDetectionService.detectAndGetName(StationRadiusDetectedNameRequest.of(latitude, longitude)));
    }


}
