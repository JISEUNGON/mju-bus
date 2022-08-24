import React from "react";
import styled from "styled-components/native";
import { Entypo } from "@expo/vector-icons";
import { Text } from "react-native";
import BusRoute from "./busResult";
import Label from "../Label";
import Timer from "../Timer";

const Container = styled.View`
  background-color: white;
  flex-direction: column;
  border-bottom-width: 0.9px;
  border-top-color: #d3d7dc;
  border-bottom-color: #d3d7dc;
  height: 170px;
  padding-left: 32px;
  padding-right: 32px;
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
  flex-direction: column;
`;

// 총 소요시간
const TotalTime = styled.Text`
  color: #353c49;
  font-weight: bold;
  font-size: 19px;
`;

// 도착 예정 시간
const ArrivalTime = styled.Text`
  margin-left: 7px;
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

const Title = styled.Text`
  color: #747c88;
  font-size: 12.5px;

  margin-left: 10px;
`;

const NextButton = styled.TouchableOpacity`
  background-color: white;
  height: 20px;

  margin-right: 0px;
`;

function BusInfoList(props) {
  return (
    <Container>
      <Topcontainer>
        <TotalTime>{props.totaltime}</TotalTime>
        <ArrivalTime>{props.arrivlatime}</ArrivalTime>
      </Topcontainer>
      <Bottomontainer>
        <StartContainer>
          <BusRoute name="start" type={props.type} />
          <Title>{props.start}</Title>
        </StartContainer>
        <MidContainer>
          <MixText>
            <BusRoute name="line" type="" />
            <Label busRoute={props.type} size="small" />
            <Text>
              <Timer value={props.time} />
            </Text>
          </MixText>

          <NextButton>
            <Entypo name="chevron-small-right" size={24} color="gray" />
          </NextButton>
        </MidContainer>
        <EndContainer>
          <BusRoute name="end" type={props.type} />
          <Title>{props.end}</Title>
        </EndContainer>
      </Bottomontainer>
    </Container>
  );
}

export default BusInfoList;
