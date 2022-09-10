/* eslint-disable react/prop-types */
import React, { useEffect, useState, useRef } from "react";
import { Text, TouchableOpacity } from "react-native";
import styled from "styled-components/native";
import { Entypo } from "@expo/vector-icons";
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
  padding-left: 32px;
  padding-right: 32px;
`;

const ResultContainer = styled.View`
  background-color: white;
  height: 100%;
  width: 30px;
`;

const InfoContainer = styled.View`
  background-color: white;
  height: 100%;
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
  height: 118px;
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
  flex: 1;
  background-color: white;

  flex-direction: row;
  align-items: center;
`;

const MidContainer = styled.View`
  flex: 1;
  background-color: white;

  flex-direction: row;
  align-items: center;

  margin-left: 10px;
`;

const ReduceContainer = styled.View`
  flex: 1;
  background-color: white;

  flex-direction: row;
  align-items: center;

  margin-left: 10px;
`;

const RouteContainer = styled.View`
  flex: 1;
  background-color: white;

  flex-direction: column;
  align-items: flex-start;

  margin-left: 10px;

  height: auto;
`;

const MixText = styled.View`
  flex: 1;
  background-color: white;

  flex-direction: row;
  align-items: center;
`;

const EndContainer = styled.View`
  flex: 1;
  background-color: white;

  flex-direction: row;
  align-items: center;
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
  const { buslist } = props;

  const nameList = buslist.map(name => <Station>{name}</Station>);

  return <RouteContainer>{nameList}</RouteContainer>;
}

function ReduceList(props) {
  const { totaltime, arrivlatime, type, start, end, num, time, buslist } =
    props;

  const [visible, setVisible] = useState(true);

  return (
    <Container>
      <Topcontainer>
        <TotalTime>{totaltime}</TotalTime>
        <ArrivalTime>{arrivlatime}</ArrivalTime>
      </Topcontainer>
      <Bottomontainer>
        <ResultContainer>
          <BusRoute
            type={type}
            num={buslist.length}
            canexpand
            visible={visible}
          />
        </ResultContainer>
        <InfoContainer>
          <StartContainer>
            <Station>{start}</Station>
          </StartContainer>
          <MidContainer>
            <BusDetail busRoute={type} busNumber={num} time={time} />
          </MidContainer>
          <ReduceContainer>
            <Text>5분, {buslist.length}개 정류장 이동</Text>
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
          </ReduceContainer>
          {visible && <RouteList buslist={buslist} />}

          <EndContainer>
            <Station>{end}</Station>
          </EndContainer>
        </InfoContainer>
      </Bottomontainer>
    </Container>
  );
}

function BusInfoList(props) {
  // eslint-disable-next-line react/prop-types
  const { totaltime, arrivlatime, type, start, end, num, time, buslist } =
    props;

  const { canexpand } = props;

  if (canexpand) {
    return (
      <ReduceList
        totaltime={totaltime}
        arrivlatime={arrivlatime}
        type={type}
        start={start}
        end={end}
        num={num}
        time={time}
        buslist={buslist}
      />
    );
  }

  return (
    <Container>
      <Topcontainer>
        <TotalTime>{totaltime}</TotalTime>
        <ArrivalTime>{arrivlatime}</ArrivalTime>
      </Topcontainer>
      <Bottomontainer>
        <ResultContainer>
          <BusRoute type={type} num={-1} canexpand={false} visible={false} />
        </ResultContainer>
        <InfoContainer>
          <StartContainer>
            <Station>{start}</Station>
          </StartContainer>
          <MidContainer>
            <BusDetail busRoute={type} busNumber={num} time={time} />
            <Entypo name="chevron-small-right" size={24} color="gray" />
          </MidContainer>
          <EndContainer>
            <Station>{end}</Station>
          </EndContainer>
        </InfoContainer>
      </Bottomontainer>
    </Container>
  );
}

export default BusInfoList;
