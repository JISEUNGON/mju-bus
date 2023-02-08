package com.mjubus.server.service.taxiParty;

import com.mjubus.server.dto.request.TaxiPartyCreateRequest;
import com.mjubus.server.dto.request.TaxiPartyJoinRequest;
import com.mjubus.server.dto.request.TaxiPartyRequest;
import com.mjubus.server.dto.response.TaxiPartyListResponse;
import com.mjubus.server.dto.response.TaxiPartyResponse;

public interface TaxiPartyService {

    TaxiPartyResponse findTaxiParty(TaxiPartyRequest req);

    TaxiPartyListResponse findTaxiPartyList();

    void createTaxiParty(TaxiPartyCreateRequest request);
    void addNewMember(Long groupId, TaxiPartyJoinRequest request);

}
