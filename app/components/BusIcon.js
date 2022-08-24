import { Ionicons } from "@expo/vector-icons";

import React from "react";
import styled from "styled-components";

const Circle = styled.View`
  position: ${props => {
    if (props.absolute === true) return "absolute";
    return "static";
  }};
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
function BusIcon({ busRoute, size, absolute }) {
  return (
    <Circle busRoute={busRoute} size={size} absolute={absolute}>
      <Board size={size} />
      <Ionicons
        name="ios-bus"
        size={size === "small" ? 15 : 30}
        color={busRoute === "sine" ? "#7974E7" : "#EC6969"}
      />
    </Circle>
  );
}

export default BusIcon;
