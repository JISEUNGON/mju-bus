import React from "react";
import {
  Dimensions,
  StyleSheet,
  View,
  FlatList,
  Text,
} from "react-native";
import styled from "styled-components";
import {
  Octicons,
} from "@expo/vector-icons";
import Profile from "../../components/Profile";
import PartyData from "./PartyData";
import PartyInformation from "./PartyInformation";

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

const ListContainer = styled.View`
  margin-left: 15px;
  background-color: beige;
`

const Styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
});



export function highlightTaxi(contents) {
  //
  return (
    <HighlightTaxi>
      {contents}
    </HighlightTaxi>
  );
}

function ListItem({ item }) {
  return (
    <Content>
      <Board>
        <Row>
          <Octicons name="person-fill" size={15} color="gray" />
          <NumOfPerson>
            {item.numOfPerson}/{item.MaxPerson}
          </NumOfPerson>
        </Row>
        <ProfileContent>
          <Profile />
        </ProfileContent>
        <Column>
          <ContentTitle>{item.start}에서</ContentTitle>
          <ContentTitle>{item.dest}로</ContentTitle>
        </Column>
      </Board>
      <PartyTitle>{item.nickname}의 파티</PartyTitle>
      <RemainingTime>{item.time}</RemainingTime>
    </Content>
  );
}

function Participation({ route: { params }, navigation: { navigate } }) {

  return (
      <View style={Styles.container}>
        <HeaderContainer >
          <Title>같이 {highlightTaxi("택시")} 시킬 사람 구해요!</Title>
          <SubTitle>택시 파티를 모집 하거나 참여 해보세요</SubTitle>
          <Hr />
        </HeaderContainer>
        
        <ListContainer>
          <FlatList
            data={PartyInformation}

          
          />
        </ListContainer>

        
      </View>
  );
}
export default Participation;
