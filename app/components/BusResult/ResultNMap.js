/* eslint-disable react/prop-types */
import React, { useCallback, useRef } from "react";
import styled from "styled-components";
import NaverMapView, { Marker, Polyline } from "react-native-nmap";

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
  const mapRef = useRef(null);
  const handleSetMapRef = useCallback(_ref => {
    mapRef.current = {
      ..._ref,
    };
  }, []);

  function renderMarker(rm) {
    console.log("------data-----");
    console.log(rm);
    const Markers = rm.map(s => (
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

  /*    
      center={setCenter(busRouteData.stations[0])}
              {renderLine(busPathData)}
        {renderMarker(busRouteData)}
      */
  return (
    <Container>
      <NaverMapView
        ref={handleSetMapRef}
        style={{ width: "100%", height: "100%" }}
        showsMyLocationButton={false}
        center={setCenter(busRouteData.stations[1])}
        useTextureView
      />
      {renderMarker(busRouteData.stations)}
    </Container>
  );
}

export default ResoultNMap;
