package com.mjubus.server.repository;

import com.mjubus.server.domain.TaxiPartyMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;


public interface TaxiPartyMembersRepository extends JpaRepository<TaxiPartyMembers, Long> {

    List<TaxiPartyMembers> findTaxiPartyMembersByTaxiParty_Id(Long taxiParty_id);

    //@Query(value = "SELECT COUNT(*) FROM TaxiPartyMembers t WHERE t.taxiParty_id :=taxi_party_id", nativeQuery = true)
    Long countTaxiPartyMembersByTaxiParty_Id(Long taxiParty_id);

    Optional<TaxiPartyMembers> findTaxiPartyMembersByTaxiParty_IdAndMember_Id(Long taxiPartyId, Long MemberId);
}
