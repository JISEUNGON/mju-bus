/* eslint-disable react/prop-types */
import React from "react";
import styled, { ThemeProvider } from "styled-components";
import { SineRunning, SiweRunning } from "../styled";

const Labelbackground = styled.View`
  align-items: center;
  justify-content: center;
  width: ${props => {
    if (props.size === "small") return 60;
    return 85;
  }}px;
  height: ${props => {
    if (props.size === "small") return 25;
    return 35;
  }}px;
  background-color: ${props => {
    if (props.activated === 1) return props.theme.beforeBgColor;
    if (props.activated === 2) return props.theme.activatedBgColor;
    if (props.activated === 3) return props.theme.excitedBgColor;
    return props.theme.defaultBgColor;
  }};
  border-radius: 4px;
`;

const LabelText = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: ${props => {
    if (props.size === "small") return 11;
    return 13;
  }}px;
  color: ${props => {
    if (props.activated === 1) return props.theme.beforeTextColor;
    if (props.activated === 2) return props.theme.activatedTextColor;
    if (props.activated === 3) return props.theme.excitedTextColor;
    return props.theme.defaultTextColor;
  }};
`;

function contents(busRoute, activate, time) {
  /**
   * activate === 1 : 운행 전
   * activate === 2: 운행 중
   * activate === 3: 운행 종료
   */
  if (busRoute === "sine") {
    if (activate === 1) {
      return "운행 전";
    }
    if (activate === 2) {
      return "운행 중";
    }
    if (activate === 3) {
      return "운행 종료";
    }
    return "시내 셔틀";
  }
  if (busRoute === "siwe") {
    return time;
  }
  return "빨간 버스";
}

function Label({ busRoute, activate, time, size }) {
  return (
    <ThemeProvider theme={busRoute === "sine" ? SineRunning : SiweRunning}>
      <Labelbackground activated={activate} size={size}>
        <LabelText activated={activate} size={size}>
          {contents(busRoute, activate, time)}
        </LabelText>
      </Labelbackground>
    </ThemeProvider>
  );
}

export default Label;
