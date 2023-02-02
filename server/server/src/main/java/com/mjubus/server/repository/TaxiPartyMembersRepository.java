package com.mjubus.server.repository;

import com.mjubus.server.domain.TaxiPartyMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxiPartyMembersRepository extends JpaRepository<TaxiPartyMembers, Long> {
}
