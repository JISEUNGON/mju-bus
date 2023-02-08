package com.mjubus.server.service.taxiParty;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.TaxiDestination;
import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.domain.TaxiPartyMembers;
import com.mjubus.server.dto.request.TaxiPartyCreateRequest;
import com.mjubus.server.dto.request.TaxiPartyJoinRequest;
import com.mjubus.server.dto.request.TaxiPartyQuitRequest;
import com.mjubus.server.dto.request.TaxiPartyRequest;
import com.mjubus.server.dto.response.TaxiPartyListResponse;
import com.mjubus.server.dto.response.TaxiPartyResponse;
import com.mjubus.server.exception.TaxiParty.TaxiPartyNotFoundException;
import com.mjubus.server.repository.MemberRepository;
import com.mjubus.server.repository.TaxiDestinationRepository;
import com.mjubus.server.repository.TaxiPartyMembersRepository;
import com.mjubus.server.repository.TaxiPartyRepository;
import com.mjubus.server.util.DateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service

public class TaxiPartyServiceImpl implements TaxiPartyService{

    private final TaxiPartyRepository taxiPartyRepository;
    private final TaxiDestinationRepository taxiDestinationRepository;
    private final MemberRepository memberRepository;
    private final TaxiPartyMembersRepository partyMembersRepository;

    @Autowired
    public TaxiPartyServiceImpl(TaxiPartyRepository taxiPartyRepository, TaxiDestinationRepository taxiDestinationRepository, MemberRepository memberRepository, TaxiPartyMembersRepository partyMembersRepository) {
        this.taxiPartyRepository = taxiPartyRepository;
        this.taxiDestinationRepository = taxiDestinationRepository;
        this.memberRepository = memberRepository;
        this.partyMembersRepository = partyMembersRepository;
    }
    @Override
    public TaxiPartyResponse findTaxiParty(TaxiPartyRequest req) {
        TaxiParty result = taxiPartyRepository.findById(req.getId()).orElseThrow(() -> new TaxiPartyNotFoundException(req.getId()));
        return TaxiPartyResponse.of(result);
    }

    @Override
    public TaxiPartyListResponse findTaxiPartyList() {
        List<TaxiParty> result = taxiPartyRepository.findAll();
        List<TaxiPartyResponse> taxipartyList = new ArrayList<>();
        for (TaxiParty taxiParty : result) {
            taxipartyList.add(TaxiPartyResponse.of(taxiParty));
        }
        return TaxiPartyListResponse.of(taxipartyList);
    }

    @Override
    public void createTaxiParty(TaxiPartyCreateRequest request) {
        Optional<Member> memberFindResult = memberRepository.findById(request.getAdminister());
        if (memberFindResult.isEmpty()) throw new IllegalArgumentException("존재하지 않는 Member");
        Optional<TaxiParty> adminFindResult = Optional.ofNullable(taxiPartyRepository.findByAdminister(memberFindResult.get()));
        if (adminFindResult.isPresent()) throw new IllegalArgumentException("존재하는 파티");
        Optional<TaxiDestination> destFindResult = taxiDestinationRepository.findById(request.getTaxiDestinationId());
        if (destFindResult.isEmpty()) throw new IllegalArgumentException("존재하지 않는 택시 목적지");


        taxiPartyRepository.save(new TaxiParty(
                null,
                memberFindResult.get(),
                destFindResult.get(),
                request.getMeetingLatitude(),
                request.getMeetingLongitude(),
                request.getMemo(),
                request.getMin(),
                request.getMax(),
                request.getEndAt(),
                DateHandler.getToday(),
                1L
        ));
    }

    @Override
    public void addNewMember(Long groupId, TaxiPartyJoinRequest request) {
        log.info("[ADD_NEW_MEMBER]");
        Optional<Member> memberFindResult = memberRepository.findById(request.getMemberId());
        if (memberFindResult.isEmpty()) throw new IllegalArgumentException("존재하지 않는 MEmber");
        Optional<TaxiParty> partyFindResult = taxiPartyRepository.findById(groupId);
        if (partyFindResult.isEmpty()) throw new IllegalArgumentException("존재하지 않는 파티");

        Optional<TaxiPartyMembers> partyMemberFind = partyMembersRepository.findTaxiPartyMembersByTaxiParty_IdAndMember_Id(groupId, request.getMemberId());
        if (partyMemberFind.isPresent()) throw new IllegalArgumentException("이미 존재하는 파티에 속한 멤버");

        partyMembersRepository.save(new TaxiPartyMembers(null, memberFindResult.get(), partyFindResult.get()));
    }

    @Override
    public void removeMember(Long groupId, TaxiPartyQuitRequest request) {
        Optional<Member> memberFindResult = memberRepository.findById(request.getMemberId());
        if (memberFindResult.isEmpty()) throw new IllegalArgumentException("존재하지 않는 MEmber");
        Optional<TaxiParty> partyFindResult = taxiPartyRepository.findById(groupId);
        if (partyFindResult.isEmpty()) throw new IllegalArgumentException("존재하지 않는 파티");

        Optional<TaxiPartyMembers> partyMemberFind = partyMembersRepository.findTaxiPartyMembersByTaxiParty_IdAndMember_Id(groupId, request.getMemberId());
        if (partyMemberFind.isEmpty()) throw new IllegalArgumentException("파티에 존재하지 않음");

        partyMembersRepository.delete(partyMemberFind.get());
    }

}
