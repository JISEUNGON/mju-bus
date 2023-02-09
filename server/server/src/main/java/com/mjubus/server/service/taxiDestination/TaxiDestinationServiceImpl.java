package com.mjubus.server.service.taxiDestination;

import com.mjubus.server.domain.TaxiDestination;
import com.mjubus.server.exception.taxidestination.TaxiDestinationNotFoundException;
import com.mjubus.server.repository.TaxiDestinationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaxiDestinationServiceImpl implements TaxiDestinationService {

    private TaxiDestinationRepository taxiDestinationRepository;
    @Override
    public TaxiDestination findTaxiDestinationById(Long id) {
        return taxiDestinationRepository.findById(id).orElseThrow(() -> new TaxiDestinationNotFoundException("존재하지 않는 목적지입니다."));
    }
}
