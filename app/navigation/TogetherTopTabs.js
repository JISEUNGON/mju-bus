import React from "react";
import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";
import { useColorScheme } from "react-native";
import Taxi from "../screens/Together/Taxi";
import Delivery from "../screens/Together/Delivery";
import CarPool from "../screens/Together/CarPool";
import { BLACK_COLOR, LIGHT_GRAY, WHITE_COLOR } from "../colors";

const Tab = createMaterialTopTabNavigator();

function TogetherTopTabs() {
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
    >
      <Tab.Screen name="택시" component={Taxi} />
      <Tab.Screen name="카풀" component={CarPool} />
      <Tab.Screen name="배달" component={Delivery} />
    </Tab.Navigator>
  );
}

export default TogetherTopTabs;

