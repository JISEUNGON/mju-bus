/* eslint-disable react/prop-types */
import { View, Dimensions, Modal, StyleSheet } from "react-native";
import styled from "styled-components";
import BouncyCheckbox from "react-native-bouncy-checkbox";
import React, { useState } from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";

const { height: SCREEN_HEIGHT, width: SCREEN_WIDTH } = Dimensions.get("window");

const STORAGE_KEY = "@routes";

const ScreenContainer = styled.View`
  height: ${SCREEN_HEIGHT}px;
  justify-content: flex-end;
`;

const Container = styled.View`
  background-color: white;
  border-radius: 20px;
  width: ${SCREEN_WIDTH}px;
  height: ${SCREEN_HEIGHT * 0.8}px;
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
  justify-content: space-between;
  flex-direction: row;
`;

const ListContents = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
`;

const SubmitContainer = styled.View`
  align-items: center;
  padding: 20px 20px;
  margin-bottom: 10px;
`;

const Submit = styled.TouchableOpacity`
  height: 70px;
  width: 100%;
  border-radius: 20px;
  background-color: #7974e7;
  align-items: center;
  justify-content: center;
`;
const SubmitText = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  color: white;
  font-size: 18px;
`;

function RouteSelect(props) {
  const { data, selectedRoutes, modalVisible, setModalVisible } = props;
  const [checkedItems, setcheckedItems] = useState(selectedRoutes);

  const clearCheckedRoute = () => {
    setcheckedItems([]);
  };

  const unSelectedRoutes = selectedList => {
    let restRouteList = [];
    // eslint-disable-next-line array-callback-return
    data.map(route => {
      const { id } = route;
      const result = selectedList.findIndex(item => id === item.id);
      if (result === -1) {
        restRouteList = [...restRouteList, route];
      }
    });
    return restRouteList;
  };

  const addRoutes = item => {
    setcheckedItems([...checkedItems, item]);
  };

  const deleteRoutes = item => {
    const newCheckItems = [...checkedItems];
    setcheckedItems(newCheckItems.filter(checked => checked.id !== item.id));
  };

  const onSubmit = async () => {
    await AsyncStorage.setItem(STORAGE_KEY, JSON.stringify(checkedItems));
    clearCheckedRoute();
    setModalVisible(false);
  };

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
            <Title>노선을 선택하세요</Title>
            <SubTitle>메인화면에 표시할 통학버스 노선을 선택하세요!</SubTitle>
          </TitleContainer>
          <SelectionContainer>
            <ListContainer>
              <ListTitle>선택된 노선</ListTitle>
              {selectedRoutes.map(item => (
                <ListItem key={item.id}>
                  <ListContents>{item.name}</ListContents>
                  <BouncyCheckbox
                    size={25}
                    isChecked
                    fillColor="#7974E7"
                    onPress={isChecked => {
                      if (isChecked) {
                        addRoutes(item);
                      } else if (!isChecked) {
                        deleteRoutes(item);
                      }
                    }}
                  />
                </ListItem>
              ))}
            </ListContainer>
            <ListContainer>
              <ListTitle>그 외 노선</ListTitle>
              {unSelectedRoutes(selectedRoutes)?.map(item => (
                <ListItem key={item.id}>
                  <ListContents>{item.name}</ListContents>
                  <BouncyCheckbox
                    size={25}
                    fillColor="#7974E7"
                    onPress={isChecked => {
                      if (isChecked) {
                        addRoutes(item);
                      } else if (!isChecked) {
                        deleteRoutes(item);
                      }
                    }}
                  />
                </ListItem>
              ))}
            </ListContainer>
          </SelectionContainer>
          <SubmitContainer>
            <Submit onPress={onSubmit}>
              <SubmitText>선택 완료</SubmitText>
            </Submit>
          </SubmitContainer>
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

export default RouteSelect;
