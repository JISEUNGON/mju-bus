/* eslint-disable react/prop-types */
import { useNavigation } from "@react-navigation/native";
import analytics from "@react-native-firebase/analytics";
import React from "react";
import {
  TouchableOpacity,
  Dimensions,
  View,
  useColorScheme,
} from "react-native";
import styled from "styled-components/native";
import { Entypo } from "@expo/vector-icons";
import BusIcon from "./BusIcon";

const { width: SCREEN_WIDTH } = Dimensions.get("window");

const Board = styled.View`
  width: 100%;
  height: 100px;
  background-color: ${props => props.theme.homeCompColor};
  padding: 10px ${SCREEN_WIDTH * 0.06}px;
  border-radius: 20px;
  flex-direction: row;
  align-items: center;
  margin-bottom: 10px;
`;

const Column = styled.View`
  margin-left: 5px;
  flex: 3;
`;

const Title = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: ${props => props.theme.mainTextColor};
`;

const SubText = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: ${SCREEN_WIDTH > 500 ? 16 : 13}px;
  color: ${props => props.theme.subTextColor};
  margin-bottom: 7px;
`;

function LinkScreen({ screenName }) {
  const navigation = useNavigation();
  const isDark = useColorScheme() === "dark";
  const appAnalytics = async () => {
    await analytics().logScreenView({
      screen_name: screenName,
      screen_class: "시내셔틀 (링크)",
    });
  };

  const goToSchedule = () => {
    appAnalytics();
    navigation.navigate("HomeBottomTabs", {
      screen: `${screenName}`,
      params: {},
    });
  };

  return (
    <TouchableOpacity onPress={goToSchedule} style={{}}>
      <Board isDark={isDark}>
        <BusIcon busRoute="sine" />
        <Column>
          <SubText isDark={isDark}>
            {screenName === "시간표"
              ? "현재 운영 중인 시간표예요"
              : "가장 빠른 셔틀버스를 탐색해요"}
          </SubText>
          <Title isDark={isDark}>
            {screenName === "시간표" ? "버스 시간표 보기" : "버스 검색하기"}
          </Title>
        </Column>
        <View
          style={{
            flex: 1,
            alignItems: "flex-end",
          }}
        >
          <Entypo name="chevron-right" size={20} color="gray" />
        </View>
      </Board>
    </TouchableOpacity>
  );
}
export default LinkScreen;
