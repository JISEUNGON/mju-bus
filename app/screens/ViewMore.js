import React, { useState } from "react";
import { Text, View, TouchableOpacity } from "react-native";
import styled from "styled-components/native";
import { Foundation } from "@expo/vector-icons";
import Modal from "react-native-modal";
import {
  AntDesign,
  Feather,
  Ionicons,
  MaterialIcons,
} from "@expo/vector-icons";

const Container = styled.View`
  background-color: white;
  height: 100%;
  width: 100%;
`;

const Loginview = styled.View`
  background-color: white;
  flex-direction: row;
  align-items: center;
  margin-left: 15px;
`;

const Image = styled.View`
  border-radius: 100px;
  height: 50px;
  width: 50px;
  background-color: greenyellow;
  align-items: flex-end;
  justify-content: flex-end;
`;

const MoreView = styled.View`
  background-color: white;
  align-items: center;
  justify-content: center;
  height: 60px;
`;

const BtContainer = styled.TouchableOpacity`
  background-color: white;
  align-items: center;
  justify-content: flex-start;
  flex-direction: row;
  height: 60px;
  border-bottom-width: 0.6px;
  border-bottom-color: #d3d7dc;
  padding-left: 10px;
`;

const NameChangeBt = styled.TouchableOpacity`
  background-color: white;
  border-radius: 10px;
  height: 20px;
  width: 80px;
  border-color: #d3d7dc;
  border-width: 1px;
  align-items: center;
  justify-content: center;
  margin-left: 10px;
`;

const BtText = styled.Text`
  font-family: "SpoqaHanSansNeo";
  font-size: 15px;
  margin-left: 10px;
`;

const ImageChangeBt = styled.TouchableOpacity`
  background-color: white;
  width: 20px;
  height: 20px;
  border-radius: 100px;
  justify-content: center;
  align-items: center;
`;

const MoreText = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
`;

// ----------
// Modal
const ModalContainer = styled.View`
  background-color: white;
  height: auto;
  width: 100%;
`;

const ModalBt = styled.TouchableOpacity`
  background-color: white;
  width: 100%;
  height: 60px;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  flex-direction: row;
`;

const ModalText = styled.Text`
  font-size: 17px;
  color: blue;
`;

function ViewMore({ navigation }) {
  const name = "무궁화삼천리";
  const [login, setLogin] = useState(true);
  const [isModal, setIsModal] = useState(false);

  return login == true ? (
    <Container>
      <MoreView>
        <MoreText>더보기</MoreText>
      </MoreView>
      <Loginview>
        <Image>
          <ImageChangeBt onPress={() => setIsModal(true)}>
            <Foundation name="pencil" size={15} color="black" />
          </ImageChangeBt>
        </Image>
        <Text style={{ marginLeft: 10, fontSize: 25 }}>{name}</Text>
        <NameChangeBt
          onPress={() => navigation.navigate("NameChange", { name: name })}
        >
          <Text style={{ fontSize: 10 }}>닉네임 수정</Text>
        </NameChangeBt>
      </Loginview>
      <BtContainer>
        <Feather name="bell" size={24} color="black" />
        <BtText>공지사항</BtText>
      </BtContainer>
      <BtContainer>
        <Ionicons name="key-outline" size={24} color="black" />
        <BtText>약관 및 정책</BtText>
      </BtContainer>
      <BtContainer>
        <AntDesign name="user" size={24} color="black" />
        <BtText>계정탈퇴</BtText>
      </BtContainer>
      <BtContainer>
        <MaterialIcons name="logout" size={24} color="black" />
        <BtText>로그아웃</BtText>
      </BtContainer>
      <View>
        <Modal isVisible={isModal} style={{ justifyContent: "flex-end" }}>
          <ModalContainer>
            <ModalBt>
              <ModalText>프로필 이미지 변경</ModalText>
            </ModalBt>
            <ModalBt>
              <ModalText>프로필 이미지 삭제</ModalText>
            </ModalBt>
            <ModalBt onPress={() => setIsModal(false)}>
              <ModalText>취소</ModalText>
            </ModalBt>
          </ModalContainer>
        </Modal>
      </View>
    </Container>
  ) : (
    <Container>
      <MoreView>
        <MoreText>더보기</MoreText>
      </MoreView>
      <Loginview>
        <Image />
        <Text style={{ marginLeft: 10, fontSize: 25 }}>로그인 해주세요</Text>
        <NameChangeBt
        // onPress={() => navigation.navigate("NameChange", { name: name })}
        >
          <Text style={{ fontSize: 10 }}>소셜 로그인</Text>
        </NameChangeBt>
      </Loginview>
      <BtContainer>
        <Feather name="bell" size={24} color="black" />
        <BtText>공지사항</BtText>
      </BtContainer>
      <BtContainer>
        <Ionicons name="key-outline" size={24} color="black" />
        <BtText>약관 및 정책</BtText>
      </BtContainer>
    </Container>
  );
}

export default ViewMore;
