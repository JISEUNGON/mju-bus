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

const TableContents = styled(TableHeader)`
  background-color: white;
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
const Contents = styled(Header)``;

function SiweTableList({ list }) {}

function SineTableList({ list }) {
  const [dataList, setDataList] = useState([]);
  useEffect(() => {
    // 시내 노선 1개일 때
    if (list.length === 1) {
      list[0]?.data?.stations[0]?.timeList.map(
        item => (item.name = list[0].data.name),
      );
      setDataList(list[0]?.data?.stations[0]?.timeList);
    }
    // 시내 노선 2개일 때 (명지대, 용인시내) : 시간순으로 SORT
    if (list.length === 2) {
      list[0]?.data?.stations[0]?.timeList.map(
        item => (item.name = list[0].data.name),
      );
      list[1]?.data?.stations[0]?.timeList.map(
        item => (item.name = list[1].data.name),
      );
      setDataList(
        [
          ...list[0]?.data?.stations[0]?.timeList,
          ...list[1]?.data?.stations[0]?.timeList,
        ].sort(sortTimeTable),
      );
    }
  }, [list]);

  let counter = 0;
  return dataList.length === 0 ? (
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <Container>
      <TableHeader>
        <Header style={{ flex: 1 }}>순번</Header>
        <Header style={{ flex: 2 }}>구분</Header>
        <Header style={{ flex: 2 }}>출발시각</Header>
        <Header style={{ flex: 2 }}>진입로 경유</Header>
      </TableHeader>
      {dataList?.map(data => {
        counter += 1;
        return (
          <TableContents key={data.depart_at}>
            <Contents style={{ flex: 1 }}>{counter}</Contents>
            <Contents style={{ flex: 2 }}>{data.name}</Contents>
            <Contents style={{ flex: 2 }}>
              {data.depart_at.substr(0, 5)}
            </Contents>
            <Contents style={{ flex: 2 }}>
              {data.arrive_at.substr(0, 5)}
            </Contents>
          </TableContents>
        );
      })}
    </Container>
  );
}

function KiheungStaionTableList({ list }) {
  const dataList = list[0]?.data?.stations[0]?.timeList;
  let counter = 0;
  return dataList === undefined ? (
    <NoDataTable>
      <NoDataContents>운행 중인 시간표가 없습니다.</NoDataContents>
      <XIcon />
    </NoDataTable>
  ) : (
    <Container>
      <TableHeader>
        <Header style={{ flex: 1 }}>순번</Header>
        <Header style={{ flex: 2 }}>학교출발</Header>
        <Header style={{ flex: 2 }}>기흥역 도착</Header>
      </TableHeader>
      {dataList?.map(data => {
        counter += 1;
        return (
          <TableContents key={data.depart_at}>
            <Contents style={{ flex: 1 }}>{counter}</Contents>
            <Contents style={{ flex: 2 }}>
              {data.depart_at.substr(0, 5)}
            </Contents>
            <Contents style={{ flex: 2 }}>
              {data.arrive_at.substr(0, 5)}
            </Contents>
          </TableContents>
        );
      })}
    </Container>
  );
}

// eslint-disable-next-line react/prop-types
function TimeTable({ data, value, type }) {
  const [sineList, setSineList] = useState([]);
  const [siweList, setSiweLisit] = useState([]);
  const [khList, setKHList] = useState([]);
  useEffect(() => {
    // eslint-disable-next-line react/prop-types
    setSineList(
      data.filter(item => item?.data?.id === 20 || item?.data?.id === 10),
    );
    // eslint-disable-next-line react/prop-types
    setKHList(data.filter(item => item?.data?.id === 30));
  }, [data]);

  if (type === "sine") {
    if (value === 0) {
      return <SineTableList list={sineList} />;
    }

    return <KiheungStaionTableList list={khList} />;
  }
  return <SiweTableList />;
}

export default TimeTable;
