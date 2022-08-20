import React, { useState } from "react";
import { ActivityIndicator, Dimensions } from "react-native";
import styled from "styled-components";
import { useQuery, useQueries } from "@tanstack/react-query";
import SwitchSelector from "react-native-switch-selector";
import { busApi, calendarApi } from "../../api";
import TimeTable from "../../components/TimeTable";
import RouteTable from "../../components/RouteTable";

const { width: SCREEN_WIDTH } = Dimensions.get("window");

const Loader = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`;

const Hr = styled.View`
  width: 100%;
  height: 1px;
  margin-top: 40px;
  border-bottom-color: #d3d7dc;
`;

const HeaderContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  background-color: white;
  height: 150px;
  justify-content: center;
`;

const Title = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: black;
  margin-top: 40px;
  margin-bottom: 20px;
`;

const HighlightTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: #7974e7;
`;

const SubTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 15px;
  color: #8d94a0;
`;

const ContentsContainer = styled.ScrollView`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  background-color: white;
`;

const ContentsTitleContainer = styled.View`
  justify-content: space-between;
  align-items: center;
  width: 100%;
  flex-direction: row;
  margin-bottom: 20px;
  justify-content: space-between;
  margin-top: 30px;
`;

const ContentsTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: black;
`;

const SwitchContatiner = styled.View`
  width: 180px;
`;
function SineShuttle() {
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

  // eslint-disable-next-line react/no-unstable-nested-components
  function SpecialTitle(contents) {
    return <HighlightTitle>{contents}</HighlightTitle>;
  }

  function GetTimeTableData(list) {
    const timeTable = useQueries({
      queries: list.map(bus => ({
        queryKey: ["timeTable", bus.id],
        queryFn: busApi.timeTable,
      })),
    });
    return timeTable;
  }
  function GetRouteTableData(list) {
    const routeTable = useQueries({
      queries: list.map(bus => ({
        queryKey: ["route", bus.id],
        queryFn: busApi.route,
      })),
    });
    return routeTable;
  }

  return loading ? (
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <>
      <HeaderContainer>
        <Title>현재는 {SpecialTitle(calendarData.name)} 이에요 !</Title>
        <SubTitle>운행 중인 노선도와 시간표를 확인하세요</SubTitle>
        <Hr style={{ borderBottomWidth: 1 }} />
      </HeaderContainer>
      <ContentsContainer showsVerticalScrollIndicator={false}>
        <ContentsTitleContainer>
          <ContentsTitle>시간표</ContentsTitle>
          <SwitchContatiner>
            <SwitchSelector
              onPress={value => setSelectedRoute(value)}
              options={options}
              initial={0}
              backgroundColor="#F5F5F5"
              borderRadius={6}
              hasPadding
              borderColor="white"
              textColor="#8D94A0"
              selectedColor="black"
              buttonColor="white"
            />
          </SwitchContatiner>
        </ContentsTitleContainer>
        <TimeTable
          data={GetTimeTableData(busListData[0]?.busList)}
          value={selectedRoute}
          type="sine"
        />

        <ContentsTitleContainer>
          <ContentsTitle>노선도</ContentsTitle>
        </ContentsTitleContainer>
        <RouteTable
          data={GetRouteTableData(busListData[0]?.busList)}
          value={selectedRoute}
          type="sine"
        />
      </ContentsContainer>
    </>
  );
}

export default SineShuttle;
