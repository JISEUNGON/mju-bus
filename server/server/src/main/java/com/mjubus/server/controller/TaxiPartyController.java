package com.mjubus.server.controller;

import com.mjubus.server.domain.TaxiPartyMembers;
import com.mjubus.server.dto.request.ChattingRoomQuitRequest;
import com.mjubus.server.dto.request.TaxiPartyMembersRequest;
import com.mjubus.server.dto.request.TaxiPartyQuitRequest;
import com.mjubus.server.dto.request.TaxiPartyRequest;
import com.mjubus.server.dto.response.TaxiPartyListResponse;
import com.mjubus.server.dto.response.TaxiPartyMembersListResponse;
import com.mjubus.server.dto.response.TaxiPartyMembersResponse;
import com.mjubus.server.dto.response.TaxiPartyResponse;
import com.mjubus.server.repository.TaxiPartyMembersRepository;
import com.mjubus.server.service.chatting.RedisMessageService;
import com.mjubus.server.service.taxiParty.TaxiPartyService;
import com.mjubus.server.service.taxiPartyMembers.TaxiPartyMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/taxi")
@Api(tags = {"택시 파티 API"})
public class TaxiPartyController {

    private final TaxiPartyService taxiPartyService;
    private final TaxiPartyMembersService taxiPartyMembersService;
    private final RedisMessageService redisMessageService;
    private final TaxiPartyMembersRepository taxiPartyMembersRepository;

    @Autowired
    public TaxiPartyController(TaxiPartyService taxiPartyService, TaxiPartyMembersService taxiPartyMembersService, RedisMessageService redisMessageService, TaxiPartyMembersRepository taxiPartyMembersRepository) {
        this.taxiPartyService = taxiPartyService;
        this.taxiPartyMembersService = taxiPartyMembersService;
        this.redisMessageService = redisMessageService;
        this.taxiPartyMembersRepository = taxiPartyMembersRepository;
    }

    @GetMapping("/list")
    @ApiOperation(value = "그룹 리스트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "게시물이 없습니다.")
    })
    public ResponseEntity<TaxiPartyListResponse> getAllPosts() {
        return ResponseEntity.ok(taxiPartyService.findTaxiPartyList());
    }

    @GetMapping("/list/{group-id}")
    @ApiOperation(value = "그룹 상세 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "게시물이 없습니다.")
    })
    public ResponseEntity<TaxiPartyResponse> info(@PathVariable(name = "group-id")TaxiPartyRequest id){
        return ResponseEntity.ok(
                taxiPartyService.findTaxiParty(id)
        );
    }

    @GetMapping("/list/{group-id}/members")
    @ApiOperation(value = "그룹 참여 인원 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "게시물이 없습니다.")
    })
    public ResponseEntity<TaxiPartyMembersListResponse> memberinfo(@PathVariable(name = "group-id") TaxiPartyMembersRequest id){
        return ResponseEntity.ok(
                taxiPartyMembersService.findTaxiPartyMembers(id)
        );
    }

    @GetMapping("/list/{group-id}/members/curnum")
    @ApiOperation(value = "그룹 현재 인원 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "게시물이 없습니다.")
    })
    @ResponseBody
    public Long currentNum(@PathVariable(name="group-id")TaxiPartyMembersRequest id){
        return taxiPartyMembersService.findCurrentMemberNum(id);
    }

    @DeleteMapping("/{group-id}/quit")
    @ApiOperation(value = "파티 탈퇴")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답")
    })
    @ResponseBody
    public ResponseEntity<String> partyQuit(@PathVariable(value = "group-id") TaxiPartyQuitRequest taxiPartyQuitRequest, @RequestParam(value = "sessionMatchingHashKey") ChattingRoomQuitRequest chattingRoomQuitRequest) {
        return ResponseEntity.ok(redisMessageService.chattingRoomQuit(taxiPartyQuitRequest, chattingRoomQuitRequest)
        );
    }
}
