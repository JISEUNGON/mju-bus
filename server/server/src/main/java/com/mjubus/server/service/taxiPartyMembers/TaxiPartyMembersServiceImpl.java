package com.mjubus.server.service.taxiPartyMembers;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.domain.TaxiPartyMembers;
import com.mjubus.server.dto.request.TaxiPartyMembersRequest;
import com.mjubus.server.dto.response.TaxiPartyMembersListResponse;
import com.mjubus.server.dto.response.TaxiPartyMembersResponse;
import com.mjubus.server.dto.response.TaxiPartyParticipantResponse;
import com.mjubus.server.enums.TaxiPartyEnum;
import com.mjubus.server.exception.TaxiParty.TaxiPartyNotFoundException;
import com.mjubus.server.repository.TaxiPartyMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaxiPartyMembersServiceImpl implements TaxiPartyMembersService{

    private final TaxiPartyMembersRepository taxiPartyMembersRepository;

    @Autowired
    public TaxiPartyMembersServiceImpl(TaxiPartyMembersRepository taxiPartyMembersRepository){ this.taxiPartyMembersRepository = taxiPartyMembersRepository; }

    @Override
    public TaxiPartyMembersListResponse findTaxiPartyMembers(TaxiPartyMembersRequest req) {
        List<TaxiPartyMembers> result = findTaxiPartyMembersByPartyId(req.getId());
        List<TaxiPartyMembersResponse> taxipartyMembersList = new ArrayList<>();
        for (TaxiPartyMembers taxiPartyMembers : result) {
            taxipartyMembersList.add(TaxiPartyMembersResponse.of(taxiPartyMembers));
        }
        return TaxiPartyMembersListResponse.of(taxipartyMembersList);
    }

    @Override
    public TaxiPartyParticipantResponse findPartyParticipantsNum(TaxiPartyMembersRequest req) {
        return TaxiPartyParticipantResponse.of(taxiPartyMembersRepository.countTaxiPartyMembersByTaxiParty_Id(req.getId()));

    }

    @Override
    public boolean isGroupMember(String groupId, Member member) {
        Optional<TaxiPartyMembers> taxiPartyMembersOptional = taxiPartyMembersRepository.findTaxiPartyMembersByTaxiParty_IdAndMember_Id(Long.parseLong(groupId), member.getId());
        return taxiPartyMembersOptional.isPresent();
    }

    @Override
    public boolean isGroupMember(Long groupId, Member member) {
        Optional<TaxiPartyMembers> taxiPartyMembersOptional = taxiPartyMembersRepository.findTaxiPartyMembersByTaxiParty_IdAndMember_Id(groupId, member.getId());
        return taxiPartyMembersOptional.isPresent();
    }

    @Override
    public Optional<TaxiParty> findOptionalPartyByMember(Member member) {
        List<TaxiPartyMembers> taxiPartyList = taxiPartyMembersRepository.findTaxiPartyMembersByMember_Id(member.getId());
        if (taxiPartyList.size() == 0) {
            return Optional.empty();
        }

        return taxiPartyList.stream().map(TaxiPartyMembers::getTaxiParty).filter(party -> party.getStatus() == TaxiPartyEnum.ON_GOING).findFirst();
    }

    @Override
    public List<TaxiPartyMembers> findGroupPartyMembers(TaxiParty taxiParty) {
        return taxiPartyMembersRepository.findTaxiPartyMembersByTaxiParty_Id(taxiParty.getId()).orElseThrow(() -> new TaxiPartyNotFoundException(taxiParty.getId()));
    }

    @Override
    public void addMember(TaxiParty taxiParty, Member member) {
        taxiPartyMembersRepository.save(TaxiPartyMembers.of(taxiParty, member));
    }

    @Override
    public void removeMember(TaxiParty taxiParty, Member member) {
        taxiPartyMembersRepository.deleteByTaxiParty_IdAndMember_Id(taxiParty.getId(), member.getId());
    }

    @Override
    public Long findMembersByTaxiParty(TaxiParty taxiParty) {
        return taxiPartyMembersRepository.countTaxiPartyMembersByTaxiParty_Id(taxiParty.getId());
    }

    @Override
    public Optional<List<TaxiPartyMembers>> findOptionalPartyMembersByPartyId(long partyId) {
        return taxiPartyMembersRepository.findTaxiPartyMembersByTaxiParty_Id(partyId);
    }

    @Override
    public List<TaxiPartyMembers> findTaxiPartyMembersByPartyId(Long groupId) {
        return taxiPartyMembersRepository.findTaxiPartyMembersByTaxiParty_Id(groupId).orElseThrow(() -> new TaxiPartyNotFoundException(groupId));
    }
}
