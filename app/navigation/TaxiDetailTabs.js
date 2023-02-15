import React, { useContext } from "react";
import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";
import Chat from "../screens/Taxi/Chat";
import Detail from "../screens/Taxi/Detail";
import Member from "../screens/Taxi/Member";
import styled from "styled-components/native";
import TaxiHeader from "../screens/Taxi/TaxiHeader";
import { TaxiChatContext } from "../screens/Taxi/Taxicontext";
import Chatting from "../screens/Taxi/Chatting";
const Tab = createMaterialTopTabNavigator();

const SafeContainer = styled.SafeAreaView``;

function TaxiTabs() {
  const { join } = useContext(TaxiChatContext);

  return (
    <>
      <SafeContainer
        edges={["top"]}
        style={{ flex: 0.01, backgroundColor: "white" }}
      />

      <TaxiHeader />

      <Tab.Navigator
        initialRouteName="상세 정보"
        initialLayout={{ height: 190 }}
        screenOptions={{
          tabBarLabelStyle: {
            fontSize: 15,
            fontFamily: "SpoqaHanSansNeo-Medium",
          },
        }}
      >
        <Tab.Screen name="상세 정보" component={Detail} />
        <Tab.Screen name="참석 인원" component={Member} />
        {join ? (
          <Tab.Screen name="채팅" component={Chatting} />
        ) : (
          <Tab.Screen name="채팅" component={Chat} />
        )}
      </Tab.Navigator>
    </>
  );
}

export default TaxiTabs;
