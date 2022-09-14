/* eslint-disable react/prop-types */
import React, { useCallback, useRef } from "react";
import styled from "styled-components";
import NaverMapView, { Polyline } from "react-native-nmap";

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

function ResoultNMap({ startPoint, busPathData }) {
  const mapRef = useRef(null);
  const handleSetMapRef = useCallback(_ref => {
    mapRef.current = {
      ..._ref,
    };
  }, []);

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
        center={setCenter(startPoint)}
        useTextureView
      >
        {renderLine(busPathData)}
      </NaverMapView>
    </Container>
  );
}

export default ResoultNMap;
