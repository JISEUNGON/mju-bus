/* eslint-disable react/prop-types */
import React from "react";
import styled, { ThemeProvider } from "styled-components";
import { SineRunning, SiweRunning } from "../styled";

const Labelbackground = styled.View`
  align-items: center;
  justify-content: center;
  width: 85px;
  height: 35px;
  background-color: ${props => {
    if (props.activated === 1) return props.theme.beforeBgColor;
    if (props.activated === 2) return props.theme.activatedBgColor;
    return props.theme.excitedBgColor;
  }};
  border-radius: 4px;
`;

const LabelText = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 13px;
  color: ${props => {
    if (props.activated === 1) return props.theme.beforeTextColor;
    if (props.activated === 2) return props.theme.activatedTextColor;
    return props.theme.excitedTextColor;
  }};
`;

function contents(busRoute, activate, time) {
  /**
   * activate === 1 : 운행 정
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
    return "운행 종료";
  }
  return time;
}

function Label({ busRoute, activate, time }) {
  return (
    <ThemeProvider theme={busRoute === "sine" ? SineRunning : SiweRunning}>
      <Labelbackground activated={activate}>
        <LabelText activated={activate}>
          {contents(busRoute, activate, time)}
        </LabelText>
      </Labelbackground>
    </ThemeProvider>
  );
}

export default Label;
