import React from "react";
import styled from "styled-components";
import StationIcon from "../StationIcon";
import BusIcon from "../BusIcon";

const Container = styled.View`
  background-color: white;

  height: 100%;
  width: 30px;

  justify-content: center;
  align-items: center;
`;

const MidContainer = styled.View`
  background-color: white;

  height: 100%;
  width: 30px;

  justify-content: center;
  align-items: center;

  flex-direction: column;
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

function BusRoute(props) {
  // eslint-disable-next-line react/prop-types
  const { type, location } = props;

  if (location === "start") {
    if (type === "red") {
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
  }
  if (location === "end") {
    if (type === "red") {
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
  return (
    <MidContainer>
      <MidLine />
    </MidContainer>
  );
}

export default BusRoute;
