import React, { useContext, useState } from "react";
import { View, Text, Linking } from "react-native";
import styled from "styled-components/native";
import { MaterialIcons } from "@expo/vector-icons";
import NaverMapView, { Marker } from "react-native-nmap";

//------------------------------------- 상세 정보 --------------------------
const Container = styled.View`
  flex: 1;
  background-color: #f7f7f7;
`;

//------------------------------------ 모집 내용 ---------------------------
const ContextContainer = styled.View`
  flex: 1.2;
  background-color: white;
  border-radius: 20px;
  margin: 20px 20px;
  box-shadow: 0.05px 0.05px 1px #d9d7d7;
`;

const ContextTitle = styled.Text`
  flex: 0.3;
  margin-top: 20px;
  margin-left: 20px;
  font-size: 16px;
  font-weight: 600;
`;

const ContextInsideContainer = styled.View`
  flex: 1;
  margin-bottom: 30px;
`;

const ContextMiniContainer = styled.View`
  flex: 1;
  flex-direction: row;
`;

const ContextApiText = styled.Text`
  margin-top: 5px;
  text-align: right;
  flex: 1;
  font-size: 16px;
  margin-right: 30px;
`;

const ContextTexts = styled.Text`
  flex: 1;
  font-weight: bold;
  font-size: 16px;
  margin-left: 30px;
  margin-top: 5px;
`;

//------------------------------------ 탑승지 상세 정보 ----------------------
const StartingPointContainer = styled.View`
  flex: 2;
  border-radius: 20px;
  background-color: white;
  margin: 5px 20px 40px;
  box-shadow: 0.05px 0.05px 1px #d9d7d7;
`;

const StartingPointTitle = styled.Text`
  flex: 1;
  font-size: 15;
  font-weight: 700;
  margin-top: 10px;
  padding-left: 25px;
  margin-bottom: -20px;
`;

const DateContainer = styled.View`
  flex: 1;
`;

const DateText = styled.Text`
  flex: 1;
  font-size: 18px;
  font-weight: bold;
  padding-left: 34px;
`;

const TimeText = styled.Text`
  font-size: 18px;
  padding-left: 34px;
  flex: 1;
`;

const MapContainer = styled.View`
  flex: 3;
  background-color: pink;
  margin: 0px 25px;
`;

const AddressContainer = styled.View`
  flex: 1;
`;

const AddressText = styled.Text`
  flex: 0.6;
  font-size: 16px;
  margin-left: 34px;
`;

const AddressDetailText = styled.Text`
  flex: 1;
  font-size: 16px;
  color: #959595;
  margin-top: 3px;
  text-decoration: underline #959595;
`;

const Icon = styled.View`
  flex: 0.07;
`;

const IconTextContainer = styled.View`
  flex: 1;
  flex-direction: row;
  margin-left: 27px;
`;

function Detail() {
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
      // latitude: 37.275296997802755,
      // longitude: 127.11623345523063,
      latitude: 37.4979502,
      longitude: 127.0276368,
    },
  };

  function MyMap() {
    const p0 = {
      latitude: TaxiDetailData.coordinate.latitude,
      longitude: TaxiDetailData.coordinate.longitude,
    };

    return (
      <NaverMapView
        style={{ width: "100%", height: "100%" }}
        center={{ ...p0, zoom: 16 }}
      >
        <Marker coordinate={p0} />
      </NaverMapView>
    );
  }
  function ZeroFunc(x) {
    x = x.toString();
    num = x.length;
    return num === 1 ? 0 : null;
  }
  function gotoWebPage() {
    return Linking.openURL(
      // site&pinId=${your_site_id}
      // `https://m.map.naver.com/map.naver?lat=${TaxiDetailData.coordinate.latitude}&lng=${TaxiDetailData.coordinate.longitude}&query=${TaxiDetailData.startingAddress.address}&dlevel=12&mapMode=0&traffic=`,
      // `https://m.map.naver.com/map.naver?&query=${TaxiDetailData.coordinate.latitude},${TaxiDetailData.coordinate.longitude}&dlevel=12&mapMode=0&traffic=`,
      // `https://map.naver.com/index.nhn?pinType=&lat=37.275296997802755&lng=127.11623345523063&dlevel=10&enc=b64
      // `,
      "nmap://place?lat=37.275296997802755&lng=127.11623345523063&name=모임장소&appname=com.mjubus.mbus",
      // `navermap://?pinType=place&pinId=2080133&x=127.1052141&y=37.3596061`,
    );
  }

  return (
    <Container>
      <ContextContainer>
        <ContextTitle>모집 내용</ContextTitle>
        <ContextInsideContainer>
          <ContextMiniContainer>
            <ContextTexts>탑승지</ContextTexts>
            <ContextApiText>
              {TaxiDetailData.basicsData.startingPoint}
            </ContextApiText>
          </ContextMiniContainer>
          <ContextMiniContainer>
            <ContextTexts>하차지</ContextTexts>
            <ContextApiText>
              {TaxiDetailData.basicsData.endingPoint}
            </ContextApiText>
          </ContextMiniContainer>
          <ContextMiniContainer>
            <ContextTexts>모집인원</ContextTexts>
            <ContextApiText>
              {TaxiDetailData.basicsData.recruitingMemberNumber}명 (최소{" "}
              {TaxiDetailData.basicsData.minMemberNumber}명)
            </ContextApiText>
          </ContextMiniContainer>
          <ContextMiniContainer>
            <ContextTexts>결제 방법</ContextTexts>
            <ContextApiText>만나서 N빵</ContextApiText>
          </ContextMiniContainer>
        </ContextInsideContainer>
      </ContextContainer>
      <StartingPointContainer>
        <StartingPointTitle>탑승지 상세 정보</StartingPointTitle>
        <DateContainer>
          <DateText>
            {TaxiDetailData.date.year}년 {ZeroFunc(TaxiDetailData.date.month)}
            {TaxiDetailData.date.month}월 {ZeroFunc(TaxiDetailData.date.days)}
            {TaxiDetailData.date.days}일 {TaxiDetailData.date.day}요일
          </DateText>
          <TimeText>
            {TaxiDetailData.time.moring ? "오전" : "오후"}{" "}
            {ZeroFunc(TaxiDetailData.time.hours)}
            {TaxiDetailData.time.hours}:{ZeroFunc(TaxiDetailData.time.mins)}
            {TaxiDetailData.time.mins}
          </TimeText>
        </DateContainer>
        <MapContainer>
          <MyMap />
        </MapContainer>
        <AddressContainer>
          <AddressText>{TaxiDetailData.startingAddress.address}</AddressText>
          <IconTextContainer>
            <Icon>
              <MaterialIcons name="location-on" size={24} color="#959595" />
            </Icon>
            <AddressDetailText onPress={gotoWebPage}>
              {TaxiDetailData.startingAddress.detailAddress}
            </AddressDetailText>
          </IconTextContainer>
        </AddressContainer>
      </StartingPointContainer>
    </Container>
  );
}
export default Detail;
