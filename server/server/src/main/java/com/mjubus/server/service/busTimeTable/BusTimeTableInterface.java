package com.mjubus.server.service.busTimeTable;

import com.mjubus.server.domain.BusTimeTableDetail;
import com.mjubus.server.domain.BusTimeTableInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface BusTimeTableInterface {
    List<BusTimeTableDetail> findByInfoId(Long id);
    LocalDateTime getFirstBus(List<BusTimeTableDetail> busTimeTableDetails);
    LocalDateTime getLastBus(List<BusTimeTableDetail> busTimeTableDetails);
}
