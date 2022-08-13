package com.mjubus.server.service.busTimeTable;

import com.mjubus.server.domain.BusTimeTable;
import com.mjubus.server.domain.BusTimeTableDetail;
import com.mjubus.server.domain.BusTimeTableInfo;
import com.mjubus.server.repository.BusTimeTableDetailRepository;
import com.mjubus.server.repository.BusTimeTableRepository;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Service
public class BusTimeTableService implements BusTimeTableInterface {

    @Autowired
    private BusTimeTableDetailRepository busTimeTableDetailRepository;

    @Autowired
    private BusTimeTableRepository busTimeTableRepository;

    @Override
    public List<BusTimeTableDetail> findByInfoId(Long id) {
        return busTimeTableDetailRepository.findBusTimeTableDetailsByBusTimeTableInfo_Id(id).get();
    }

    @Override
    public LocalDateTime getFirstBus(List<BusTimeTableDetail> busTimeTableDetails) {
        BusTimeTableDetail first = busTimeTableDetails.get(0);
        LocalDateTime depart = first.getDepart();

        return DateHandler.getTodayWith(depart.getHour(), depart.getMinute());
    }

    @Override
    public LocalDateTime getLastBus(List<BusTimeTableDetail> busTimeTableDetails) {
        BusTimeTableDetail last = busTimeTableDetails.get(busTimeTableDetails.size() - 1);
        LocalDateTime depart = last.getDepart();

        return DateHandler.getTodayWith(depart.getHour(), depart.getMinute());
    }

    @Override
    public List<BusTimeTable> findListById(Long id) {
        return busTimeTableRepository.findBusTimeTablesByBusCalendar_Id(id);
    }
}
