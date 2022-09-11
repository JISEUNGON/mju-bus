package com.mjubus.server.service.path;

import com.mjubus.server.domain.PathDetail;
import com.mjubus.server.domain.PathInfo;
import com.mjubus.server.domain.Station;
import com.mjubus.server.dto.stationPath.PathDto;
import com.mjubus.server.exception.Path.PathDetailNotFoundException;
import com.mjubus.server.exception.Path.PathInfoNotFoundException;
import com.mjubus.server.repository.PathDetailRepository;
import com.mjubus.server.repository.PathInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PathService {

    @Autowired
    private PathDetailRepository pathDetailRepository;

    @Autowired
    private PathInfoRepository pathInfoRepository;

    public PathInfo findPathInfoByStation(Station src, Station dest) {
        Optional<PathInfo> optionalPathInfo = pathInfoRepository.findPathInfoByStationFrom_IdAndStationAt_Id(src.getId(), dest.getId());
        return optionalPathInfo.orElseThrow(() -> new PathInfoNotFoundException(src, dest));
    }

    public List<PathDetail> findPathDetailByPathInfo(PathInfo pathInfo) {
        Optional<List<PathDetail>> optionalPathDetails = pathDetailRepository.findPathDetailsByPathInfo_IdOrderByRouteOrder(pathInfo.getId());
        return optionalPathDetails.orElseThrow(() -> new PathDetailNotFoundException(pathInfo));
    }

    public List<PathDto> findPathListBetween(Station src, Station dest) {
        PathInfo pathInfo = findPathInfoByStation(src, dest);
        List<PathDetail> pathDetailList = findPathDetailByPathInfo(pathInfo);

        List<PathDto> result = new LinkedList<>();
        for (int i = 0; i < pathDetailList.size(); i++) {
            PathDetail pathDetail = pathDetailList.get(i);
            result.add(
                    PathDto.builder()
                            .latitude(pathDetail.getLatitude())
                            .longitude(pathDetail.getLongitude())
                            .path_order(i + 1)
                            .build()
            );
        }
        return result;
    }
}
