import React from "react";
import styled from "styled-components";
import { Ionicons } from "@expo/vector-icons";
import StationIcon from "../StationIcon";
import BusIcon from "../BusIcon";

const Container = styled.View`
  background-color: white;

  height: 100%;
  width: 30px;

  justify-content: center;
  align-items: center;
`;

const MidLine = styled.View`
  background-color: #c1c6cd;

  height: 100%;
  width: 10%;
`;

const StartLine = styled.View`
  background-color: #c1c6cd;

  height: 100%;
  width: 10%;

  margin-top: 50%;
`;

const EndLine = styled.View`
  background-color: #c1c6cd;

  height: 100%;
  width: 10%;

  margin-bottom: 100%;
`;

const StartCity = styled.View`
  background-color: #f6e3e3;
  border-radius: 50px;

  height: 25px;
  width: 25px;

  margin-top: 8px;
  position: absolute;

  justify-content: center;
  align-items: center;
`;

const Startshuttle = styled.View`
  background-color: #dad8fb;
  border-radius: 50px;

  height: 25px;
  width: 25px;

  margin-top: 8px;
  position: absolute;

  justify-content: center;
  align-items: center;
`;

const BusImage = styled.Image`
  position: absolute;

  height: 15px;
  width: 15px;
`;

const EndCity = styled.View`
  background-color: #f6e3e3;
  border-radius: 50px;

  height: 10px;
  width: 10px;

  justify-content: center;
  align-items: center;

  position: absolute;
`;

const EndShuttle = styled.View`
  background-color: #dad8fb;
  border-radius: 50px;

  height: 10px;
  width: 10px;

  justify-content: center;
  align-items: center;

  position: absolute;
`;

const EndCircle = styled.View`
  background-color: white;
  border-radius: 50px;

  height: 5px;
  width: 5px;
`;

function BusRoute(props) {
  const string = props.name;
  const { type } = props;

  // 중간 선
  if (string == "line") {
    return (
      <Container>
        <MidLine />
      </Container>
    );
    // 시작 선
  }
  if (string == "start") {
    // 서틀버스 시작 점
    if (type == "red") {
      return (
        <Container>
          <StartLine />
          <BusIcon busRoute="red" size="small" absolute />
        </Container>
      );
    }
    return (
      <Container>
        <StartLine />
        <BusIcon busRoute="sine" size="small" absolute />
      </Container>
    );

    // 끝 점
  }
  if (type == "red") {
    return (
      <Container>
        <EndLine />
        <StationIcon busRoute="red" absolute />
      </Container>
    );
  }
  return (
    <Container>
      <EndLine />
      <StationIcon busRoute="sine" absolute />
    </Container>
  );
}

export default BusRoute;
