import {
  TouchableOpacity,
  Dimensions,
  ActivityIndicator,
  ScrollView,
} from "react-native";
import styled from "styled-components";
import { Entypo } from "@expo/vector-icons";
import React, { useEffect } from "react";
import { useQuery } from "@tanstack/react-query";
import BusInfoList from "../../components/BusResult/BusInfo";
import { busApi } from "../../api";
import NMap from "./NMap";

const { height: SCREEN_HEIGHT, width: SCREEN_WIDTH } = Dimensions.get("window");

const Loader = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`;

const Conatiner = styled.View`
  height: ${SCREEN_HEIGHT}px;
  width: ${SCREEN_WIDTH}px;

  background-color: white;

  flex-direction: column;
  align-items: flex-start;

  flex: 1;
`;

const MapContainer = styled.View`
  background-color: white;
  width: 100%;
  height: auto;

  flex: 1.5;
`;

const BusContainer = styled.View`
  background-color: white;
  width: ${SCREEN_WIDTH}px;
  height: auto;

  align-items: flex-end;

  border-color: gray;
  border-top-width: 1px;

  flex: 1;
`;

const ListContainer = styled.View`
  background-color: blue;
`;

function CustomNavButton(navigation) {
  return (
    <TouchableOpacity onPress={() => navigation.goBack()}>
      <Entypo name="chevron-left" size={24} color="gray" />
    </TouchableOpacity>
  );
}

function BusDetail({ navigation, route: { params } }) {
  // Route 데이터 불러오기
  const { isLoading: busRouteLoading, data: busRemainData } = useQuery(
    ["route", parseInt(params.params.item.id, 10)],
    busApi.route,
  );

  const { item } = params.params;

  function TitleName() {
    const { start } = params.params;
    const { end } = params.params;

    const name = `${start}->${end}`;

    return name;
  }

  useEffect(() => {
    navigation.setOptions({
      title: TitleName(),
      headerLeft: () => CustomNavButton(navigation),
    });
  }, []);

  return busRouteLoading ? (
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <Conatiner>
      <MapContainer />
      <BusContainer>
        <ScrollView>
          <BusInfoList
            totaltime={params.params.totaltime}
            arrivlatime={item.arrive_at}
            departtime={item.depart_at}
            start={params.params.start}
            end={params.params.end}
            type={item.id >= 200 ? "red" : "sine"}
            num={item.name}
            time={item.remains}
            canexpand
            stationlist={busRemainData.stations}
          />
        </ScrollView>
      </BusContainer>
    </Conatiner>
  );
}

export default BusDetail;
