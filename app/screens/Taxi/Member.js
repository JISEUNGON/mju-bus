import React, { useContext, useEffect, useState } from "react";
import {
  Text,
  Image,
  TouchableOpacity,
  Dimensions,
  View,
  ActivityIndicator,
  AsyncStorage,
} from "react-native";
import styled from "styled-components/native";
import UserAvatar from "react-native-user-avatar";
import { TaxiChatContext } from "./Taxicontext";

// 전체를 감싸는 컨테이너
const JoinMemberContainer = styled.View`
  flex: 1;
  background-color: #f7f7f7;
`;

//---------------------------------------------------모집 상세---------------------------------------------
// 모집상세 부분을 감싸는 컨테이너
const DetailContainer = styled.View`
  flex: 1.3;
  background-color: white;
  border-radius: 20px;
  margin: 20px 20px;
  box-shadow: 0.05px 0.05px 1px #d9d7d7;
`;

// 모집상세의 오른쪽 부분의 텍스트와 아이콘들을 모두 담는 컨테이너
const DetailTextsIconsContainer = styled.View`
  justify-content: center;
  flex: 1.5;
`;

// 모집상세의 도넛 그래프를 담는 컨테이너
const DetailGraphContainer = styled.View`
  flex: 1;
  align-items: center;
  justify-content: center;
`;

// 그래프와 우측의 텍스트와 아이콘을 모두 담는 컨테이너
const DetailTextsGraphContainer = styled.View`
  flex: 3;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
`;

// 아래의 DetailText를 담는 Container
const DetailTextContainer = styled.View`
  flex: 1;

  padding-left: 20px;
  padding-top: 20px;
`;

// ex) 현재인원 2명 , 택시 최소 인원까지 ~명 등등 나타내는 Text
const DetailText = styled.Text`
  font-size: 16px;
  font-family: "SpoqaHanSansNeo-Bold";
`;

// 인원옆에 뜨는 초록색 아이콘 담당 View
const DetailIcon = styled.View`
  width: 25px;
  height: 15px;
  border-radius: 20px;
  margin-right: 10px;
  margin-top: 2px;
`;

// ex) 현재인원2명 과 그 옆에 초록색 아이콘 하나를 담는 View
const DetailIconTextContainer = styled.View`
  flex: 1;
  flex-direction: row;
`;

//---------------------------------------------모집중-------------------------------------------------
// 아래의 모집중 화면의 전체 Container
const MemberContainer = styled.View`
  flex: 3;
  background-color: white;
  border-radius: 20px;
  margin: 20px 20px;
  margin-bottom: 43px;
  margin-top: -2px;
  box-shadow: 0.05px 0.05px 1px #d9d7d7;
  justify-content: flex-start;
`;

// 사용자의 프로필 사진을 담는 View
const MemberProfileImage = styled.View`
  flex: 1.3;
  width: 60px;
  align-items: center;

  /* margin-bottom: 10px; */
`;

// 사용자의 프로필 이름을 담는 View
const MemberProfileName = styled.View`
  flex: 0.7;
  justify-content: center;
  align-items: center;
  width: 70px;
`;

// 사용자의 Profile 하나를 담는 View
const MemberProfile = styled.View`
  flex: 1;
  align-items: center;
  margin-left: 0px;
  margin-top: 10px;
`;

// 파티장과 참가할때 자신을 나타내는 표시를 담을 View
const MemberCapMe = styled.View`
  flex: 0.5;
  align-items: center;
  width: 40px;
`;

const ClearView = styled.View`
  flex: 3;
  width: 40px;
`;

// 사용자들의 Profile를 전부 담는 Container
const MemberProfileContainer = styled.View`
  flex-direction: row;
  flex: 0.5;
  margin-left: 10px;
  margin-right: 10px;
  width: 320px;
`;

// "참가하기" 버튼을 감싸는 View
const MemberButtonContainer = styled.View`
  justify-content: flex-end;
  align-items: center;
  flex: 0.2;
`;

// "모집중" 을 감싸는 View
const MemberTitleText = styled.View`
  flex: 0.15;
`;

const CapIconView = styled.View`
  flex: 1;
  background-color: #aadcc4;
  width: 100%;
  border-radius: 20px;
  justify-content: center;
  align-items: center;
`;

const CapText = styled.Text`
  color: #4f8645;
  font-weight: bold;
  font-size: 10.5px;
  font-family: "SpoqaHanSansNeo-Regular";
`;

const MeView = styled.View`
  flex: 1;
  background-color: #f2f3f4;
  width: 100%;
  border-radius: 20px;
  justify-content: center;
  align-items: center;
`;

