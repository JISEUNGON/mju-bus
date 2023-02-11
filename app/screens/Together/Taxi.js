import React, { useState, useRef, useCallback, useMemo } from "react";
import {
  Dimensions,
  StyleSheet,
  TouchableOpacity,
  Platform,
  View,
  SectionList,
  Text,
} from "react-native";
import { FlatList } from "react-native-gesture-handler";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import {
  BottomSheetModal,
  BottomSheetModalProvider,
} from "@gorhom/bottom-sheet";
import styled from "styled-components";
import DATA from "../Taxi/Data";
import {
  Fontisto,
  MaterialIcons,
  Ionicons,
  Octicons,
} from "@expo/vector-icons";
import Icon from "react-native-vector-icons/AntDesign";
import Profile from "../../components/Profile";

const { width: SCREEN_WIDTH } = Dimensions.get("window");

const HeaderContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  height: 20%;
  //height: 140px;
  //flex:0.4;
  justify-content: center;
  background-color: ${props => props.theme.scheduleBgColor};
`;

const HighlightTaxi = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: #7974e7;
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

const SubHeading = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 13px;
  color: #505866;
  margin-left: 15px;
`;

const Heading = styled(Title)`
  margin-left: 15px;
`;
const Content = styled.View`
  flex-direction: column;
  margin-bottom: 25px;
  margin-left: 15px;
`;
const Board = styled.View`
  width: 140px;
  height: 120px;
  background-color: ${props => props.theme.taxiPartyColor};
  padding: 20px 20px;
  border-radius: 20px;
  flex-direction: column;
  margin-bottom: 10px;
`;
const Row = styled.View`
  flex-direction: row;
  justify-content: flex-end;
  align-items: baseline;
`;

const ProfileContent = styled.View`
  top: -23px;
`;

const NumOfPerson = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 15px;
  color: gray;
  margin-left: 3px;
`;

const Column = styled.View`
  margin-top: 15px;
`;
const ContentTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 17px;
  color: #353c49;
  top: -25px;
  margin-bottom: 5px;
`;
const PartyTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 16px;
  color: ${props => props.theme.mainTextColor};
  margin-left: 5px;
`;
const RemainingTime = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 13px;
  color: #dd5257;
  margin-left: 5px;
  margin-top: 5px;
`;

const BodyContainer = styled.View`
  height: 80%;
  //background-color: beige;
`;

const ModalContainer = styled.View`
  flex: 1;
  width: 350px;
  padding: 0 20px;
  margin-left: 22px;
  margin-bottom: 5px;
  //background-color: beige;
`;

const ModalTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 18px;
  margin-bottom: 5px;
`;

const ModalRow = styled.View`
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
  container: {
    flex: 1,
    backgroundColor: "white",
  },
  circle: {
    backgroundColor: "#EFEFEF",
    position: "absolute",
    width: 40,
    height: 40,
    borderRadius: 50,
    justifyContent: "center",
    alignItems: "center",
    ...Platform.select({
      ios: {
        left: 335,
        top: 630,
      },
      android: {
        left: 310,
        top: 520,
      },
    }),
  },
  highlight: {
    color: "#46437D",
  },
  purple: {
    backgroundColor: "#767586",
  },
  red: {
    color: "#783235",
  },
  background: {
    backgroundColor: "#888888",
  },
  gray: {
    color: "#555555",
  },
  hr: {
    borderBottomColor: "#737577",
  },
  title: {
    color: "#24272E",
  },
});

export function highlightTaxi(contents, isOpen) {
  return (
    <HighlightTaxi style={[isOpen ? Styles.highlight : null]}>
      {contents}
    </HighlightTaxi>
  );
}

function ListItem({ item, isOpen }) {
  return (
    <Content>
      <Board style={[isOpen ? Styles.purple : null]}>
        <Row>
          <Octicons
            name="person-fill"
            size={15}
            color="gray"
            style={[isOpen ? Styles.gray : null]}
          />
          <NumOfPerson style={[isOpen ? Styles.gray : null]}>
            {item.numOfPerson}/{item.MaxPerson}
          </NumOfPerson>
        </Row>
        <ProfileContent>
          <Profile />
        </ProfileContent>
        <Column>
          <ContentTitle style={[isOpen ? Styles.title : null]}>
            {item.start}에서
          </ContentTitle>
          <ContentTitle style={[isOpen ? Styles.title : null]}>
            {item.dest}로
          </ContentTitle>
        </Column>
      </Board>
      <PartyTitle>{item.nickname}의 파티</PartyTitle>
      <RemainingTime style={[isOpen ? Styles.red : null]}>
        {item.time}
      </RemainingTime>
    </Content>
  );
}

function Taxi({ navigation: { navigate } }) {
  const bottomSheetModalRef = useRef(null);

  const [isOpen, setIsOpen] = useState(false);

  const snapPoints = useMemo(() => [Platform.OS === "ios" ? "25%" : "30%"], []);

  const handlePresentModal = useCallback(() => {
    bottomSheetModalRef.current?.present();
    setIsOpen(true);
  }, []);

  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <BottomSheetModalProvider>
        <View style={[isOpen ? Styles.background : Styles.container]}>
          <HeaderContainer style={[isOpen ? Styles.background : null]}>
            <Title>
              같이 {highlightTaxi("택시", isOpen)} 시킬 사람 구해요!
            </Title>
            <SubTitle style={[isOpen ? Styles.gray : null]}>
              택시 파티를 모집 하거나 참여 해보세요
            </SubTitle>
            <Hr style={[isOpen ? Styles.hr : null]} />
          </HeaderContainer>
          <BodyContainer>
            <SectionList
              //contentContainerStyle={{ paddingHorizontal: 20}}
              stickySectionHeadersEnabled={false}
              sections={DATA}
              renderSectionHeader={({ section }) => (
                <>
                  <SubHeading>{section.subTitle}</SubHeading>
                  <Heading>{section.title}</Heading>
                  <FlatList
                    horizontal
                    data={section.data}
                    renderItem={({ item }) => (
                      <ListItem item={item} isOpen={isOpen} />
                    )}
                    showsHorizontalScrollIndicator={false}
                  />
                </>
              )}
              renderItem={({ item, section }) => {
                return null;
              }}
              bottomInset={8}
              detached={true}
              onDismiss={() => setIsOpen(false)}
            >
              <ModalContainer>
                <ModalTitle>어떤 파티를 만들까요?</ModalTitle>
                <TouchableOpacity
                    onPress={() => {
                      navigate("TaxiNmap");
                    }}
                  >
                <Row>
                    <Fontisto name="taxi" size={15} color="rgb(255,211,26)"/>
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
                
                  <Ionicons name="thumbs-up" size={20} color="rgb(48,52,63)"/>
                  <ModalText>카풀</ModalText>
                  
                </Row>
                </TouchableOpacity>
              </ModalContainer>
            </BottomSheetModal>
          
        </BottomContainer>

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
                  navigate("TaxiNmap");
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
        </View>
      </BottomSheetModalProvider>
    </GestureHandlerRootView>
  );
}
export default Taxi;
