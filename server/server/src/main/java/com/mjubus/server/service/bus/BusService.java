package com.mjubus.server.service.bus;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.BusTimeTable;
import com.mjubus.server.domain.MjuCalendar;
import com.mjubus.server.repository.BusRepository;
import com.mjubus.server.repository.BusTimeTableRepository;
import com.mjubus.server.repository.MjuCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class BusService implements BusServiceInterface {
    @Autowired
    private BusRepository busRepository;

    @Autowired
    private MjuCalendarRepository mjuCalendarRepository;

    @Autowired
    private BusTimeTableRepository busTimeTableRepository;

    @Override
    public Optional<Bus> getBus(int type) {
        return busRepository.findByType(type);
    }

    @Override
    public boolean isActivate(int type) {
        Optional<MjuCalendar> mjuCalendar = mjuCalendarRepository.findMjuCalendarByDate(new Date(Calendar.getInstance().getTime().getTime()));
        Optional<Bus> bus = busRepository.findByType(type);
        if (mjuCalendar.isPresent() && bus.isPresent()) {
            Optional<List<BusTimeTable>> busTimeTables = busTimeTableRepository.findBusTimeTablesByBusAndMjuCalendar(bus.get(), mjuCalendar.get());
            return busTimeTables.isPresent();
        }
        return false;
    }

    @Override
    public boolean isActivate(String sid) {
        Optional<Bus> bus = busRepository.findById(sid);
        Optional<MjuCalendar> mjuCalendar = mjuCalendarRepository.findMjuCalendarByDate(new Date(Calendar.getInstance().getTime().getTime()));
        if (mjuCalendar.isPresent() && bus.isPresent()) {
            Optional<List<BusTimeTable>> busTimeTables = busTimeTableRepository.findBusTimeTablesByBusAndMjuCalendar(bus.get(), mjuCalendar.get());
            return busTimeTables.isPresent();
        }
        return false;
    }
}
