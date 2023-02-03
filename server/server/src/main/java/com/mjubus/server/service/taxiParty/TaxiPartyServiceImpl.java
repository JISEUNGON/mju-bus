package com.mjubus.server.service.taxiParty;

import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.dto.request.TaxiPartyRequest;
import com.mjubus.server.dto.response.TaxiPartyListResponse;
import com.mjubus.server.dto.response.TaxiPartyResponse;
import com.mjubus.server.exception.TaxiParty.TaxiPartyNotFoundException;
import com.mjubus.server.repository.TaxiPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class TaxiPartyServiceImpl implements TaxiPartyService{

    private final TaxiPartyRepository taxiPartyRepository;

    @Autowired
    public TaxiPartyServiceImpl(TaxiPartyRepository taxiPartyRepository){ this.taxiPartyRepository = taxiPartyRepository; }

    @Override
    public TaxiPartyResponse findTaxiParty(TaxiPartyRequest req) {
        TaxiParty result = taxiPartyRepository.findTaxiById(req.getId()).orElseThrow(() -> new TaxiPartyNotFoundException(req.getId()));
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

    @Override
    public long save(TaxiPartyRequest req) {
        return 0;
    }

}
