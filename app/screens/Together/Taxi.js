import React, {
  useState,
  useRef,
  useCallback,
  useMemo,
  useEffect,
} from "react";
import {
  Dimensions,
  StyleSheet,
  TouchableOpacity,
  Platform,
  View,
  Text,
  ActivityIndicator,
  Circle,
} from "react-native";
import {
  FlatList,
  RefreshControl,
  ScrollView,
} from "react-native-gesture-handler";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import {
  BottomSheetModal,
  BottomSheetModalProvider,
} from "@gorhom/bottom-sheet";
import styled from "styled-components";
import {
  Fontisto,
  MaterialIcons,
  Ionicons,
  Octicons,
} from "@expo/vector-icons";
import Icon from "react-native-vector-icons/AntDesign";
import { taxiApi } from "../../api";
import { useQueries, useQuery, useQueryClient } from "@tanstack/react-query";
import UserAvatar from "react-native-user-avatar";
import Loader from "../../components/Loader";
import HList from "../../components/HList";
import TaxiTabs from "../../navigation/TaxiDetailTabs";
import { useNavigation } from "@react-navigation/native";

const { width: SCREEN_WIDTH } = Dimensions.get("window");

const HeaderContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  //height: 60%;
  height: 140px;
  justify-content: center;
  //background-color: ${props => props.theme.scheduleBgColor};
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

const Content = styled.View`
  flex-direction: column;
  margin-bottom: 25px;
  //background-color: beige;
`;
const Board = styled.View`
  width: 140px;
  height: 125px;
  background-color: ${props => props.theme.taxiPartyColor};
  padding: 20px 20px;
  border-radius: 20px;
  flex-direction: column;
  margin-bottom: 10px;
  margin-top: 5px;
`;
const Row = styled.View`
  flex-direction: row;
  align-items: baseline;
  justify-content: flex-end;
`;

const ProfileContent = styled.View`
  width: 60px;
  top: -15px;
  align-items: flex-start;
`;

const NumOfPerson = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 15px;
  color: #737882;
  margin-left: 3px;
`;

const Column = styled.View`
  margin-top: 27px;
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
  margin-bottom: 15px;
`;

const separator = styled.View`
  //argin-right: 10px;
  width: 15px;
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
  margin-bottom: 20px;
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
    backgroundColor: "#979797",
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
    color: "#773134",
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

export function TaxiTimer({ item, isOpen }) {
  const time = item.end_at.substring(11, 19);
  const endTime = time.split(":");
  const timerId = useRef(null);

  const [hour, setHour] = useState("");
  const [min, setMin] = useState("");
  const [sec, setSec] = useState("");
  const [remain, setRemain] = useState("");
  //let remain = 0;
  const [present, setPresent] = useState("");
  const date = new Date();

  useEffect(() => {
    timerId.current = setInterval(() => {
      setPresent(
        date.getHours() * 3600 + date.getMinutes() * 60 + date.getSeconds(),
      );
      setRemain(endTime[0] * 3600 + endTime[1] * 60 + endTime[2] * 1 - present);
      setHour(Math.floor(remain / 3600));
      setMin(Math.floor((remain % 3600) / 60));
      setSec(Math.floor((remain % 3600) % 60));
    }, 1000);
    return () => clearInterval(timerId.current);
  }, [hour, min, sec]);
  return (
    <RemainingTime style={[isOpen ? Styles.red : null]}>
      {hour}시간 {min}분 {sec}초 남음
    </RemainingTime>
  );
}

export function RealTimePerson({ item, isOpen }) {
  const [maxMember, setMaxMember] = useState("");
  const [currMember, setCurrMember] = useState("");
  const personId = useRef(null);

  useEffect(() => {
    personId.current = setInterval(() => {
      setMaxMember(item.max_member);
      setCurrMember(item.curr_member);
    }, 1000);
    return () => clearInterval(personId.current);
  }, [maxMember, currMember]);

  return (
    <NumOfPerson style={[isOpen ? Styles.gray : null]}>
      {currMember}/{maxMember}
    </NumOfPerson>
  );
}

