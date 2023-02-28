import React, { useContext } from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { Ionicons, MaterialCommunityIcons } from "@expo/vector-icons";
import { Text, useColorScheme, View } from "react-native";
import Home from "../screens/Home/Home";
import ScheduleTopTabs from "./ScheduleTopTabs";
import BusTopTabs from "./BusTopTabs";
import TogetherTopTabs from "./TogetherTopTabs";
import ViewMore from "../screens/ViewMore";
import { BLACK_COLOR, DARK_GRAY, WHITE_COLOR } from "../colors";
import TaxiTabs from "./TaxiDetailTabs";
import Chatting from "../screens/Taxi/Chatting";
import { MBAContext } from "./Root";

const Tab = createBottomTabNavigator();

function EmptyText() {
  return (
    <View>
      <Text />
    </View>
  );
}

function HomeBottomTabs() {
  const {   
    sineBusList,
    siweBusList,
    mjuCalendar,
    stationList, } = useContext(MBAContext);

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

      {/* <Tab.Screen
        name="같이 해요"
        component={TogetherTopTabs}
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
              name={focused ? "people-sharp" : "people-outline"}
              color={color}
              size={size}
            />
          ),
        }}
      /> */}

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

      <Tab.Screen
        name="더 보기"
        component={ViewMore}
        options={{
          headerStyle: {
            backgroundColor: isDark ? BLACK_COLOR : WHITE_COLOR,
          },
          headerStatusBarHeight: 0,
          headerTitle: EmptyText,
          headerShadowVisible: false,
          // eslint-disable-next-line react/no-unstable-nested-components
          tabBarIcon: ({ focused, color, size }) => (
            <MaterialCommunityIcons
              name={
                focused
                  ? "dots-horizontal-circle"
                  : "dots-horizontal-circle-outline"
              }
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
