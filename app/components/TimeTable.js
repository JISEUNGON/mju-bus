/* eslint-disable no-return-assign */
/* eslint-disable no-param-reassign */
/* eslint-disable react/prop-types */
import React, { useEffect, useState } from "react";
import { ActivityIndicator } from "react-native";
import styled from "styled-components";
import { sortTimeTable } from "../utils";
import XIcon from "./XIcon";

const Loader = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`;

const Container = styled.ScrollView`
  width: 100%;
  height: 300px;
`;

const TableHeader = styled.View`
  flex-direction: row;
  background-color: ${props => props.theme.tableHeader};
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
  color: ${props => props.theme.tableHeaderText};
`;

const TableContents = styled(TableHeader)`
  background-color: ${props => props.theme.tableContents};
  margin-top: 0px;
  flex: 1;
`;

const NoDataTable = styled.View`
  width: 100%;
  height: 300px;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
`;

const NoDataContents = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  color: #ec6969;
  font-size: 20px;
  margin-bottom: 30px;
`;
const Contents = styled(Header)`
  color: ${props => props.theme.tableContentsText};
`;

// 시내 (용인시내 + 명지대역) 시간표
function SineTableList({ list }) {
  const [dataList, setDataList] = useState([]);

  useEffect(() => {
    // 시내 노선 1개일 때
    if (list.length === 1) {
      const { id, name, timeTable } = list[0];
      setDataList(timeTable[0]);
    }
    // 시내 노선 2개일 때 (명지대, 용인시내) : 시간순으로 SORT
    if (list.length === 2) {
      setDataList(
        [
          list[0].timeTable[0],
          list[1].timeTable[0]
        ].sort(sortTimeTable),
      );
    }
  }, [list]);

  let counter = 0;
  // 시내 데이터는 항상 1개 이상 이므로 로딩 출력
  return dataList.length === 0 ? (
    <Loader>
      <ActivityIndicator size="large" color="#0000ff" />
    </Loader>)
     :  (
    <Container nestedScrollEnabled>
      <TableHeader>
        <Header style={{ flex: 1 }}>순번</Header>
        <Header style={{ flex: 2 }}>구분</Header>
        <Header style={{ flex: 2 }}>출발시각</Header>
        <Header style={{ flex: 2 }}>진입로 경유</Header>
      </TableHeader>
      {dataList.timeList.map(data => {
        counter += 1;
        return (
          <TableContents key={data.depart_at}>
            {/* 1. 순번 */}
            <Contents style={{ flex: 1 }}>{counter}</Contents>
            {/* 구분 (명지대역 / 시내) */}
            <Contents style={{ flex: 2 }}>{dataList.name}</Contents>
            {/* 출발 시각 */}
            <Contents style={{ flex: 2 }}>
              {data.depart_at.substr(0, 5)}
            </Contents>
            {/* 진입로 경유 시간 */}
            <Contents style={{ flex: 2 }}>
              {data.arrive_at.substr(0, 5)}
            </Contents>
          </TableContents>
        );
      })}
    </Container>
  );
}

// 기흥역 시간표
function KiheungStaionTableList({ list }) {
  const [dataList, setDataList] = useState([]);
  useEffect(() => {
    if (list.length !== 0) {
      setDataList(list[0]?.data?.stations[0]?.timeList);
    }
  }, [list]);

  let counter = 0;
  return dataList.length === 0 ? (
    <NoDataTable>
      <NoDataContents>운행 중인 시간표가 없습니다.</NoDataContents>
      <XIcon />
    </NoDataTable>
  ) : (
    <Container nestedScrollEnabled>
      <TableHeader>
        <Header style={{ flex: 1 }}>순번</Header>
        <Header style={{ flex: 2 }}>학교출발</Header>
        <Header style={{ flex: 2 }}>기흥역 도착</Header>
      </TableHeader>
      {dataList?.map(data => {
        counter += 1;
        return (
          <TableContents key={data.depart_at}>
            {/* 1. 순번 */}
            <Contents style={{ flex: 1 }}>{counter}</Contents>
            {/* 학교 출발 시각 */}
            <Contents style={{ flex: 2 }}>
              {data.depart_at.substr(0, 5)}
            </Contents>
            {/* 3. 기흥역 도착 시각 */}
            <Contents style={{ flex: 2 }}>
              {data.arrive_at.substr(0, 5)}
            </Contents>
          </TableContents>
        );
      })}
    </Container>
  );
}

// 시간표 출력 함수
function TimeTable({
    busTimeTable, // 버스 시간표
    value // 시내, 기흥역 탭
  }) {

  const [sineList, setSineList] = useState([]);
  const [khList, setKHList] = useState([]);

  useEffect(() => {
    setSineList(busTimeTable.filter(bus => bus.id === 20 || bus.id === 10));
    setKHList(busTimeTable.filter(bus => bus.id === 30));
  }, []);

  if (value === 0) {
    return <SineTableList list={sineList} />;
  }
  return <KiheungStaionTableList list={khList} />;
}

export default TimeTable;
