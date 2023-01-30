import React, { useState, useRef, useCallback, useMemo } from "react";
import { View, Dimensions, StyleSheet, TouchableOpacity, SafeAreaView} from "react-native";
import { BottomSheetModal, BottomSheetModalProvider, BottomSheetBackdropProps,} from "@gorhom/bottom-sheet";
import styled from "styled-components";
import { Entypo, Fontisto, MaterialIcons, Ionicons } from "@expo/vector-icons";
import Icon from "react-native-vector-icons/AntDesign";
import BusIcon from "../../components/BusIcon";
import Profile from "../../components/Profile";
import colors, { DARK_GRAY, GRAY, LIGHT_GRAY, WHITE_COLOR } from "../../colors";
import LinkJoin from "../../components/LinkJoin";

const { width: SCREEN_WIDTH } = Dimensions.get("window");

const HeaderContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  height: 140px;
  justify-content: center;
  background-color: ${props => props.theme.scheduleBgColor};
  flex: 0.75;
`;

const HighlightTaxi = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: #4F8645;
`;

const Title = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: ${props => props.theme.mainTextColor};
  margin-bottom: 10px;
`;

const SubTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 15px;
  color: ${props => props.theme.subTextColor};
`;

const Hr = styled.View`
  width: 100%;
  margin-top: 20px;
  border-bottom-color: #d3d7dc;
  opacity: 0.3;
  border-bottom-width: 2px;
`;

const BodyContainer = styled.ScrollView`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  flex: 2;
  padding-bottom: 20px;
  background-color: ${props => props.theme.scheduleBgColor};
`;


const ParticipationContainer = styled.View`
  width: 100%;
`;

const ContentsTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 18px;
  color: ${props => props.theme.mainTextColor};
  margin-bottom: 20px;
`;

const RecruitmentContainer = styled(ParticipationContainer)`
  margin-top: 33px;
`;

const BottomContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  flex: 1;
  background-color: ${props => props.theme.scheduleBgColor};
`;

const ModalContainer = styled.View`
  width: ${SCREEN_WIDTH - 10}px;
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


const Styles = StyleSheet.create({
  circle: {
    backgroundColor: "#EFEFEF",
    position: "absolute",
    width: 40,
    height: 40,
    left: 320,
    top: 120,
    borderRadius: 50,
    justifyContent: "center",
    alignItems: "center",
  },
  con: {
    backgroundColor: "#888888",
  },
  hr: {
    borderBottomColor:"#737577"
  },
  text: {
    color:"#555555"
  },

});

export function highlightTaxi(contents, isOpen){
  //
  return <HighlightTaxi style = {[isOpen ? Styles.title : null]}>{contents}</HighlightTaxi>;
}

function Taxi() {
  const bottomSheetModalRef = useRef(null);

  const [isOpen, setIsOpen] = useState(false);

  const snapPoints = useMemo(() => ["25%"], []);

  const handlePresentModal = useCallback(() => {
    bottomSheetModalRef.current?.present();
    setIsOpen(true);
  }, []);

  return (
    <BottomSheetModalProvider>
        {/* Head Conatiner*/}
        <HeaderContainer style={[isOpen ? Styles.con : null]}>
          <Title>같이 {highlightTaxi( "배달", isOpen)} 시킬 사람 구해요!</Title>
          <SubTitle style = {[isOpen ? Styles.text : null]}>배달 파티를 모집 하거나 참여 해보세요</SubTitle>
          <Hr style={[isOpen ? Styles.hr : null]} />
        </HeaderContainer>

        {/*BodyContainer*/}
        <BodyContainer style={[isOpen ? Styles.con : null]}>
          <ParticipationContainer>
            <ContentsTitle>참여 중인 파티</ContentsTitle>
            <LinkJoin isOpen={isOpen} screenName ="참여"/>
          </ParticipationContainer>
          <RecruitmentContainer >
            <ContentsTitle>모집 중인 파티</ContentsTitle>
            <LinkJoin isOpen={isOpen} screenName = "모집"/>
          </RecruitmentContainer>
        </BodyContainer>

        {/*BottomContainer*/}
        <BottomContainer style={[isOpen ? Styles.con : null]}>
          <ModalContainer>
            <TouchableOpacity
              style={Styles.circle}
              onPress={handlePresentModal}
            >
              <Icon name="plus" size={18} color="#788898" />
            </TouchableOpacity>
            <BottomSheetModal
              ref={bottomSheetModalRef}
              index={0}
              snapPoints={snapPoints}
              backgroundStyle={{
                borderRadius: 22,
                marginHorizontal: 10,
                marginTop: 5,
              }}
              bottomInset={8}
              detached={true}
              onDismiss={() => setIsOpen(false)}
            >
              <ModalContentContainer>
                <ModalTitle>어떤 파티를 만들까요?</ModalTitle>
                <Row>
                  <Fontisto name="taxi" size={15} color="rgb(255,211,26)" />
                  <ModalText>택시</ModalText>
                </Row>
                <Row>
                  <MaterialIcons
                    name="delivery-dining"
                    size={24}
                    color="rgb(76,150,180)"
                  />
                  <ModalText>배달</ModalText>
                </Row>
                <Row>
                  <Ionicons name="thumbs-up" size={18} color="rgb(48,52,63)" />
                  <ModalText>카풀</ModalText>
                </Row>
              </ModalContentContainer>
            </BottomSheetModal>
          </ModalContainer>
        </BottomContainer>

    </BottomSheetModalProvider>
  );
}
export default Taxi;
