import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";
import React from "react";
import SineShuttle from "../screens/Schedule/SineShuttle";
import SiweShuttle from "../screens/Schedule/SiweShuttle";

const Tab = createMaterialTopTabNavigator();

function ScheduleTopTabs() {
  return (
    <Tab.Navigator
      screenOptions={{
        tabBarLabelStyle: {
          fontSize: 13,
          fontFamily: "SpoqaHanSansNeo-Medium",
        },
      }}
      sceneContainerStyle={{}}
    >
      <Tab.Screen name="시내 셔틀" component={SineShuttle} />
      <Tab.Screen name="시외 셔틀" component={SiweShuttle} />
    </Tab.Navigator>
  );
}

export default ScheduleTopTabs;
