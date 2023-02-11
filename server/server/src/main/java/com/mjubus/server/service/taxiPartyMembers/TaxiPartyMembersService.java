package com.mjubus.server.service.taxiPartyMembers;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.request.TaxiPartyMembersRequest;
import com.mjubus.server.dto.response.TaxiPartyMembersListResponse;
import com.mjubus.server.dto.response.TaxiPartyMembersResponse;


public interface TaxiPartyMembersService {
    TaxiPartyMembersListResponse findTaxiPartyMembers(TaxiPartyMembersRequest req);

    Long findCurrentMemberNum(TaxiPartyMembersRequest req);

    boolean isMember(String groupId, Member member);
}
