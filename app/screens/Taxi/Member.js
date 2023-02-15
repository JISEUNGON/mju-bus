import React, { useContext, useEffect, useState } from "react";
import {
  Text,
  Image,
  TouchableOpacity,
  Dimensions,
  View,
  ActivityIndicator,
} from "react-native";
import styled from "styled-components/native";
import UserAvatar from "react-native-user-avatar";
import { TaxiChatContext } from "./Taxicontext";
import TextTicker from "react-native-text-ticker";

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

// 모집중인 프로필의 글씨색 변경
const Nomember = styled.Text`
  color: #c4c4c4;
`;

const CaptainIcon = styled.View`
  background-color: #aadcc4;
  width: 10px;
  height: 3px;
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

function Member() {
  const [MemberBasicsData, setMemberBasicsData] = useState(null);
  const [minMember2, setMinMember2] = useState(null);

  useEffect(() => {
    fetch("http://staging-api.mju-bus.com:80/taxi/21/members")
      .then(res => res.json())
      .then(data => setMemberBasicsData(data));
  }, []);
  useEffect(() => {
    fetch("http://staging-api.mju-bus.com:80/taxi/21")
      .then(res => {
        return res.json();
      })
      .then(data => {
        setMinMember2(data);
      });
  }, []);

  const [MemberLength, setMemberLength] = useState();
  useEffect(() => {
    if (MemberBasicsData !== null) {
      setMemberLength(MemberBasicsData.taxiPartyMembersList.length);
    }
  }, [MemberBasicsData]);
  const cruitingMember = 4;
  const MemberList = [];
  const totalMember = [];
  const windowHeight = Dimensions.get("window").height;
  // 유저 profile 하나를 component화 시킴
  function Profile(props) {
    const Capfuntion = () => {
      if (props.cap && props.id === 2) {
        return <Cap />;
      } else if (props.id === 2) {
        return <Me />;
      } else if (props.cap && props.id !== 2) {
        return <Cap />;
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

  (() => {
    for (let i = 1; i <= cruitingMember - MemberLength; i++) {
      MemberList.push(1);
    }
  })();

  (() => {
    for (let i = 1; i <= 4 - cruitingMember; i++) {
      totalMember.push(1);
    }
  })();

  const { goChat, setGoChat, join, setJoin } = useContext(TaxiChatContext);

  const PressJoinButton = function () {
    setJoin(!join);
  };

  if (minMember2 !== null) {
    function MinMember() {
      return (
        <DetailIconTextContainer>
          <DetailIcon style={{ backgroundColor: "#AADCC4" }}></DetailIcon>
          <Text style={{ fontSize: 16, fontFamily: "SpoqaHanSansNeo-Regular" }}>
            택시 최소 인원까지{" "}
            <Text style={{ fontSize: 16, fontFamily: "SpoqaHanSansNeo-Bold" }}>
              {minMember2.min_member - MemberLength > 0
                ? minMember2.min_member - MemberLength
                : 0}
              명
            </Text>
          </Text>
        </DetailIconTextContainer>
      );
    }
    function MaxMember() {
      return (
        <DetailIconTextContainer>
          <DetailIcon style={{ backgroundColor: "#DFF1E8" }}></DetailIcon>
          <Text style={{ fontSize: 16, fontFamily: "SpoqaHanSansNeo-Regular" }}>
            택시 최대 인원까지
            <Text style={{ fontSize: 16, fontFamily: "SpoqaHanSansNeo-Bold" }}>
              {4 - MemberLength}명
            </Text>
          </Text>
        </DetailIconTextContainer>
      );
    }
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
      {MemberBasicsData !== null && minMember2 !== null ? (
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
                모집중({MemberLength}/4)
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
                  {join ? "파티나가기" : "참가하기"}
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
