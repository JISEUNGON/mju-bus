import { createNativeStackNavigator } from "@react-navigation/native-stack";
import React from "react";
import AddDelivery from "../screens/Party/AddDelivery";
import AddCarPool from "../screens/Party/AddCarPool";
const Search = createNativeStackNavigator();

function AddPartyStack() {
  return (
    <Search.Navigator
      screenOptions={{
        headerShown: false,
        
        gestureEnabled: true,
      }}
    >
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