import React from "react";
import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";
import { useColorScheme } from "react-native";
import FromSchool from "../screens/Bus/FromSchool";
import ToSchool from "../screens/Bus/ToSchool";
import { BLACK_COLOR, DARK_GRAY, LIGHT_GREY, WHITE_COLOR } from "../colors";

const Tab = createMaterialTopTabNavigator();

function BusTopTabs() {
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
        tabBarInactiveTintColor: LIGHT_GREY,
      }}
    >
      <Tab.Screen name="학교로" component={ToSchool} />
      <Tab.Screen name="학교에서" component={FromSchool} />
    </Tab.Navigator>
  );
}

export default BusTopTabs;
