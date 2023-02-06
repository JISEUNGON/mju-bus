import React from "react";
import styled from "styled-components/native";
import { Text } from "react-native";
import Taxi from "../Together/Taxi";

const HeaderContainer = styled.View`
  flex: 0.1;
`;
const DataContainer = styled.View`
  flex: 1;
  flex-direction: row;
`;

const DataTextView = styled.View`
  flex: auto;
  justify-content: center;
`;

const DataText = styled.Text`
  text-align: right;
  padding-right: 10px;
  font-size: 19px;
  font-weight: 800;
`;
const DataIconView = styled.View`
  flex: auto;

  justify-content: center;
`;
const DataDate = styled.View`
  flex: 0.7;
  justify-content: center;
  align-items: center;
`;

const Icon = styled.View`
  flex: 0.6;
  background-color: #e8f3e6;
  width: 60px;
  align-items: center;
  justify-content: center;
  border-radius: 20px;
`;

function TaxiHeader() {
  const TaxiDetailData = {
    basicsData: {
      recruitingMemberNumber: 4,
      startingPoint: "기흥역",
      endingPoint: "학교",
      minMemberNumber: 3,
    },
    date: {
      year: 2023,
      month: 1,
      days: 7,
      day: "토",
    },
    time: {
      moring: true,
      hours: 11,
      mins: 50,
    },
    startingAddress: {
      address: "기흥역 3번 출구",
      detailAddress: "경기 용인시 기흥구 구갈동 660-1",
    },
    coordinate: {
      latitude: 37.275296997802755,
      longitude: 127.11623345523063,
    },
  };

  function ZeroFunc(x) {
    x = x.toString();
    num = x.length;
    return num === 1 ? 0 : null;
  }

  return (
    <HeaderContainer>
      <DataContainer>
        <DataTextView>
          <DataText>
            {TaxiDetailData.basicsData.startingPoint} →{" "}
            {TaxiDetailData.basicsData.endingPoint}
          </DataText>
        </DataTextView>
        <DataIconView>
          <Icon>
            <Text style={{ color: "#4F8645", fontWeight: "bold" }}>모집중</Text>
          </Icon>
        </DataIconView>
      </DataContainer>
      <DataDate>
        <Text style={{ color: "#929292" }}>
          {ZeroFunc(TaxiDetailData.date.month)}
          {TaxiDetailData.date.month}/{ZeroFunc(TaxiDetailData.date.days)}
          {TaxiDetailData.date.days}{" "}
          {TaxiDetailData.time.moring ? "오전" : "오후"}{" "}
          {ZeroFunc(TaxiDetailData.time.hours)}
          {TaxiDetailData.time.hours}:{ZeroFunc(TaxiDetailData.time.mins)}
          {TaxiDetailData.time.mins}
        </Text>
      </DataDate>
    </HeaderContainer>
  );
}

export default TaxiHeader;
