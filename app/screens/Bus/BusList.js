import { StatusBar } from "expo-status-bar";
import React, { useEffect } from "react";
import styled from "styled-components/native";
import { Entypo } from "@expo/vector-icons";
import { TouchableOpacity } from "react-native";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import BusInfoList from "../../components/BusResult/BusInfo";

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

const renderItem = ({ item }) => (
  <BusInfoList
    totaltime={item.totaltime}
    arrivlatime={item.arrivlatime}
    start={item.start}
    end={item.end}
    type={item.type}
    num={item.num}
    time={item.time}
  />
);

function CustomNavButton(navigation) {
  return (
    <TouchableOpacity onPress={() => navigation.goBack()}>
      <Entypo name="chevron-left" size={24} color="gray" />
    </TouchableOpacity>
  );
}

// eslint-disable-next-line react/prop-types
function BusList({ navigation, route: { params } }) {
  useEffect(() => {
    navigation.setOptions({
      title: `${params.selectedStations[0].name} -> ${params.selectedStations[1].name}`,
      headerLeft: () => CustomNavButton(navigation),
    });
  }, []);

  return (
    <Container>
      <SafeAreaProvider>
        <SafeAreaView edges={["bottom"]} style={{ flex: 1 }}>
          <StatusBar backgroundColor="#f2f4f6" />
          <ListView
            data={DATA}
            renderItem={renderItem}
            // keyExtractor={item => item.id}
          />
        </SafeAreaView>
      </SafeAreaProvider>
    </Container>
  );
}

export default BusList;
