package com.mjubus.server.controller;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.member.MemberPrincipalDto;
import com.mjubus.server.dto.request.*;
import com.mjubus.server.dto.response.*;
import com.mjubus.server.service.chatting.*;
import com.mjubus.server.service.station.radiusDetection.StationRadiusDetectionService;
import com.mjubus.server.service.taxiParty.TaxiPartyService;
import com.mjubus.server.service.taxiPartyMembers.TaxiPartyMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.websocket.server.PathParam;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/taxi")
@AllArgsConstructor
@Api(tags = {"택시 파티 API"})
public class TaxiPartyController {

    private final TaxiPartyService taxiPartyService;
    private final TaxiPartyMembersService taxiPartyMembersService;
    private final RedisMessageService redisMessageService;
    private final RedisMessageSubService redisMessageSubService;
    private final DynamoDbMessageService dynamoDbMessageService;
    private final StationRadiusDetectionService stationRadiusDetectionService;

    @GetMapping("/list")
    @ApiOperation(value = "그룹 리스트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "게시물이 없습니다.")
    })
    public ResponseEntity<TaxiPartyListResponse> getAllPosts() {
        return ResponseEntity.ok(taxiPartyService.findTaxiPartyList());
    }

    @PostMapping("/create")
    @ApiOperation(value = "파티 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 파티"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "멤버 또는 택시 도착지가 존재하지 않음")
    })
    @ResponseBody
    public ResponseEntity<TaxiPartyCreateResponse> createNewParty(@ApiIgnore Authentication authentication, @RequestBody TaxiPartyCreateRequest request) {
        return ResponseEntity.ok(taxiPartyService.createTaxiParty((MemberPrincipalDto) authentication.getPrincipal(), request));
    }

    @GetMapping("/{group-id}")
    @ApiOperation(value = "그룹 상세 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "게시물이 없습니다.")
    })
    public ResponseEntity<TaxiPartyDetailResponse> info(@ApiIgnore Authentication authentication, @PathVariable(name = "group-id")TaxiPartyRequest id){
        return ResponseEntity.ok(
                taxiPartyService.findTaxiParty(id)
        );
    }
    @PostMapping("/{group-id}")
    @ApiOperation(value = "파티 참여")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "204", description = "해당하는 파티가 존재하지 않음"),
            @ApiResponse(responseCode = "400", description = "이미 파티에 속한 멤버임"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당하는 멤버가 존재하지 않음")
    })
    @ResponseBody
    public ResponseEntity<TaxiPartyJoinResponse> addNewMember(@ApiIgnore Authentication authentication, @PathVariable(value = "group-id") Long groupId) {

        //TODO: FCM "~ 사용자님이 새게 들어왔습니다" Actions;
        return ResponseEntity.ok(taxiPartyService.addNewMember(groupId, (MemberPrincipalDto) authentication.getPrincipal()));
    }
    @DeleteMapping("/{group-id}")
    @ApiOperation(value = "파티 탈퇴")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "204", description = "해당하는 파티가 존재하지 않음"),
            @ApiResponse(responseCode = "400", description = "이미 파티에 존재하지 않음"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당하는 멤버가 존재하지 않음")
    })
    @ResponseBody
    public ResponseEntity<TaxiPartyQuitResponse> partyQuit(@ApiIgnore Authentication authentication, @PathVariable(value = "group-id") Long groupId) {
        //TODO: FCM "~ 사용자님이 탈퇴하였습니다" Actions;
        return ResponseEntity.ok(taxiPartyService.quitParty((MemberPrincipalDto) authentication.getPrincipal(), groupId));
    }

    @GetMapping("/{group-id}/members")
    @ApiOperation(value = "그룹 참여 인원 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "게시물이 없습니다.")
    })
    public ResponseEntity<TaxiPartyMembersListResponse> memberinfo(@ApiIgnore Authentication authentication, @PathVariable(name = "group-id") TaxiPartyMembersRequest id){
        return ResponseEntity.ok(
                taxiPartyMembersService.findTaxiPartyMembers(id)
        );
    }
    @GetMapping("/{group-id}/members/num")
    @ApiOperation(value = "그룹 현재 인원 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "게시물이 없습니다.")
    })
    @ResponseBody
    public ResponseEntity<TaxiPartyParticipantResponse> findPartyParticipantsNum(@ApiIgnore Authentication authentication, @PathVariable(name="group-id")TaxiPartyMembersRequest id){
        return ResponseEntity.ok(
                taxiPartyMembersService.findPartyParticipantsNum(id)
        );
    }

    @DeleteMapping("{group-id}/delete")
    @ApiOperation(value = "파티 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "204", description = "해당하는 파티가 존재하지 않음")
    })
    @ResponseBody
    public ResponseEntity<TaxiPartyDeleteResponse> partyDelete(@ApiIgnore Authentication authentication, @PathParam(value = "group-id") TaxiPartyDeleteRequest taxiPartyDeleteRequest) {
        redisMessageService.chattingRoomDelete(taxiPartyDeleteRequest);
        taxiPartyService.deleteParty(taxiPartyDeleteRequest);
        return ResponseEntity.ok(TaxiPartyDeleteResponse.builder().isDeleted("success").build());
    }

    @GetMapping("/{group-id}/chatting")
    @ApiOperation(value = "채팅 기록을 얻는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "Room ID가 정상적이지 않은 경우")
    })
    @ResponseBody
    public ResponseEntity<List<MessageHistoryResponse>> findMessageHistory(@ApiIgnore Authentication authentication, @PathVariable(value = "group-id") MessageHistoryRequest req) {
        return ResponseEntity.ok(MessageHistoryResponse.of(dynamoDbMessageService.findMessageHistory(req)));
    }

    @PatchMapping("/{group-id}/chatting/status")
    @ApiOperation(value = "채팅방에 참여중인 사람의 subscribed 상태를 변경한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "Room ID가 정상적이지 않은 경우 또는 simpSubscriptionId가 존재하지 않는 경우")
    })
    public ResponseEntity<String> updateSessionHashStatus(@ApiIgnore Authentication authentication, @RequestBody UpdateChattingSessionHashRequest updateChattingSessionHashRequest) {
        return ResponseEntity.ok(redisMessageSubService.updateSessionHash(updateChattingSessionHashRequest));
    }

    @GetMapping("/{group-id}/location")
    @ApiOperation(value = "택시 파티가 가진 위도경도의 장소명을 반환한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당하는 파티가 존재하지 않음")
    })
    public ResponseEntity<StationRadiusDetectedNameResponse> getLocationName(@ApiIgnore Authentication authentication, @PathVariable(value = "group-id") StationRadiusDetectedNameRequest request) {
        return ResponseEntity.ok(stationRadiusDetectionService.detectAndGetName(request));
    }

}
