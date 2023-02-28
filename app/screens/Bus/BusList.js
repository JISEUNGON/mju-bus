/* eslint-disable react/prop-types */
import React, { useEffect, useLayoutEffect, useRef, useState } from "react";
import styled from "styled-components/native";
import { Entypo } from "@expo/vector-icons";
import {
  ActivityIndicator,
  AppState,
  Dimensions,
  FlatList,
  TouchableOpacity,
} from "react-native";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import BusInfoList from "../../components/BusResult/BusInfo";
import { stationApi } from "../../api";
import { CalculatorTime, DeleteSecond } from "../../utils";
import XIcon from "../../components/XIcon";

const { width: SCREEN_WIDTH, height: SCREEN_HEIGHT } = Dimensions.get("window");

const Loader = styled.View`
  flex: 1;
  background-color: ${props => props.theme.busCompColor};
  justify-content: center;
  align-items: center;
`;

const Container = styled.View`
  flex: 1;
  background-color: ${props => props.theme.busCompColor};
  flex-direction: column;
`;

const NoContents = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  color: #ec6969;
  font-size: 20px;
  margin-bottom: 30px;
`;

const Board = styled.View`
  width: ${SCREEN_WIDTH}px;
  height: ${SCREEN_HEIGHT}px;
  align-items: center;
  justify-content: center;
  padding-bottom: 120px;
  background-color: ${props => props.theme.busCompColor};
`;
const BusListStatus = styled.StatusBar`
  background-color: ${props => props.theme.busListHeader};
`;

function CustomNavButton(navigation) {
  return (
    // eslint-disable-next-line react/destructuring-assignment
    <TouchableOpacity onPress={() => navigation.goBack()}>
      <Entypo name="chevron-left" size={24} color="gray" />
    </TouchableOpacity>
  );
}

// eslint-disable-next-line react/prop-types
function BusList({ navigation, route: { params } }) {
  const queryClient = useQueryClient();

  const { 
    src,  // 출발지
    dest, // 목적지
    redBus, // 빨간버스 여부
    toSchool // 학교행 여부
  } = params;

  // Refreshing 상태
  const [refreshing, setRefreshing] = useState(false);

  const [start, setStart] = useState("");
  const [end, setEnd] = useState("");

  // 목적지 Param 유무에 따른 queryKey 설정
  const destId = () => {
    if (dest === undefined) {
      return undefined;
    }
    return dest.id;
  };

  // 도착하는 버스 데이터 불러오기
  const {
    isLoading: busRemainLoading,
    isRefetching,
    data: busRemainData,
  } = useQuery(
    ["remain", parseInt(src.id, 10), destId(), redBus, toSchool],
    stationApi.remain,
  );

  const appState = useRef(AppState.currentState);
  useEffect(() => {
    const subscription = AppState.addEventListener("change", nextAppState => {
      if (
        appState.current.match(/inactive|background/) &&
        nextAppState === "active"
      ) {
        queryClient.refetchQueries(["remain"]);
      }
      appState.current = nextAppState;
    });
    return () => {
      subscription.remove();
    };
  }, [queryClient]);

  // 로딩중인 경우, 로딩화면 띄우기
  const onRefresh = async () => {
    setRefreshing(true);
    await queryClient.refetchQueries(["remain"]);
    setRefreshing(false);
  };

  // 목적지,출발지 설정
  // eslint-disable-next-line react-hooks/exhaustive-deps
  function SetStartAndDest() {
    if (toSchool) {
      setStart(src.name);
      setEnd("명지대학교");
      return `${src.name}   →   명지대학교`;
    } else {
      setStart("명지대학교");
      setEnd(dest.name);
      return `명지대학교   →   ${dest.name}`;
    }
  }

  useLayoutEffect(() => {
    navigation.setOptions({
      title: SetStartAndDest(),
      headerLeft: () => CustomNavButton(navigation),
    });
  }, [SetStartAndDest, navigation]);

  // 총 소요시간 계산
  // eslint-disable-next-line no-shadow
  function renderBusList() {
    if (busRemainData.busList === null || busRemainData.busList.length === 0) {
      return (
        <Board>
          <NoContents>운행 중인 버스가 없습니다</NoContents>
          <XIcon />
        </Board>
      );
    }
    return (
      <FlatList
        onRefresh={onRefresh}
        refreshing={refreshing}
        data={busRemainData.busList}
        renderItem={({ item }) => (
          <TouchableOpacity
            onPress={() => {
              navigation.navigate("BusDetail", {
                screen: "BusDetail",
                params: {
                  item,
                  totaltime: CalculatorTime(item.depart_at, item.arrive_at),
                  toSchool,
                  redBus,
                  src,
                  dest,
                  busRemainData,
                },
              });
            }}
          >
            <BusInfoList
              totaltime={CalculatorTime(item.depart_at, item.arrive_at)}
              arrivlatime={DeleteSecond(item.arrive_at)}
              departtime={DeleteSecond(item.depart_at)}
              start={start}
              end={end}
              type={item.id >= 200 ? "red" : "sine"}
              num={item.name}
              time={item.remains}
              toSchool={toSchool}
            />
          </TouchableOpacity>
        )}
      />
    );
  }

  return busRemainLoading ? (
    // 운행중인 버스 && 현재 일정표 데이터를 얻는 동안 로딩 출력
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <Container>
      <SafeAreaProvider>
        <SafeAreaView edges={["bottom"]} style={{ flex: 1 }}>
          <BusListStatus />

          {isRefetching ? (
            <Loader>
              <ActivityIndicator />
            </Loader>
          ) : (
            renderBusList()
          )}
        </SafeAreaView>
      </SafeAreaProvider>
    </Container>
  );
}

export default BusList;
