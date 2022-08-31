import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { Ionicons } from "@expo/vector-icons";
import { useQuery } from "@tanstack/react-query";
import {
  TouchableOpacity,
  Dimensions,
  ActivityIndicator,
  View,
  Text,
} from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import NaverMapView, {
  Align,
  Circle,
  Marker,
  Path,
  Polygon,
  Polyline,
} from "react-native-nmap";
import { GetRouteTableData, highlights } from "../../utils";
import { busApi, calendarApi } from "../../api";
import StationSelect from "./StationSelect";

const { height: SCREEN_HEIGHT, width: SCREEN_WIDTH } = Dimensions.get("window");

const Loader = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`;

const Conatiner = styled.View`
  height: ${SCREEN_HEIGHT}px;
  width: ${SCREEN_WIDTH}px;
  flex: 1;
`;

const SelectContainer = styled.View`
  width: 100%;
  height: 120px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  padding: 30px 30px;
  background-color: white;
  top: 0;
  position: absolute;
`;

const TextContainer = styled.View`
  flex-direction: row;
`;

const SelectTextFrom = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: black;
`;

const SelectTextSub = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-weight: 500;
  font-size: 15px;
  color: gray;
  margin-top: 10px;
`;

const NaverMap = styled.View`
  width: 100%;
  height: 100%;
  background-color: green;
  flex: 8;
`;

const SubmitContainer = styled.View`
  width: 100%;
  height: 60px;
  align-items: center;
  justify-content: center;
  background-color: #7974e7;
  bottom: 0;
  position: absolute;
  flex: 1;
`;
const SubmitText = styled.Text`
  color: white;
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 15px;
`;

const SubmitButton = styled.TouchableOpacity``;

// eslint-disable-next-line react/prop-types
function ToSchool({ navigation: { navigate } }) {
  const [modalVisible, setModalVisible] = useState(false);
  const [isSelected, setIsSelected] = useState(false);
  const [station, setStation] = useState({ name: "정류장을 선택하세요" });
  const [staredStation, setStaredStation] = useState([]);

  const P0 = { latitude: 37.564362, longitude: 126.977011 };
  const P1 = { latitude: 37.565051, longitude: 126.978567 };
  const P2 = { latitude: 37.565383, longitude: 126.976292 };
  const P4 = { latitude: 37.564834, longitude: 126.977218 };

  const { isLoading: buslistLoading, data: busListData } = useQuery(
    ["busList"],
    busApi.list,
  );

  const { isLoading: calendarLoading, data: calendarData } = useQuery(
    ["calendar"],
    calendarApi.calendar,
  );

  const loadSelectedRoutes = async STORAG_KEY => {
    try {
      const string = await AsyncStorage.getItem(STORAG_KEY);
      if (string != null) {
        setStaredStation(JSON.parse(string));
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    // eslint-disable-next-line no-use-before-define
    loadSelectedRoutes(calendarData.description);
  }, [calendarData, modalVisible]);

  const stationData = GetRouteTableData(busListData[0].busList);

  const loading = buslistLoading || calendarLoading;

  const onStart = () => {
    setModalVisible(true);
  };

  return loading ? (
    // 운행중인 버스 && 현재 일정표 데이터를 얻는 동안 로딩 출력
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <Conatiner>
      <NaverMap>
        <NaverMapView
          style={{ width: "100%", height: "100%" }}
          showsMyLocationButton
          center={{ ...P0, zoom: 16 }}
          onTouch={e => console.warn("onTouch", JSON.stringify(e.nativeEvent))}
          onCameraChange={e =>
            console.warn("onCameraChange", JSON.stringify(e))
          }
          onMapClick={e => console.warn("onMapClick", JSON.stringify(e))}
          useTextureView
        >
          <Marker
            coordinate={P0}
            onClick={() => console.warn("onClick! p0")}
            caption={{ text: "test caption", align: Align.Left }}
          />
          <Marker
            coordinate={P1}
            pinColor="blue"
            onClick={() => console.warn("onClick! p1")}
          />
          <Marker
            coordinate={P2}
            pinColor="red"
            onClick={() => console.warn("onClick! p2")}
          />
          <Marker
            coordinate={P4}
            onClick={() => console.warn("onClick! p4")}
            width={48}
            height={48}
          />
          <Path
            coordinates={[P0, P1]}
            onClick={() => console.warn("onClick! path")}
            width={10}
          />
          <Polyline
            coordinates={[P1, P2]}
            onClick={() => console.warn("onClick! polyline")}
          />
          <Circle
            coordinate={P0}
            color="rgba(255,0,0,0.3)"
            radius={200}
            onClick={() => console.warn("onClick! circle")}
          />
          <Polygon
            coordinates={[P0, P1, P2]}
            color="rgba(0, 0, 0, 0.5)"
            onClick={() => console.warn("onClick! polygon")}
          />
        </NaverMapView>
        <TouchableOpacity
          style={{ position: "absolute", bottom: "10%", right: 8 }}
        >
          <View style={{ backgroundColor: "gray", padding: 4 }}>
            <Text style={{ color: "white" }}>open stack</Text>
          </View>
        </TouchableOpacity>
        <Text
          style={{
            position: "absolute",
            top: "95%",
            width: "100%",
            textAlign: "center",
          }}
        >
          Icon made by Pixel perfect from www.flaticon.com
        </Text>
      </NaverMap>

      <SelectContainer>
        <TouchableOpacity onPress={onStart}>
          <TextContainer>
            {highlights(station.name)}
            <SelectTextFrom> 으로 </SelectTextFrom>
            <Ionicons
              name="chevron-down"
              size={15}
              color="gray"
              style={{ marginTop: 5 }}
            />
          </TextContainer>
        </TouchableOpacity>
        <SelectTextSub>학교에서 가는 가장 빠른 버스를 탐색해요</SelectTextSub>
      </SelectContainer>

      {isSelected ? (
        <SubmitButton
          onPress={() =>
            navigate("SearchStack", {
              screen: "BusList",
              params: {
                toSchool: false,
                station,
              },
            })
          }
        >
          <SubmitContainer>
            <SubmitText>버스 검색</SubmitText>
          </SubmitContainer>
        </SubmitButton>
      ) : null}
      {modalVisible ? (
        <StationSelect
          data={stationData}
          staredStation={staredStation}
          storageKey={calendarData.description}
          modalVisible={modalVisible}
          setSubmitBtn={setIsSelected}
          setModalVisible={setModalVisible}
          setStation={setStation}
        />
      ) : null}
    </Conatiner>
  );
}

export default ToSchool;
