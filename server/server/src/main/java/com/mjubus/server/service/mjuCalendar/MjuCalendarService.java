package com.mjubus.server.service.mjuCalendar;

import com.mjubus.server.domain.MjuCalendar;
import com.mjubus.server.repository.MjuCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class MjuCalendarService implements MjuCalendarInterface {

    @Autowired
    private MjuCalendarRepository mjuCalendarRepository;

    @Override
    public Optional<MjuCalendar> findByDate(Date date) {
        return mjuCalendarRepository.findMjuCalendarByDate(date);
    }
}
