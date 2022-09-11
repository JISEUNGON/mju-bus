/* eslint-disable react/prop-types */
import React, { useEffect, useState, useRef } from "react";
import { Text, TouchableOpacity, View } from "react-native";
import styled from "styled-components/native";
import { createIconSetFromFontello, Entypo } from "@expo/vector-icons";
import BusRoute from "./BusRoute";
import Label from "../Label";
import Timer from "../Timer";

const Container = styled.View`
  background-color: white;
  flex-direction: column;
  border-bottom-width: 0.9px;
  border-top-color: #d3d7dc;
  border-bottom-color: #d3d7dc;
  height: auto;
  width: 100%;

  padding-left: 32px;
  padding-right: 32px;
  padding-top: 10px;
  padding-bottom: 10px;
`;

const InfoContainer = styled.View`
  background-color: white;
  height: auto;
  width: 100%;
`;

const Topcontainer = styled.View`
  background-color: white;

  height: 50px;
  border-bottom-width: 1.5px;
  border-bottom-color: #d3d7dc;

  flex-direction: row;
  align-items: flex-end;

  padding-bottom: 9px;
`;

const Bottomontainer = styled.View`
  background-color: white;
  height: auto;
  flex-direction: row;

  padding-right: 32px;
`;

// 총 소요시간
const TotalTime = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  margin-left: 7px;
  color: #353c49;
  font-size: 19px;
`;

// 도착 예정 시간
const ArrivalTime = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  margin-left: 10px;
  color: #747c88;
  font-size: 13px;
`;

const StartContainer = styled.View`
  background-color: white;

  flex-direction: row;
  align-items: center;
  height: 35px;
`;

const MidContainer = styled.View`
  background-color: white;

  flex-direction: row;
  align-items: center;

  height: 30px;
`;

const RouteContainer = styled.View`
  flex: 1;
  background-color: white;

  flex-direction: column;
  align-items: flex-start;

  margin-left: 10px;

  height: 30px;
`;

const MixText = styled.View`
  flex: 1;
  background-color: white;

  flex-direction: row;
  align-items: center;

  margin-left: 8px;
`;

const EndContainer = styled.View`
  background-color: white;

  flex-direction: row;
  align-items: center;

  height: 30px;
`;

const Station = styled.Text`
  color: #747c88;
  font-size: 12.5px;
  font-family: "SpoqaHanSansNeo-Medium";
  margin-left: 10px;
`;

const BusNumber = styled.Text`
  margin-left: 10px;
  font-family: "SpoqaHanSansNeo-Bold";
`;

const TimerText = styled.Text`
  margin-left: 10px;
  color: gray;
  font-size: 12px;
  font-family: "SpoqaHanSansNeo-Medium";
`;

// eslint-disable-next-line react/prop-types
function BusDetail({ busRoute, busNumber, time }) {
  if (busRoute === "sine") {
    return (
      <MixText>
        <Label busRoute={busRoute} size="small" />
        <TimerText>
          <Timer value={time} />
        </TimerText>
      </MixText>
    );
  }
  return (
    <MixText>
      <Label busRoute={busRoute} size="small" />
      <BusNumber>{busNumber}</BusNumber>
      <TimerText>
        <Timer value={time} />
      </TimerText>
    </MixText>
  );
}

function RouteList(props) {
  const { stationlist } = props;

  const nameList = stationlist.map(name => (
    <MidContainer>
      <BusRoute location="mid" />
      <Station>{name.name}</Station>
    </MidContainer>
  ));

  return <View>{nameList}</View>;
}

function ReduceList(props) {
  const {
    totaltime,
    arrivlatime,
    departtime,
    type,
    start,
    end,
    num,
    time,
    stationlist,
    Stationnum,
  } = props;

  const [visible, setVisible] = useState(false);

  function WaitTime(total, remain) {
    const temp = remain % 60 > 30 ? remain / 60 + 1 : remain / 60;

    const result = Math.floor(temp - total);
    return result;
  }

  return (
    <Container>
      <Topcontainer>
        <TotalTime>{totaltime}분</TotalTime>
        <ArrivalTime>{departtime}</ArrivalTime>
        <ArrivalTime>~</ArrivalTime>
        <ArrivalTime>{arrivlatime}</ArrivalTime>
      </Topcontainer>
      <Bottomontainer>
        <InfoContainer>
          <StartContainer>
            <BusRoute type={type} visible={visible} location="start" />
            <Station>{start}</Station>
          </StartContainer>
          <MidContainer>
            <BusRoute type={type} visible={visible} location="mid" />
            <BusDetail busRoute={type} busNumber={num} time={time} />
          </MidContainer>
          <MidContainer>
            <BusRoute type={type} location="mid" />
            <Station>
              {WaitTime(totaltime, time)}분, {Stationnum}개 정류장 이동
            </Station>
            <TouchableOpacity
              onPress={() => {
                setVisible(!visible);
              }}
            >
              {visible === true ? (
                <Entypo name="chevron-small-up" size={24} color="gray" />
              ) : (
                <Entypo name="chevron-small-down" size={24} color="gray" />
              )}
            </TouchableOpacity>
          </MidContainer>
          {visible === true && <RouteList stationlist={stationlist} />}
          <EndContainer>
            <BusRoute type={type} visible={visible} location="end" />
            <Station>{end}</Station>
          </EndContainer>
        </InfoContainer>
      </Bottomontainer>
    </Container>
  );
}

function BusInfoList(props) {
  // eslint-disable-next-line react/prop-types
  const {
    totaltime,
    arrivlatime,
    departtime,
    type,
    start,
    end,
    num,
    time,
    stationlist,
  } = props;

  const { canexpand } = props;

  if (canexpand) {
    // 마지막 정류장 재거 작업
    const lastname = stationlist[stationlist.length - 1].name;
    const StationNum = stationlist.length;

    // 마지막 정류장 제거 작업
    stationlist.pop();

    return (
      <ReduceList
        totaltime={totaltime}
        arrivlatime={arrivlatime}
        departtime={departtime}
        type={type}
        start={start}
        end={lastname}
        num={num}
        time={time}
        stationlist={stationlist}
        Stationnum={StationNum}
      />
    );
  }

  return (
    <Container>
      <Topcontainer>
        <TotalTime>{totaltime}분</TotalTime>
        <ArrivalTime>{departtime}</ArrivalTime>
        <ArrivalTime>~</ArrivalTime>
        <ArrivalTime>{arrivlatime}</ArrivalTime>
      </Topcontainer>
      <Bottomontainer>
        <InfoContainer>
          <StartContainer>
            <BusRoute type={type} location="start" />
            <Station>{start}</Station>
          </StartContainer>
          <MidContainer>
            <BusRoute type={type} location="mid" />
            <BusDetail busRoute={type} busNumber={num} time={time} />
            <Entypo name="chevron-small-right" size={24} color="gray" />
          </MidContainer>
          <EndContainer>
            <BusRoute type={type} location="end" />
            <Station>{end}</Station>
          </EndContainer>
        </InfoContainer>
      </Bottomontainer>
    </Container>
  );
}

export default BusInfoList;
