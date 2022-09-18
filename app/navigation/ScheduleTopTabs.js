import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";
import React from "react";
import { useColorScheme } from "react-native";
import { BLACK_COLOR, LIGHT_GRAY, WHITE_COLOR } from "../colors";
import SineShuttle from "../screens/Schedule/SineShuttle";
import SiweShuttle from "../screens/Schedule/SiweShuttle";

const Tab = createMaterialTopTabNavigator();

function ScheduleTopTabs() {
  const isDark = useColorScheme() === "dark";

  return (
    <Tab.Navigator
      screenOptions={{
        tabBarStyle: {
          backgroundColor: isDark ? BLACK_COLOR : WHITE_COLOR,
        },
        tabBarIndicatorStyle: {
          backgroundColor: isDark ? WHITE_COLOR : BLACK_COLOR,
        },
        tabBarLabelStyle: {
          fontSize: 13,
          fontFamily: "SpoqaHanSansNeo-Bold",
        },

        tabBarActiveTintColor: isDark ? WHITE_COLOR : BLACK_COLOR,
        tabBarInactiveTintColor: LIGHT_GRAY,
      }}
      sceneContainerStyle={{}}
    >
      <Tab.Screen name="시내 셔틀" component={SineShuttle} />
      <Tab.Screen name="시외 셔틀" component={SiweShuttle} />
    </Tab.Navigator>
  );
}

export default ScheduleTopTabs;
