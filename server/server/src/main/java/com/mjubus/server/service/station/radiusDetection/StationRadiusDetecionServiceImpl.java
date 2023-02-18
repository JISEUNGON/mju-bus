package com.mjubus.server.service.station.radiusDetection;

import com.mjubus.server.domain.StationLocation;
import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.dto.request.StationRadiusDetectedNameRequest;
import com.mjubus.server.dto.response.StationRadiusDetectedNameResponse;
import com.mjubus.server.exception.TaxiParty.TaxiPartyNotFoundException;
import com.mjubus.server.repository.StationLocationRepository;
import com.mjubus.server.repository.TaxiPartyRepository;
import com.mjubus.server.util.HaversineUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class StationRadiusDetecionServiceImpl implements StationRadiusDetectionService {

    private final StationLocationRepository stationLocationRepository;
    private final TaxiPartyRepository taxiPartyRepository;

    public StationRadiusDetecionServiceImpl(StationLocationRepository stationLocationRepository, TaxiPartyRepository taxiPartyRepository) {
        this.stationLocationRepository = stationLocationRepository;
        this.taxiPartyRepository = taxiPartyRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public StationRadiusDetectedNameResponse detectAndGetName(StationRadiusDetectedNameRequest request) {
        Optional<TaxiParty> taxiParty = taxiPartyRepository.findById(request.getGroupId());
        TaxiParty taxiPartyFound = taxiParty.orElseThrow(() -> new TaxiPartyNotFoundException(request.getGroupId()));
        Double taxiPartyLatitude = taxiPartyFound.getMeeting_latitude();
        Double taxiPartyLongitude = taxiPartyFound.getMeeting_longitude();
        List<StationLocation> all = stationLocationRepository.findAll();
        Optional<StationLocation> matchs = all.stream().
                filter(stationLocation -> {
                    return HaversineUtil.distance(
                            taxiPartyLatitude,
                            taxiPartyLongitude,
                            stationLocation.getLatitude(),
                            stationLocation.getLongitude()
                    ) < stationLocation.getRange();
                })
                .sorted(Comparator.comparing(StationLocation::getPriority))
                .findFirst();

        if (matchs.isPresent()) return StationRadiusDetectedNameResponse.builder().name(matchs.get().getName()).build();

        Pattern regex = Pattern.compile("(([가-힣]+(d|d(,|.)d|)+(읍|면|동|가|리))(^구|)((d(~|-)d|d)(가|리|)|))([ ](산(d(~|-)d|d))|)|     (([가-힣]|(d(~|-)d)|d)+(로|길))");
        Matcher matcher = regex.matcher(taxiPartyFound.getMeeting_place());
        matcher.find();
        return StationRadiusDetectedNameResponse.builder()
                .name(matcher.group())
                .build();
    }
}
