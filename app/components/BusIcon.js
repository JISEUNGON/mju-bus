import { Ionicons } from "@expo/vector-icons";

import React from "react";
import styled, { ThemeProvider } from "styled-components";
import { SineRunning, SiweRunning } from "../styled";

const Circle = styled.View`
  width: ${props => {
    if (props.size === "small") return 25;
    return 50;
  }}px;
  height: ${props => {
    if (props.size === "small") return 25;
    return 50;
  }}px;
  border-radius: 25px;
  justify-content: center;
  align-items: center;
  background-color: ${props => {
    if (props.busRoute === "sine") return "#DAD8FB";
    return "#F6E3E3";
  }};
  margin-right: 20px;
`;
const Board = styled.View`
  background-color: white;
  width: ${props => {
    if (props.size === "small") return 10;
    return 20;
  }}px;
  height: ${props => {
    if (props.size === "small") return 11;
    return 22;
  }}px;
  position: absolute;
`;

// eslint-disable-next-line react/prop-types
function BusIcon({ busRoute, size }) {
  return (
    <ThemeProvider theme={busRoute === "sine" ? SineRunning : SiweRunning}>
      <Circle busRoute={busRoute} size={size}>
        <Board size={size} />
        <Ionicons
          name="ios-bus"
          size={size === "small" ? 15 : 30}
          color={busRoute === "sine" ? "#7974E7" : "#EC6969"}
        />
      </Circle>
    </ThemeProvider>
  );
}

export default BusIcon;
