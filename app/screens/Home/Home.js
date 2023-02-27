/* eslint-disable global-require */
import React, { useEffect, useRef, useState } from "react";
import { Ionicons } from "@expo/vector-icons";
import styled from "styled-components/native";
import {
  ActivityIndicator,
  AppState,
  Dimensions,
  Image,
  TouchableOpacity,
} from "react-native";
import { useQueries, useQueryClient } from "@tanstack/react-query";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import { StatusBar } from "expo-status-bar";
import AsyncStorage from "@react-native-async-storage/async-storage";
import LinkScreen from "../../components/LinkScreen";
import RunningBus, { NoSiweList } from "../../components/RunningBus";
import { busApi, stationApi } from "../../api";
import RunningRedBus from "../../components/RunningRedBus";
import RouteSelect from "./RouteSelect";
import { stationId } from "../../id";
import { MBAContext } from "../../navigation/Root";
import { KEY_FAVORITE_SIWE_BUS } from "../StorageKey";

const { width: SCREEN_WIDTH } = Dimensions.get("window");

const Loader = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`;

const Container = styled.FlatList`
  background-color: ${props => props.theme.homeBgColor};
  padding: 0 20px;
`;

const NoticeContainer = styled.View`
  width: 100%;
  height: 70px;
  align-items: center;
  justify-content: space-between;
  flex-direction: row;
  padding-left: 20px;
  padding-right: 20px;
  padding-top: 20px;
`;

const LogoContainer = styled.View`
  width: 150px;
  height: 100%;
  flex-direction: row;
  margin-top: 20px;
`;

const LogoText = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 22px;
  margin-left: 10px;
  color: #b1b8c0;
`;

const LinkContainer = styled.View`
  width: 100%;
  align-items: center;
  margin-top: 20px;
`;

const RunningBusContainer = styled.View`
  width: 100%;
  align-items: center;
  margin-top: 30px;
`;
const RedBusComingContainer = styled.View`
  width: 100%;
  margin-top: 20px;
`;

const TitleContainer = styled.View`
  width: 100%;
  padding: 0 10px;
  margin-bottom: 20px;
`;
const Title = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: ${props => props.theme.mainTextColor};
`;

const SubTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 15px;
  color: ${props => props.theme.subTextColor};
  margin-top: 10px;
`;

