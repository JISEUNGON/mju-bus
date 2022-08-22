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
