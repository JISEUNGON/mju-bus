package com.mjubus.server.service.path;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.domain.PathDetail;
import com.mjubus.server.domain.PathInfo;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.StationDTO;
import com.mjubus.server.dto.stationPath.PathDto;
import com.mjubus.server.exception.Path.PathDetailNotFoundException;
import com.mjubus.server.exception.Path.PathInfoNotFoundException;
import com.mjubus.server.exception.Station.StationNotFoundException;
import com.mjubus.server.repository.PathDetailRepository;
import com.mjubus.server.repository.PathInfoRepository;
import com.mjubus.server.service.route.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PathService {

    @Autowired
    private PathDetailRepository pathDetailRepository;

    @Autowired
    private PathInfoRepository pathInfoRepository;

    @Autowired
    private RouteService routeService;

    private static int offset = 1;

    public PathInfo findPathInfoByStation(StationDTO src, StationDTO dest) {
        Optional<PathInfo> optionalPathInfo = pathInfoRepository.findPathInfoByStationFrom_IdAndStationAt_Id(src.getId(), dest.getId());
        return optionalPathInfo.orElseThrow(() -> new PathInfoNotFoundException(src, dest));
    }

    public List<PathDetail> findPathDetailByPathInfo(PathInfo pathInfo) {
        Optional<List<PathDetail>> optionalPathDetails = pathDetailRepository.findPathDetailsByPathInfo_IdOrderByRouteOrder(pathInfo.getId());
        return optionalPathDetails.orElseThrow(() -> new PathDetailNotFoundException(pathInfo));
    }

    public List<PathDto> findPathListBetween(StationDTO src, StationDTO dest) {
        PathInfo pathInfo = findPathInfoByStation(src, dest);
        List<PathDetail> pathDetailList = findPathDetailByPathInfo(pathInfo);

        List<PathDto> result = new LinkedList<>();
        for (int i = 0; i < pathDetailList.size(); i++) {
            PathDetail pathDetail = pathDetailList.get(i);
            result.add(
                    PathDto.builder()
                            .latitude(pathDetail.getLatitude())
                            .longitude(pathDetail.getLongitude())
                            .path_order(offset + i)
                            .build()
            );
        }
        offset += pathDetailList.size();
        return result;
    }

    public List<PathDto> findPathList(Bus bus, Station station, boolean toSchool) {
        List<StationDTO> pathStations = routeService.findStationsByBus(bus);

        int i = -1;
        offset = 1;
        for (int j = 0; j < pathStations.size(); j++) {
            StationDTO stationDTO = pathStations.get(j);
            if (Objects.equals(stationDTO.getId(), station.getId())) {
                i = j;
                break;
            }
        }

        if (i == -1) throw new StationNotFoundException(station.getId());

        List<PathDto> pathDtoList = new LinkedList<>();
        if (toSchool) {
            for(int j = i; j < pathStations.size() - 1; j++) {
                StationDTO src = pathStations.get(j);
                StationDTO dest = pathStations.get(j + 1);
                pathDtoList.addAll(findPathListBetween(src, dest));
            }
        }
        else {
            for(int j = 0; j <= i - 1; j++) {
                StationDTO src = pathStations.get(j);
                StationDTO dest = pathStations.get(j + 1);
                pathDtoList.addAll(findPathListBetween(src, dest));
        }
    }
    return pathDtoList;
    }
}
