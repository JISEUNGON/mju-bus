package com.mjubus.busserver.util;

import com.mjubus.busserver.domain.Station;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@Component
public class NaverHandler {

    public static String NAVER_SECRETKEY;

    public static String NAVER_CLIENTID;

    public static final String NAVER_ENDPOINT = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving";
    public static final String NAVER_SRC = "?start=";
    public static final String NAVER_DEST = "&goal=";
    public static final String NAVER_OPTION = "&option=trafast";

    @Value("${external.naver.client-id}")
    public void setId(String clientId) {
        NAVER_CLIENTID = clientId;
    }

    @Value("${external.naver.key}")
    public void setKey(String key) {
        NAVER_SECRETKEY = key;
    }

    public static Long getDuration(Station src, Station dest) throws IOException, ParseException {
        long duration = -1L;
        String line;
        JSONParser parser = new JSONParser();

        URL url = new URL(NAVER_ENDPOINT + NAVER_SRC + src.getLongitude() + "," + src.getLatitude() + NAVER_DEST + dest.getLongitude() + "," + dest.getLatitude() + NAVER_OPTION);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.addRequestProperty("X-NCP-APIGW-API-KEY-ID", NAVER_CLIENTID);
        conn.addRequestProperty("X-NCP-APIGW-API-KEY", NAVER_SECRETKEY);
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);

        InputStream in = conn.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while ((line = reader.readLine()) != null) {
            JSONObject parsedJsonArray = (JSONObject) parser.parse(line);
            JSONObject route = (JSONObject) parsedJsonArray.get("route");
            JSONArray trafast = (JSONArray) route.get("trafast");
            JSONObject summary = (JSONObject) ((JSONObject) trafast.get(0)).get("summary");
            duration = (Long) summary.get("duration") / 1000;
            System.out.println(src.getName() + " ~ " + dest.getName() + " : " + duration);
        }

        return duration;
    }
}
