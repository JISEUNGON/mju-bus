package com.mjubus.server.service.mjuCalendar;

import com.mjubus.server.domain.BusCalendar;
import com.mjubus.server.repository.BusCalendarRepository;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class BusCalendarService implements BusCalendarInterface {

    @Autowired
    private BusCalendarRepository busCalendarRepository;

    @Override
    public Optional<BusCalendar> findByDate(ZonedDateTime date) {
        if (DateHandler.isWeekend(date)) {
            return busCalendarRepository.findBusCalendarByDateOnWeekend(date);
        }
        return busCalendarRepository.findBusCalendarByDateOnWeekday(date);
    }
}

