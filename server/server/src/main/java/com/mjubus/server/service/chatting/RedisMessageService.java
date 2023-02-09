package com.mjubus.server.service.chatting;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.request.TaxiPartyDeleteRequest;
import com.mjubus.server.dto.request.TaxiPartyQuitRequest;

public interface RedisMessageService {
    void chattingRoomQuit(Long groupId, Member member);
    void chattingRoomAndSessionDelete(TaxiPartyDeleteRequest taxiPartyDeleteRequest);

}
