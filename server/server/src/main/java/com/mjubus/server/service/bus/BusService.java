package com.mjubus.server.service.bus;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.dto.busListDto.BusList;
import com.mjubus.server.dto.response.BusListResponse;
import com.mjubus.server.dto.response.BusResponse;
import com.mjubus.server.dto.response.BusRouteResponse;
import com.mjubus.server.dto.response.BusStatusResponse;
import com.mjubus.server.dto.request.BusTimeTableRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface BusService {

    /**
     * Controller 에서 호출되는 메소드
     */
    BusResponse findBus(BusTimeTableRequest req);
    BusStatusResponse getBusStatus(BusTimeTableRequest req);
    BusRouteResponse getBusRoute(BusTimeTableRequest req);


    Bus findBusByBusId(Long id);
    BusListResponse getBusListByDate(LocalDateTime date);

    List<Bus> findBusListByDate(LocalDateTime date);

}
