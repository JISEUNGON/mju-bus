import React, { useState, useRef, useCallback, useMemo } from "react";
<<<<<<< HEAD
import {
  Dimensions,
  StyleSheet,
  TouchableOpacity,
  Platform,View
} from "react-native";
import {
  GestureHandlerRootView,
  ScrollView,
} from "react-native-gesture-handler";
import {
  BottomSheetModal,
  BottomSheetModalProvider,
} from "@gorhom/bottom-sheet";
=======
import { Dimensions, StyleSheet, TouchableOpacity, Platform} from "react-native";
import { GestureHandlerRootView, ScrollView } from "react-native-gesture-handler";
import { BottomSheetModal, BottomSheetModalProvider, SCREEN_HEIGHT} from "@gorhom/bottom-sheet";
>>>>>>> b24e126049ef5db6f45b599f701006ab1567e878
import styled from "styled-components";
import { Fontisto, MaterialIcons, Ionicons } from "@expo/vector-icons";
import Icon from "react-native-vector-icons/AntDesign";
import LinkJoin from "../../components/LinkJoin";

const { width: SCREEN_WIDTH } = Dimensions.get("window");


const HeaderContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  height: 140px;
  justify-content: center;
  background-color: ${props => props.theme.scheduleBgColor};
`;

const HighlightTaxi = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: #4f8645;
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
const ContentsTitleContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
<<<<<<< HEAD
=======
  //background-color: beige;
  background-color: ${props => props.theme.scheduleBgColor};
`
const BodyContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  height:110%;
  padding-bottom: 20px;
  //background-color: beige;
>>>>>>> b24e126049ef5db6f45b599f701006ab1567e878
  background-color: ${props => props.theme.scheduleBgColor};
  //background-color: beige;
  margin-bottom: 20px;
`;
<<<<<<< HEAD
=======


const ParticipationContainer = styled.View`
  //background-color: aqua;
  width: 100%;
  //height:20%;
`;

>>>>>>> b24e126049ef5db6f45b599f701006ab1567e878
const ContentsTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 18px;
  color: ${props => props.theme.mainTextColor};
<<<<<<< HEAD
  padding-top: 3px;
  //background-color: beige;
  
`;
const ParticipationContainer = styled.View`
  //background-color: aqua;
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  background-color: ${props => props.theme.scheduleBgColor};
`;


const RecruitmentContainer = styled(ParticipationContainer)`
  //background-color: green;
  //height:50%;
  
`;
const BorderView = styled.View`
  margin-top: 40px;
  z-index: -10;
  background-color: beige;
`;
=======
  margin-bottom: 20px;
`;


const RecruitmentContainer = styled(ParticipationContainer)`
  //background-color: green;
  //height:50%;
  margin-top: 15px;
  
`;
>>>>>>> b24e126049ef5db6f45b599f701006ab1567e878
const ModalContainer = styled.View`
  flex: 1;
  width: 350px;
  padding: 0 20px;
<<<<<<< HEAD
  margin-left: 22px;
  margin-bottom: 5px;
=======
  margin-left:22px;
  margin-bottom:5px;
>>>>>>> b24e126049ef5db6f45b599f701006ab1567e878
  //background-color: beige;
`;

const ModalTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 18px;
  margin-bottom: 5px;
`;

const Row = styled.View`
  width: 100%;
  height: 30px;
  flex-direction: row;
  justify-content: flex-start;
  margin-bottom: 10px;
  //background-color: aqua;
`;

const ModalText = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 15px;
  margin-left: 10px;
  margin-bottom: 10px;
