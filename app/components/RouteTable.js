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
  width: 12px;
  height: 12px;
  border-radius: 9px;
  align-items: center;
  justify-content: center;
  margin-top: ${props => {
    if (props.level === 0) {
      return -7;
    }
    if (props.level === 1) {
      return 1;
    }
    if (props.level === 2) {
      return 65;
    }
    if (props.level === 3) {
      return 132;
    }
    return 141;
  }}px;
  margin-left: ${props => {
    if (props.level === 2) {
      return ROUTE_END_POINT + 2;
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
      return -22;
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
    return 152;
  }}px;
  margin-left: ${props => {
    if (props.level === 2) {
      return ROUTE_END_POINT + 2;
    }
    if (props.level === 1 || props.level === 3) {
      return ROUTE_ROUND_POINT + 10;
    }
    return props.value - 23;
  }}px;
`;

const StationName = styled.Text`
  font-size: 6px;
  font-family: "SpoqaHanSansNeo-Medium";
  color: #6d7582;
`;

const StationInnerImage = styled.View`
  background-color: white;
  width: 6px;
  height: 6px;
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
    // 노선 이미지
    <RouteImgage
      style={{
        borderTopRightRadius: 75,
        borderBottomRightRadius: 75,
        borderTopWidth: 2,
        borderRightWidth: 2,
        borderBottomWidth: 2,
      }}
    >
      {/* 노선 내 정류장 점들의 margin 세팅 */}
      {/* 정류장이 4개 이상이라면 level 2,4 에도 정류장 및 정류장명 출력 */}
      {/* 정류장이 4개 미만이라면 level 2,4 에는 정류장 및 정류장명 마출력 */}
      {/* 정류장이 짝수라면 level 3 에는 정류장 및 정류장명 미출력 */}
      {/* 정류장이 홀수라면 level 3 에는 정류장 및 정류장명 출력 */}
      {data.map((station, index) => {
        let margin = 0;
        let level = 0;
        // level 4에는 역순으로 출력되기 때문에 Margin값을
        // 반대로 담는 배열을 만든다.
        const ReverseMargin = [...result].reverse();
        // 4이상 , 홀수 일 때
        if (count >= 4 && count % 2 !== 0) {
          if (index === points) {
            level = 1;
          }
          if (index === points + 1) {
            level = 2;
          }
          if (index === points + 2) {
            level = 3;
          }
          if (index < points) {
            margin = result[index];
            level = 0;
          }
          if (index >= points + 3) {
            margin = ReverseMargin[index % (points + 3)];
            level = 4;
          }
        }
        // 4이상 , 짝수 일 때
        if (count >= 4 && count % 2 === 0) {
          if (index === points) {
            level = 1;
          }
          if (index === points + 1) {
            level = 3;
          }
          if (index < points) {
            margin = result[index];
            level = 0;
          }
          if (index >= points + 2) {
            margin = ReverseMargin[index % (points + 2)];
            level = 4;
          }
        }
        // 4미만 , 홀수 일 때
        if (count < 4 && count % 2 !== 0) {
          if (index === points) {
            level = 2;
          }
          if (index < points) {
            margin = result[index];
            level = 0;
          }
          if (index > points) {
            margin = ReverseMargin[index % (points + 1)];
            level = 4;
          }
        }
        return (
          // 정류장명 출력
          <StaionNameContainer
            key={station.route_order}
            value={margin}
            level={level}
          >
            <StationName>{station.name}</StationName>
          </StaionNameContainer>
        );
      })}

      {/* 정류장 출력 */}
      {/* level 0, 2 정류장 출력 */}
      {count % 2 === 0 ? null : (
        <StationOuterImage value={0} level={2}>
          <StationInnerImage />
        </StationOuterImage>
      )}
      {/* level 1, 3 정류장 출력 */}
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
      {/* level 0, 4 정류장 출력 */}
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

// 시내 노선 (용안시내 , 명지대, 기흥역) 노선 분류 함수
function SineRouteList({ list }) {
  // 해당 노선의 데이터가 없다면 아무것도 출력하지 않음
  return list.length === 0 ? null : (
    <>
      <RouteNameContainer>
        {/* 시내 or 명지대 or 기흥역 */}
        <RouteName>{list[0].data.name} 노선</RouteName>
      </RouteNameContainer>
      <StationImages list={list} />
    </>
  );
}

// 노선도 출력 함수
function RouteTable({ data, value }) {
  const [sineList, setSineList] = useState([]);
  const [khList, setKHList] = useState([]);
  const [mjList, setMJList] = useState([]);
  // ID 값에 따른 데이터 세팅
  useEffect(() => {
    setMJList(data.filter(item => item?.data?.id === 10));
    setSineList(data.filter(item => item?.data?.id === 20));
    setKHList(data.filter(item => item?.data?.id === 30));
  }, [data]);

  // SWITCH VALUE : 0 -> 시내 노선도
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

  // SWITCH VALUE : 1 -> 기흥역 노선도
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

export default RouteTable;
