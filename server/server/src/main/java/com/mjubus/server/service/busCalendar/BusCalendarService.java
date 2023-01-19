package com.mjubus.server.service.busCalendar;

import com.mjubus.server.domain.BusCalendar;
import com.mjubus.server.dto.request.BusCalendarSetDateRequest;
import com.mjubus.server.dto.response.BusCalendarGetDateResponse;
import com.mjubus.server.dto.response.BusCalendarResponse;

import java.time.LocalDateTime;

public interface BusCalendarService {

    // Controller 에서 사용되는 메소드
    BusCalendarResponse findBusCalendar();
    void setDate(BusCalendarSetDateRequest busCalendarSetDateRequest);
    BusCalendarGetDateResponse getDate();
    BusCalendarGetDateResponse resetDate();


    // Service 에서 사용되는 메소드
    BusCalendar findByDate(LocalDateTime date);


}
