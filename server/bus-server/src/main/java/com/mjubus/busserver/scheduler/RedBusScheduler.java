package com.mjubus.busserver.scheduler;

import com.mjubus.busserver.domain.Bus;
import com.mjubus.busserver.domain.BusArrival;
import com.mjubus.busserver.domain.Station;
import com.mjubus.busserver.repository.BusArrivalRepository;
import com.mjubus.busserver.repository.BusRepository;
import com.mjubus.busserver.repository.StationRepository;
import com.mjubus.busserver.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;

@Component
public class RedBusScheduler {

    /**
     * 데이터들을 저장한 클래스
     * */
    private static RData rData = new RData();
    private static MyData myData;

    private static HashMap<String, Long> busId = new HashMap<String, Long>(){{
        put("228000176", 200L);//5001
        put("228000177", 201L);//5001-1
        put("228000182", 202L);//5003B
        put("228000174", 210L);//5000B
        put("228000394", 211L);//5002A
        put("228000183", 212L);//5002B
        put("228000175", 213L);//5005
        put("228000184", 214L);//5600
        put("228000395", 215L);//5700A
        put("228000271", 216L);//5700B
    }};

    @Autowired
    private BusArrivalRepository busArrivalRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private StationRepository stationRepository;

    @Scheduled(cron = "10 * * * * *")
    public void printDate() {
        setRData();
        myData = rData.getMyData();

        // 테스트 데이터
        for(int i = 0; i < myData.getRouteId().size(); i++) {
            if(busId.containsKey(myData.getRouteId(i))) {
                Long id = busId.get(myData.getRouteId(i));

                Bus bus = busRepository.getReferenceById(id);

                Station station = stationRepository.getReferenceById(4L);

                BusArrival test =  BusArrival.builder()
                        .bus(bus)
                        .station(station)
                        .expected(DateHandler.getToday())
                        .build();

                busArrivalRepository.save(test);

                System.out.println("Saved !");
            }
        }
    }

    public void setRData() {
        rData.setStationId("228002024");
        /**
         * 버스 도착 목록 정보 URL
         * */
        try {
            rData.makeUrl();//url 생성
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try {
            rData.getXml();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        rData.setPredictTime1();
        rData.setPredictTime2();
        rData.setRouteId();
        rData.setStaOrder();
    }
}
