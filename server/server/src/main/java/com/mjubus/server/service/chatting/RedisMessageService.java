package com.mjubus.server.service.chatting;

import com.mjubus.server.dto.request.ChattingRoomQuitRequest;
import com.mjubus.server.dto.request.TaxiPartyQuitRequest;

public interface RedisMessageService {
    public String chattingRoomQuit(Long groupId, TaxiPartyQuitRequest taxiPartyQuitRequest);

}
