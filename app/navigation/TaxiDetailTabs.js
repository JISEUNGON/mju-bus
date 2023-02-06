import React, { useEffect, useState, useContext } from "react";
import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";
import Chat from "../screens/Taxi/Chat";
import Detail from "../screens/Taxi/Detail";
import Member from "../screens/Taxi/Member";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import styled from "styled-components/native";
import TaxiHeader from "../screens/Taxi/TaxiHeader";

import { createNativeStackNavigator } from "@react-navigation/native-stack";

const Tab = createMaterialTopTabNavigator();

const SafeContainer = styled.SafeAreaView``;

function TaxiTabs() {
  return (
    <>
      <SafeContainer
        edges={["top"]}
        style={{ flex: 0.01, backgroundColor: "white" }}
      />

      <TaxiHeader />

      <Tab.Navigator
        initialLayout={{ height: 190 }}
        screenOptions={{ tabBarLabelStyle: { fontWeight: "900" } }}
      >
        <Tab.Screen name="상세 정보" component={Detail} />
        <Tab.Screen name="참석 인원" component={Member} />
        <Tab.Screen name="채팅" component={Chat} />
      </Tab.Navigator>
    </>
  );
}

export default TaxiTabs;
