import { createNativeStackNavigator } from "@react-navigation/native-stack";
import React from "react";
import Taxi_nmap from "../screens/Taxi/Taxi_nmap";
import Taxi_start from "../screens/Taxi/Taxi_start";
import Taxi_destination from "../screens/Taxi/Taxi_destination";

const Search = createNativeStackNavigator();

function TaxiStack() {
  return (
    <Search.Navigator
      screenOptions={{
        headerShown: false,
        gestureEnabled: true,
      }}
    >
      <Search.Screen
        name="Taxi_nmap"
        component={Taxi_nmap}
      />
      <Search.Screen
        name="Taxi_start"
        component={Taxi_start}
      />
      <Search.Screen
        name="Taxi_destination"
        component={Taxi_destination}
      />

    </Search.Navigator>
  );
}

export default TaxiStack;