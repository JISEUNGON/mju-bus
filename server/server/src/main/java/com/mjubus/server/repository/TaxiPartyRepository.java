package com.mjubus.server.repository;

import com.mjubus.server.domain.TaxiParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaxiPartyRepository extends JpaRepository<TaxiParty, Integer> {

    Optional<TaxiParty> findTaxiById(@Param(value = "id") Integer id);

    List<TaxiParty> findAll();

}
