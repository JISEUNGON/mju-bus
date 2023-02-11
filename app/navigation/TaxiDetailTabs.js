import React, { useEffect, useState, useContext } from "react";
import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";
import Chat from "../screens/Taxi/Chat";
import Detail from "../screens/Taxi/Detail";
import Member from "../screens/Taxi/Member";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import styled from "styled-components/native";
import TaxiHeader from "../screens/Taxi/TaxiHeader";
import { TaxiChatContext } from "../screens/Taxi/Taxicontext";
import { AntDesign } from "@expo/vector-icons";
import { TouchableOpacity } from "react-native";
import { NavigationContainer } from "@react-navigation/native";
import Chatting from "../screens/Taxi/Chatting";
import { View } from "react-native";

const Tab = createMaterialTopTabNavigator();

const SafeContainer = styled.SafeAreaView``;

function TaxiTabs() {
  const { goChat, setGoChat, focused, setFocuesd } =
    useContext(TaxiChatContext);
  return (
    <>
      <SafeContainer
        edges={["top"]}
        style={{ flex: 0.01, backgroundColor: "white" }}
      />
      {/* <View style={{ marginLeft: 10, width: 30 }}>
        <TouchableOpacity
          onPress={() => {
            navigation.goBack();
          }}
        >
          <AntDesign name="left" size={25} color="black" />
        </TouchableOpacity>
      </View> */}

      <TaxiHeader />

      <Tab.Navigator
        initialLayout={{ height: 190 }}
        screenOptions={{ tabBarLabelStyle: { fontWeight: "900" } }}
      >
        <Tab.Screen name="상세 정보" component={Detail} />
        <Tab.Screen name="참석 인원" component={Member} />
        {goChat ? (
          <Tab.Screen name="채팅" component={Chatting} />
        ) : (
          <Tab.Screen name="채팅" component={Chat} />
        )}
      </Tab.Navigator>
    </>
  );
}

export default TaxiTabs;
