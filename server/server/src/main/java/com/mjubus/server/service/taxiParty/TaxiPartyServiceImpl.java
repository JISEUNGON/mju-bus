package com.mjubus.server.service.taxiParty;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.TaxiDestination;
import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.domain.TaxiPartyMembers;
import com.mjubus.server.dto.request.*;
import com.mjubus.server.dto.response.TaxiPartyCreateResponse;
import com.mjubus.server.dto.response.TaxiPartyDetailResponse;
import com.mjubus.server.dto.response.TaxiPartyListResponse;
import com.mjubus.server.dto.response.TaxiPartyResponse;
import com.mjubus.server.enums.TaxiPartyEnum;
import com.mjubus.server.exception.TaxiParty.IllegalPartyMembersStateException;
import com.mjubus.server.exception.TaxiParty.IllegalPartyStateException;
import com.mjubus.server.exception.TaxiParty.TaxiPartyNotFoundException;
import com.mjubus.server.repository.TaxiPartyRepository;
import com.mjubus.server.service.member.MemberService;
import com.mjubus.server.service.taxiDestination.TaxiDestinationService;
import com.mjubus.server.service.taxiPartyMembers.TaxiPartyMembersService;
import com.mjubus.server.util.DateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TaxiPartyServiceImpl implements TaxiPartyService{

    private final TaxiPartyRepository taxiPartyRepository;
    private final TaxiDestinationService taxiDestinationService;
    private final MemberService memberService;
    private final TaxiPartyMembersService taxiPartyMembersService;

    @Autowired
    public TaxiPartyServiceImpl(TaxiPartyRepository taxiPartyRepository, TaxiDestinationService taxiDestinationService, @Lazy MemberService memberService, TaxiPartyMembersService taxiPartyMembersService) {
        this.taxiPartyRepository = taxiPartyRepository;
        this.taxiDestinationService = taxiDestinationService;
        this.memberService = memberService;
        this.taxiPartyMembersService = taxiPartyMembersService;
    }
    @Override
    public TaxiPartyDetailResponse findTaxiParty(TaxiPartyRequest req) {
        TaxiParty result = taxiPartyRepository.findById(req.getId()).orElseThrow(() -> new TaxiPartyNotFoundException(req.getId()));
        return TaxiPartyDetailResponse.of(result);
    }

    @Override
    public TaxiPartyListResponse findTaxiPartyList() {
        List<TaxiParty> taxiPartyList = taxiPartyRepository.findByStatusAndEndAtAfter(TaxiPartyEnum.ON_GOING, DateHandler.getToday()).orElse(new ArrayList<>());
        List<TaxiPartyResponse> taxiPartyResponses = new LinkedList<>();

        // taxiPartyList를 순회하면서, 각 taxiParty의 taxiPartyMembers를 가져온다.
        taxiPartyList.forEach(taxiParty -> {
            taxiPartyResponses.add(TaxiPartyResponse.of(taxiParty, taxiPartyMembersService.findMembersByTaxiParty(taxiParty)));
        });

        return TaxiPartyListResponse.of(taxiPartyResponses);
    }


    @Transactional
    @Override
    public TaxiPartyCreateResponse createTaxiParty(Member administer, TaxiPartyCreateRequest request) {
        // 진행중인 택시파티가 있는 경우
        if (hasActiveParty(administer)) {
            throw new IllegalPartyStateException("이미 진행중인 택시파티가 있습니다.");
        }

        // 목적지 검증
        TaxiDestination destination = taxiDestinationService.findTaxiDestinationById(request.getTaxiDestinationId());

        // 택시파티 생성
        TaxiParty taxiParty = TaxiParty.of(request, administer, destination);
        taxiPartyRepository.save(taxiParty);

        // 택시파티 생성 후 생성자를 파티에 참여
        addNewMember(taxiParty.getId(), administer);
        return TaxiPartyCreateResponse.builder().isCreated("success").build();
    }

    @Transactional
    @Override
    public void addNewMember(Long groupId, Member member) {
        TaxiParty taxiParty = findTaxiPartyById(groupId);

        if (taxiPartyMembersService.isGroupMember(taxiParty.getId(), member)) { // 이미 파티에 참여중인 경우
            throw new IllegalPartyMembersStateException("이미 참여중인 택시파티입니다.");
        } else if (hasActiveParty(member)) { // 진행중인 택시파티가 있는 경우
            throw new IllegalPartyStateException("이미 진행중인 택시파티가 있습니다.");
        }

        List<TaxiPartyMembers> groupPartyMembers = taxiPartyMembersService.findGroupPartyMembers(taxiParty);
        if (groupPartyMembers.size() >= taxiParty.getMax()) { // 파티 인원이 꽉 찬 경우
            throw new IllegalPartyStateException("택시파티 인원이 초과되었습니다.");
        }

        taxiPartyMembersService.addMember(taxiParty, member);
    }

    @Transactional
    @Override
    public void removeMember(Long groupId, Member member) {
        TaxiParty taxiParty = findTaxiPartyById(groupId);

        if (!taxiPartyMembersService.isGroupMember(taxiParty.getId(), member)) { // 파티에 참여중이지 않은 경우
            throw new IllegalPartyMembersStateException("참여중이지 않은 택시파티입니다.");
        }

        taxiPartyMembersService.removeMember(taxiParty, member);
    }

    @Transactional
    @Override
    public void deleteParty(TaxiPartyDeleteRequest request) {
        TaxiParty taxiParty = findTaxiPartyById(request.getGroupId());

        // 택시에 참여한 모든 멤버를 퇴장시킴
        List<TaxiPartyMembers> partyMembers = taxiPartyMembersService.findGroupPartyMembers(taxiParty);
        for (TaxiPartyMembers partyMember : partyMembers) {
            taxiPartyMembersService.removeMember(taxiParty, partyMember.getMember());
        }

        taxiPartyRepository.delete(taxiParty);
    }

    @Override
    public boolean hasActiveParty(Member member) {
        Optional<TaxiParty> taxiParty = taxiPartyMembersService.findOptionalPartyByMember(member);
        return taxiParty.isPresent();
    }

    @Override
    public TaxiParty findTaxiPartyById(Long id) {
        return taxiPartyRepository.findById(id).orElseThrow(() -> new TaxiPartyNotFoundException(id));
    }

    @Override
    public Optional<TaxiParty> findOptionalPartyById(long parseLong) {
        return taxiPartyRepository.findById(parseLong);
    }

}
