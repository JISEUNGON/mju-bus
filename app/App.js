import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { StatusBar } from "react-native";
import Root from "./navigation/Root";

export default function App() {
  return (
    <NavigationContainer>
      <StatusBar backgroundColor="white" />
      <Root />
    </NavigationContainer>
  );
}
