import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { Ionicons } from "@expo/vector-icons";
import { TouchableOpacity, Dimensions, ActivityIndicator } from "react-native";
import { highlights } from "../../utils";
import StationSelect from "./StationSelect";
import NMap from "./NMap";
import { stationId } from "../../id";
import { MBAContext } from "../../navigation/Root";

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
  background-color: ${props => props.theme.busBgColor};
  top: 0;
  position: absolute;
`;

const TextContainer = styled.View`
  flex-direction: row;
`;

const SelectTextFrom = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: ${props => props.theme.mainTextColor};
`;

const SelectTextSub = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-weight: 500;
  font-size: 15px;
  color: ${props => props.theme.subTextColor};
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
  const {
    sineBusList,
    siweBusList,
    mjuCalendar,
    stationList,
    busTimeTable,
  } = React.useContext(MBAContext);

  // 정류장 선택 모달 
  const [modalVisible, setModalVisible] = useState(false);

  // 현재 선택된 정류장
  const [station, setStation] = useState({ name: "정류장을 선택하세요" });

  // 운행중인 버스들의 모든 정류장
  const [allStation, setAllStation] = useState([]);

  // 로딩
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // "학교에서" 정류장 블랙리스트
    const blackList = [4, 5, 1, 6, 11, 24];

    // 운행중인 버스들의 모든 정류장을 중복을 제거하며 저장
    const stations = new Set();

    // 모든 버스에 대해서 정류장을 저장
    stationList.forEach(bus => {
      bus.stations.forEach(station => {
        if (!blackList.includes(station.id)) { // 블랙리스트에 없는 정류장만 저장
          stations.add({
            id: station.id,
            name: station.name,
            latitude: station.latitude,
            longitude: station.longitude,
          });
        }
      });
    });

    // 저장
    setAllStation([...stations]);

    // 로딩 종료
    setLoading(false);
  }, []);

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
      {allStation.every(item => item.id !== undefined) ? (
        <NMap
          routeData={allStation} // 모든 정류장
          selectedStation={station} // 현재 선택된 정류장
          setStation={setStation} // 현재 선택된 정류장 변경
        />
      ) : (
        <Loader>
          <ActivityIndicator />
        </Loader>
      )}

      <SelectContainer>
        <TouchableOpacity onPressOut={onStart}>
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
          onPressOut={() =>
            navigate("SearchStack", {
              screen: "BusList",
              params: {
                toSchool: false,
                redBus: false,
                src: stationId.ChapleGwan,
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
      {modalVisible ? (
        <StationSelect
          stations={allStation}
          modalVisible={modalVisible}
          setModalVisible={setModalVisible}
          setStation={setStation}
        />
      ) : null}
    </Conatiner>
  );
}

export default FromSchool;
