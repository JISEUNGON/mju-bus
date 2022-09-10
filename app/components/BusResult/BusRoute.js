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

  flex-direction: column;
`;

const TopContainer = styled.View`
  background-color: white;

  width: 30px;
  flex: 1;

  justify-content: center;
  align-items: center;
`;

const MidContainer = styled.View`
  background-color: white;

  width: 30px;
  flex: 1;

  justify-content: center;
  align-items: center;
`;

const BottomContainer = styled.View`
  background-color: white;

  width: 30px;
  flex: 1;

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

function LineList(num) {
  const rendering = () => {
    for (let i = 0; i < num.length; i++) {
      <MidLine />;
    }
    return null;
  };

  return { rendering };
}
function BusRoute(props) {
  // eslint-disable-next-line react/prop-types
  const { num, type, canexpand, visible } = props;

  // 간단히 보기 화면

  if (canexpand) {
    if (type === "red") {
      return (
        <Container>
          <TopContainer>
            <StartLine />
            <BusIcon busRoute="red" size="small" absolute />
          </TopContainer>
          <MidContainer>
            <MidLine />
            <MidLine />
          </MidContainer>
          <BottomContainer>
            <EndLine />
            <StationIcon busRoute="red" absolute />
          </BottomContainer>
        </Container>
      );
    }

    return (
      <Container>
        <TopContainer>
          <StartLine />
          <BusIcon busRoute="sine" size="small" absolute />
        </TopContainer>
        <MidContainer>
          <MidLine />
          <MidLine />
        </MidContainer>
        <BottomContainer>
          <EndLine />
          <StationIcon busRoute="sine" absolute />
        </BottomContainer>
      </Container>
    );
  }

  if (num === -1) {
    if (type === "red") {
      return (
        <Container>
          <TopContainer>
            <StartLine />
            <BusIcon busRoute="red" size="small" absolute />
          </TopContainer>
          <MidContainer>
            <MidLine />
          </MidContainer>
          <BottomContainer>
            <EndLine />
            <StationIcon busRoute="red" absolute />
          </BottomContainer>
        </Container>
      );
    }

    return (
      <Container>
        <TopContainer>
          <StartLine />
          <BusIcon busRoute="sine" size="small" absolute />
        </TopContainer>
        <MidContainer>
          <MidLine />
        </MidContainer>
        <BottomContainer>
          <EndLine />
          <StationIcon busRoute="sine" absolute />
        </BottomContainer>
      </Container>
    );
  }
}

export default BusRoute;
