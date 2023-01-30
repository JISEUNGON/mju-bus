import { useQueries } from "@tanstack/react-query";
import styled from "styled-components";
import { busApi } from "./api";
import React from 'react'

const HighlightTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: #7974e7;
`;


export function sortTimeTable(a, b) {
  return (
    parseInt(a.depart_at.substr(0, 2) + a.depart_at.substr(3, 4), 10) -
    parseInt(b.depart_at.substr(0, 2) + b.depart_at.substr(3, 4), 10)
  );
}

export function highlights(contents) {
  // eslint-disable-next-line react/react-in-jsx-scope
  return <HighlightTitle>{contents}</HighlightTitle>;
}

export function highlightTaxi(contents){
  //
  return <HighlightTaxi>{contents}</HighlightTaxi>;
}

export function GetTimeTableData(list) {
  const timeTable = useQueries({
    queries: list.map(bus => ({
      queryKey: ["timeTable", bus.id],
      queryFn: busApi.timeTable,
    })),
  });
  return timeTable;
}

export function GetRouteTableData(list) {
  const routeTable = useQueries({
    queries: list.map(bus => ({
      queryKey: ["route", bus.id],
      queryFn: busApi.route,
    })),
  });
  return routeTable;
}

export function GetBusInfoData(list) {
  const busTable = useQueries({
    queries: list.map(bus => ({
      queryKey: ["info", bus.id],
      queryFn: busApi.bus,
    })),
  });
  return busTable;
}

export function RemoveDuplicateStation(data) {
  let stationList = [];

  const stations = data?.map(route => route.data.stations);
  // eslint-disable-next-line array-callback-return
  stations?.map(list => {
    stationList.push(...list);
  });

  stationList = stationList.reduce((acc, current) => {
    if (acc.findIndex(({ id }) => id === current.id) === -1) {
      acc.push(current);
    }
    return acc;
  }, []);

  return stationList;
}

export function getHiddenStation(toSchool) {
  return toSchool ? [3, 2, 1, 6] : [4, 5, 1, 6, 11, 24];
}

export function RemoveHiddenStation(stationList, toSchool) {
  const hiddenStations = getHiddenStation(toSchool);
  return stationList.filter(station => !hiddenStations.includes(station.id));
}

export function CalculatorTime(start, end) {
  const startSec = start.split(":");
  const endSec = end.split(":");

  const startResult =
    Math.floor(startSec[0]) * 3600 + Math.floor(startSec[1]) * 60;

  const endResult = Math.floor(endSec[0]) * 3600 + Math.floor(endSec[1]) * 60;

  // 만약 result가 음수라면? : endTime만 자정을 넘긴 경우 -> 이 경우 endResult에 24시간을 더해준다.
  // 만약 result가 0이라면? : 1분으로 지정

  const result = endResult - startResult;

  if (result === 0) {
    return 1;
  }
  if (result > 0) {
    return Math.floor(result / 60);
  }
  return Math.floor((result + 24 * 3600) / 60);
}

export function DeleteSecond(str) {
  const temp = str.split(":");

  const result = `${temp[0]}:${temp[1]}`;

  return result;
}
