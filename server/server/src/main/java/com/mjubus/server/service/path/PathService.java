package com.mjubus.server.service.path;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.PathDetail;
import com.mjubus.server.domain.PathInfo;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.station.StationDTO;
import com.mjubus.server.dto.stationPath.PathDto;

import java.util.List;

public interface PathService {
    /**
     * Controller에서 호출되는 메소드
     */

    PathInfo findPathInfoByStation(StationDTO src, StationDTO dest);
    List<PathDetail> findPathDetailByPathInfo(PathInfo pathInfo);
    List<PathDto> findPathListBetween(StationDTO src, StationDTO dest);
    List<PathDto> findPathList(Bus bus, Station station, boolean toSchool);

}
