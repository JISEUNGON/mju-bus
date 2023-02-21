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

function Chat() {
  // 가상의 데이터 (api 연동후 삭제예정)
  const memberData = [
    {
      id: 1,
      cap: true,
      memberName: "박성수",
      memberImage:
        "data:image/jpeg;base64,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",
    },
    {
      id: 2,
      cap: false,
      memberName: "지승언",
      memberImage:
        "data:image/jpeg;base64,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",
    },
    {
      id: 3,
      cap: false,
      memberName: "김예찬",
      memberImage:
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5mapqLZtFBf1Pzs87N2JdJ92PrO6gFU4qig&usqp=CAU",
    },
  ];

  // Context 변수 선언
  const { join, setJoin, out, setOut } = useContext(TaxiChatContext);
  const windowHeight = Dimensions.get("window").height;
  const ChattingOnOff = () => {
    setOut(!out);
    timeoutId = setTimeout(() => {
      setJoin(!join);
    }, 100);

    // AsyncStorage에 join 및 out 변수 값을 저장한다.
    AsyncStorage.setItem("join", (!join).toString());
    AsyncStorage.setItem("out", (!out).toString());
  };

  // api 호출부분
  const [data, setData] = useState(null);
  const [diffHours, setDiffHours] = useState(null);
  const [diffMinutes, setDiffMinutes] = useState(null);
  useEffect(() => {
    fetch("http://staging-api.mju-bus.com:80/taxi/21/")
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
    AsyncStorage.getItem("join").then(value => {
      if (value !== null) {
        setJoin(value === "true");
      }
    });
    AsyncStorage.getItem("out").then(value => {
      if (value !== null) {
        setOut(value === "true");
      }
    });
  }, []);

  return (
    <>
      <Container>
        <TopClearView></TopClearView>
        <ProfileView>
          <UserAvatar
            size={windowHeight > 700 ? 60 : 50}
            src={memberData[0].memberImage}
            bgColor="white"
          />
        </ProfileView>
        <MainTextView>
          <MainText>{memberData[0].memberName}님의</MainText>
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
    </>
  );
}

export default Chat;
