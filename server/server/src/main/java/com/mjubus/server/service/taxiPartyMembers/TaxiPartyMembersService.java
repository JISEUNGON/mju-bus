package com.mjubus.server.service.taxiPartyMembers;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.domain.TaxiPartyMembers;
import com.mjubus.server.dto.request.TaxiPartyMembersRequest;
import com.mjubus.server.dto.response.TaxiPartyMembersListResponse;
import com.mjubus.server.dto.response.TaxiPartyMembersResponse;
import com.mjubus.server.dto.response.TaxiPartyParticipantResponse;

import java.util.List;
import java.util.Optional;


public interface TaxiPartyMembersService {
    TaxiPartyMembersListResponse findTaxiPartyMembers(TaxiPartyMembersRequest req);

    TaxiPartyParticipantResponse findPartyParticipantsNum(TaxiPartyMembersRequest req);

    boolean isGroupMember(String groupId, Member member);
    boolean isGroupMember(Long groupId, Member member);
    Optional<TaxiParty> findOptionalPartyByMember(Member member);

    List<TaxiPartyMembers> findGroupPartyMembers(TaxiParty taxiParty);

    void addMember(TaxiParty taxiParty, Member member);

    void removeMember(TaxiParty taxiParty, Member member);
}