`;

const Styles = StyleSheet.create({
  circle: {
    backgroundColor: "#EFEFEF",
    position: "absolute",
    width: 40,
    height: 40,
    borderRadius: 50,
    justifyContent: "center",
    alignItems: "center",
    ...Platform.select({
<<<<<<< HEAD
      ios: {
        left: 335,
        top: 630,
      },
      android: {
        left: 350,
        top: 480,
      },
    }),
=======
      ios:{
        left: 335,
        top: 630,
      },
      android:{
        left: 350,
        top: 480,
      }

    })
>>>>>>> b24e126049ef5db6f45b599f701006ab1567e878
  },
  con: {
    backgroundColor: "#888888",
  },
  hr: {
    borderBottomColor: "#737577",
  },
  text: {
    color: "#555555",
  },
});

export function highlightTaxi(contents, isOpen) {
  //
  return (
    <HighlightTaxi style={[isOpen ? Styles.title : null]}>
      {contents}
    </HighlightTaxi>
  );
}

function Taxi({ route: { params }, navigation: { navigate } }) {
  const bottomSheetModalRef = useRef(null);

  const [isOpen, setIsOpen] = useState(false);

  const snapPoints = useMemo(() => [Platform.OS === "ios" ? "25%" : "35%"], []);

  const handlePresentModal = useCallback(() => {
    bottomSheetModalRef.current?.present();
    setIsOpen(true);
  }, []);

  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <BottomSheetModalProvider>
        
        <ScrollView
          stickyHeaderIndices={[1, 4]}
          showsVerticalScrollIndicator={false}
        >
          {/* Head Conatiner*/}
          <HeaderContainer style={[isOpen ? Styles.con : null]}>
            <Title>
              같이 {highlightTaxi("배달", isOpen)} 시킬 사람 구해요!
            </Title>
            <SubTitle style={[isOpen ? Styles.text : null]}>
              배달 파티를 모집 하거나 참여 해보세요
            </SubTitle>
            <Hr style={[isOpen ? Styles.hr : null]} />
          </HeaderContainer>

          <ContentsTitleContainer style={[isOpen ? Styles.con : null]}>
            <ContentsTitle>참여 중인 파티</ContentsTitle>
          </ContentsTitleContainer>

          <ParticipationContainer style={[isOpen ? Styles.con : null]}>
            <LinkJoin isOpen={isOpen} screenName="참여" />
            <LinkJoin isOpen={isOpen} screenName="참여" />
            <LinkJoin isOpen={isOpen} screenName="참여" />
          </ParticipationContainer>

          <BorderView  style={[isOpen ? Styles.con : null]}/>
          <ContentsTitleContainer style={[isOpen ? Styles.con : null]}>
            <ContentsTitle>모집 중인 파티</ContentsTitle>
          </ContentsTitleContainer>

          <RecruitmentContainer style={[isOpen ? Styles.con : null]}>
            <LinkJoin isOpen={isOpen} screenName="모집" />
            <LinkJoin isOpen={isOpen} screenName="모집" />
            <LinkJoin isOpen={isOpen} screenName="모집" />
            <LinkJoin isOpen={isOpen} screenName="모집" />
            <LinkJoin isOpen={isOpen} screenName="모집" />
            <LinkJoin isOpen={isOpen} screenName="모집" />
            <LinkJoin isOpen={isOpen} screenName="모집" />
            <LinkJoin isOpen={isOpen} screenName="모집" />
          </RecruitmentContainer>
        </ScrollView>

        <TouchableOpacity style={Styles.circle} onPress={handlePresentModal}>
          <Icon name="plus" size={18} color="#788898" active />
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
          <ModalContainer>
            <ModalTitle>어떤 파티를 만들까요?</ModalTitle>
            <TouchableOpacity
              onPress={() => {
                navigate("TaxiStack", {
                  screen: "Taxi_nmap",
                });
              }}
            >
              <Row>
                <Fontisto name="taxi" size={15} color="rgb(255,211,26)" />
                <ModalText>택시</ModalText>
              </Row>
            </TouchableOpacity>
            <TouchableOpacity
              onPress={() => {
                navigate("AddPartyStack", {
                  screen: "AddDelivery",
                });
              }}
            >
              <Row>
                <MaterialIcons
                  name="delivery-dining"
                  size={24}
                  color="rgb(76,150,180)"
                />
                <ModalText>배달</ModalText>
              </Row>
            </TouchableOpacity>
            <TouchableOpacity
              onPress={() => {
                navigate("AddPartyStack", {
                  screen: "AddCarPool",
                });
              }}
            >
              <Row>
                <Ionicons name="thumbs-up" size={20} color="rgb(48,52,63)" />
                <ModalText>카풀</ModalText>
              </Row>
            </TouchableOpacity>
          </ModalContainer>
        </BottomSheetModal>
        
      </BottomSheetModalProvider>
    </GestureHandlerRootView>
  );
}
export default Taxi;




