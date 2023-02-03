package com.mjubus.server.service.taxiPartyMembers;

import com.mjubus.server.domain.TaxiPartyMembers;
import com.mjubus.server.dto.request.TaxiPartyMembersRequest;
import com.mjubus.server.dto.response.TaxiPartyMembersListResponse;
import com.mjubus.server.dto.response.TaxiPartyMembersResponse;
import com.mjubus.server.repository.TaxiPartyMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaxiPartyMembersServiceImpl implements TaxiPartyMembersService{

    private final TaxiPartyMembersRepository taxiPartyMembersRepository;

    @Autowired
    public TaxiPartyMembersServiceImpl(TaxiPartyMembersRepository taxiPartyMembersRepository){ this.taxiPartyMembersRepository = taxiPartyMembersRepository; }

    @Override
    public TaxiPartyMembersListResponse findTaxiPartyMembers(TaxiPartyMembersRequest req) {
        List<TaxiPartyMembers> result = taxiPartyMembersRepository.findTaxiPartyMembersByTaxiParty_Id(req.getId());
        List<TaxiPartyMembersResponse> taxipartyMembersList = new ArrayList<>();
        for (TaxiPartyMembers taxiPartyMembers : result) {
            taxipartyMembersList.add(TaxiPartyMembersResponse.of(taxiPartyMembers));
        }
        return TaxiPartyMembersListResponse.of(taxipartyMembersList);
    }

    @Override
    public Long findCurrentMemberNum(TaxiPartyMembersRequest req) {
        return taxiPartyMembersRepository.countTaxiPartyMembersByTaxiParty_Id(req.getId());
    }
}
