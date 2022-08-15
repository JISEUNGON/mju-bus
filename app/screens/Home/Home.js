import React, { useEffect, useState } from "react";
import styled from "styled-components/native";
import { ActivityIndicator, Dimensions, TouchableOpacity } from "react-native";
import { useQueries, useQuery, useQueryClient } from "@tanstack/react-query";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import { StatusBar } from "expo-status-bar";
import AsyncStorage from "@react-native-async-storage/async-storage";
import LinkScreen from "../../components/LinkScreen";
import RunningBus from "../../components/RunningBus";
import { busApi, calendarApi, stationApi } from "../../api";
import RunningRedBus from "../../components/RunningRedBus";
import RouteSelect from "./RouteSelect";
import { busId, stationId } from "../../id";

const STORAGE_KEY = "@routes";

const { width: SCREEN_WIDTH } = Dimensions.get("window");

const Loader = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`;

const Container = styled.FlatList`
  background-color: #f2f4f6;
`;

const LinkContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  align-items: center;
  margin-top: 20px;
`;

const RunningBusContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  align-items: center;
  margin-top: 20px;
`;
const RedBusComingContainer = styled.View`
  width: ${SCREEN_WIDTH}px;
  align-items: center;
  margin-top: 20px;
`;

const TitleContainer = styled.View`
  width: 85%;
  margin-bottom: 20px;
`;
const Title = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: black;
`;

const SubTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 15px;
  color: gray;
  margin-top: 10px;
`;

function Home() {
  const queryClient = useQueryClient();
  const [modalVisible, setModalVisible] = useState(false);
  const [refreshing, setRefreshing] = useState(false);
  const [selectedRoutes, setSelectedRoutes] = useState([]);

  const loadSelectedRoutes = async () => {
    try {
      const string = await AsyncStorage.getItem(STORAGE_KEY);
      if (string != null) {
        setSelectedRoutes(JSON.parse(string));
      }
    } catch (error) {
      console.log(error);
    }
  };

  const onRefresh = async () => {
    setRefreshing(true);
    await queryClient.refetchQueries([
      "remain",
      "status",
      "calendar",
      "busList",
    ]);
    setRefreshing(false);
  };

  const onStart = () => {
    setModalVisible(true);
  };

  // SPALSH 화면에서 가져온 데이터 캐싱
  const { isFetching: buslistLoading, data: busListData } = useQuery(
    ["busList"],
    busApi.status,
    {
      enabled: false,
    },
  );

  const [sineList, setSineList] = useState(
    busListData.filter(data => data.type === 1).map(data => data.busList),
  );
  const [siweList, setSiweList] = useState(
    busListData.filter(data => data.type === 2).map(data => data.busList),
  );

  const redBusRemain = useQueries({
    queries: [
      { queryKey: ["remain", stationId.JinIpRo], queryFn: stationApi.remain },
      {
        queryKey: ["remain", stationId.YongInTerminal],
        queryFn: stationApi.remain,
      },
    ],
  });

  const SiweStatus = useQueries({
    queries: siweList.map(data => ({
      queryKey: ["timeTable", data.id],
      queryFn: busApi.timeTable,
    })),
  });

  const SineStatus = useQueries({
    queries: sineList.map(data => ({
      queryKey: ["status", data.id],
      queryFn: busApi.status,
    })),
  });

  useEffect(() => {
    // eslint-disable-next-line no-use-before-define
    loadSelectedRoutes();
  }, [modalVisible]);

  const SineStatusisLoading = SineStatus.some(result => result.isLoading);
  const redBusRemainisLoading = redBusRemain.some(result => result.isLoading);
  const loading = redBusRemainisLoading || SineStatusisLoading;

  console.log(siweList);
  console.log(sineList);

  return loading ? (
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <SafeAreaProvider>
      <SafeAreaView edges={["top"]} style={{ flex: 1 }}>
        <StatusBar backgroundColor="#f2f4f6" />

        <Container
          onRefresh={onRefresh}
          refreshing={refreshing}
          ListHeaderComponent={
            <>
              <LinkContainer>
                <LinkScreen screenName="시간표" />
              </LinkContainer>

              <RunningBusContainer>
                <TitleContainer>
                  <Title>운행중인 버스</Title>
                </TitleContainer>
                <RunningBus bustype="sine" data={SineStatus} />
                <TouchableOpacity onPress={onStart}>
                  {/* <RunningBus bustype="siwe" data={SiweStatus} /> */}
                  <RouteSelect
                    data={busListData.filter(data => data.type === "1")}
                    modalVisible={modalVisible}
                    setModalVisible={setModalVisible}
                    selectedRoutes={selectedRoutes}
                  />
                </TouchableOpacity>
              </RunningBusContainer>

              <RedBusComingContainer>
                <TitleContainer>
                  <Title>빨버 언제와?</Title>
                  <SubTitle>진입로, 용인터미널 빨간 버스 도착정보에요</SubTitle>
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
