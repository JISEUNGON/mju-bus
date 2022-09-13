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
  const { stations } = busRouteData;

  console.log("-------------");
  console.log(stations);

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
      {console.log("------4-------")}
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
}
export default ResoultNMap;
