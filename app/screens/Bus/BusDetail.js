import { View, Text, TouchableOpacity } from "react-native";
import { Entypo } from "@expo/vector-icons";
import React, { useEffect } from "react";

function CustomNavButton(navigation) {
  return (
    <TouchableOpacity onPress={() => navigation.goBack()}>
      <Entypo name="chevron-left" size={24} color="gray" />
    </TouchableOpacity>
  );
}

function BusDetail({ navigation, route: { params } }) {
  useEffect(() => {
    navigation.setOptions({
      title: TitleName(),
      headerLeft: () => CustomNavButton(navigation),
    });
  }, []);

  return (
    <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Text>BusDetail</Text>
    </View>
  );
}

export default BusDetail;
