import { useCallback, useMemo, useState, useEffect } from "react";
import { useQuery } from "@tanstack/react-query";
import BusContext from "./AppContext";
import { busApi, calendarApi, stationApi } from "../api";

const AppProvider = ({ children }) => {

    const [mjuCalendar, setCalendar] = useState(null);
    const [stationList, setStationList] = useState([]);
    const [busTimeTable, setBusTimeTable] = useState([]);
    const [sineBusList, setSineBusList] = useState([]);
    const [siweBusList, setSiweBusList] = useState([]);

    const { isLoading: buslistLoading, data: busListData } = useQuery(
        ["busList"],
        busApi.list,
    );
    
    const { isLoading: calendarLoading, data: calendarData } = useQuery(
        ["calendar"],
        calendarApi.calendar,
    ); 

    const getStationList = async bus => {
        const res = await busApi.route({ queryKey: ["", bus.id] });
        const stations = res.stations;
        setStationList([
          ...stationList,
          {
            id: bus.id,
            name: bus.name,
            stations,
          },
        ]);
      };

    useEffect(() => {
        if (!buslistLoading) {
            // 버스 리스트 로딩이 완료되면
            // 버스 리스트를 저장한다.
            setSineBusList(busListData.sine_bus_list);
            setSiweBusList(busListData.siwe_bus_list);

            // 버스별 정류장 목록을 가져온다.
            busListData.sine_bus_list.forEach(async bus => {
                await getStationList(bus);
            });


            // [시내]버스별 시간표를 가져온다.
            busListData.sine_bus_list.forEach(async bus => {
                const res = await busApi.timeTable({ queryKey: ["", bus.id] });
                setBusTimeTable([
                    ...busTimeTable,
                    {
                        id: bus.id,
                        name: bus.name,
                        timeTable: res.stations,
                    },
                ]);
            });

            // [시외]버스별 시간표를 가져온다.
            busListData.siwe_bus_list.forEach(async bus => {
                const res = await busApi.timeTable({ queryKey: ["", bus.id] });
                setBusTimeTable([
                ...busTimeTable,
                {
                    id: bus.id,
                    name: bus.name,
                    timeTable: res.stations,
                },
                ]);
            });
        } 
    }, [buslistLoading]);

    useEffect(() => {
        if (!calendarLoading) {
            setCalendar(calendarData);
        }
    }, [calendarData]);

    const value = useMemo(() => {
        return {
            mjuCalendar,
            stationList,
            busTimeTable,
            sineBusList,
            siweBusList,
        };
    }, [mjuCalendar, stationList, busTimeTable, sineBusList, siweBusList]);
    
    return <BusContext.Provider value={value}>{children}</BusContext.Provider>;
};

export default AppProvider;