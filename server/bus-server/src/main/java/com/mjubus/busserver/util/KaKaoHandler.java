package com.mjubus.busserver.util;

import com.mjubus.busserver.domain.PathInfo;
import com.mjubus.busserver.domain.PathNaviDetail;
import com.mjubus.busserver.domain.Station;
import com.mjubus.busserver.repository.prod.PathInfoRepository;
import com.mjubus.busserver.repository.prod.PathNaviDetailRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@Component
public class KaKaoHandler {

    @Autowired
    PathNaviDetailRepository pathNaviDetailRepository;

    @Autowired
    PathInfoRepository pathInfoRepository;


    private static final String KAKAO_SECRETKEY = "KakaoAK e5faece78decd0580c5bb9f24bcb39f6";

    private static final String KAKAO_ENDPOINT = "https://apis-navi.kakaomobility.com/v1/directions?";

    private static final String KAKAO_ORIGIN = "&origin=";

    private static final String KAKAO_DEST = "&destination=";

    private static final String KAKAO_OPTION = "&waypoints=&priority=RECOMMEND&car_fuel=GASOLINE&car_hipass=false&alternatives=false&road_details=false";


    public Long getDuration(Station srcStation, Station destStation) throws IOException, ParseException {

        Long total_duration = 0L;
        PathInfo pathInfo = pathInfoRepository.findPathInfoByStationFrom_IdAndStationAt_Id(srcStation.getId(), destStation.getId());
        List<PathNaviDetail> pathNaviDetails = pathNaviDetailRepository.findPathNaviDetailsByPathInfo_Id(pathInfo.getId());

        for (PathNaviDetail detail : pathNaviDetails) {
            Station src = detail.getStationFrom();
            Station dest = detail.getStationAt();

            String origin_url = KAKAO_ORIGIN + src.getKakaoLongitude() + "," + src.getKakaoLatitude();
            String dest_url = KAKAO_DEST + dest.getKakaoLongitude() + "," + dest.getKakaoLatitude();
            String req_url = KAKAO_ENDPOINT + origin_url + dest_url + KAKAO_OPTION;

            java.net.URL url = new URL(req_url);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.addRequestProperty("Authorization", KAKAO_SECRETKEY);
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            InputStream in = conn.getInputStream();

            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            JSONParser parser = new JSONParser();
            Long duration = 0L;
            while ((line = reader.readLine()) != null) {
                JSONObject parsedJsonArray = (JSONObject) parser.parse(line);
                JSONArray route = (JSONArray) parsedJsonArray.get("routes");
                JSONObject summary = (JSONObject) ((JSONObject) route.get(0)).get("summary");
                duration = (Long) summary.get("duration");
            }
            System.out.println(duration);
            total_duration += duration;
        }
        System.out.println(srcStation.getName() + " ~ " + destStation.getName() + " : " + total_duration);
        return total_duration;
    }
}