// eslint-disable-next-line react/prop-types
function Home({ route: { params }, navigation: { navigate } }) {
  const { sineBusList, siweBusList, mjuCalendar, stationList, busTimeTable } =
    React.useContext(MBAContext);

  const queryClient = useQueryClient();
  const [modalVisible, setModalVisible] = useState(false);
  const [refreshing, setRefreshing] = useState(false);

  // 즐겨찾기한 시외버스 노선
  const [favoriteSiweBus, setFavoriteSiweBus] = useState([]);

  // 앱이 백그라운드에서 돌아왔을 때 강제 refetch 진행
  const appState = useRef(AppState.currentState);
  useEffect(() => {
    const subscription = AppState.addEventListener("change", nextAppState => {
      if (
        appState.current.match(/inactive|background/) &&
        nextAppState === "active"
      ) {
        queryClient.invalidateQueries();
        queryClient.refetchQueries(["remain", "status"]);
      }
      appState.current = nextAppState;
    });
    return () => {
      subscription.remove();
    };
  }, [queryClient]);

  // 즐겨찾기한 시외버스 노선 불러오기
  const loadSelectedRoutes = async () => {
    try {
      const storageSiweBus = await AsyncStorage.getItem(KEY_FAVORITE_SIWE_BUS);
      if (storageSiweBus != null) {
        setFavoriteSiweBus(JSON.parse(storageSiweBus));
      }
    } catch (error) {
      console.log(error);
    }
  };

  // 새로고침
  const onRefresh = async () => {
    setRefreshing(true);
    await queryClient.invalidateQueries();
    await queryClient.refetchQueries(["remain", "status"]);
    setRefreshing(false);
  };

  const onStart = () => {
    setModalVisible(true);
  };

  // 빨간버스 진입로/용인터미널 남은 시간
  const redBusRemain = useQueries({
    queries: [
      {
        queryKey: ["remain", stationId.JinIpRo.id, undefined, true, true],
        queryFn: stationApi.remain,
      },
      {
        queryKey: [
          "remain",
          stationId.YongInTerminal.id,
          undefined,
          true,
          true,
        ],
        queryFn: stationApi.remain,
      },
    ],
  });

  // 시외버스 운행정보 (운행 전|운행 중|운행 종료)
  // 아마 NotFound 에러 때문에 timeTable API 참조하는 거 같음?
  // 시외버스 정보를 ContextAPI를 통해 불러오기 때문에 필요 없다고 생각됨.
  // staleTime 5분으로 설정함으로써 API 호출을 줄임
  const SiweStatus = useQueries({
    // eslint-disable-next-line react/prop-types
    queries: siweBusList.map(data => ({
      queryKey: ["timeTable", data.id],
      queryFn: busApi.timeTable,
      staleTime: 5 * 60 * 1000,
    })),
  });

  // 시내버스 운행정보 (운행 전|운행 중|운행 종료)
  // 3분마다 캐시 시간 갱신
  const SineStatus = useQueries({
    // eslint-disable-next-line react/prop-types
    queries: sineBusList.map(data => ({
      queryKey: ["status", data.id],
      queryFn: busApi.status,
      staleTime: 3 * 60 * 1000,
    })),
  });

  useEffect(() => {
    // eslint-disable-next-line no-use-before-define
    loadSelectedRoutes();
  }, [modalVisible]);

  const SineStatusisLoading = SineStatus.some(result => result.isLoading);
  const redBusRemainisLoading = redBusRemain.some(result => result.isLoading);
  const SiweStatusisLoading = SiweStatus.some(result => result.isLoading);
  const loading =
    redBusRemainisLoading || SineStatusisLoading || SiweStatusisLoading;

  return loading ? (
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <SafeAreaProvider>
      <SafeAreaView edges={["top"]} style={{ flex: 1 }}>
        <StatusBar />
        <Container
          width={SCREEN_WIDTH}
          onRefresh={onRefresh}
          refreshing={refreshing}
          ListHeaderComponent={
            <>
              <NoticeContainer>
                <LogoContainer>
                  <Image source={require("../../assets/image/Logo.png")} />
                  <LogoText>Bus Alarm</LogoText>
                </LogoContainer>
                <TouchableOpacity
                  onPress={() => {
                    navigate("NoticeStack", {
                      screen: "Notice",
                    });
                  }}
                >
                  <Ionicons name="notifications" size={28} color="#B1B8C0" />
                </TouchableOpacity>
              </NoticeContainer>
              <LinkContainer>
                <LinkScreen screenName="시간표" />
              </LinkContainer>

              <RunningBusContainer>
                <TitleContainer>
                  <Title>운행중인 버스</Title>
                </TitleContainer>
                <RunningBus bustype="sine" data={SineStatus} />
                {siweBusList.length === 0 ? (
                  <NoSiweList />
                ) : (
                  <TouchableOpacity onPress={onStart}>
                    <RunningBus bustype="siwe" data={favoriteSiweBus} />
                  </TouchableOpacity>
                )}
                {modalVisible ? (
                  <RouteSelect
                    data={SiweStatus}
                    modalVisible={modalVisible}
                    setModalVisible={setModalVisible}
                    selectedRoutes={favoriteSiweBus}
                  />
                ) : null}
              </RunningBusContainer>

              <RedBusComingContainer>
                <TitleContainer>
                  <Title>빨버 언제와?</Title>
                  <SubTitle>
                    진입로, 용인터미널 빨간 버스 도착 정보예요
                  </SubTitle>
                </TitleContainer>
                <RunningRedBus data={redBusRemain} />
              </RedBusComingContainer>
            </>
          }
        />
      </SafeAreaView>
    </SafeAreaProvider>
  );
}

export default Home;
