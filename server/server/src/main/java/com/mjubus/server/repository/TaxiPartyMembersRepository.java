package com.mjubus.server.repository;

import com.mjubus.server.domain.TaxiPartyMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaxiPartyMembersRepository extends JpaRepository<TaxiPartyMembers, Long> {

    List<TaxiPartyMembers> findTaxiPartyMembersByTaxiParty_Id(Long taxiParty_id);

    Long countTaxiPartyMembersByTaxiParty_Id(Long taxiParty_id);

    Optional<TaxiPartyMembers> findTaxiPartyMembersByTaxiParty_IdAndMember_Id(Long taxiPartyId, Long MemberId);

    List<TaxiPartyMembers> findTaxiPartyMembersByMember_Id(Long memberId);

    void deleteByTaxiParty_IdAndMember_Id(Long taxiPartyId, Long memberId);
}
