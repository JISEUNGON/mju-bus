package com.mjubus.server.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HaversineUtilTest {

    @Test
    public void 거리측정_테스트() {
        //지금 디비에 있는 택시 파티 25번에 대한 위도경도
        //네이버 지도에 복붙하면, 기흥역 2번출구가 나옴
        Double lat1 = 37.276344;
        Double lon1 = 127.116355;
        //지금 디비에 있는 택시 파티 6번에 대한 위도경도
        //네이버 지도에 복붙하면, 학교 입구가 나옴
        Double lat2 = 37.224704;
        Double lon2 = 127.1878498;

        //두 거리를 네이버 지도상에서 직선거리 계산하면 8.5~8.6km 쯤 나옴

        assertTrue(HaversineUtil.distance(lat1, lon1, lat2, lon2) > 8000 && HaversineUtil.distance(lat1, lon1, lat2, lon2) < 9000);
    }

}