/* eslint-disable react/prop-types */
import {
  TouchableOpacity,
  Dimensions,
  ActivityIndicator,
  ScrollView,
} from "react-native";
import styled from "styled-components";
import { Entypo } from "@expo/vector-icons";
import React, { useLayoutEffect } from "react";
import { useQuery } from "@tanstack/react-query";

import BusInfoList from "../../components/BusResult/BusInfo";
import { busApi, pathApi, stationApi } from "../../api";

import ResoultNMap from "../../components/BusResult/ResultNMap";
import { DeleteSecond } from "../../utils";
import { stationId } from "../../id";

const { height: SCREEN_HEIGHT, width: SCREEN_WIDTH } = Dimensions.get("window");

const Loader = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`;

const Conatiner = styled.View`
  height: ${SCREEN_HEIGHT}px;
  width: ${SCREEN_WIDTH}px;

  flex-direction: column;
  align-items: flex-start;

  flex: 1;
`;

const MapContainer = styled.View`
  background-color: ${props => props.theme.busCompColor};

  width: 100%;
  height: auto;
  flex: 1.5;
`;

const BusContainer = styled.View`
  background-color: ${props => props.theme.busCompColor};

  width: ${SCREEN_WIDTH}px;
  height: 220px;
  align-items: flex-end;
  padding-bottom: 10px;
`;

function CustomNavButton(navigation) {
  return (
    // eslint-disable-next-line react/destructuring-assignment
    <TouchableOpacity onPress={() => navigation.goBack()}>
      <Entypo name="chevron-left" size={24} color="gray" />
    </TouchableOpacity>
  );
}

function BusDetail({ navigation, route: { params } }) {
  // PARAMS DATA
  const { item, totaltime, toSchool, src, dest, redBus } = params.params;

  function getPathTarget() {
    if (toSchool) {
      return src.id;
    }
    return dest.id;
  }

  const destId = () => {
    if (dest === undefined) {
      return undefined;
    }
    return dest.id;
  };

  function getLastPoint() {
    if (toSchool) {
      if (item.id === 10 || item.id === 20) {
        return stationId.SamGongHakGwan;
      }
      if (item.id === 30) {
        return stationId.ChapleGwan;
      }
      return stationId.SiweBusStation;
    }
    return dest;
  }

  // Route 데이터 불러오기
  const { isLoading: busRouteLoading, data: busRouteData } = useQuery(
    ["route", parseInt(item.id, 10)],
    busApi.route,
  );

  // Path 데이터 불러오기
  const { isLoading: busPathLoading, data: busPathData } = useQuery(
    ["path", item.id, getPathTarget(), toSchool],
    pathApi.path,
  );
  // 시작 정류장 데이터 불러오기
  const { isLoading: startStationLoading, data: startStationData } = useQuery(
    ["station", src.id],
    stationApi.station,
  );

  // Remain 데이터 불러오기
  const {
    isLoading: busRemainLoading,
    isRefetching,
    data: busRemainData,
  } = useQuery(
    ["remain", parseInt(src.id, 10), destId(), redBus, toSchool],
    stationApi.remain,
  );

  // eslint-disable-next-line react-hooks/exhaustive-deps
  function TitleName() {
    return `${src.name}    →   ${getLastPoint().name}`;
  }

  useLayoutEffect(() => {
    navigation.setOptions({
      title: TitleName(),
      headerLeft: () => CustomNavButton(navigation),
    });
  }, [TitleName, navigation]);

  function getItem() {
    const busItem = busRemainData.busList.filter(bus => bus.id === item.id);
    if (busItem.length === 0 || busItem[0] === undefined) {
      navigation.goBack();
    }
    return busItem[0];
  }

  const lodaing =
    busRouteLoading ||
    busPathLoading ||
    startStationLoading ||
    busRemainLoading ||
    isRefetching;

  return lodaing ? (
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <Conatiner>
      <MapContainer>
        <ResoultNMap startPoint={startStationData} busPathData={busPathData} />
      </MapContainer>
      <BusContainer>
        <ScrollView showsVerticalScrollIndicator={false}>
          <BusInfoList
            totaltime={totaltime}
            arrivlatime={DeleteSecond(item.arrive_at)}
            departtime={DeleteSecond(item.depart_at)}
            start={src}
            end={getLastPoint()}
            type={getItem().id >= 200 ? "red" : "sine"}
            num={getItem().name}
            time={getItem().remains}
            canexpand
            stationlist={busRouteData?.stations}
          />
        </ScrollView>
      </BusContainer>
    </Conatiner>
  );
}

export default BusDetail;
