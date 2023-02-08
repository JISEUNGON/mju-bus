package com.mjubus.server.repository;

import com.mjubus.server.domain.TaxiParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaxiPartyRepository extends JpaRepository<TaxiParty, Long> {

    List<TaxiParty> findAll();

}
