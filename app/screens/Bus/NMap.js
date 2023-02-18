/* eslint-disable react/prop-types */
import React, { useCallback, useEffect, useRef, useState } from "react";
import styled from "styled-components";
import NaverMapView, { Marker } from "react-native-nmap";

const Container = styled.View`
  width: 100%;
  height: 100%;
`;

function NMap({ 
    routeData,  // 정류장 데이터
    setStation,  // 정류장 선택 함수
    selectedStation, // 현재 선택된 정류장
  }) {

  const stations = routeData; // 정류장 데이터
  const [selectedMarker, selectMarker] = useState(-1); // 현재 선택된 마커
  const mapRef = useRef(null); // 지도 객체

  const handleSetMapRef = useCallback(_ref => {
    mapRef.current = {
      ..._ref,
    };
    _ref.setLayerGroupEnabled("transit", true); // Transit Layer
  }, []);

  // 현재 선택된 정류장 혹은 마커가 변경되면 지도를 해당 정류장으로 이동
  useEffect(() => {
    const targetStation = stations.filter(station => station.id === selectedStation.id);
    if (targetStation.length !== 0) {
      mapRef.current.animateToCoordinate(targetStation[0], 1000);
    }
  }, [selectedStation, selectedMarker]);

  // 정류장 마커 렌더링
  function renderMarker() {
    const Markers = stations.map(station => (
      <Marker
        key={station.id}
        coordinate={station}
        caption={{ text: station.name }}
        pinColor={station.id === selectedStation.id ? "blue" : 0}
        onClick={() => { // 마커 클릭 시 관련 내용 업데이트
          selectMarker(station.id);
          setStation(station);
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
        nightMode
        ref={handleSetMapRef}
        style={{ width: "100%", height: "100%" }}
        showsMyLocationButton={false}
        center={{ // 지도 초기 위치
          latitude: stations[0].latitude,
          longitude: stations[0].longitude,
          zoom: 16.9,
        }} 
        useTextureView
      >
        {renderMarker()}
      </NaverMapView>
    </Container>
  );
}
export default NMap;
