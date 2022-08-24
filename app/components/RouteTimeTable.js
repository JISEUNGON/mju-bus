/* eslint-disable react/prop-types */
import React, { useEffect, useState } from "react";
import { View } from "react-native";
import styled from "styled-components";
import StationIcon from "./StationIcon";
import XIcon from "./XIcon";

const Container = styled.View`
  margin-top: 10px;
  align-items: center;
  width: 100%;
`;

const NoDataTable = styled.View`
  width: 100%;
  height: 300px;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
  padding-top: 68px;
`;

const NoDataContents = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  color: #ec6969;
  font-size: 20px;
  margin-bottom: 30px;
`;

const RouteName = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  color: #6d7582;
  font-size: 13px;
`;

const RouteNameContainer = styled.View`
  width: 100%;
  align-items: flex-end;
  margin-bottom: 8px;
`;

const TableHeader = styled.View`
  flex-direction: row;
  background-color: #f5f5f5;
  border-radius: 4px;
  width: 100%;
  height: 30px;
  margin-bottom: 10px;
  align-items: center;
  padding: 0px 10px;
  flex: 1;
`;

const Header = styled.Text`
  text-align: center;
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 12px;
  color: #6d7582;
`;

const Contents = styled(Header)``;

const TableContents = styled(TableHeader)`
  background-color: white;
  margin-top: 0px;
  height: 60px;
  flex: 1;
`;

const RouteImgage = styled.View`
  height: 30px;
  border-bottom-width: 2px;
  border-bottom-color: #c1c6cd;
  margin-left: 15px;
  margin-right: 15px;
  flex-direction: row;
  justify-content: space-around;
`;
const StationInnerImage = styled.View`
  background-color: white;
  width: 6px;
  height: 6px;
  border-radius: 4px;
`;
const StationOuterImage = styled.View`
  background-color: #f6e3e3;
  width: 12px;
  height: 12px;
  border-radius: 9px;
  align-items: center;
  justify-content: center;
  margin-top: 23px;
`;

const StationNamesContainer = styled.View`
  height: 14px;
  justify-content: space-around;
  align-items: center;
  flex-direction: row;
  margin-left: 15px;
  margin-right: 15px;
  margin-top: 7px;
`;

const StationName = styled.Text`
  font-size: 7px;
  font-family: "SpoqaHanSansNeo-Bold";
  color: #6d7582;
`;

const StationNameContainer = styled.View`
  justify-content: center;
  align-items: center;
  width: 53px;
  height: 18px;
`;
const TimeListsContainer = styled(StationNamesContainer)`
  margin-top: 0px;
`;

const Absolute = styled.View`
  position: absolute;
  flex-direction: row;
`;

const TimeListContainer = styled(StationNameContainer)``;
const Time = styled(StationName)`
  font-family: "SpoqaHanSansNeo-Medium";
`;

function RouteStationImages({ stations }) {
  return (
    <RouteImgage>
      {stations?.map(station => (
        <StationIcon busRoute="siwe" key={station.name} />
      ))}
    </RouteImgage>
  );
}

function RouteTimeTable({ timeList, busInfo, isToSchool }) {
  const [siweTimeList, setSiweTimeList] = useState([]);
  const [siweBusInfo, setSiweBusInfo] = useState([]);
  useEffect(() => {
    setSiweTimeList(timeList);
    setSiweBusInfo(busInfo);
  }, [timeList, busInfo]);

  return siweTimeList.length === 0 ? (
    <NoDataTable>
      <NoDataContents>운행 중인 시외 셔틀이 없습니다.</NoDataContents>
      <XIcon />
    </NoDataTable>
  ) : (
    <Container>
      <RouteNameContainer>
        <RouteName>{isToSchool ? "등교" : "하교"}</RouteName>
      </RouteNameContainer>
      <TableHeader>
        <Header style={{ flex: 1 }}>순번</Header>
        <Header style={{ flex: 3 }}>노선 및 시간표</Header>
        <Header style={{ flex: 1 }}>요금</Header>
      </TableHeader>
      {siweTimeList?.map((item, itemIndex) => (
        <TableContents key={item.data.id}>
          {/* 1. 정류장 */}
          <Contents style={{ flex: 1 }}>{item.data.name}</Contents>

          {/* 2. 노선도 + 시간 */}
          <View style={{ flex: 3 }}>
            <RouteStationImages stations={item.data.stations} />
            <StationNamesContainer>
              {item?.data?.stations?.map(station => (
                <StationNameContainer key={station.name}>
                  <StationName>{station.name}</StationName>
                </StationNameContainer>
              ))}
            </StationNamesContainer>
            {/* 등교 / 하교 유무 분류 */}
            <TimeListsContainer>
              {isToSchool
                ? // 등교 일때: 시간표를 모든 정류장에 대해 표기한다
                  item?.data?.stations?.map(station => (
                    <TimeListContainer key={station.name}>
                      <Time>{station.timeList[0].depart_at.substr(0, 5)}</Time>
                    </TimeListContainer>
                  ))
                : // 하교일때 : 첫 정류장 (명지대)에 대해서 출발 시간만 표기한다
                  item?.data?.stations?.map((station, index) => (
                    <TimeListContainer key={station.name}>
                      {index === 0 ? (
                        <Time>
                          {station.timeList[0].depart_at.substr(0, 5)}
                        </Time>
                      ) : null}
                    </TimeListContainer>
                  ))}
            </TimeListsContainer>
          </View>
          {/* 3. 요금 */}
          <Contents style={{ flex: 1 }}>
            {siweBusInfo[itemIndex]?.data?.charge}
          </Contents>
        </TableContents>
      ))}
    </Container>
  );
}

export default RouteTimeTable;
