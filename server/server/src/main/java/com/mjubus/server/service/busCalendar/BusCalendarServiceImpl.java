package com.mjubus.server.service.busCalendar;

import com.mjubus.server.domain.BusCalendar;
import com.mjubus.server.dto.request.BusCalendarSetDateRequest;
import com.mjubus.server.dto.response.BusCalendarGetDateResponse;
import com.mjubus.server.dto.response.BusCalendarResponse;
import com.mjubus.server.exception.BusCalenderNotFoundException;
import com.mjubus.server.repository.BusCalendarRepository;
import com.mjubus.server.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BusCalendarServiceImpl implements BusCalendarService {

    @Autowired
    private BusCalendarRepository busCalendarRepository;

    /**
     * LocalDateTime 을 받아서 BusCalendar 를 반환
     * @param date 날짜
     * @return BusCalendar
     */
    @Override
    public BusCalendar findBusCalendarByDate(LocalDateTime date) {
        Optional<BusCalendar> optionalBusCalendar = busCalendarRepository.findBusCalendarByDate(date.toLocalDate(), date.toLocalTime(), DateHandler.getDayOfWeek(date));
        return optionalBusCalendar.orElseThrow(() -> new BusCalenderNotFoundException(date));
    }

    /**
     * 오늘 날짜의 BusCalendar 를 반환
     * @return BusCalendar
     */
    @Override
    public BusCalendarResponse findBusCalendar() {
        return BusCalendarResponse.of(findBusCalendarByDate(DateHandler.getToday()));
    }

    /**
     * 서버의 날짜를 변경
     * @param busCalendarSetDateRequest 날짜
     */
    @Override
    public void setDate(BusCalendarSetDateRequest busCalendarSetDateRequest) {
        DateHandler.setZonedDateTime(busCalendarSetDateRequest.getTime());
    }

    /**
     * 서버의 날짜를 반환
     * @return 서버의 날짜
     */
    @Override
    public BusCalendarGetDateResponse getDate() {
        return BusCalendarGetDateResponse.of(DateHandler.getToday());
    }

    /**
     * 서버의 날짜를 오늘 날짜로 변경
     * @return 서버의 날짜
     */
    @Override
    public BusCalendarGetDateResponse resetDate() {
        DateHandler.reset();
        return BusCalendarGetDateResponse.of(DateHandler.getToday());
    }

}

