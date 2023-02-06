import React from "react";
import {Dimensions, Text, View, StyleSheet} from "react-native";
import styled from "styled-components/native";
import { Entypo } from "@expo/vector-icons";
import Profile from "./Profile";


const { width: SCREEN_WIDTH } = Dimensions.get("window");

const Board = styled.View`
  width: 100%;
  height: 90px;
  background-color: ${props => props.theme.taxiFrameColor};
  padding: 10px ${SCREEN_WIDTH * 0.04}px;
  border-radius: 20px;
  flex-direction: row;
  align-items: center;
  margin-bottom: 10px;
`;

const Column = styled.View`
  margin-left: 6px;
  flex: 3;
`;

const RouteTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: ${props => props.theme.mainTextColor};
  margin-top: 5px;
  margin-bottom: 3px;
`;

const Bind = styled.View`
  flex-direction: row;
  margin-left: 6px;
  
  //justify-content: space-around;
`;

const LeftPeopleText = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 15px;
  color: ${props => props.theme.subTextColor};
`;

const NoticeText = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 13px;
  color: #4f8645;
  margin-left: 10px;
  margin-top: 2px;
`;

const Sign = styled.View`
  flex: 1;
  align-items: flex-end;
`;

const Styles = StyleSheet.create({
  box: {
    backgroundColor: "#7D827C",
  },
  text: {
    color:"#555555"
  },
  title:{
    color:"#314C2C"
  },
});

function LinkJoin({isOpen, screenName}){
    return (
    <Board style={[isOpen  === true ? Styles.box : null]}>
        <Profile/>
        <Column>
            <RouteTitle> {screenName ==="참여" ? "기흥역에서 학교로!" : "진입로에서 학교로!"} </RouteTitle>
            <Bind>
                <LeftPeopleText style = {[isOpen  === true ? Styles.text: null]}>
                  {screenName === "참여" ? "2명 참여 중" : "1명 참여 중"}</LeftPeopleText>
                <NoticeText style={[isOpen  === true ? Styles.title : null]}>
                  {screenName === "참여" ? "11시 50분 출발" : "15시 20분 출발"}
                </NoticeText>
            </Bind>
        </Column>
        <Sign>
            <Entypo name="chevron-right" size={20} color="#40444A" />
        </Sign>
    </Board>
    );
}


export default LinkJoin;