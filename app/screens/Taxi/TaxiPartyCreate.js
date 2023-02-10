import React, { useState, useCallback } from "react";
import { Text, View, TouchableOpacity, StyleSheet } from "react-native";
import { AntDesign } from "@expo/vector-icons";
import styled from "styled-components/native";

const CreatePartyBt = styled.TouchableOpacity`
  border-radius: 15px;
  border-width: 2px;
  width: 85%;
  height: 50px;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  padding-left: 10px;
  padding-right: 10px;
  background-color: #e8f3e6;
  border-color: #e8f3e6;
`;
const BtText = styled.Text`
  font-size: 23px;
  font-family: "SpoqaHanSansNeo-Bold";
  color: #4f8645;
`;
const MainText = styled.Text`
  font-size: 23px;
  font-family: "SpoqaHanSansNeo-Bold";
  color: black;
  margin-bottom: 150px;
  text-align: center;
`;
const SubContainer = styled.View`
  background-color: white;
  height: 100%;
  width: 100%;
  flex: 1;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;
const ImageView = styled.View`
  background-color: blue;
  width: 50px;
  height: 50px;
  border-radius: 100px;
  margin-bottom: 15px;
`;
function TaxiPartyCreate({ navigation, route }) {
  const start = route.params.start;
  const startDetail = route.params.startDetail;
  const destination = route.params.destination;
  const minPeople = route.params.minPeople;
  const maxPeople = route.params.maxPeople;
  const time = route.params.time;

  {
    console.log("start---------------");
    console.log(start);
    console.log("startDetail------------------------------");
    console.log(startDetail);
    console.log("destination------------------");
    console.log(destination);
    console.log("people----------------------------");
    console.log(minPeople + " " + maxPeople);
    console.log("time----");
    console.log(time);
  }

  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={styles.back}
        onPress={() => {
          navigation.goBack();
        }}
      >
        <AntDesign name="left" size={25} color="black" />
      </TouchableOpacity>
      <SubContainer>
        <ImageView></ImageView>
        <MainText>미정님의{"\n"}파티가 생성 되었습니다</MainText>
        <CreatePartyBt>
          <BtText>파티 모집 창 이동</BtText>
        </CreatePartyBt>
      </SubContainer>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: "white",
    flex: 1,
    paddingHorizontal: 20,
  },
  back: {
    marginBottom: 10,
    marginTop: 30,
  },
});

export default TaxiPartyCreate;
