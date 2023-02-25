import React, { useEffect, useState, useRef } from "react";
import {
  Dimensions,
  StyleSheet,
  View,
  FlatList,
  Text,
  ActivityIndicator,
  Image,
} from "react-native";
import styled from "styled-components";
import { Octicons } from "@expo/vector-icons";
import Profile from "../../components/Profile";
import PartyInformation from "./PartyInformation";
import { Entypo } from "@expo/vector-icons";
import { taxiApi } from "../../api";
import { useQueries, useQuery, useQueryClient } from "@tanstack/react-query";
import UserAvatar from "react-native-user-avatar";
import Loader from "../../components/Loader";

const { width: SCREEN_WIDTH } = Dimensions.get("window");

const HeaderContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0 20px;
  height: 20%;
  justify-content: center;
  background-color: ${props => props.theme.scheduleBgColor};
`;

const HighlightTaxi = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: #547649;
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

const Heading = styled(Title)`
  margin-left: 20px;
  margin-bottom: 25px;
`;

const Content = styled.View`
  flex-direction: column;
  margin-bottom: 25px;
  //margin-left: 15px;
`;

const Board = styled.View`
  width: 140px;
  height: 155px;
  background-color: #e6eef3;
  padding: 20px 20px;
  border-radius: 20px;
  flex-direction: column;
  margin-bottom: 10px;
  margin-left: 15px;
`;

const BodyContainer = styled.View`
  width: 100%;
  height: 80%;
  //background-color: beige;
`;

const SubText = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 14px;
  margin-top: 10px;
  color: #565b64;
`;
const IconContent = styled.View`
  align-items: flex-end;
  left: 10;
`;
const HeadText = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 17px;
  margin-top: -10px;
`;

const TitleContainer = styled.View`
  width: 100%;
`;
const SubHeading = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 13px;
  color: #505866;
  margin-left: 15px;
`;

const PartyBoard = styled.View`
  width: 140px;
  height: 120px;
  background-color: ${props => props.theme.taxiFrameColor};
  padding: 20px 20px;
  border-radius: 20px;
  flex-direction: column;
  margin-bottom: 10px;
  //margin-left: 15px;
`;
const Row = styled.View`
  flex-direction: row;
  justify-content: flex-end;
  align-items: baseline;
`;
const NumOfPerson = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 15px;
  color: #547649;
  margin-left: 3px;
`;

const ProfileContent = styled.View`
  width: 60px;
  top: -23px;
  align-items: flex-start;
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

const separator = styled.View`
  width: 15px;
`;
const Styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
});

export function highlightTaxi(contents) {
  //
  return <HighlightTaxi>{contents}</HighlightTaxi>;
}

function LinkParty() {
  return (
    <>
      <Board>
        <ProfileContent>
          <Profile />
        </ProfileContent>
        <SubText>잘 모이고 있나?</SubText>
        <IconContent>
          <Entypo name="chevron-right" size={20} color="#C4C4C4" />
        </IconContent>
        <HeadText>파티 정보{"\n"}바로 가기</HeadText>
      </Board>
    </>
  );
}

function ListItem({ item }) {
  return (
    <>
      <LinkParty />
      <Board></Board>
      <Board></Board>
      <Board></Board>
    </>
  );
}

export function TaxiTimer({ item }) {
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
    <RemainingTime>
      {hour}시 {min}분 {sec}초 남음
    </RemainingTime>
  );
}

function renderItem({ item }) {
  let text = item.meeting_place;
  if (
    text == "경기 용인시 기흥구 구갈동 660-1" ||
    text == "경기 용인시 기흥구 구갈동 657-3"
  ) {
    text = "기흥역";
  } else {
    text = text.match(/([가-힣]+(읍|동)\s)/g);
    // if(text == null){
    //   text = text.match
    // }
  }
  return (
    <Content>
      <PartyBoard>
        <Row>
          <Octicons name="person-fill" size={15} color="#547649" />
          <NumOfPerson>
            {item.curr_member}/{item.max_member}
          </NumOfPerson>
        </Row>
        <ProfileContent>
          <UserAvatar
            size={20}
            src={item.administer.profile}
            bgColor={"#00ff0000"}
          />
        </ProfileContent>
        <Column>
          <ContentTitle>{text}에서</ContentTitle>
          <ContentTitle>학교로</ContentTitle>
        </Column>
      </PartyBoard>
      <PartyTitle>{item.administer.name}의 파티</PartyTitle>
      <TaxiTimer item={item} />
    </Content>
  );
}

function Participation({ route: { params }, navigation: { navigate } }) {
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
    <View style={Styles.container}>
      <HeaderContainer>
        <Title>같이 {highlightTaxi("택시")} 탈 사람 구해요!</Title>
        <SubTitle>택시 파티를 모집 하거나 참여 해보세요</SubTitle>
        <Hr />
      </HeaderContainer>
      <BodyContainer>
        <Heading>참여 중인 파티</Heading>
        <FlatList
          horizontal
          data={PartyInformation}
          showsHorizontalScrollIndicator={false}
          renderItem={({ item }) => <ListItem item={item} />}
        />
        <TitleContainer>
          <SubHeading>지금! 바로!</SubHeading>
          <Heading>마감 임박 파티</Heading>
        </TitleContainer>
        <FlatList
          onRefresh={onRefresh}
          refreshing={refreshing}
          horizontal
          data={taxiListData.taxiPartyList}
          showsHorizontalScrollIndicator={false}
          contentContainerStyle={{ paddingHorizontal: 20 }}
          ItemSeparatorComponent={separator}
          renderItem={renderItem}
        />
      </BodyContainer>
    </View>
  );
}
export default Participation;