const MeText = styled.Text`
  color: #58606d;
  font-weight: bold;
  font-size: 10.5px;
  font-family: "SpoqaHanSansNeo-Regular";
`;

// 파티장 아이콘
const Cap = () => (
  <CapIconView>
    <CapText>파티장</CapText>
  </CapIconView>
);

// 나 아이콘
const Me = () => (
  <MeView>
    <MeText>나</MeText>
  </MeView>
);

// 참가하기 버튼 눌렀을때 실행되는 함수

//------------------------------- 실행부 ----------------------------

function Member({ route }) {
  const [MemberBasicsData, setMemberBasicsData] = useState(null);
  const [minMember, setminMember] = useState(null);

  const item = route.params.item;

  // api 호출 부분
  useEffect(() => {
    fetch(`http://staging-api.mju-bus.com:80/taxi/${item.id}/members`)
      .then(res => res.json())
      .then(data => setMemberBasicsData(data));
  }, []);
  useEffect(() => {
    fetch(`http://staging-api.mju-bus.com:80/taxi/${item.id}`)
      .then(res => {
        return res.json();
      })
      .then(data => {
        setminMember(data);
      });
  }, []);

  const [MemberLength, setMemberLength] = useState(null);
  useEffect(() => {
    if (MemberBasicsData !== null) {
      setMemberLength(MemberBasicsData.taxiPartyMembersList.length);
    }
  }, [MemberBasicsData]);

  // 화면 크기에따른 프로필 사진크기 변화를 위해 사용
  const windowHeight = Dimensions.get("window").height;

  // 유저 profile 하나하나를 component화 시킴
  function Profile(props) {
    const Capfuntion = () => {
      if (MemberBasicsData.administer === props.id) {
        return <Cap />;
      } else if (props.id === 2) {
        return <Me />;
      } else {
        return <></>;
      }
    };

    return (
      <MemberProfile>
        <MemberProfileImage>
          <UserAvatar
            size={windowHeight > 700 ? 47 : 33}
            src={props.img}
            bgColor={props.back}
            name={props.pro}
          />
        </MemberProfileImage>
        <MemberProfileName>
          {props.name === "모집중" ? (
            <Text
              style={{ color: "#C4C4C4", fontFamily: "SpoqaHanSansNeo-Medium" }}
            >
              모집중
            </Text>
          ) : (
            <Text
              style={{ fontSize: 14, fontFamily: "SpoqaHanSansNeo-Medium" }}
            >
              {props.name}
            </Text>
          )}
        </MemberProfileName>
        <MemberCapMe>
          <Capfuntion />
        </MemberCapMe>
        <ClearView></ClearView>
      </MemberProfile>
    );
  }

  // 모집중프로필과 투명한프로필을 위한 변수
  const [MemberList, setMemberList] = useState(null);
  const [totalMember, setTotalMember] = useState(null);

  // 모집중 멤버 프로필 개수생성
  useEffect(() => {
    if (minMember !== null && MemberLength !== null) {
      const newMemberList = [];
      for (let i = 1; i <= minMember.max_member - MemberLength; i++) {
        newMemberList.push(1);
      }
      setMemberList(newMemberList);
    }
  }, [minMember, MemberLength]);

  // 투명한 프로필 개수생성
  useEffect(() => {
    if (minMember !== null && MemberLength !== null) {
      const newTotalMember = [];
      for (let i = 1; i <= 4 - minMember.max_member; i++) {
        newTotalMember.push(1);
      }
      setTotalMember(newTotalMember);
    }
  }, [minMember, MemberLength]);

  // 참가하기, 파티나가기 등을 위한 useContext
  const { join, setJoin, out, setOut } = useContext(TaxiChatContext);

  // 참가중일때와 아닐때의 상태를 저장하기 위한 AsyncStorage부분
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
    // AsyncStorage.getItem("out").then(value => {
    //   if (value !== null) {
    //     setOut(value === "true");
    //   }
    // });
  }, []);
  useEffect(() => {
    join === true ? setOut(true) : setOut(false);
  }, []);

  // 참가하기/파티나가기 버튼의 실행함수
  const PressJoinButton = function () {
    setOut(!out);
    timeoutId = setTimeout(() => {
      setJoin(!join);
    }, 500);
    // AsyncStorage에 join 및 out 변수 값을 저장한다.
    // AsyncStorage.setItem("join", (!join).toString());
    if (join !== true) {
      AsyncStorage.setItem(`room${item.id}`, true.toString());
    } else {
      AsyncStorage.removeItem(`room${item.id}`);
    }
  };

  // 최소인원 부분
  if (minMember !== null) {
    function MinMember() {
      return (
        <DetailIconTextContainer>
          <DetailIcon style={{ backgroundColor: "#AADCC4" }}></DetailIcon>
          <Text style={{ fontSize: 16, fontFamily: "SpoqaHanSansNeo-Regular" }}>
            택시 최소 인원까지{" "}
            <Text style={{ fontSize: 16, fontFamily: "SpoqaHanSansNeo-Bold" }}>
              {minMember.min_member - MemberLength > 0
                ? minMember.min_member - MemberLength
                : 0}
              명
            </Text>
          </Text>
        </DetailIconTextContainer>
      );
    }
    // 최대인원 부분
    function MaxMember() {
      return (
        <DetailIconTextContainer>
          <DetailIcon style={{ backgroundColor: "#DFF1E8" }}></DetailIcon>
          <Text style={{ fontSize: 16, fontFamily: "SpoqaHanSansNeo-Regular" }}>
            택시 최대 인원까지
            <Text style={{ fontSize: 16, fontFamily: "SpoqaHanSansNeo-Bold" }}>
              {item.max_member - MemberLength}명
            </Text>
          </Text>
        </DetailIconTextContainer>
      );
    }
    // 현재 인원 부분
    function RecentMember() {
      return (
        <DetailIconTextContainer>
          <DetailIcon style={{ backgroundColor: "#00A857" }}></DetailIcon>
          <DetailText style={{ fontWeight: "bold" }}>
            현재 인원 {MemberLength}명
          </DetailText>
        </DetailIconTextContainer>
      );
    }
  }

  //보여지는 화면부분
  return (
    <>
      {MemberBasicsData !== null &&
      minMember !== null &&
      MemberList !== null &&
      totalMember !== null ? (
        <JoinMemberContainer>
          <DetailContainer>
            <DetailTextContainer>
              <Text
                style={{
                  fontWeight: "bold",
                  fontSize: 18,
                  fontFamily: "SpoqaHanSansNeo-Bold",
                }}
              >
                모집 상세
              </Text>
            </DetailTextContainer>
            <DetailTextsGraphContainer>
              <DetailGraphContainer>
                {/* 그래프 이미지 */}
                {windowHeight > 700 ? (
                  <Image
                    source={require("../../img/graph.png")}
                    style={{ width: 100 }}
                    resizeMode={"contain"}
                  ></Image>
                ) : (
                  <Image
                    source={require("../../img/graph.png")}
                    style={{ width: 70 }}
                    resizeMode={"contain"}
                  ></Image>
                )}
              </DetailGraphContainer>
              <DetailTextsIconsContainer>
                <RecentMember></RecentMember>
                <MinMember></MinMember>
                <MaxMember></MaxMember>
              </DetailTextsIconsContainer>
            </DetailTextsGraphContainer>
          </DetailContainer>
          <MemberContainer>
            <MemberTitleText>
              <Text
                style={{
                  paddingTop: 20,
                  paddingLeft: 20,
                  fontWeight: "bold",
                  fontSize: 18,
                  fontFamily: "SpoqaHanSansNeo-Bold",
                }}
              >
                모집중({MemberLength}/{item.max_member})
              </Text>
            </MemberTitleText>
            <MemberProfileContainer>
              {MemberBasicsData.taxiPartyMembersList.map(mem => (
                <Profile
                  name={mem.name}
                  img={mem.profileImageUrl}
                  id={mem.id}
                />
              ))}
              {MemberList.map(mem => (
                <Profile name="모집중" img="" back="#F3F3F3" pro="" />
              ))}
              {totalMember.map(mem => (
                <Profile name="" img="" back="white" />
              ))}
            </MemberProfileContainer>
            <MemberButtonContainer>
              <TouchableOpacity
                style={{
                  backgroundColor: "#E8F3E6",
                  width: 240,
                  height: 60,
                  justifyContent: "center",
                  alignItems: "center",
                  borderRadius: 20,
                }}
                onPress={PressJoinButton}
              >
                <Text
                  style={{
                    fontSize: 25,
                    fontWeight: "bold",
                    color: "#4F8645",
                    fontFamily: "SpoqaHanSansNeo-Bold",
                  }}
                >
                  {out ? "파티나가기" : "참가하기"}
                </Text>
              </TouchableOpacity>
            </MemberButtonContainer>
          </MemberContainer>
        </JoinMemberContainer>
      ) : (
        <View
          style={{
            width: "100%",
            height: "100%",
            justifyContent: "center",
            alignItems: "center",
            backgroundColor: "white",
          }}
        >
          <ActivityIndicator />
        </View>
      )}
    </>
  );
}
export default Member;
