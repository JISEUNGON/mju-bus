import React, { useContext, useEffect, useState } from "react";
import { Text, TouchableOpacity, Dimensions, AsyncStorage } from "react-native";
import styled from "styled-components/native";
import UserAvatar from "react-native-user-avatar";
import { TaxiChatContext } from "./Taxicontext";

const Container = styled.View`
  flex: 1;
  background-color: white;
`;

const TopClearView = styled.View`
  flex: 1;
`;

const ProfileView = styled.View`
  flex: 0.5;

  justify-content: center;
`;

const MainTextView = styled.View`
  flex: 0.5;

  justify-content: center;
`;

const TimeTextView = styled.View`
  flex: 0.3;

  justify-content: center;
`;

const ButtonView = styled.View`
  flex: 1.7;

  justify-content: center;
  align-items: center;
`;

const MainText = styled.Text`
  font-size: 27px;
  font-weight: bold;
  text-align: center;
  font-family: "SpoqaHanSansNeo-Bold";
`;

const BottomClearView = styled.View`
  flex: 0.4;
`;

function Chat({ route }) {
  const item = route.params.item;

  // Context 변수 선언
  const { join, setJoin, out, setOut } = useContext(TaxiChatContext);
  const windowHeight = Dimensions.get("window").height;

  const ChattingOnOff = () => {
    setOut(!out);
    timeoutId = setTimeout(() => {
      setJoin(!join);
    }, 100);

    // AsyncStorage에 join 및 out 변수 값을 저장한다.
    if (join !== true) {
      AsyncStorage.setItem(`room${item.id}`, true.toString());
    }
    // AsyncStorage.setItem("out", (!out).toString());
  };
  useEffect(() => {
    join === true ? setOut(true) : setOut(false);
  }, []);

  // api 호출부분
  const [data, setData] = useState(null);
  const [diffHours, setDiffHours] = useState(null);
  const [diffMinutes, setDiffMinutes] = useState(null);
  useEffect(() => {
    fetch(`http://staging-api.mju-bus.com:80/taxi/${item.id}/`)
      .then(res => res.json())
      .then(data => setData(data));
  }, []);

  // timedata 가공 부분
  useEffect(() => {
    if (data !== null) {
      let dateStr = data.end_at;
      let date = new Date(dateStr);

      let presentTime = new Date();
      let diffTime = date - presentTime;

      setDiffMinutes(Math.floor(diffTime / 1000 / 60) % 60);
      setDiffHours(Math.floor(diffTime / 1000 / 60 / 60));
    }
  }, [data]);

  // 참가후 메시지 보내기 버튼 asyncstorage부분
  useEffect(() => {
    AsyncStorage.getItem(`room${item.id}`).then(value => {
      if (value === null) {
        setJoin(false);
        setOut(false);
      } else {
        setJoin(true);
        setOut(true);
      }
    });
  }, []);

  return (
    <>
      {item !== null ? (
        <Container>
          <TopClearView></TopClearView>
          <ProfileView>
            <UserAvatar
              size={windowHeight > 700 ? 60 : 50}
              src={item.administer.profile}
              bgColor="white"
            />
          </ProfileView>
          <MainTextView>
            <MainText>{item.administer.name}님의</MainText>
            <MainText> 택시 파티에 참여하시겠어요?</MainText>
          </MainTextView>
          <TimeTextView>
            <Text
              style={{
                color: "#929292",
                textAlign: "center",
                fontSize: 16,
                fontFamily: "SpoqaHanSansNeo-Medium",
              }}
            >
              모집 마감 까지 {diffHours}시간 {diffMinutes}분 남았어요!
            </Text>
          </TimeTextView>
          <ButtonView>
            <TouchableOpacity
              style={{
                backgroundColor: "#E8F3E6",
                width: 300,
                height: 60,
                justifyContent: "center",
                alignItems: "center",
                borderRadius: 20,
              }}
              onPress={ChattingOnOff}
            >
              <Text
                style={{
                  fontSize: 20,
                  fontWeight: "bold",
                  color: "#4F8645",
                  fontFamily: "SpoqaHanSansNeo-Bold",
                }}
              >
                참가 후 메시지 보내기
              </Text>
            </TouchableOpacity>
          </ButtonView>
          <BottomClearView></BottomClearView>
        </Container>
      ) : (
        <></>
      )}
    </>
  );
}

export default Chat;
