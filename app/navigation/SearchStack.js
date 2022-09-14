import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import styled from "styled-components";
import BusList from "../screens/Bus/BusList";
import BusDetail from "../screens/Bus/BusDetail";

const Search = createNativeStackNavigator();

const MyHeader = styled.View`
  justify-content: center;
  align-items: center;
  border-radius: 30px;
  width: 300px;
  height: 30px;
  background-color: #fafbfb;
`;

const TopTitle = styled.Text`
  color: #c0c0c3;
`;

function renderHeader(children) {
  return (
    <MyHeader>
      <TopTitle>{children}</TopTitle>
    </MyHeader>
  );
}

function SearchStack() {
  return (
    <Search.Navigator
      screenOptions={{ headerShown: true, headerBackVisible: false }}
    >
      <Search.Screen
        name="BusList"
        component={BusList}
        options={{
          headerTitle: ({ children }) => renderHeader(children),
          headerTitleAlign: "center",
          headerTitleStyle: {},
        }}
      />
      <Search.Screen
        name="BusDetail"
        component={BusDetail}
        options={{
          headerTitle: ({ children }) => renderHeader(children),

          headerTitleAlign: "center",
          headerTitleStyle: {},
        }}
      />
    </Search.Navigator>
  );
}

export default SearchStack;
