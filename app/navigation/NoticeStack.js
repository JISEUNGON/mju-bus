import { createNativeStackNavigator } from "@react-navigation/native-stack";
import React from "react";
import Notice from "../screens/Home/Notice";

const Search = createNativeStackNavigator();

function NoticeStack() {
  return (
    <Search.Navigator
      screenOptions={{
        headerShown: true,
        headerTitle: "공지사항",
      }}
    >
      <Search.Screen name="Notice" component={Notice} />
    </Search.Navigator>
  );
}

export default NoticeStack;
