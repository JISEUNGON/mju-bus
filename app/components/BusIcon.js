import { Ionicons } from "@expo/vector-icons";

import React from "react";
import styled from "styled-components";

const Circle = styled.View`
  width: 50px;
  height: 50px;
  border-radius: 25px;
  justify-content: center;
  align-items: center;
  background-color: #dad8fb;
  margin-right: 20px;
`;
const Board = styled.View`
  background-color: white;
  width: 20px;
  height: 22px;
  position: absolute;
`;

function BusIcon() {
  return (
    <Circle>
      <Board />
      <Ionicons name="ios-bus" size={30} color="#7974E7" />
    </Circle>
  );
}

export default BusIcon;
