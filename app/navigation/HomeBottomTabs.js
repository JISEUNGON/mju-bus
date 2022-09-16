import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { Ionicons } from "@expo/vector-icons";
import { Text, View } from "react-native";
import Home from "../screens/Home/Home";
import ScheduleTopTabs from "./ScheduleTopTabs";
import BusTopTabs from "./BusTopTabs";

const Tab = createBottomTabNavigator();

function EmptyText() {
  return (
    <View>
      <Text />
    </View>
  );
}

function HomeBottomTabs() {
  return (
    <Tab.Navigator
      sceneContainerStyle={{
        backgroundColor: "#F2F4F6",
      }}
      screenOptions={{
        tabBarActiveTintColor: "black",
        tabBarInactiveTintColor: "gray",
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
          header: () => (
            <View>
              <Text>aaaa</Text>
            </View>
          ),
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
          headerStatusBarHeight: 0,
          headerShadowVisible: false,
          headerTitle: EmptyText,
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
          headerStatusBarHeight: 0,
          headerShadowVisible: false,
          headerTitle: EmptyText,
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
