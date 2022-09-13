import {
  TouchableOpacity,
  Dimensions,
  ActivityIndicator,
  ScrollView,
} from "react-native";
import styled from "styled-components";
import { Entypo } from "@expo/vector-icons";
import React, { useEffect, useRef, useState } from "react";
import { useQuery } from "@tanstack/react-query";

import BusInfoList from "../../components/BusResult/BusInfo";
import { busApi } from "../../api";

import ResoultNMap from "../../components/BusResult/ResultNMap";
import { DeleteSecond, PathData } from "../../utils";

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

function CustomNavButton(navigation) {
  return (
    <TouchableOpacity onPress={() => navigation.goBack()}>
      <Entypo name="chevron-left" size={24} color="gray" />
    </TouchableOpacity>
  );
}

function BusDetail({ navigation, route: { params } }) {
  // PARAMS DATA
  const { item, start, end, totaltime, toSchool } = params.params;

  // Route 데이터 불러오기
  const { isLoading: busRouteLoading, data: busRouteData } = useQuery(
    ["route", parseInt(item.id, 10)],
    busApi.route,
  );

  /*


  // Path 데이터 불러오기
  const { isLoading: busPathLoading, data: busPathData } = useQuery(
    [
      "path",
      busRouteData.stations[busRouteData.stations.length - 1].id,
      item.id,
    ],
    busApi.route,
  );
*/
  /*
  console.log(busRouteData.stations.length - 1);
    console.log(busRouteData.stations[busRouteData.stations.length - 1].id);
  const [endname, setEndName] = useState(dest.name);
  if (toSchool) {
    setEndName(busRouteData[busRouteData.length - 1].name);
  }
  */

  function TitleName() {
    return `${start}  →  ${end}`;
  }

  useEffect(() => {
    navigation.setOptions({
      title: TitleName(),
      headerLeft: () => CustomNavButton(navigation),
    });
  }, []);

  /*
  const [isStart, setIsStart] = useState(false);

  // route 시작점 정하기
  if (item.id < 200) {
    if (!toSchool) {
      busRouteLoading.stations.reverse();
    }

    busRouteLoading.stations.map(s, index =>
      (busRouteLoading.stations[index].id === item.id
        ? setIsStart(true)
        : null)(
        isStart === true ? null : busRouteLoading.stations.sclice(index, index),
      ),
    );
  }
  */

  return busRouteLoading ? (
    <Loader>
      <ActivityIndicator />
    </Loader>
  ) : (
    <Conatiner>
      <MapContainer>
        <ResoultNMap busRouteData={busRouteData} />
        {console.log("====2222222222222====routeData")}
        {console.log(busRouteData)}
      </MapContainer>
      <BusContainer>
        <ScrollView>
          <BusInfoList
            totaltime={totaltime}
            arrivlatime={DeleteSecond(item.arrive_at)}
            departtime={DeleteSecond(item.depart_at)}
            start={start}
            end={busRouteData.stations[busRouteData.stations.length - 1].name}
            type={item.id >= 200 ? "red" : "sine"}
            num={item.name}
            time={item.remains}
            canexpand
            stationlist={busRouteData.stations}
          />
        </ScrollView>
      </BusContainer>
    </Conatiner>
  );
}

export default BusDetail;
