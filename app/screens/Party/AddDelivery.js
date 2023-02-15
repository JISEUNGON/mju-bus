import React, { useLayoutEffect, useState } from "react";
import {  TouchableOpacity , Text, View} from "react-native";
import styled from "styled-components";
import { Entypo } from "@expo/vector-icons";

function CustomNavButton(navigation) {
  return (
    // eslint-disable-next-line react/destructuring-assignment
    <TouchableOpacity onPress={() => navigation.goBack()}>
      <Entypo name="chevron-left" size={24} color="gray" />
    </TouchableOpacity>
  );
}

function AddDelivery({navigation}){

  useLayoutEffect(() => {
    navigation.setOptions({
      headerLeft: () => CustomNavButton(navigation),
    });
  }, [navigation]);

  return(
    <View>
      <Text>배달 파티 추가</Text>
    </View>
  );
}
export default AddDelivery;