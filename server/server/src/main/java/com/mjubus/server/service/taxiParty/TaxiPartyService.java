package com.mjubus.server.service.taxiParty;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.dto.member.MemberPrincipalDto;
import com.mjubus.server.dto.request.TaxiPartyRequest;
import com.mjubus.server.dto.request.*;
import com.mjubus.server.dto.response.*;

import java.util.Optional;

public interface TaxiPartyService {

    TaxiPartyDetailResponse findTaxiParty(TaxiPartyRequest req);

    TaxiPartyListResponse findTaxiPartyList();

    TaxiPartyCreateResponse createTaxiParty(Member member, TaxiPartyCreateRequest request);
    TaxiPartyJoinResponse addNewMember(Long groupId, Member member);

    TaxiPartyQuitResponse quitParty(Member member, Long partyId);

    void deleteParty(TaxiPartyDeleteRequest request);

    void hidePartyWithSchedule();

    boolean removeMember(Long groupId, Member member);

    boolean hasActiveParty(Member member);

    TaxiParty findTaxiPartyById(Long id);

    Optional<TaxiParty> findOptionalPartyById(long parseLong);
}