function ListItem({ item, isOpen, num }) {
  const [location, setLocation] = useState(null);

  useEffect(() => {
    fetch(`http://staging-api.mju-bus.com/taxi/${item.id}/location`)
      .then(res => res.json())
      .then(data => setLocation(data.name));
  }, []);

  if (num == 1) {
    if (location == "구갈동" || location == "기흥역") {
      return (
        <Content>
          <Board style={[isOpen ? Styles.purple : null]}>
            <Row>
              <Octicons name="person-fill" size={15} color="#737882" />
              <RealTimePerson item={item} isOpen={isOpen} />
            </Row>
            <ProfileContent>
              <UserAvatar
                size={20}
                src={item.administer.profile}
                bgColor={"#00ff0000"}
                style={[isOpen ? Styles.gray : null]}
              />
            </ProfileContent>
            <Column>
              <ContentTitle style={[isOpen ? Styles.title : null]}>
                {location}에서
              </ContentTitle>
              <ContentTitle style={[isOpen ? Styles.title : null]}>
                학교로
              </ContentTitle>
            </Column>
          </Board>
          <PartyTitle>{item.administer.name}의 파티</PartyTitle>
          <TaxiTimer item={item} isOpen={isOpen} />
        </Content>
      );
    } else {
      return null;
    }
  } else {
    if (location !== "구갈동" && location !== "기흥역") {
      return (
        <Content>
          <Board style={[isOpen ? Styles.purple : null]}>
            <Row>
              <Octicons name="person-fill" size={15} color="#737882" />
              <RealTimePerson item={item} isOpen={isOpen} />
            </Row>
            <ProfileContent>
              <UserAvatar
                size={20}
                src={item.administer.profile}
                bgColor={"#00ff0000"}
                style={[isOpen ? Styles.gray : null]}
              />
            </ProfileContent>
            <Column>
              <ContentTitle style={[isOpen ? Styles.title : null]}>
                {location}에서
              </ContentTitle>
              <ContentTitle style={[isOpen ? Styles.title : null]}>
                학교로
              </ContentTitle>
            </Column>
          </Board>
          <PartyTitle>{item.administer.name}의 파티</PartyTitle>
          <TaxiTimer item={item} />
        </Content>
      );
    } else {
      return null;
    }
  }
}

function Taxi({ navigation: { navigate } }) {
  const navigation = useNavigation();
  const bottomSheetModalRef = useRef(null);

  const [isOpen, setIsOpen] = useState(false);

  const snapPoints = useMemo(() => [Platform.OS === "ios" ? "18%" : "30%"], []);

  const handlePresentModal = useCallback(() => {
    bottomSheetModalRef.current?.present();
    setIsOpen(true);
  }, []);

  const queryClient = useQueryClient();

  const {
    isLoading: taxiListLoading,
    data: taxiListData,
    isRefetching: isRefetchingTaxiList,
  } = useQuery(["taxi", "taxiList"], taxiApi.taxiList);

  const onRefresh = async () => {
    queryClient.refetchQueries(["taxi"]);
  };

  const loading = taxiListLoading;
  const refreshing = isRefetchingTaxiList;

  return loading ? (
    <Loader />
  ) : (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <BottomSheetModalProvider>
        <ScrollView
          style={Styles.container}
          refreshControl={
            <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
          }
          contentContainerStyle={{}}
        >
          <HeaderContainer style={[isOpen ? Styles.background : null]}>
            <Title>같이 {highlightTaxi("택시", isOpen)} 탈 사람 구해요!</Title>
            <SubTitle style={[isOpen ? Styles.gray : null]}>
              택시 파티를 모집 하거나 참여 해보세요
            </SubTitle>
            <Hr style={[isOpen ? Styles.hr : null]} />
          </HeaderContainer>
          <HList title="기흥 파티" subtitle="지각생 다 모여!" isOpen={isOpen} />
          <FlatList
            style={[isOpen ? Styles.background : null]}
            horizontal
            data={taxiListData.taxiPartyList}
            showsHorizontalScrollIndicator={false}
            contentContainerStyle={{ paddingHorizontal: -20 }}
            ItemSeparatorComponent={separator}
            inverted={true}
            renderItem={({ item }) => (
              <TouchableOpacity
                onPress={() => {
                  navigation.navigate("TaxiTabs", { item: item });
                }}
              >
                <ListItem item={item} isOpen={isOpen} num={1} />
              </TouchableOpacity>
            )}
          />
          <HList title="기흥 외 파티" subtitle="머니까 같이!" isOpen={isOpen} />
          <FlatList
            style={[isOpen ? Styles.background : null]}
            horizontal
            data={taxiListData.taxiPartyList}
            showsHorizontalScrollIndicator={false}
            contentContainerStyle={{ paddingHorizontal: 20 }}
            ItemSeparatorComponent={separator}
            renderItem={({ item }) => (
              <TouchableOpacity
                onPress={() => {
                  navigation.navigate("TaxiTabs", { item: item });
                }}
              >
                <ListItem item={item} isOpen={isOpen} num={2} />
              </TouchableOpacity>
            )}
          />

          <TouchableOpacity style={Styles.circle} onPress={handlePresentModal}>
            <Icon name="plus" size={18} color="white" active />
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
                    screen: "TaxiNmap",
                  });
                }}
              >
                <ModalRow>
                  <Fontisto name="taxi" size={15} color="rgb(255,211,26)" />
                  <ModalText>택시</ModalText>
                </ModalRow>
              </TouchableOpacity>
            </ModalContainer>
          </BottomSheetModal>
        </ScrollView>
      </BottomSheetModalProvider>
    </GestureHandlerRootView>
  );
}
export default Taxi;

{
  /* <TouchableOpacity
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
*/
}
