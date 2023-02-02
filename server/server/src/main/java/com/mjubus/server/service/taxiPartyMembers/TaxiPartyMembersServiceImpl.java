package com.mjubus.server.service.taxiPartyMembers;

import com.mjubus.server.repository.TaxiPartyMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxiPartyMembersServiceImpl implements TaxiPartyMembersService{

    private final TaxiPartyMembersRepository taxiPartyMembersRepository;

    @Autowired
    public TaxiPartyMembersServiceImpl(TaxiPartyMembersRepository taxiPartyMembersRepository){ this.taxiPartyMembersRepository = taxiPartyMembersRepository; }
}
