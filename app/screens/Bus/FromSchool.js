import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { Ionicons } from "@expo/vector-icons";
import { useQuery } from "@tanstack/react-query";
import { TouchableOpacity, Dimensions, ActivityIndicator } from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { GetRouteTableData, highlights } from "../../utils";
import { busApi, calendarApi } from "../../api";
import StationSelect from "./StationSelect";
import NMap from "./NMap";
import { stationId } from "../../id";

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
function FromSchool({ navigation: { navigate } }) {
  const [modalVisible, setModalVisible] = useState(false);
  const [station, setStation] = useState({ name: "정류장을 선택하세요" });
  const [staredStation, setStaredStation] = useState([]);

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

  const routeData = GetRouteTableData(busListData[0].busList);

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
      {routeData.every(item => item.data !== undefined) ? (
        <NMap routeData={routeData} setStation={setStation} station={station} />
      ) : (
        <Loader>
          <ActivityIndicator />
        </Loader>
      )}

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

      {station.name !== "정류장을 선택하세요" ? (
        <SubmitButton
          onPress={() =>
            navigate("SearchStack", {
              screen: "BusList",
              params: {
                toSchool: false,
                redBus: false,
                stationId: stationId.ChapleGwan,
                dest: station,
              },
            })
          }
        >
          <SubmitContainer>
            <SubmitText>버스 검색</SubmitText>
          </SubmitContainer>
        </SubmitButton>
      ) : null}
      {modalVisible && routeData.every(item => item.data !== undefined) ? (
        <StationSelect
          data={routeData}
          staredStation={staredStation}
          storageKey={calendarData.description}
          modalVisible={modalVisible}
          setModalVisible={setModalVisible}
          setStation={setStation}
        />
      ) : null}
    </Conatiner>
  );
}

export default FromSchool;
