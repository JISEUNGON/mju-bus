import styled from "styled-components";
import React from "react";
import { Dimensions } from "react-native";

const { width: SCREEN_WIDTH } = Dimensions.get("window");
const ROUTE_WIDTH = (SCREEN_WIDTH - 40) * 0.9 - 75;
const ROUTE_END_POINT = (SCREEN_WIDTH - 40) * 0.9 - 9;
const ROUTE_ROUND_POINT = ROUTE_WIDTH + (ROUTE_END_POINT - ROUTE_WIDTH) * 0.4;

const StationOuterImage = styled.View`
  position: ${props => {
    if (props.absolute === true) return "absolute";
    return "static";
  }};
  background-color: ${props => {
    if (props.busRoute === "sine") return "#DAD8FB";
    return "#F6E3E3";
  }};
  width: 12px;
  height: 12px;
  border-radius: 9px;
  align-items: center;
  justify-content: center;
  margin-top: ${props => {
    if (props.level === 0) {
      return -7;
    }
    if (props.level === 1) {
      return 1;
    }
    if (props.level === 2) {
      return 65;
    }
    if (props.level === 3) {
      return 132;
    }
    if (props.level === 4) {
      return 141;
    }
    if (props.busRoute === "siwe") {
      return 23;
    }
    return 0;
  }}px;
  margin-left: ${props => {
    if (props.level === 2) {
      return ROUTE_END_POINT + 2;
    }
    if (props.level === 1 || props.level === 3) {
      return ROUTE_ROUND_POINT;
    }
    if (props.level === 0 || props.level === 4) {
      return props.value;
    }
    return 0;
  }}px;
`;

const StationInnerImage = styled.View`
  background-color: white;
  width: 6px;
  height: 6px;
  border-radius: 4px;
`;

// eslint-disable-next-line react/prop-types
function StationIcon({ busRoute, value, level, absolute }) {
  return (
    <StationOuterImage
      busRoute={busRoute}
      value={value}
      level={level}
      absolute={absolute}
    >
      <StationInnerImage />
    </StationOuterImage>
  );
}

export default StationIcon;
