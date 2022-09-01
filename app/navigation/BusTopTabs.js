import React from "react";
import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";
import FromSchool from "../screens/Bus/FromSchool";
import ToSchool from "../screens/Bus/ToSchool";

const Tab = createMaterialTopTabNavigator();

function BusTopTabs() {
  return (
    <Tab.Navigator
      screenOptions={{
        tabBarIndicatorStyle: {
          backgroundColor: "black",
        },
        tabBarLabelStyle: {
          fontSize: 13,
          fontFamily: "SpoqaHanSansNeo-Bold",
        },
        tabBarActiveTintColor: "black",
        tabBarInactiveTintColor: "#6D7582",
      }}
    >
      <Tab.Screen name="학교로" component={ToSchool} />
      <Tab.Screen name="학교에서" component={FromSchool} />
    </Tab.Navigator>
  );
}

export default BusTopTabs;
