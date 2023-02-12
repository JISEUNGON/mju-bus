package com.mjubus.server.repository;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.enums.TaxiPartyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaxiPartyRepository extends JpaRepository<TaxiParty, Long> {

    List<TaxiParty> findAll();
    TaxiParty findByAdminister(Member administer);

    @Query(value = "SELECT administer FROM taxi_party WHERE taxi_party.id=:id", nativeQuery = true)
    Optional<Long> findAdministerById(@Param("id") Long id);

    Optional<List<TaxiParty>> findTaxiPartiesByStatus(TaxiPartyEnum status);

}
