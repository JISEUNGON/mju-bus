import React, { useState, useRef, useCallback } from "react";
import {
  Text,
  View,
  Image,
  Dimensions,
  StyleSheet,
  TouchableOpacity,
  SafeAreaView,
} from "react-native";
import {
  BottomSheetModal,
  BottomSheetModalProvider,
} from "@gorhom/bottom-sheet";
import styled from "styled-components";
import { Entypo } from "@expo/vector-icons";
import Icon from "react-native-vector-icons/AntDesign";
import { highlightTaxi } from "../../utils";
import BusIcon from "../../components/BusIcon";
import colors from "../../colors";

const { width: SCREEN_WIDTH } = Dimensions.get("window");

const HeaderContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  background-color: white;
  height: 140px;
  justify-content: center;
  background-color: ${props => props.theme.scheduleBgColor};
`;

const Title = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: ${props => props.theme.mainTextColor};
  margin-bottom: 10px;
`;

const LeftPeopleTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 15px;
  color: ${props => props.theme.subTextColor};
`;

const NoticeTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 13px;
  color: #4f8645;
  margin-left: 10px;
  margin-top: 2px;
`;

const Hr = styled.View`
  width: 100%;
  margin-top: 20px;
  border-bottom-color: #d3d7dc;
  opacity: 0.3;
`;

const BodyContainer = styled.ScrollView`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  background-color: ${props => props.theme.scheduleBgColor};
`;

const Board = styled.View`
  width: 100%;
  height: 100px;
  background-color: ${props => props.theme.taxiFrameColor};
  padding: 10px ${SCREEN_WIDTH * 0.06}px;
  border-radius: 20px;
  flex-direction: row;
  align-items: center;
  //margin-bottom: 3px;
`;

const Column = styled.View`
  margin-left: 5px;
  flex: 3;
`;

const SecondTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: ${props => props.theme.mainTextColor};
  margin-bottom: 7px;
`;

const SubTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 15px;
  color: ${props => props.theme.subTextColor};
`;

const ParticipationContainer = styled.View`
  justify-content: space-between;
  //align-items: center;
  width: 100%;
  flex-direction: row;
  margin-bottom: 3px;
  justify-content: space-between;
  flex-direction: column;
`;

const ContentsTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 18px;
  color: ${props => props.theme.mainTextColor};
  margin-bottom: 20px;
`;

const RecruitmentContainer = styled(ParticipationContainer)`
  margin-top: 30px;
`;

const Bind = styled.View`
  flex-direction: row;
  //justify-content: space-around;
`;

const Styles = StyleSheet.create({
  contentContainer: {
    marginTop: 10,
    flex: 1,
    paddingHorizontal: 15,
  },
  title: {
    fontFamily: "SpoqaHanSansNeo-Bold",
    fontSize: 18,
  },
  row: {
    width: "100%",
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "flex-start",
    marginVertical: 10,
  },
  image: {
    marginRight: 20,
    width: 30,
    height: 30,
  },
  text: {
    fontFamily: "SpoqaHanSansNeo-Medium",
    fontSize: 15,
  },
  circle: {
    backgroundColor: "#979797",
    width: 40,
    height: 40,
    position: "absolute",
    bottom: -110,
    right: 0,
    borderRadius: 50,
    justifyContent: "center",
    alignItems: "center",
  },
  header: {
    backgroundColor: "#FFFFFF",
    shadowColor: "#333333",
    shadowOffset: { width: -1, height: -3 },
    shadowRadius: 2,
    shadowOpacity: 0.4,
    paddingTop: 20,
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
  },
  panelHeader: {
    alignItems: "center",
  },
  panelHandle: {
    width: 40,
    height: 0,
    borderRadius: 4,
    backgroundColor: "#00000040",
    marginBottom: 10,
  },
});

function Taxi() {
  const bottomSheetModalRef = useRef(null);

  const snapPoints = ["50%"];

  function handlePresentModal() {
    bottomSheetModalRef.current?.present();
  }

  return (
    <>
      {/* Head Conatiner*/}
      <HeaderContainer>
        <Title>같이 {highlightTaxi("배달")} 시킬 사람 구해요!</Title>
        <SubTitle>배달 파티를 모집 하거나 참여 해보세요</SubTitle>
        <Hr style={{ borderBottomWidth: 2 }} />
      </HeaderContainer>

      {/*BodyContainer*/}
      <BodyContainer showsVerticalScrollIndicator={false}>
        <ParticipationContainer>
          <ContentsTitle>참여중인 파티</ContentsTitle>
          <Board>
            <BusIcon busRoute="sine" />
            <Column>
              <SecondTitle>학관에서 BBQ를!</SecondTitle>
              <Bind>
                <LeftPeopleTitle>2명 남음</LeftPeopleTitle>
                <NoticeTitle>최소 인원 모으면 주문</NoticeTitle>
              </Bind>
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
        </ParticipationContainer>
        <RecruitmentContainer>
          <ContentsTitle>모집 중인 파티</ContentsTitle>
          <Board>
            <BusIcon busRoute="sine" />
            <Column>
              <SecondTitle>학관에서 BBQ를!</SecondTitle>
              <Bind>
                <LeftPeopleTitle>2명 남음</LeftPeopleTitle>
                <NoticeTitle>최소 인원 모으면 주문</NoticeTitle>
              </Bind>
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
        </RecruitmentContainer>
        <BottomSheetModalProvider>
          <View style={{ marginTop: 90 }}>
            <TouchableOpacity
              style={Styles.circle}
              onPress={handlePresentModal}
            >
              <Icon name="plus" size={18} color="#FFFFFF" />
            </TouchableOpacity>
            <BottomSheetModal
              ref={bottomSheetModalRef}
              index={0}
              snapPoints={snapPoints}
              backgroundStyle={{ borderRadius: 50 }}
              bottomInset={10}
              detached={true}
            >
              <View style={Styles.contentContainer}>
                <Text style={Styles.title}>어떤 파티를 만들까요?</Text>
                <View style={Styles.row}>
                  <Image
                    style={Styles.image}
                    source={require("../../assets/image/taxi.png")}
                  ></Image>
                  <Text style={Styles.text}>택시</Text>
                </View>
                <View style={Styles.row}>
                  <Image
                    style={Styles.image}
                    source={require("../../assets/image/delivery.png")}
                  ></Image>
                  <Text style={Styles.text}>배달</Text>
                </View>
                <View style={Styles.row}>
                  <Image
                    style={Styles.image}
                    source={require("../../assets/image/carpool.png")}
                  ></Image>
                  <Text style={Styles.text}>카풀</Text>
                </View>
              </View>
            </BottomSheetModal>
          </View>
        </BottomSheetModalProvider>
      </BodyContainer>
    </>
  );
}
export default Taxi;
