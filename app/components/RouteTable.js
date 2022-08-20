/* eslint-disable react/prop-types */
import React, { useEffect, useState } from "react";
import { Dimensions } from "react-native";
import styled from "styled-components";
import XIcon from "./XIcon";

const { width: SCREEN_WIDTH } = Dimensions.get("window");
const ROUTE_WIDTH = (SCREEN_WIDTH - 40) * 0.9 - 75;
const ROUTE_END_POINT = (SCREEN_WIDTH - 40) * 0.9 - 9;
const ROUTE_ROUND_POINT = ROUTE_WIDTH + (ROUTE_END_POINT - ROUTE_WIDTH) * 0.4;

const Absolute = styled.View`
  position: absolute;
  flex-direction: row;
`;

const Container = styled.View`
  margin-top: 10px;
  align-items: center;
  width: 100%;
`;

const RouteImgage = styled.View`
  margin-top: 30px;
  margin-right: 20px;
  margin-bottom: 70px;
  background-color: white;
  border-color: #c1c6cd;
  width: 90%;
  height: 150px;
`;

const StationOuterImage = styled.View`
  position: absolute;
  background-color: #dad8fb;
  width: 18px;
  height: 18px;
  border-radius: 9px;
  align-items: center;
  justify-content: center;
  margin-top: ${props => {
    if (props.level === 0) {
      return -10;
    }
    if (props.level === 1) {
      return 1;
    }
    if (props.level === 2) {
      return 63;
    }
    if (props.level === 3) {
      return 129;
    }
    return 138;
  }}px;
  margin-left: ${props => {
    if (props.level === 2) {
      return ROUTE_END_POINT;
    }
    if (props.level === 1 || props.level === 3) {
      return ROUTE_ROUND_POINT;
    }
    return props.value;
  }}px;
`;

const StaionNameContainer = styled.View`
  align-items: center;
  width: 60px;
  justify-content: center;
  height: 18px;
  margin-bottom: 10px;
  position: absolute;
  margin-top: ${props => {
    if (props.level === 0) {
      return -30;
    }
    if (props.level === 1) {
      return -2;
    }
    if (props.level === 2) {
      return 63;
    }
    if (props.level === 3) {
      return 132;
    }
    return 160;
  }}px;
  margin-left: ${props => {
    if (props.level === 2) {
      return ROUTE_END_POINT + 4;
    }
    if (props.level === 1 || props.level === 3) {
      return ROUTE_ROUND_POINT + 18;
    }
    return props.value - 20;
  }}px;
`;

const StationName = styled.Text`
  font-size: 6px;
  font-family: "SpoqaHanSansNeo-Medium";
  color: #6d7582;
`;

const StationInnerImage = styled.View`
  background-color: white;
  width: 8px;
  height: 8px;
  border-radius: 4px;
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

const RouteName = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  color: #6d7582;
  font-size: 13px;
`;

const RouteNameContainer = styled.View`
  width: 100%;
  align-items: flex-end;
`;

function StationImages({ list }) {
  const count = list[0].data.stations.length;
  const data = list[0].data.stations;

  const points =
    count < 4 ? parseInt(count / 2, 10) : parseInt((count - 2) / 2, 10);

  const margins = new Array(points);
  let counter = 0;

  for (let i = 0; i < points; i += 1) {
    margins[i] = 0;
  }
  const result = margins.map(() => {
    counter += 1;
    return parseInt(ROUTE_WIDTH / (points + 1), 10) * counter;
  });

  return (
    <RouteImgage
      style={{
        borderTopRightRadius: 75,
        borderBottomRightRadius: 75,
        borderTopWidth: 2,
        borderRightWidth: 2,
        borderBottomWidth: 2,
      }}
    >
      {data.map((station, index) => {
        let margin = 0;
        let level = 0;
        const ReverseMargin = [...result].reverse();
        if (count >= 4 && count % 2 !== 0) {
          if (index === points) {
            // 5
            level = 1;
          }
          if (index === points + 1) {
            // 6
            level = 2;
          }
          if (index === points + 2) {
            // 7
            level = 3;
          }
          if (index < points) {
            // 0~4
            margin = result[index];
            level = 0;
          }
          if (index >= points + 3) {
            margin = ReverseMargin[index % (points + 3)];
            level = 4;
          }
        }
        if (count >= 4 && count % 2 === 0) {
          if (index === points) {
            // 5
            level = 1;
          }
          if (index === points + 1) {
            // 6
            level = 3;
          }
          if (index < points) {
            // 0~4
            margin = result[index];
            level = 0;
          }
          if (index >= points + 2) {
            margin = ReverseMargin[index % (points + 2)];
            level = 4;
          }
        }
        if (count < 4 && count % 2 !== 0) {
          if (index === points) {
            // 6
            level = 2;
          }
          if (index < points) {
            // 0~4
            margin = result[index];
            level = 0;
          }
          if (index > points) {
            margin = ReverseMargin[index % (points + 1)];
            level = 4;
          }
        }
        return (
          <StaionNameContainer
            key={station.route_order}
            value={margin}
            level={level}
          >
            <StationName>{station.name}</StationName>
          </StaionNameContainer>
        );
      })}
      {count % 2 === 0 ? null : (
        <StationOuterImage value={0} level={2}>
          <StationInnerImage />
        </StationOuterImage>
      )}
      {count < 4 ? null : (
        <Absolute>
          <StationOuterImage value={0} level={1}>
            <StationInnerImage />
          </StationOuterImage>
          <StationOuterImage value={0} level={3}>
            <StationInnerImage />
          </StationOuterImage>
        </Absolute>
      )}
      {result.map(margin => (
        <Absolute key={`${margin}`}>
          <StationOuterImage value={margin} level={0}>
            <StationInnerImage />
          </StationOuterImage>
          <StationOuterImage value={margin} level={4}>
            <StationInnerImage />
          </StationOuterImage>
        </Absolute>
      ))}
    </RouteImgage>
  );
}

function SineRouteList({ list }) {
  return list.length === 0 ? null : (
    <>
      <RouteNameContainer>
        <RouteName>{list[0].data.name} 노선</RouteName>
      </RouteNameContainer>
      <StationImages list={list} />
    </>
  );
}

function SiweRouteList({ list }) {
  return list.length === 0 ? null : <StationImages list={list} />;
}

export function RouteTable({ data, value, type }) {
  const [sineList, setSineList] = useState([]);
  const [khList, setKHList] = useState([]);
  const [mjList, setMJList] = useState([]);
  useEffect(() => {
    // eslint-disable-next-line react/prop-types
    setMJList(data.filter(item => item?.data?.id === 10));
    // eslint-disable-next-line react/prop-types
    setSineList(data.filter(item => item?.data?.id === 20));
    // eslint-disable-next-line react/prop-types
    setKHList(data.filter(item => item?.data?.id === 30));
  }, [data]);

  if (type === "sine") {
    if (value === 0) {
      return mjList.length === 0 && sineList.length === 0 ? (
        <NoDataTable>
          <NoDataContents>운행 중인 노선도가 없습니다.</NoDataContents>
          <XIcon />
        </NoDataTable>
      ) : (
        <Container>
          <SineRouteList list={mjList} />
          <SineRouteList list={sineList} />
        </Container>
      );
    }

    return khList.length === 0 ? (
      <NoDataTable>
        <NoDataContents>운행 중인 노선도가 없습니다.</NoDataContents>
        <XIcon />
      </NoDataTable>
    ) : (
      <Container>
        <SineRouteList list={khList} />
      </Container>
    );
  }
  return <SiweRouteList />;
}

export default RouteTable;
