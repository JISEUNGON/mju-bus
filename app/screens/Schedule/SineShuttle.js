import React, { useState } from "react";
import { ActivityIndicator, Dimensions, useColorScheme } from "react-native";
import styled from "styled-components";
import { useQuery } from "@tanstack/react-query";
import SwitchSelector from "react-native-switch-selector";
import { busApi, calendarApi } from "../../api";
import TimeTable from "../../components/TimeTable";
import RouteTable from "../../components/RouteTable";
import { GetRouteTableData, GetTimeTableData, highlights } from "../../utils";
import { BLACK_BUTTON_COLOR, DARK_GRAY, WHITE_COLOR } from "../../colors";

const { width: SCREEN_WIDTH } = Dimensions.get("window");

const Loader = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`;

const Hr = styled.View`
  width: 100%;
  margin-top: 40px;
  border-bottom-color: #d3d7dc;
  opacity: 0.5;
`;

const HeaderContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  background-color: white;
  height: 150px;
  justify-content: center;
  background-color: ${props => props.theme.scheduleBgColor};
`;

const Title = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: ${props => props.theme.mainTextColor};
  margin-top: 40px;
  margin-bottom: 20px;
`;

const SubTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 15px;
  color: ${props => props.theme.subTextColor};
`;

const ContentsContainer = styled.ScrollView`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  background-color: ${props => props.theme.scheduleBgColor};
`;

const TimmTableTitleContainer = styled.View`
  justify-content: space-between;
  align-items: center;
  width: 100%;
  flex-direction: row;
  margin-bottom: 20px;
  justify-content: space-between;
  margin-top: 20px;
`;

const RouteTableTitleContainer = styled(TimmTableTitleContainer)`
  margin-top: 40px;
`;

const ContentsTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: ${props => props.theme.mainTextColor};
`;

const SwitchContatiner = styled.View`
  width: 180px;
`;
function SineShuttle() {
  const isDark = useColorScheme() === "dark";

  const [selectedRoute, setSelectedRoute] = useState(0);
  const options = [
    { label: "시내", value: 0 },
    { label: "기흥역", value: 1 },
  ];
  const { isLoading: buslistLoading, data: busListData } = useQuery(
    ["busList"],
    busApi.list,
  );
  const { isLoading: calendarLoading, data: calendarData } = useQuery(
    ["calendar"],
    calendarApi.calendar,
  );

  const loading = buslistLoading || calendarLoading;

  return loading ? (
    // 운행중인 버스 && 현재 일정표 데이터를 얻는 동안 로딩 출력
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <>
      {/* 일정표 */}
      <HeaderContainer>
        <Title>현재는 {highlights(calendarData.name)} 이에요 !</Title>
        <SubTitle>운행 중인 노선도와 시간표를 확인하세요</SubTitle>
        <Hr style={{ borderBottomWidth: 3 }} />
      </HeaderContainer>
      {/* 시내 셔틀 노선도 및 시간표 */}
      <ContentsContainer showsVerticalScrollIndicator={false}>
        {/* 시간표 */}
        <TimmTableTitleContainer>
          <ContentsTitle>시간표</ContentsTitle>
          <SwitchContatiner>
            <SwitchSelector
              onPress={value => setSelectedRoute(value)}
              options={options}
              initial={0}
              backgroundColor={isDark ? BLACK_BUTTON_COLOR : "#F5F5F5"}
              borderRadius={6}
              hasPadding
              borderColor={isDark ? BLACK_BUTTON_COLOR : "#F5F5F5"}
              textColor="#8D94A0"
              selectedColor={isDark ? "white" : "black"}
              buttonColor={isDark ? DARK_GRAY : WHITE_COLOR}
            />
          </SwitchContatiner>
        </TimmTableTitleContainer>
        <TimeTable
          data={GetTimeTableData(busListData[0]?.busList)}
          value={selectedRoute}
        />
        {/* 노선도 */}
        <RouteTableTitleContainer>
          <ContentsTitle>노선도</ContentsTitle>
        </RouteTableTitleContainer>
        <RouteTable
          data={GetRouteTableData(busListData[0]?.busList)}
          value={selectedRoute}
        />
      </ContentsContainer>
    </>
  );
}

export default SineShuttle;
