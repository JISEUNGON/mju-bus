/* eslint-disable react/prop-types */
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
  height: 250px;
  align-items: flex-end;
`;

function CustomNavButton(navigation) {
  return (
    <TouchableOpacity onPress={() => navigation.goBack()}>
      <Entypo name="chevron-left" size={24} color="gray" />
    </TouchableOpacity>
  );
}

function BusDetail({ navigation, route: { params } }) {
  // PARAMS DATA
  const { item, totaltime, toSchool, src, dest, start, end } = params.params;

  function getPathTarget() {
    if (toSchool) {
      return src.id;
    }
    return dest.id;
  }

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

  function TitleName() {
    return `${start}  →  ${end}`;
  }

  useEffect(() => {
    navigation.setOptions({
      title: TitleName(),
      headerLeft: () => CustomNavButton(navigation),
    });
  }, []);

  const lodaing = busRouteLoading || busPathLoading || startStationLoading;

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
        <ScrollView>
          <BusInfoList
            totaltime={totaltime}
            arrivlatime={DeleteSecond(item.arrive_at)}
            departtime={DeleteSecond(item.depart_at)}
            start={src}
            end={getLastPoint()}
            type={item.id >= 200 ? "red" : "sine"}
            num={item.name}
            time={item.remains}
            canexpand
            stationlist={busRouteData?.stations}
          />
        </ScrollView>
      </BusContainer>
    </Conatiner>
  );
}

export default BusDetail;
