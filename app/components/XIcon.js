import { Ionicons } from "@expo/vector-icons";
import React from "react";
import styled from "styled-components";

const Circle = styled.View`
  width: 34px;
  height: 34px;
  border-radius: 17px;
  justify-content: center;
  align-items: center;
  background-color: #f7e3e3;
`;

function XIcon() {
  return (
    <Circle>
      <Ionicons name="md-close" size={25} color="#EC6969" />
    </Circle>
  );
}

export default XIcon;
