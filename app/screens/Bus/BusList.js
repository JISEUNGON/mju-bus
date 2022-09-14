/* eslint-disable react/prop-types */
import { StatusBar } from "expo-status-bar";
import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components/native";
import { Entypo } from "@expo/vector-icons";
import {
  ActivityIndicator,
  AppState,
  FlatList,
  TouchableOpacity,
} from "react-native";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import BusInfoList from "../../components/BusResult/BusInfo";
import { stationApi } from "../../api";
import { CalculatorTime, DeleteSecond } from "../../utils";

const Loader = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`;

const Container = styled.View`
  flex: 1;
  background-color: white;
  flex-direction: column;
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

  // PARMAS
  const { src, dest, redBus, toSchool } = params;
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

  // Remain 데이터 불러오기
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
    }
    setStart(src.name);
    setEnd(dest.name);
    return `${src.name}   →  ${dest.name}`;
  }

  useEffect(() => {
    navigation.setOptions({
      title: SetStartAndDest(),
      headerLeft: () => CustomNavButton(navigation),
    });
  }, [SetStartAndDest, navigation]);

  // 총 소요시간 계산
  // eslint-disable-next-line no-shadow

  return busRemainLoading || isRefetching ? (
    // 운행중인 버스 && 현재 일정표 데이터를 얻는 동안 로딩 출력
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <Container>
      <SafeAreaProvider>
        <SafeAreaView edges={["bottom"]} style={{ flex: 1 }}>
          <StatusBar backgroundColor="#f2f4f6" />
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
                      start,
                      end,
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
                />
              </TouchableOpacity>
            )}
          />
        </SafeAreaView>
      </SafeAreaProvider>
    </Container>
  );
}

export default BusList;
