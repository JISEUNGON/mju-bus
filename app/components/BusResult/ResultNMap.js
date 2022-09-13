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

function ResoultNMap({ busPathData, busRouteData }) {
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

  return (
    <Container>
      <NaverMapView
        ref={handleSetMapRef}
        style={{ width: "100%", height: "100%" }}
        showsMyLocationButton={false}
        center={setCenter(busPathData[0])}
        useTextureView
      >
        {renderLine(busPathData)}
        {renderMarker(busRouteData)}
      </NaverMapView>
    </Container>
  );
}

export default ResoultNMap;
