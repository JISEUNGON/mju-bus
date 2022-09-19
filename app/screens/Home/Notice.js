/* eslint-disable react/prop-types */
import React, { useLayoutEffect, useState } from "react";
import { Dimensions, FlatList, Image, TouchableOpacity } from "react-native";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import styled from "styled-components";
import { Entypo } from "@expo/vector-icons";

const { width: SCREEN_WIDTH, height: SCREEN_HEIGTH } = Dimensions.get("window");

const Container = styled.View`
  background-color: white;
  width: ${SCREEN_WIDTH}px;
  height: ${SCREEN_HEIGTH}px;
`;

const NoticeDetailContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
`;

const NoticeBannerContainer = styled.View`
  flex: 1;
  height: 100px;

  width: ${SCREEN_WIDTH}px;
  border-color: #f5f5f5;
  border-bottom-width: 1.5px;
  flex-direction: row;
`;

const NoticeBannerText = styled.View`
  flex: 5;
  flex-direction: column;
  padding-left: 20px;
  justify-content: center;
`;

const NoticeBannerButton = styled.View`
  flex: 1;
  align-items: center;
  justify-content: center;
`;

const BannerTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 18px;
  color: black;
`;

const BannerDate = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 12px;
  margin-bottom: 5px;
  color: gray;
`;

const DetailTitleContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  height: 70px;
  justify-content: center;
  padding-left: 10px;
  background-color: #fbfbfb;
`;

const DetailTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 13px;
  color: #6d7582;
`;

const DetailImageContainer = styled.View`
  background-color: white;
`;
const DetailDescriptionContainer = styled.ScrollView`
  width: ${SCREEN_WIDTH}px;
  height: 100px;
  background-color: #fbfbfb;
  padding-top: 20px;
  padding-left: 20px;
  padding-right: 20px;
`;

const DetailDescription = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 13px;
  color: gray;
`;
const StatusBar = styled.StatusBar`
  background-color: ${props => props.theme.noticeHeader};
`;

const NoticeImg = styled.Image`
  flex: 1;
`;

const data = [
  {
    id: 1,
    date: "2022/09/15",
    title: "MBA VERSION 1.0.0",
    img: "",
    subTitle: "#셔틀버스? 언제 오는지 확인하자",
    description: "[기본 기능]\n" +
    "- 앱 공지사항 알림 버튼\n" +
    "- 시내/시외 버스 시간표 보기\n" +
    "- 운행 종료 확인하기\n" +
    "- 도착 시간 확인하기\n" +
    "- 자주 보는 정류장 즐겨찾기\n\n" +
    
    "[주요 기능을 한눈에]\n" +
    "- 버스 운행 정보 확인, 빨간 버스 도착 정보 확인 등 홈화면에서 바로 확인 가능합니다\n\n" +
    
    "[간편한 버스 찾기]\n" +
    "- 원하는 정류장으로 갈 버스를 찾을 수 있습니다.\n" +
    "- 지도에서 마커를 누르거나 리스트에서 원하는 정류장을 선택하면 버스 도착 정보를 확인할 수 있어요\n" +
    "- 버스 검색을 누르면 해당 정류장에 도착하는 버스들과 몇 분 남았는지 표시됩니다.\n\n" +
    "[자주 가는 정류장은 저장]\n" +
    "- 자주 가는 정류장은 즐겨 찾기도 가능해요\n" +
    "- 즐겨 찾기하면 다음에 버스 검색시 편리해요\n\n" +
    "[셔틀버스의 시간표 확인]\n" +
    "- 현재 앱을 사용하는 날짜를 기준으로 운행하는 버스의 정보만 표시 돼요.\n" +
    "- 운행한다면 시간표와 노선도를 한눈에 알아 볼 수 있어요.\n",
  },
  {
    id: 2,
    date: "2022/09/15",
    title: "ICT 멘토링 프로젝트 관련 공지사항",
    // eslint-disable-next-line import/no-unresolved, global-require
    img: require("../../assets/image/ICT_Notice50.png"),
    subTitle: "#ICT 멘토링 프로젝트",
    description:
      "본 프로그램 과학기술정보통신부 정보통신창의인재양성사업의 지원을 통해 수행한 ICT멘토링 프로젝트 결과물입니다.",
  },
];

function CustomNavButton(navigation) {
  return (
    // eslint-disable-next-line react/destructuring-assignment
    <TouchableOpacity onPress={() => navigation.goBack()}>
      <Entypo name="chevron-left" size={24} color="gray" />
    </TouchableOpacity>
  );
}

function Notice({ navigation }) {
  const [selectedId, setSelectedId] = useState(false);

  useLayoutEffect(() => {
    navigation.setOptions({
      headerLeft: () => CustomNavButton(navigation),
    });
  }, [navigation]);

  // eslint-disable-next-line react/no-unstable-nested-components
  function NoticeItem({ id, date, title, img, subTitle, description }) {
    return (
      <TouchableOpacity
        onPress={() => {
          if (id === selectedId) {
            setSelectedId(null);
          } else {
            setSelectedId(id);
          }
        }}
      >
        <NoticeBannerContainer>
          <NoticeBannerText>
            <BannerDate>{date}</BannerDate>
            <BannerTitle>{title}</BannerTitle>
          </NoticeBannerText>
          <NoticeBannerButton>
            {id === selectedId ? (
              <Entypo name="chevron-small-up" size={30} color="gray" />
            ) : (
              <Entypo name="chevron-small-down" size={30} color="gray" />
            )}
          </NoticeBannerButton>
        </NoticeBannerContainer>
        {id === selectedId ? (
          <NoticeDetailContainer>
            <DetailTitleContainer>
              <DetailTitle>{subTitle}</DetailTitle>
            </DetailTitleContainer>
            <DetailImageContainer>
              {img !== "" ? <NoticeImg source={img}/>:null}
            </DetailImageContainer>
            <DetailDescriptionContainer>
              <DetailDescription>{description}</DetailDescription>
            </DetailDescriptionContainer>
          </NoticeDetailContainer>
        ) : null}
      </TouchableOpacity>
    );
  }

  const renderNoticeItem = ({ item }) => (
    <NoticeItem
      id={item.id}
      date={item.date}
      title={item.title}
      img={item.img}
      subTitle={item.subTitle}
      description={item.description}
      selectedId={selectedId}
      setSelectedId={setSelectedId}
    />
  );

  return (
    <SafeAreaProvider>
      <SafeAreaView edges={["bottom", "top"]} style={{ flex: 1 }}>
        <StatusBar />
        <Container>
          <FlatList
            extraData={selectedId}
            data={data}
            renderItem={renderNoticeItem}
          />
        </Container>
      </SafeAreaView>
    </SafeAreaProvider>
  );
}

export default Notice;
