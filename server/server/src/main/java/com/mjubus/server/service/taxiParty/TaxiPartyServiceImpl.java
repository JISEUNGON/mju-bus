package com.mjubus.server.service.taxiParty;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.TaxiDestination;
import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.domain.TaxiPartyMembers;
import com.mjubus.server.dto.request.*;
import com.mjubus.server.dto.response.TaxiPartyCreateResponse;
import com.mjubus.server.dto.response.TaxiPartyListResponse;
import com.mjubus.server.dto.response.TaxiPartyResponse;
import com.mjubus.server.exception.TaxiParty.IllegalPartyMembersStateException;
import com.mjubus.server.exception.TaxiParty.IllegalPartyStateException;
import com.mjubus.server.exception.TaxiParty.TaxiPartyNotFoundException;
import com.mjubus.server.exception.member.MemberNotFoundException;
import com.mjubus.server.exception.taxidestination.TaxiDestinationNotFoundException;
import com.mjubus.server.repository.MemberRepository;
import com.mjubus.server.repository.TaxiDestinationRepository;
import com.mjubus.server.repository.TaxiPartyMembersRepository;
import com.mjubus.server.repository.TaxiPartyRepository;
import com.mjubus.server.util.DateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public TaxiPartyCreateResponse createTaxiParty(TaxiPartyCreateRequest request) {
        Optional<Member> administerFind = memberRepository.findById(request.getAdminister());
        Member administer = administerFind.orElseThrow(() -> new MemberNotFoundException("존재하지 않는 Member"));

        Optional<TaxiParty> partyFind = Optional.ofNullable(taxiPartyRepository.findByAdminister(administer));
        partyFind.ifPresent((party) -> {
            throw new IllegalPartyStateException("이미 존재하는 파티");
        });

        Optional<TaxiDestination> destinationFind = taxiDestinationRepository.findById(request.getTaxiDestinationId());
        TaxiDestination destination = destinationFind.orElseThrow(() -> new TaxiDestinationNotFoundException("존재하지 않는 택시 목적지"));

        taxiPartyRepository.save(new TaxiParty(
                null,
                administer,
                destination,
                request.getMeetingLatitude(),
                request.getMeetingLongitude(),
                request.getMemo(),
                request.getMin(),
                request.getMax(),
                request.getEndAt(),
                DateHandler.getToday(),
                1L
        ));
        return TaxiPartyCreateResponse.builder().isCreated("success").build();
    }

    @Transactional
    @Override
    public void addNewMember(Long groupId, TaxiPartyJoinRequest request) {
        Optional<Member> memberFind = memberRepository.findById(request.getMemberId());
        Member newMember = memberFind.orElseThrow(() -> new MemberNotFoundException("존재하지 않는 멤버"));

        Optional<TaxiParty> partyFind = taxiPartyRepository.findById(groupId);
        TaxiParty targetParty = partyFind.orElseThrow(() -> new TaxiPartyNotFoundException(groupId));

        Optional<TaxiPartyMembers> partyMemberFind = partyMembersRepository.findTaxiPartyMembersByTaxiParty_IdAndMember_Id(groupId, request.getMemberId());
        partyMemberFind.ifPresent((partyMember) -> {
            throw new IllegalPartyMembersStateException("이미 파티에 속한 멤버");
        });

        partyMembersRepository.save(new TaxiPartyMembers(null, newMember, targetParty));
    }

    @Transactional
    @Override
    public void removeMember(Long groupId, TaxiPartyQuitRequest request) {
        Optional<Member> memberFind = memberRepository.findById(request.getMemberId());
        memberFind.orElseThrow(() -> new MemberNotFoundException("존재하지 않는 멤버"));

        Optional<TaxiParty> partyFind = taxiPartyRepository.findById(groupId);
        partyFind.orElseThrow(() -> new TaxiPartyNotFoundException(groupId));

        Optional<TaxiPartyMembers> partyMemberFind = partyMembersRepository.findTaxiPartyMembersByTaxiParty_IdAndMember_Id(groupId, request.getMemberId());
        TaxiPartyMembers taxiPartyMembers = partyMemberFind.orElseThrow(() -> new IllegalPartyMembersStateException("이미 파티에 존재하지 않음"));

        partyMembersRepository.delete(taxiPartyMembers);
    }

    @Transactional
    @Override
    public void deleteParty(TaxiPartyDeleteRequest request) {
        Optional<TaxiParty> partyFind = taxiPartyRepository.findById(request.getGroupId());
        TaxiParty taxiParty = partyFind.orElseThrow(() -> new TaxiPartyNotFoundException(request.getGroupId()));

        List<TaxiPartyMembers> partyMembers = partyMembersRepository.findTaxiPartyMembersByTaxiParty_Id(request.getGroupId());
        partyMembers.forEach(partyMembersRepository::delete);

        taxiPartyRepository.delete(taxiParty);
    }

}
