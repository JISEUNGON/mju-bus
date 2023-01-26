import React, { useState, useRef, useCallback, useMemo } from "react";
import {Text, View, Image, Dimensions, StyleSheet, TouchableOpacity, SafeAreaView,} from "react-native";
import { BottomSheetModal, BottomSheetModalProvider,BottomSheetBackdropProps} from "@gorhom/bottom-sheet";
import styled from "styled-components";
import { Entypo, Fontisto, MaterialIcons, Ionicons } from "@expo/vector-icons";
import Icon from "react-native-vector-icons/AntDesign";
import { highlightTaxi } from "../../utils";
import BusIcon from "../../components/BusIcon";
import colors, { DARK_GRAY, GRAY, LIGHT_GRAY, WHITE_COLOR } from "../../colors";

const { width: SCREEN_WIDTH  } = Dimensions.get("window");

const HeaderContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  height: 140px;
  justify-content: center;
  //color:${props => props.theme.scheduleBgColor};
  background-color: ${props => props.theme.scheduleBgColor};
  flex:0.75;
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

const BodyContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  flex:2;
  //color: ${props => props.theme.scheduleBgColor};
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

const Sign = styled.View`
  flex: 1;
  align-items: flex-end;
`;

const RouteTitle = styled.Text`
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

const ModalContainer = styled.View`
  width: ${SCREEN_WIDTH -10 }px;
  justify-content: center;
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
  width:100%;
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

const BottomContainer = styled.View`
  width: ${SCREEN_WIDTH }px;
  padding: 0 20px;
  flex:1;
  background-color: ${props => props.theme.scheduleBgColor};
  //color:${props => props.theme.scheduleBgColor};
`;

const Styles = StyleSheet.create({
  circle: {
    backgroundColor: "#979797",
    position:'absolute',
    width: 40,
    height: 40,
    left:320,
    top:120,
    borderRadius: 50,
    justifyContent: "center",
    alignItems: "center",
  },
  container: {
    flex:1,
    backgroundColor:GRAY,
  }
  
});

function Taxi() {
  const bottomSheetModalRef = useRef(null);

  const [isOpen, setIsOpen] = useState(false);

  const snapPoints = useMemo(() =>["25%"],[]);

  const handlePresentModal = useCallback(() => {
    bottomSheetModalRef.current?.present();
    setIsOpen(true);
  }, []);




  return (
    <BottomSheetModalProvider>
      <View style={[Styles.container, { opacity: isOpen? 0.3: 1}]}>
      {/* Head Conatiner*/}
        <HeaderContainer>
          <Title>같이 {highlightTaxi("배달")} 시킬 사람 구해요!</Title>
          <SubTitle>배달 파티를 모집 하거나 참여 해보세요</SubTitle>
          <Hr style={{ borderBottomWidth: 2 }} />
        </HeaderContainer>

        {/*BodyContainer*/}
        <BodyContainer>
          <ParticipationContainer>
            <ContentsTitle>참여 중인 파티</ContentsTitle>
            <Board>
              <BusIcon busRoute="sine" />
              <Column>
                <RouteTitle>학관에서 BBQ를!</RouteTitle>
                <Bind>
                  <LeftPeopleTitle>2명 남음</LeftPeopleTitle>
                  <NoticeTitle>최소 인원 모으면 주문</NoticeTitle>
                </Bind>
              </Column>
              <Sign>
                <Entypo name="chevron-right" size={20} color="gray" />
              </Sign>
            </Board>
          </ParticipationContainer>
          <RecruitmentContainer>
            <ContentsTitle>모집 중인 파티</ContentsTitle>
            <Board>
              <BusIcon busRoute="sine" />
              <Column>
                <RouteTitle>학관에서 BBQ를!</RouteTitle>
                <Bind>
                  <LeftPeopleTitle>2명 남음</LeftPeopleTitle>
                  <NoticeTitle>최소 인원 모으면 주문</NoticeTitle>
                </Bind>
              </Column>
              <Sign>
                <Entypo name="chevron-right" size={20} color="gray" />
              </Sign>
            </Board>
          </RecruitmentContainer>
        
        </BodyContainer>
        <BottomContainer>
            <ModalContainer>
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
                backgroundStyle={{ borderRadius: 22,  marginHorizontal:10, marginTop:5}}
                bottomInset={8}
                detached={true}
                onDismiss = {() => setIsOpen(false)}

              >
                <ModalContentContainer>
                  <ModalTitle>어떤 파티를 만들까요?</ModalTitle>
                  <Row>
                    <Fontisto name="taxi" size={15} color="#00008B" />
                    <ModalText>택시</ModalText>
                  </Row>
                  <Row>
                    <MaterialIcons name="delivery-dining" size={24} color="skyblue" />
                    <ModalText>배달</ModalText>
                  </Row>
                  <Row>
                    <Ionicons name="thumbs-up" size={18} color="black" />
                    <ModalText>카풀</ModalText>
                  </Row>
                </ModalContentContainer>
              </BottomSheetModal>
            </ModalContainer>
        </BottomContainer>
      </View>
    </BottomSheetModalProvider>
  );
}
export default Taxi;




