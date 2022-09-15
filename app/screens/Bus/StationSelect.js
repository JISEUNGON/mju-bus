/* eslint-disable react/prop-types */
import { View, Dimensions, Modal, StyleSheet } from "react-native";
import styled from "styled-components";
import React, { useState } from "react";
import BouncyCheckbox from "react-native-bouncy-checkbox";
import { FontAwesome } from "@expo/vector-icons";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { RemoveDuplicateStation, RemoveHiddenStation } from "../../utils";

const { height: SCREEN_HEIGHT, width: SCREEN_WIDTH } = Dimensions.get("window");

const ScreenContainer = styled.View`
  height: ${SCREEN_HEIGHT}px;
  justify-content: flex-end;
  position: absolute;
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

const ListTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  color: gray;
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
    data,
    staredStation,
    storageKey,
    modalVisible,
    setModalVisible,
    setStation,
    toSchool,
  } = props;

  const [checkedStation, setCheckedStation] = useState(staredStation);

  const unSelectedStations = (stationList, staredStationList) => {
    let restStationList = [];
    // eslint-disable-next-line array-callback-return
    stationList?.map(station => {
      const { id } = station;
      const result = staredStationList.findIndex(item => id === item?.id);
      if (result === -1) {
        restStationList = [...restStationList, station];
      }
    });
    return restStationList;
  };

  const addStation = item => {
    setCheckedStation([...checkedStation, item]);
  };

  const deletStation = item => {
    const newCheckItems = [...checkedStation];
    setCheckedStation(newCheckItems.filter(checked => checked.id !== item.id));
  };

  const preStationList = RemoveDuplicateStation(data);
  const stationList = RemoveHiddenStation(preStationList, toSchool);
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
            <SubTitle>자주 찾는 정류장을 즐겨찾기에 추가하세요!</SubTitle>
          </TitleContainer>
          <SelectionContainer>
            <ListContainer>
              <ListTitle>즐겨찾는 정류장</ListTitle>
              {staredStation?.map(item => (
                <ListItem key={item?.id}>
                  <ListContents
                    onPress={async () => {
                      await AsyncStorage.setItem(
                        storageKey,
                        JSON.stringify(checkedStation),
                      );
                      setModalVisible(false);
                      setStation(item);
                    }}
                  >
                    <Contnets>{item?.name}</Contnets>
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
                      onPress={isChecked => {
                        if (isChecked) {
                          addStation(item);
                        } else if (!isChecked) {
                          deletStation(item);
                        }
                      }}
                    />
                  </ListCheckBox>
                </ListItem>
              ))}
            </ListContainer>
            <ListContainer>
              <ListTitle>그 외 정류장</ListTitle>
              {unSelectedStations(stationList, staredStation)?.map(item => (
                <ListItem key={item?.id}>
                  <ListContents
                    onPress={async () => {
                      await AsyncStorage.setItem(
                        storageKey,
                        JSON.stringify(checkedStation),
                      );
                      setModalVisible(false);
                      setStation(item);
                    }}
                  >
                    <Contnets>{item?.name}</Contnets>
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
                        <FontAwesome name="star" size={20} color="#FFEDCB" />
                      }
                      onPress={isChecked => {
                        if (isChecked) {
                          addStation(item);
                        } else if (!isChecked) {
                          deletStation(item);
                        }
                      }}
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
