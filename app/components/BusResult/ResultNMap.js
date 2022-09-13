/* eslint-disable react/prop-types */
import React, { useCallback, useRef } from "react";
import styled from "styled-components";
import NaverMapView, { Marker, Polyline } from "react-native-nmap";
import { useQuery } from "@tanstack/react-query";
import { ActivityIndicator } from "react-native";
import { pathApi } from "../../api";

const Loader = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`;

const Container = styled.View`
  width: 100%;
  height: 100%;
`;

function setCenter(station) {
  return {
    latitude: station.latitude,
    longitude: station.longitude,
    zoom: 16.9,
  };
}

function ResoultNMap({ busRouteData }) {
  console.log("////////////////");
  const { stations } = busRouteData;
  console.log(stations);
  console.log(stations.length);
  console.log(stations[0]);
  console.log(stations[0].id);

  /*
  console.log("////////////////");
  const { stations } = busRouteData;
  console.log(stations);
  console.log(stations.length);
  console.log(stations[0]);
  console.log(stations[0].id);

  // Path 데이터 불러오기
  const { isLoading: busPathLoading, data: busPathData } = useQuery(
    [
      "path",
      parseInt(stations[stations.length - 1].id, 10),
      parseInt(stations[0].id, 10),
    ],
    pathApi.path,
  );

  const mapRef = useRef(null);
  const handleSetMapRef = useCallback(_ref => {
    mapRef.current = {
      ..._ref,
    };
  }, []);

  function renderMarker(data) {
    const Markers = data.map(s => (
      <Marker key={s.id} coordinate={s} width={25} height={32} />
    ));
    return Markers;
  }

  function renderLine(data) {
    const result = data.map((name, index) =>
      index !== data.length - 1 ? (
        <Polyline
          strokeColor="#ff0000"
          key={name.path_order}
          coordinates={[data[index], data[index + 1]]}
        />
      ) : null,
    );

    return result;
  }

  return busPathLoading ? (
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <Container>
      {console.log(busPathData)}
      <NaverMapView
        ref={handleSetMapRef}
        style={{ width: "100%", height: "100%" }}
        showsMyLocationButton={false}
        center={setCenter(stations[0])}
        useTextureView
      >
        {renderLine(busPathData)}
        {renderMarker(stations)}
      </NaverMapView>
    </Container>
  );
  */

  const Tempdata = [
    {
      latitude: 37.233992,
      longitude: 127.188683,
      path_order: 1,
    },
    {
      latitude: 37.23375,
      longitude: 127.188644,
      path_order: 2,
    },
    {
      latitude: 37.233652,
      longitude: 127.18863,
      path_order: 3,
    },
    {
      latitude: 37.233628,
      longitude: 127.188627,
      path_order: 4,
    },
    {
      latitude: 37.233579,
      longitude: 127.188619,
      path_order: 5,
    },
    {
      latitude: 37.232992,
      longitude: 127.18852,
      path_order: 6,
    },
    {
      latitude: 37.232894,
      longitude: 127.188505,
      path_order: 7,
    },
    {
      latitude: 37.232747,
      longitude: 127.188485,
      path_order: 8,
    },
    {
      latitude: 37.232572,
      longitude: 127.188457,
      path_order: 9,
    },
    {
      latitude: 37.232426,
      longitude: 127.188433,
      path_order: 10,
    },
    {
      latitude: 37.232258,
      longitude: 127.188406,
      path_order: 11,
    },
    {
      latitude: 37.232033,
      longitude: 127.188367,
      path_order: 12,
    },
    {
      latitude: 37.23198,
      longitude: 127.188359,
      path_order: 13,
    },
    {
      latitude: 37.23165,
      longitude: 127.188304,
      path_order: 14,
    },
    {
      latitude: 37.231266,
      longitude: 127.188224,
      path_order: 15,
    },
    {
      latitude: 37.231191,
      longitude: 127.188212,
      path_order: 16,
    },
    {
      latitude: 37.231125,
      longitude: 127.1882,
      path_order: 17,
    },
    {
      latitude: 37.231029,
      longitude: 127.188182,
      path_order: 18,
    },
    {
      latitude: 37.230791,
      longitude: 127.188137,
      path_order: 19,
    },
    {
      latitude: 37.230683,
      longitude: 127.188117,
      path_order: 20,
    },
    {
      latitude: 37.230567,
      longitude: 127.188096,
      path_order: 21,
    },
    {
      latitude: 37.230435,
      longitude: 127.188072,
      path_order: 22,
    },
    {
      latitude: 37.229615,
      longitude: 127.187914,
      path_order: 23,
    },
    {
      latitude: 37.229201,
      longitude: 127.187848,
      path_order: 24,
    },
    {
      latitude: 37.229099,
      longitude: 127.187833,
      path_order: 25,
    },
    {
      latitude: 37.229066,
      longitude: 127.187827,
      path_order: 26,
    },
    {
      latitude: 37.22841,
      longitude: 127.187705,
      path_order: 27,
    },
    {
      latitude: 37.228383,
      longitude: 127.187699,
      path_order: 28,
    },
    {
      latitude: 37.228164,
      longitude: 127.187657,
      path_order: 29,
    },
    {
      latitude: 37.227965,
      longitude: 127.18762,
      path_order: 30,
    },
    {
      latitude: 37.227925,
      longitude: 127.187612,
      path_order: 31,
    },
    {
      latitude: 37.227716,
      longitude: 127.187588,
      path_order: 32,
    },
    {
      latitude: 37.227708,
      longitude: 127.187587,
      path_order: 33,
    },
    {
      latitude: 37.227383,
      longitude: 127.187592,
      path_order: 34,
    },
    {
      latitude: 37.227247,
      longitude: 127.187597,
      path_order: 35,
    },
    {
      latitude: 37.227107,
      longitude: 127.187621,
      path_order: 36,
    },
    {
      latitude: 37.22669,
      longitude: 127.187722,
      path_order: 37,
    },
    {
      latitude: 37.226683,
      longitude: 127.187724,
      path_order: 38,
    },
    {
      latitude: 37.22635,
      longitude: 127.187799,
      path_order: 39,
    },
    {
      latitude: 37.226225,
      longitude: 127.187837,
      path_order: 40,
    },
    {
      latitude: 37.225825,
      longitude: 127.187962,
      path_order: 41,
    },
    {
      latitude: 37.225714,
      longitude: 127.187989,
      path_order: 42,
    },
    {
      latitude: 37.2256,
      longitude: 127.187996,
      path_order: 43,
    },
    {
      latitude: 37.225584,
      longitude: 127.187996,
      path_order: 44,
    },
    {
      latitude: 37.2255,
      longitude: 127.187999,
      path_order: 45,
    },
    {
      latitude: 37.22537,
      longitude: 127.187964,
      path_order: 46,
    },
    {
      latitude: 37.225267,
      longitude: 127.187916,
      path_order: 47,
    },
    {
      latitude: 37.225251,
      longitude: 127.187908,
      path_order: 48,
    },
    {
      latitude: 37.225156,
      longitude: 127.187863,
      path_order: 49,
    },
    {
      latitude: 37.225155,
      longitude: 127.187863,
      path_order: 50,
    },
    {
      latitude: 37.22512,
      longitude: 127.187846,
      path_order: 51,
    },
    {
      latitude: 37.225023,
      longitude: 127.187809,
      path_order: 52,
    },
    {
      latitude: 37.225025,
      longitude: 127.18779,
      path_order: 53,
    },
    {
      latitude: 37.225023,
      longitude: 127.187772,
      path_order: 54,
    },
    {
      latitude: 37.225019,
      longitude: 127.187752,
      path_order: 55,
    },
    {
      latitude: 37.225011,
      longitude: 127.187737,
      path_order: 56,
    },
    {
      latitude: 37.225002,
      longitude: 127.187726,
      path_order: 57,
    },
    {
      latitude: 37.224992,
      longitude: 127.187718,
      path_order: 58,
    },
    {
      latitude: 37.224977,
      longitude: 127.187714,
      path_order: 59,
    },
    {
      latitude: 37.224962,
      longitude: 127.187715,
      path_order: 60,
    },
    {
      latitude: 37.224953,
      longitude: 127.187717,
      path_order: 61,
    },
    {
      latitude: 37.224937,
      longitude: 127.187726,
      path_order: 62,
    },
    {
      latitude: 37.22492,
      longitude: 127.187744,
      path_order: 63,
    },
    {
      latitude: 37.224914,
      longitude: 127.187756,
      path_order: 64,
    },
    {
      latitude: 37.22491,
      longitude: 127.187772,
      path_order: 65,
    },
    {
      latitude: 37.224908,
      longitude: 127.18779,
      path_order: 66,
    },
    {
      latitude: 37.224824,
      longitude: 127.187792,
      path_order: 67,
    },
    {
      latitude: 37.224781,
      longitude: 127.187792,
      path_order: 68,
    },
    {
      latitude: 37.224698,
      longitude: 127.187801,
      path_order: 69,
    },
    {
      latitude: 37.224693,
      longitude: 127.187802,
      path_order: 70,
    },
    {
      latitude: 37.224635,
      longitude: 127.187813,
      path_order: 71,
    },
    {
      latitude: 37.22428,
      longitude: 127.187898,
      path_order: 72,
    },
    {
      latitude: 37.224253,
      longitude: 127.188029,
      path_order: 73,
    },
    {
      latitude: 37.224246,
      longitude: 127.188274,
      path_order: 74,
    },
    {
      latitude: 37.224246,
      longitude: 127.188367,
      path_order: 75,
    },
  ];

  console.log("*");

  const mapRef = useRef(null);
  const handleSetMapRef = useCallback(_ref => {
    mapRef.current = {
      ..._ref,
    };
  }, []);

  function renderMarker(data) {
    const Markers = data.map(s => (
      <Marker key={s.id} coordinate={s} width={25} height={32} />
    ));
    return Markers;
  }

  function renderLine(data) {
    const result = data.map((name, index) =>
      index !== data.length - 1 ? (
        <Polyline
          strokeColor="#ff0000"
          key={name.path_order}
          coordinates={[data[index], data[index + 1]]}
        />
      ) : null,
    );

    // Path 데이터 불러오기
    const { isLoading: busPathLoading, data: busPathData } = useQuery(
      [
        "path",
        parseInt(stations[stations.length - 1].id, 10),
        parseInt(stations[0].id, 10),
      ],
      pathApi.path,
    );

    return result;
  }

  return (
    <Container>
      <NaverMapView
        ref={handleSetMapRef}
        style={{ width: "100%", height: "100%" }}
        showsMyLocationButton={false}
        center={setCenter(Tempdata[0])}
        useTextureView
      >
        {renderLine(Tempdata)}
      </NaverMapView>
    </Container>
  );
}

export default ResoultNMap;
