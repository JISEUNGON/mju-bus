import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import HomeBottomTabs from "./HomeBottomTabs";
import Splash from "../screens/Splash";
import SearchStack from "./SearchStack";
import NoticeStack from "./NoticeStack";
import Login from "../components/Login/Login";
import StudentAuth from "../components/Login/StudentAuth";
import AddPartyStack from "./AddPartyStack";
import TaxiStack from "./TaxiStack";
import AsyncStorage from "@react-native-async-storage/async-storage";

const Nav = createNativeStackNavigator();
// AsyncStorage.clear();
function Root() {
  return (
      <Nav.Navigator
        initialRouteName="Splash"
        screenOptions={{
          headerShown: false,
        }}
      >
        <Nav.Screen name="Splash" component={Splash} />
        <Nav.Screen name="Login" component={Login} />
        <Nav.Screen name="StudentAuth" component={StudentAuth} />
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

        <Nav.Screen name="NoticeStack" component={NoticeStack} />
        <Nav.Screen name="AddPartyStack" component={AddPartyStack} />
        <Nav.Screen name="TaxiStack" component={TaxiStack} />
      </Nav.Navigator>
  );
}

export default Root;
