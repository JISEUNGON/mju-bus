import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import HomeBottomTabs from "./HomeBottomTabs";
import Splash from "../screens/Splash";
import SearchStack from "./SearchStack";
import NoticeStack from "./NoticeStack";
import AddPartyStack from "./AddPartyStack";
import TaxiDestination from "../screens/Taxi/TaxiDestination";
import TaxiStart from './../screens/Taxi/TaxiStart';
import TaxiNmap from "../screens/Taxi/TaxiNmap";

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

      <Nav.Screen name="NoticeStack" component={NoticeStack} />
      <Nav.Screen name="AddPartyStack" component={AddPartyStack}/>
      
      <Nav.Screen name="TaxiNmap" component={TaxiNmap}/>
      <Nav.Screen name="TaxiStart" component={TaxiStart}/>
      <Nav.Screen name="TaxiDestination" component={TaxiDestination}/>
    </Nav.Navigator>
  );
}

export default Root;
