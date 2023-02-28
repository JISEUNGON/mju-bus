package com.mjubus.server.service.chatting;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.request.TaxiPartyDeleteRequest;

public interface RedisMessageService {
    boolean quitChattingRoom(Long groupId, Member member);
    void chattingRoomDelete(TaxiPartyDeleteRequest taxiPartyDeleteRequest);

}
