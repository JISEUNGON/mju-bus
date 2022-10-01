/* eslint-disable react/prop-types */
import { Ionicons } from "@expo/vector-icons";
import React from "react";
import { Dimensions } from "react-native";
import styled from "styled-components";

const { height: SCREEN_HEIGHT } = Dimensions.get("window");

const Circle = styled.View`
  width: 100px;
  height: 100px;
  border-radius: 50px;
  justify-content: center;
  align-items: center;
  background-color: #dad8fb;
`;

const Board = styled.View`
  background-color: white;
  width: 35px;
  height: 36px;
  position: absolute;
`;

const Container = styled.View`
  background-color: ${props => props.theme.homeBgColor};
  flex: 1;
`;

const IconContainer = styled.View`
  height: ${SCREEN_HEIGHT / 2 + 50}px;
  align-items: center;
  justify-content: flex-end;
`;

const TextContainer = styled.View`
  height: ${SCREEN_HEIGHT / 2 - 50}px;
  align-items: center;
  justify-content: flex-end;
  padding-bottom: 50px;
`;

const UpdateText = styled.Text`
  color: ${props => props.theme.subTextColor};
  font-size: 15px;
`;

// eslint-disable-next-line react/prop-types
function SyncProgressView({ syncProgress }) {
  return (
    <Container>
      <IconContainer>
        <Circle>
          <Board />
          <Ionicons name="ios-bus" size={50} color="#7974E7" />
        </Circle>
      </IconContainer>
      <TextContainer>
        <UpdateText>필수 업데이트 진행 중입니다 ...</UpdateText>
      </TextContainer>
    </Container>
  );
}

export default SyncProgressView;
