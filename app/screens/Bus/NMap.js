import React from "react";
import styled from "styled-components";
import NaverMapView, {
  Align,
  Circle,
  Marker,
  Path,
  Polygon,
  Polyline,
} from "react-native-nmap";

const Container = styled.View`
  width: 100%;
  height: 100%;
`;

function NMap({ method }) {
  const setStation = method;
  const P0 = { latitude: 37.234, longitude: 127.188613 };
  const stationData = [
    { id: 26, name: "명현관", latitude: 37.223013, longitude: 127.182092 },
    { id: 25, name: "함박관", latitude: 37.22158, longitude: 127.188257 },
    { id: 23, name: "명지대 정문", latitude: 37.2241, longitude: 127.188018 },
    { id: 2, name: "상공회의소", latitude: 37.23068, longitude: 127.188246 },
    { id: 3, name: "진입로", latitude: 37.233992, longitude: 127.188829 },
    { id: 20, name: "동부경찰서", latitude: 37.234755, longitude: 127.198177 },
    {
      id: 21,
      name: "중앙공영주차장",
      latitude: 37.23543,
      longitude: 127.206678,
    },
    { id: 10, name: "명지대역", latitude: 37.238513, longitude: 127.189606 },
    { id: 4, name: "진입로", latitude: 37.234, longitude: 127.188613 },
    { id: 5, name: "이마트", latitude: 37.230369, longitude: 127.187997 },
    { id: 24, name: "제1공학관", latitude: 37.222711, longitude: 127.186784 },
  ];

  function renderMarker(data, callback) {
    const Markers = data.map(item => (
      <Marker
        coordinate={item}
        pinColor="blue"
        onClick={() => callback(item)}
        width={30}
        height={40}
      />
    ));
    return Markers;
  }

  return (
    <Container>
      <NaverMapView
        style={{ width: "100%", height: "100%" }}
        showsMyLocationButton
        center={{ ...P0, zoom: 15.8 }}
        onTouch={e => console.warn("onTouch", JSON.stringify(e.nativeEvent))}
        onCameraChange={e => console.warn("onCameraChange", JSON.stringify(e))}
        onMapClick={e => console.warn("onMapClick", JSON.stringify(e))}
        useTextureView
      >
        {renderMarker(stationData, setStation)}
      </NaverMapView>
    </Container>
  );
}
export default NMap;
