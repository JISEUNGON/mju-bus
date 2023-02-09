package com.mjubus.server.service.chatting;

import com.mjubus.server.dto.request.TaxiPartyDeleteRequest;
import com.mjubus.server.dto.request.TaxiPartyQuitRequest;

public interface RedisMessageService {
    public void chattingRoomQuit(Long groupId, TaxiPartyQuitRequest taxiPartyQuitRequest);
    public void chattingRoomAndSessionDelete(TaxiPartyDeleteRequest taxiPartyDeleteRequest);

}
