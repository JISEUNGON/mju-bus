/* eslint-disable react/prop-types */
import React, { useCallback, useEffect, useRef } from "react";
import styled from "styled-components";
import NaverMapView, { Marker } from "react-native-nmap";
import { RemoveDuplicateStation } from "../../utils";

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

function NMap({ routeData, setStation, station }) {
  const stationData = RemoveDuplicateStation(routeData);
  const mapRef = useRef(null);
  const handleSetMapRef = useCallback(_ref => {
    mapRef.current = {
      ..._ref,
    };
  }, []);

  useEffect(() => {
    const data = stationData.filter(item => item.id === station.id);
    if (data.length !== 0) {
      mapRef.current.animateToCoordinate(data[0]);
    }
  }, [station, stationData]);

  function renderMarker(data, callback) {
    const Markers = data.map(item => (
      <Marker
        key={item.id}
        coordinate={item}
        onClick={() => {
          mapRef.current.animateToCoordinate(item);
          callback(item);
        }}
        width={25}
        height={32}
      />
    ));
    return Markers;
  }

  return (
    <Container>
      <NaverMapView
        ref={handleSetMapRef}
        style={{ width: "100%", height: "100%" }}
        showsMyLocationButton={false}
        center={setCenter(stationData[4])}
      >
        {renderMarker(stationData, setStation)}
      </NaverMapView>
    </Container>
  );
}
export default NMap;
