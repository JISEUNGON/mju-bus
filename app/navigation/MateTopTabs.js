import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";
import React from "react";
import Taxi from "../screens/Mate/Taxi";
import CarPool from "../screens/Mate/CarPool";
import BaeDal from "../screens/Mate/BaeDal";

const Tab = createMaterialTopTabNavigator();

function MateTopTabs() {
  return (
    <Tab.Navigator>
      <Tab.Screen name="택시" component={Taxi} />
      <Tab.Screen name="카풀" component={CarPool} />
      <Tab.Screen name="배달" component={BaeDal} />
    </Tab.Navigator>
  );
}

export default MateTopTabs;
