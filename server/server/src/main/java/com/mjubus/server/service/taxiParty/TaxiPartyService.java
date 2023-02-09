package com.mjubus.server.service.taxiParty;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.dto.request.TaxiPartyRequest;
import com.mjubus.server.dto.request.*;
import com.mjubus.server.dto.response.TaxiPartyCreateResponse;
import com.mjubus.server.dto.response.TaxiPartyDetailResponse;
import com.mjubus.server.dto.response.TaxiPartyListResponse;
import com.mjubus.server.dto.response.TaxiPartyResponse;

public interface TaxiPartyService {

    TaxiPartyDetailResponse findTaxiParty(TaxiPartyRequest req);

    TaxiPartyListResponse findTaxiPartyList();

    TaxiPartyCreateResponse createTaxiParty(Member member, TaxiPartyCreateRequest request);
    void addNewMember(Long groupId, Member member);
    void removeMember(Long groupId, Member member);
    void deleteParty(TaxiPartyDeleteRequest request);

    boolean hasActiveParty(Member member);

    TaxiParty findTaxiPartyById(Long id);

}
