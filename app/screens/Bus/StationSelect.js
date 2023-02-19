/* eslint-disable react/prop-types */
import { View, Dimensions, Modal, StyleSheet } from "react-native";
import styled from "styled-components";
import React, { useEffect, useState } from "react";
import BouncyCheckbox from "react-native-bouncy-checkbox";
import { FontAwesome } from "@expo/vector-icons";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { KEY_FAVORITE_STATION } from "../StorageKey";

const { height: SCREEN_HEIGHT, width: SCREEN_WIDTH } = Dimensions.get("window");

const ScreenContainer = styled.View`
  height: ${SCREEN_HEIGHT}px;
  justify-content: flex-end;
  position: absolute;
`;

const Container = styled.View`
  background-color: ${props => props.theme.busCompColor};
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
  color: ${props => props.theme.mainTextColor};
`;

const SubTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 15px;
  color: ${props => props.theme.subTextColor};
  margin-top: 20px;
  margin-bottom: 20px;
`;

const SelectionContainer = styled.ScrollView``;

const ListContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  padding: 0px 30px;
  margin-bottom: 20px;
`;

const ListTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  color: ${props => props.theme.subTextColor};

  font-weight: 900;
  font-size: 16px;
`;

const ListItem = styled.View`
  padding: 20px 10px;
  width: 100%;
  flex-direction: row;
  flex: 1;
`;

const Contnets = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: ${props => props.theme.mainTextColor};
`;

const ListContents = styled.TouchableOpacity`
  flex: 5;
`;

const ListCheckBox = styled.View`
  justify-content: center;
  align-items: center;
  flex: 1;
`;

function StationSelect(props) {
  const {
    stations,
    modalVisible,
    setModalVisible,
    setStation,
  } = props;

  const [staredStation, setStaredStation] = useState([]);
  const [unstaredStation, setUnstaredStation] = useState([]);
  const [nextStaredStation, setNextStaredStation] = useState([]);

  // 즐겨찾기에 저장한 정류장 리스트를 가져온다.
  useEffect(() => {
    const loadSelectedRoutes = async () => {
      try {
        const favoriteStation = JSON.parse(await AsyncStorage.getItem(KEY_FAVORITE_STATION));
        if (favoriteStation !== null) {
          setStaredStation(stations.filter(station => favoriteStation.includes(station.id))); // 즐겨찾기에 저장한 정류장 리스트
          setNextStaredStation(stations.filter(station => favoriteStation.includes(station.id))); // 즐겨찾기에 저장할 정류장 리스트
          setUnstaredStation(stations.filter(station => !favoriteStation.includes(station.id))); // 즐겨찾기에 저장하지 않은 정류장 리스트
        }
      } catch (error) {
        console.log(error);
      }
    }

    loadSelectedRoutes();
  }, []);

  // 즐겨찾기에 정류장을 추가한다.
  const starStaion = station => {
    setNextStaredStation([...nextStaredStation, station]);
  };

  // 즐겨찾기에서 정류장을 삭제한다.
  const unstarStation = station => {
    setNextStaredStation(nextStaredStation.filter(stared => stared.id !== station.id));
  };

  // 즐겨찾기에 정류장을 추가하거나 삭제한다.
  const handleStar = (station) => {
    if (nextStaredStation.includes(station)) {
      unstarStation(station);
    } else {
      starStaion(station);
    }
  }

  return (
    <Modal animationType="slide" transparent visible={modalVisible}>
      <ScreenContainer>
        <View
          // eslint-disable-next-line no-use-before-define
          style={styles.blankSpace}
          onTouchEnd={async () => {
            await AsyncStorage.setItem(KEY_FAVORITE_STATION, JSON.stringify(nextStaredStation.map(station => station.id)));
            setModalVisible(false)
          }} // 모달 빈 공간을 누르면 창 닫기
        />
        <Container>
          <TitleContainer>
            <Title>정류장을 선택하세요</Title>
            <SubTitle>자주 찾는 정류장을 즐겨찾기에 추가하세요!</SubTitle>
          </TitleContainer>
          <SelectionContainer>
            <ListContainer>
              <ListTitle>즐겨찾는 정류장</ListTitle>
              {staredStation?.map(station => (
                <ListItem key={station.id}>
                  <ListContents
                    onPress={async () => { // 정류장을 선택한 경우
                      await AsyncStorage.setItem(KEY_FAVORITE_STATION, JSON.stringify(nextStaredStation.map(station => station.id)));
                      setModalVisible(false);
                      setStation(station);
                    }}
                  >
                    <Contnets>{station.name}</Contnets>
                  </ListContents>
                  <ListCheckBox>
                    <BouncyCheckbox
                      size={27}
                      fillColor="#FFA800"
                      unfillColor="#FFEDCB"
                      isChecked
                      innerIconStyle={{
                        borderColor: "white",
                      }}
                      icon
                      iconComponent={
                        <FontAwesome name="star" size={16} color="#FFEDCB" />
                      }
                      onPress={() => handleStar(station)} // 즐겨찾기 버튼 클릭한 경우
                    />
                  </ListCheckBox>
                </ListItem>
              ))}
            </ListContainer>
            <ListContainer>
              <ListTitle>그 외 정류장</ListTitle>
              {unstaredStation?.map(station => (
                <ListItem key={station.id}>
                  <ListContents
                    onPress={async () => { // 정류장을 선택한 경우
                      await AsyncStorage.setItem(KEY_FAVORITE_STATION, JSON.stringify(nextStaredStation.map(station => station.id)));
                      setModalVisible(false);
                      setStation(station);
                    }}
                  >
                    <Contnets>{station.name}</Contnets>
                  </ListContents>
                  <ListCheckBox>
                    <BouncyCheckbox
                      size={27}
                      fillColor="#FFA800"
                      unfillColor="#FFEDCB"
                      innerIconStyle={{
                        borderColor: "white",
                      }}
                      icon
                      iconComponent={
                        <FontAwesome name="star" size={16} color="#FFEDCB" />
                      }
                      onPress={() => handleStar(station)} // 즐겨찾기 버튼 클릭한 경우
                    />
                  </ListCheckBox>
                </ListItem>
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

export default StationSelect;
