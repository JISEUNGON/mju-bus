import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import HomeBottomTabs from "./HomeBottomTabs";
import Splash from "../screens/Splash";
import SearchStack from "./SearchStack";

const Nav = createNativeStackNavigator();

function Root() {
  return (
    <Nav.Navigator
      initialRouteName="Splash"
      screenOptions={{
        headerShown: false,
      }}
    >
      <Nav.Screen name="Splash" component={Splash} />
      <Nav.Screen
        name="HomeBottomTabs"
        component={HomeBottomTabs}
        options={{ gestureEnabled: false }}
      />
      <Nav.Screen
        name="SearchStack"
        component={SearchStack}
        options={{ headerShown: false }}
      />
    </Nav.Navigator>
  );
}

export default Root;
