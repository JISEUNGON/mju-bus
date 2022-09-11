/* eslint-disable react/prop-types */
import { StatusBar } from "expo-status-bar";
import React, { useEffect } from "react";
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

const DATA = [
  {
    id: "01",
    totaltime: "20분",
    arrivlatime: "오후 1:58 ~ 2:18",
    start: "진입로(명지대방향)",
    end: "명지대학교",
    type: "red",
    num: "5000",
    time: 80,
  },
  {
    id: "02",
    totaltime: "24분",
    arrivlatime: "오후 1:58 ~ 2:22",
    start: "진입로(명지대방향)",
    end: "명지대학교",
    type: "red",
    num: "5000-1",
    time: 200,
  },
  {
    id: "03",
    totaltime: "32분",
    arrivlatime: "오후 1:58 ~ 2:30",
    start: "진입로(명지대방향)",
    end: "명지대학교",
    type: "sine",
    num: "",
    time: 300,
  },
  {
    id: "04",
    totaltime: "65분",
    arrivlatime: "오후 1:58 ~ 2:18",
    start: "진입로(명지대방향)",
    end: "명지대학교",
    type: "sine",
    num: "9999",
    time: 555,
  },
  {
    id: "05",
    totaltime: "32분",
    arrivlatime: "오후 1:58 ~ 2:30",
    start: "진입로(명지대방향)",
    end: "명지대학교",
    type: "sine",
    num: "",
    time: 300,
  },
  {
    id: "06",
    totaltime: "41분",
    arrivlatime: "오후 1:58 ~ 5:45",
    start: "진입로(명지대방향)",
    end: "명지대학교",
    type: "sine",
    num: "",
    time: 300,
  },
];

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

  // 타이틀 글씨 설정
  function TitleName() {
    if (toSchool) {
      return `${stationId.name}   →   명지대학교`;
    }
    return `명지대학교   →  ${dest.name}`;
  }

  useEffect(() => {
    navigation.setOptions({
      title: TitleName(),
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

  return busRemainLoading ? (
    // 운행중인 버스 && 현재 일정표 데이터를 얻는 동안 로딩 출력
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <Container>
      {console.log(busRemainData)}
      <SafeAreaProvider>
        <SafeAreaView edges={["bottom"]} style={{ flex: 1 }}>
          <StatusBar backgroundColor="#f2f4f6" />
          <ListView
            data={DATA}
            renderItem={({ item }) => (
              <TouchableOpacity
                onPress={() =>
                  navigation.navigate("BusDetail", {
                    params: {
                      item,
                    },
                  })
                }
              >
                <BusInfoList
                  totaltime={item.totaltime}
                  arrivlatime={item.arrivlatime}
                  start={item.start}
                  end={item.end}
                  type={item.type}
                  num={item.num}
                  time={item.time}
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
