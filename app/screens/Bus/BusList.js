import React from "react";
import { Text, TouchableOpacity } from "react-native";
import styled from "styled-components";

const Container = styled.ScrollView`
  background-color: white;
`;

const List = styled.View`
  width: 100%;
  height: 200px;
  align-items: center;
  justify-content: center;
  border-color: aqua;
`;

// eslint-disable-next-line react/prop-types
function BusList({ navigation: { navigate } }) {
  return (
    <Container>
      <List>
        <TouchableOpacity onPress={() => navigate("BusDetail")}>
          <Text>리스트 1 </Text>
        </TouchableOpacity>
      </List>
      <List>
        <TouchableOpacity onPress={() => navigate("BusDetail")}>
          <Text>리스트 2 </Text>
        </TouchableOpacity>
      </List>
      <List>
        <TouchableOpacity onPress={() => navigate("BusDetail")}>
          <Text>리스트 3 </Text>
        </TouchableOpacity>
      </List>
    </Container>
  );
}

export default BusList;
