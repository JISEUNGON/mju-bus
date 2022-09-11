/* eslint-disable react/prop-types */
import { StatusBar } from "expo-status-bar";
import React, { useEffect, useState } from "react";
import styled from "styled-components/native";
import { Entypo } from "@expo/vector-icons";
import { ActivityIndicator, TouchableOpacity } from "react-native";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import { useQuery } from "@tanstack/react-query";
import BusInfoList from "../../components/BusResult/BusInfo";
import { stationApi } from "../../api";

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

const ListView = styled.FlatList`
  background-color: white;
`;

function CustomNavButton(navigation) {
  return (
    <TouchableOpacity onPress={() => navigation.goBack()}>
      <Entypo name="chevron-left" size={24} color="gray" />
    </TouchableOpacity>
  );
}

// eslint-disable-next-line react/prop-types
function BusList({ navigation, route: { params } }) {
  // PARMAS
  const { stationId, dest, redBus, toSchool } = params;

  const [start, setStart] = useState("");
  const [end, setend] = useState("");

  // 목적지,출발지 설정
  function SetStartAndDest() {
    if (toSchool) {
      setStart(stationId.name);
      setend("명지대학교");
      return `${stationId.name}   →   명지대학교`;
    }
    setStart("명지대학교");
    setend(dest.name);
    return `명지대학교   →  ${dest.name}`;
  }

  useEffect(() => {
    navigation.setOptions({
      title: SetStartAndDest(),
      headerLeft: () => CustomNavButton(navigation),
    });
  }, []);

  // 목적지 Param 유무에 따른 queryKey 설정
  const destId = () => {
    if (dest === undefined) {
      return undefined;
    }
    return dest.id;
  };

  // Remain 데이터 불러오기
  const { isLoading: busRemainLoading, data: busRemainData } = useQuery(
    ["remain", parseInt(stationId.id, 10), destId(), redBus, toSchool],
    stationApi.remain,
  );

  // 총 소요시간 계산
  function CalculatorTime(start, end) {
    const start_second = start.split(":");
    const end_second = end.split(":");

    const start_result =
      Math.floor(start_second[0]) * 3600 + Math.floor(start_second[1]) * 60;

    const end_result =
      Math.floor(end_second[0]) * 3600 + Math.floor(end_second[1]) * 60;

    const result = Math.floor((end_result - start_result) / 60);

    return result;
  }

  function DeleteSecond(str) {
    const temp = str.split(":");

    const result = `${temp[0]}:${temp[1]}`;

    return result;
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
          <StatusBar backgroundColor="#f2f4f6" />
          <ListView
            data={busRemainData.busList}
            renderItem={({ item }) => (
              <TouchableOpacity
                onPress={() =>
                  navigation.navigate("BusDetail", {
                    screen: "BusDetail",
                    params: {
                      item,
                      totaltime: CalculatorTime(item.depart_at, item.arrive_at),
                      start,
                      end,
                    },
                  })
                }
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
            // keyExtractor={item => item.id}
          />
        </SafeAreaView>
      </SafeAreaProvider>
    </Container>
  );
}

export default BusList;
