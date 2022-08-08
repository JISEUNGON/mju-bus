import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import BusList from "../screens/Bus/BusList";
import BusDetail from "../screens/Bus/BusDetail";

const Search = createNativeStackNavigator();

function SearchStack() {
  return (
    <Search.Navigator screenOptions={{ headerBackVisible: true }}>
      <Search.Screen name="BusList" component={BusList} />
      <Search.Screen name="BusDetail" component={BusDetail} />
    </Search.Navigator>
  );
}

export default SearchStack;
