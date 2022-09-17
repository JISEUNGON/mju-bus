import { useQuery } from "@tanstack/react-query";
import React, { useEffect, useState } from "react";
import { ActivityIndicator, Dimensions } from "react-native";
import styled from "styled-components";
import { busApi, calendarApi } from "../../api";
import RouteTimeTable from "../../components/RouteTimeTable";
import { GetBusInfoData, GetTimeTableData, highlights } from "../../utils";

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
  background-color: ${props => props.theme.scheduleBgColor};
  height: 150px;
  justify-content: center;
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

const ContentsTitleContainer = styled.View`
  justify-content: space-between;
  align-items: center;
  width: 100%;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 30px;
  margin-bottom: -10px;
`;

const ContentsTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: ${props => props.theme.mainTextColor};
`;

function SiweShuttle() {
  const { isLoading: buslistLoading, data: busListData } = useQuery(
    ["busList"],
    busApi.list,
  );
  const { isLoading: calendarLoading, data: calendarData } = useQuery(
    ["calendar"],
    calendarApi.calendar,
  );
  const [isToSchool, setIsToSchool] = useState(true);

  const loading = buslistLoading || calendarLoading;

  useEffect(() => {
    setIsToSchool(new Date().getHours() < 12);
  }, []);

  return loading ? (
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <>
      <HeaderContainer>
        <Title>현재는 {highlights(calendarData.name)} 이에요 !</Title>
        <SubTitle>운행 중인 노선도와 시간표를 확인하세요</SubTitle>
        <Hr style={{ borderBottomWidth: 3 }} />
      </HeaderContainer>
      <ContentsContainer showsVerticalScrollIndicator={false}>
        <ContentsTitleContainer>
          <ContentsTitle>노선 및 시간표</ContentsTitle>
        </ContentsTitleContainer>
        <RouteTimeTable
          isToSchool={isToSchool}
          timeList={GetTimeTableData(busListData[1]?.busList)}
          busInfo={GetBusInfoData(busListData[1]?.busList)}
        />
      </ContentsContainer>
    </>
  );
}

export default SiweShuttle;
