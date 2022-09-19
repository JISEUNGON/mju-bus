import { createNativeStackNavigator } from "@react-navigation/native-stack";
import React from "react";
import { useColorScheme } from "react-native";
import { BLACK_COLOR } from "../colors";
import Notice from "../screens/Home/Notice";

const Search = createNativeStackNavigator();

function NoticeStack() {
  const isDark = useColorScheme() === "dark";

  return (
    <Search.Navigator
      screenOptions={{
        headerShown: true,
        headerTitle: "공지사항",
        gestureEnabled: true,
      }}
    >
      <Search.Screen
        name="Notice"
        component={Notice}
        options={{
          headerTitleStyle: {
            color: isDark ? "white" : "black",
          },
          headerStyle: {
            backgroundColor: isDark ? BLACK_COLOR : "white",
          },
        }}
      />
    </Search.Navigator>
  );
}

export default NoticeStack;
