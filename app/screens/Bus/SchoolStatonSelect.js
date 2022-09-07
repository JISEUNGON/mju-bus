import {
  View,
  Dimensions,
  Modal,
  StyleSheet,
  TouchableOpacity,
} from "react-native";
import styled from "styled-components";
import React from "react";
import { stationId } from "../../id";

const { height: SCREEN_HEIGHT, width: SCREEN_WIDTH } = Dimensions.get("window");

const ScreenContainer = styled.View`
  height: ${SCREEN_HEIGHT}px;
  justify-content: flex-end;
`;

const Container = styled.View`
  background-color: white;
  border-radius: 20px;
  width: ${SCREEN_WIDTH}px;
  height: ${SCREEN_HEIGHT * 0.8}px;
  border-bottom-right-radius: 0px;
  border-bottom-left-radius: 0px;
`;

const TitleContainer = styled.View`
  padding: 0px 30px;
`;

const Title = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 27px;
  margin-top: 40px;
`;

const SubTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 15px;
  color: gray;
  margin-top: 20px;
  margin-bottom: 20px;
`;

const SelectionContainer = styled.ScrollView``;

const ListContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0px 30px;
  margin-bottom: 20px;
`;

const Contents = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
`;

const ListItem = styled.View`
  padding: 20px 10px;
  width: 100%;
  flex-direction: row;
  flex: 1;
`;

function SchoolStationSelect(props) {
  // eslint-disable-next-line react/prop-types
  const { modalVisible, setModalVisible, station, navigate } = props;

  const schoolStations = [
    stationId.MyongHyunGwan,
    stationId.HamBakGwan,
    stationId.Maingate,
  ];

  return (
    <Modal animationType="slide" transparent visible={modalVisible}>
      <ScreenContainer>
        <View
          // eslint-disable-next-line no-use-before-define
          style={styles.blankSpace}
          onTouchEnd={() => setModalVisible(false)} // 모달 빈 공간을 누르면 창 닫기
        />
        <Container>
          <TitleContainer>
            <Title>정류장을 선택하세요</Title>
            <SubTitle>출발할 학교 세부 정류장을 선택해주세요</SubTitle>
          </TitleContainer>
          <SelectionContainer>
            <ListContainer>
              {schoolStations.map(item => (
                <TouchableOpacity
                  key={item.id}
                  onPress={() => {
                    setModalVisible(false);
                    navigate("SearchStack", {
                      screen: "BusList",
                      params: {
                        toSchool: false,
                        src: item,
                        dest: station,
                      },
                    });
                  }}
                >
                  <ListItem>
                    <Contents>{item.name}</Contents>
                  </ListItem>
                </TouchableOpacity>
              ))}
            </ListContainer>
          </SelectionContainer>
        </Container>
      </ScreenContainer>
    </Modal>
  );
}
const styles = StyleSheet.create({
  blankSpace: {
    position: "absolute",
    width: SCREEN_WIDTH,
    height: SCREEN_HEIGHT,
    backgroundColor: "#000000",
    opacity: 0.8,
  },
});

export default SchoolStationSelect;
