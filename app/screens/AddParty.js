import { View, Dimensions, Modal, StyleSheet, Text } from "react-native";
import styled from "styled-components";
import React, { useEffect, useState } from "react";
import { Entypo, Fontisto, MaterialIcons, Ionicons } from "@expo/vector-icons";

const { height: SCREEN_HEIGHT, width: SCREEN_WIDTH } = Dimensions.get("window");

const ScreenContainer = styled.View`
  justify-content: center;
  
`;

const Container = styled.View`
  background-color: ${props => props.theme.busCompColor};
  border-radius: 20px;
  width: ${SCREEN_WIDTH-100}px;
  height: ${SCREEN_HEIGHT -100}px;
  border-bottom-right-radius: 0px;
  border-bottom-left-radius: 0px;
  margin-top: 10px;
`;

const ModalContentContainer = styled.View`
  margin-top: 10px;
  margin-left: 30px;
`;

const ModalTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 18px;
  margin-bottom: 13px;
`;

const Row = styled.View`
  width: 100%;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  margin-bottom: 10px;
`;

const ModalText = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 15px;
  margin-left: 13px;
`;

const styles = StyleSheet.create({
  blankSpace: {
    position: "absolute",
    width: SCREEN_WIDTH,
    height: SCREEN_HEIGHT,
    backgroundColor: "#000000",
    opacity: 0.8,
  },
});

function AddParty(isOpen, setIsOpen){
  return(
    <Container>
    <Modal animationType="slide" transparent visible={isOpen}>
      
      <View>
        <Text>Hello</Text>
      </View>
    </Modal>
    </Container>
  )

};
export default AddParty;