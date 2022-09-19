import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { Ionicons } from "@expo/vector-icons";
import { Text, useColorScheme, View } from "react-native";
import Home from "../screens/Home/Home";
import ScheduleTopTabs from "./ScheduleTopTabs";
import BusTopTabs from "./BusTopTabs";
import { BLACK_COLOR, DARK_GRAY, WHITE_COLOR } from "../colors";

const Tab = createBottomTabNavigator();

function EmptyText() {
  return (
    <View>
      <Text />
    </View>
  );
}

function HomeBottomTabs() {
  const isDark = useColorScheme() === "dark";
  return (
    <Tab.Navigator
      sceneContainerStyle={{
        backgroundColor: isDark ? BLACK_COLOR : "#F2F4F6",
      }}
      screenOptions={{
        tabBarStyle: {
          backgroundColor: isDark ? BLACK_COLOR : WHITE_COLOR,
          borderTopColor: isDark ? "gray" : "white",
          borderTopWidth: 0.2,
        },

        tabBarActiveTintColor: isDark ? WHITE_COLOR : BLACK_COLOR,
        tabBarInactiveTintColor: isDark ? WHITE_COLOR : DARK_GRAY,
        tabBarLabelStyle: {
          fontSize: 10,
          fontWeight: "600",
          marginTop: -5,
          fontFamily: "SpoqaHanSansNeo-Bold",
        },
      }}
    >
      <Tab.Screen
        name="홈"
        component={Home}
        options={{
          headerShown: false,
          // eslint-disable-next-line react/no-unstable-nested-components
          tabBarIcon: ({ focused, color, size }) => (
            <Ionicons
              name={focused ? "home-sharp" : "home-outline"}
              color={color}
              size={size}
            />
          ),
        }}
      />

      <Tab.Screen
        name="시내 셔틀"
        component={BusTopTabs}
        options={{
          headerStyle: {
            backgroundColor: isDark ? BLACK_COLOR : WHITE_COLOR,
          },
          headerStatusBarHeight: 0,
          headerTitle: EmptyText,
          headerShadowVisible: false,
          // eslint-disable-next-line react/no-unstable-nested-components
          tabBarIcon: ({ focused, color, size }) => (
            <Ionicons
              name={focused ? "bus-sharp" : "bus-outline"}
              color={color}
              size={size}
            />
          ),
        }}
      />
      <Tab.Screen
        name="시간표"
        component={ScheduleTopTabs}
        options={{
          headerStyle: {
            backgroundColor: isDark ? BLACK_COLOR : WHITE_COLOR,
          },
          headerStatusBarHeight: 0,
          headerTitle: EmptyText,
          headerShadowVisible: false,
          // eslint-disable-next-line react/no-unstable-nested-components
          tabBarIcon: ({ focused, color, size }) => (
            <Ionicons
              name={focused ? "calendar-sharp" : "calendar-outline"}
              color={color}
              size={size}
            />
          ),
        }}
      />
    </Tab.Navigator>
  );
}

export default HomeBottomTabs;
