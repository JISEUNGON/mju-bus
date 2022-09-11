import { useQueries } from "@tanstack/react-query";
import styled from "styled-components";
import { busApi } from "./api";

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

export function CalculatorTime(start, end) {
  const startSec = start.split(":");
  const endSec = end.split(":");

  const startResult =
    Math.floor(startSec[0]) * 3600 + Math.floor(startSec[1]) * 60;

  const endResult = Math.floor(endSec[0]) * 3600 + Math.floor(endSec[1]) * 60;

  const result = Math.floor((endResult - startResult) / 60);

  return result;
}

export function DeleteSecond(str) {
  const temp = str.split(":");

  const result = `${temp[0]}:${temp[1]}`;

  return result;
}
