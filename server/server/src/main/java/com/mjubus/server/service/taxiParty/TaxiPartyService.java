package com.mjubus.server.service.taxiParty;

import com.mjubus.server.dto.request.*;
import com.mjubus.server.dto.response.TaxiPartyListResponse;
import com.mjubus.server.dto.response.TaxiPartyResponse;

public interface TaxiPartyService {

    TaxiPartyResponse findTaxiParty(TaxiPartyRequest req);

    TaxiPartyListResponse findTaxiPartyList();

    void createTaxiParty(TaxiPartyCreateRequest request);
    void addNewMember(Long groupId, TaxiPartyJoinRequest request);
    void removeMember(Long groupId, TaxiPartyQuitRequest request);
    void deleteParty(TaxiPartyDeleteRequest request);

}
