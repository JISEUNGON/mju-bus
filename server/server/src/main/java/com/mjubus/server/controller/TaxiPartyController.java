package com.mjubus.server.controller;

import com.mjubus.server.dto.request.*;
import com.mjubus.server.dto.response.*;
import com.mjubus.server.repository.TaxiPartyMembersRepository;
import com.mjubus.server.service.chatting.RedisMessageService;
import com.mjubus.server.service.taxiParty.TaxiPartyService;
import com.mjubus.server.service.taxiPartyMembers.TaxiPartyMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


@Slf4j
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
    public ResponseEntity<TaxiPartyParticipantResponse> findPartyParticipantsNum(@PathVariable(name="group-id")TaxiPartyMembersRequest id){
        return ResponseEntity.ok(
                taxiPartyMembersService.findPartyParticipantsNum(id)
        );
    }

    @PostMapping("/create")
    @ApiOperation(value = "파티 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 파티"),
            @ApiResponse(responseCode = "404", description = "멤버 또는 택시 도착지가 존재하지 않음")
    })
    @ResponseBody
    public ResponseEntity<TaxiPartyCreateResponse> createNewParty(@RequestBody TaxiPartyCreateRequest request) {
        return ResponseEntity.ok(taxiPartyService.createTaxiParty(request));
    }

    @PostMapping("/list/{group-id}/members/new")
    @ApiOperation(value = "파티 멤버 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "204", description = "해당하는 파티가 존재하지 않음"),
            @ApiResponse(responseCode = "400", description = "이미 파티에 속한 멤버임"),
            @ApiResponse(responseCode = "404", description = "해당하는 멤버가 존재하지 않음")
    })
    @ResponseBody
    public ResponseEntity<TaxiPartyJoinResponse> addNewMember(@PathVariable(value = "group-id") Long groupId, @RequestBody TaxiPartyJoinRequest request) {
        taxiPartyService.addNewMember(groupId, request);
        //TODO: FCM "~ 사용자님이 새롭게 들어왔습니다" Actions;
        return ResponseEntity.ok(TaxiPartyJoinResponse.builder().isAdded("success").build());
    }


    @DeleteMapping("/{group-id}/members/quit")
    @ApiOperation(value = "파티 탈퇴")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "204", description = "해당하는 파티가 존재하지 않음"),
            @ApiResponse(responseCode = "400", description = "이미 파티에 존재하지 않음"),
            @ApiResponse(responseCode = "404", description = "해당하는 멤버가 존재하지 않음")
    })
    @ResponseBody
    public ResponseEntity<TaxiPartyQuitResponse> partyQuit(@PathVariable(value = "group-id") Long groupId, @RequestParam(value = "memberId") TaxiPartyQuitRequest taxiPartyQuitRequest) {
        taxiPartyService.removeMember(groupId, taxiPartyQuitRequest);
        redisMessageService.chattingRoomQuit(groupId, taxiPartyQuitRequest);
        //TODO: FCM "~ 사용자님이 탈퇴하였습니다" Actions;
        return ResponseEntity.ok(TaxiPartyQuitResponse.builder().isQuited("success").build());
    }

    @DeleteMapping("{group-id}/delete")
    @ApiOperation(value = "파티 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "204", description = "해당하는 파티가 존재하지 않음")
    })
    @ResponseBody
    public ResponseEntity<TaxiPartyDeleteResponse> partyDelete(@PathParam(value = "group-id") TaxiPartyDeleteRequest taxiPartyDeleteRequest) {
        redisMessageService.chattingRoomAndSessionDelete(taxiPartyDeleteRequest);
        taxiPartyService.deleteParty(taxiPartyDeleteRequest);
        return ResponseEntity.ok(TaxiPartyDeleteResponse.builder().isDeleted("success").build());
    }
}
