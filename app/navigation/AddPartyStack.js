import { createNativeStackNavigator } from "@react-navigation/native-stack";
import React from "react";
import AddTaxi from "../screens/Party/AddTaxi";
import AddDelivery from "../screens/Party/AddDelivery";
import AddCarPool from "../screens/Party/AddCarPool";

const Search = createNativeStackNavigator();

function AddPartyStack() {
  return (
    <Search.Navigator
      screenOptions={{
        headerShown: true,
        headerTitle: "파티원 추가",
        gestureEnabled: true,
      }}
    >
      <Search.Screen
        name="AddTaxi"
        component={AddTaxi}
      />
      <Search.Screen
        name="AddDelivery"
        component={AddDelivery}
      />
      <Search.Screen
        name="AddCarPool"
        component={AddCarPool}
      />

    </Search.Navigator>
  );
}

export default AddPartyStack;