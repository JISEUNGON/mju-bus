import { MaterialCommunityIcons } from "@expo/vector-icons";

import React from "react";
import { defined } from "react-native-reanimated";
import styled from "styled-components";


function Profile() {
  return (
    <MaterialCommunityIcons 
        name = "account-circle" size={25} color="#576F72" />
  );
}

export default Profile;