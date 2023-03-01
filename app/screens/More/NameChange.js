import React, { useState } from "react";
import { Text, View, TouchableOpacity, TextInput } from "react-native";
import styled from "styled-components/native";

const Container = styled.View`
  padding-top: 10px;
  background-color: white;
  height: 100%;
  width: 100%;
`;

const SubContainer = styled.View`
  padding-top: 10px;
  background-color: white;
  height: 100%;
  width: 100%;
  padding-left: 30px;
  padding-right: 30px;
`;

const MoreView = styled.View`
  background-color: white;
  align-items: center;
  justify-content: center;
  height: 60px;
`;

const NameInput = styled.TextInput`
  background-color: #f5f6f8;
  align-items: center;
  justify-content: flex-start;
  height: 45px;
  border-radius: 10px;
  margin-top: 5px;
`;

const MoreText = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
`;

const NullNameText = styled.Text`
  font-size: 15px;
  color: #9c9c9c;
`;

const NoticeText = styled.Text`
  margin-top: 5px;
  font-size: 13px;
  color: #ff9797;
`;

const ChangeBt = styled.TouchableOpacity`
  background-color: #7974e7;
  align-items: center;
  justify-content: center;
  height: 45px;
  border-radius: 10px;
  margin-top: 30px;
`;

const NoneChangeBt = styled.View`
  background-color: #bdbbf3;
  align-items: center;
  justify-content: center;
  height: 45px;
  border-radius: 10px;
  margin-top: 30px;
`;

function NameChange({ navigation, route }) {
  const [notice, setNotice] = useState("");
  const [change, setChange] = useState(false);

  const strNum = values => {
    if (values == route.params.name) {
      setChange(false);
    }

    if (values.length > 5) {
      setNotice("닉네임은 최대 5글자 입니다.");
      setChange(false);
    } else if (values.length == 0) {
      setNotice("닉네임은 최소 1글자 입니다.");
      setChange(false);
    } else {
      setNotice("");
      setChange(true);
    }
  };

  return (
    <Container>
      <MoreView>
        <MoreText>닉네임 수정</MoreText>
      </MoreView>
      <SubContainer>
        <Text>닉네임</Text>
        <NameInput onChangeText={strNum}>
          <Text>{route.params.name}</Text>
        </NameInput>
        <NoticeText>{notice}</NoticeText>
        {change == true ? (
          <ChangeBt
            onPress={() => {
              navigation.goBack();
            }}
          >
            <Text style={{ color: "white", fontSize: 15 }}>수정 완료</Text>
          </ChangeBt>
        ) : (
          <NoneChangeBt>
            <Text style={{ color: "white", fontSize: 15 }}>수정 완료</Text>
          </NoneChangeBt>
        )}
      </SubContainer>
    </Container>
  );
}

export default NameChange;
