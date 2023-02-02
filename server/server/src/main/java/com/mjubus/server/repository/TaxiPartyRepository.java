package com.mjubus.server.repository;

import com.mjubus.server.domain.TaxiParty;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface TaxiPartyRepository extends JpaRepository<TaxiParty, Long> {

    List<TaxiParty> findAll();

}
