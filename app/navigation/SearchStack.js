import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import styled from "styled-components";
import { useColorScheme } from "react-native";
import BusList from "../screens/Bus/BusList";
import BusDetail from "../screens/Bus/BusDetail";
import { BLACK_COLOR } from "../colors";

const Search = createNativeStackNavigator();

const MyHeader = styled.View`
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  width: 300px;
  height: 30px;
  background-color: ${props => props.theme.busListHeader};
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
  const isDark = useColorScheme() === "dark";

  return (
    <Search.Navigator
      screenOptions={{
        headerShown: true,
        headerBackVisible: false,
      }}
    >
      <Search.Screen
        name="BusList"
        component={BusList}
        options={{
          headerTitle: ({ children }) => renderHeader(children),
          headerTitleAlign: "center",
          headerStyle: {
            backgroundColor: isDark ? BLACK_COLOR : "white",
          },
        }}
      />
      <Search.Screen
        name="BusDetail"
        component={BusDetail}
        options={{
          headerTitle: ({ children }) => renderHeader(children),
          headerTitleAlign: "center",
          headerStyle: {
            backgroundColor: isDark ? BLACK_COLOR : "white",
          },
        }}
      />
    </Search.Navigator>
  );
}

export default SearchStack;
