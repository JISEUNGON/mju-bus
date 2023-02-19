
import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import { Animated, BackHandler, Dimensions } from "react-native";
import * as Font from "expo-font";
import { useQuery } from "@tanstack/react-query";
import { Ionicons } from "@expo/vector-icons";
import { busApi, calendarApi } from "../api";
import { MBAContext } from "../navigation/Root";
const { height: SCREEN_HEIGHT } = Dimensions.get("window");

const Circle = styled.View`
  width: 100px;
  height: 100px;
  border-radius: 50px;
  justify-content: center;
  align-items: center;
  background-color: #dad8fb;
`;

const Board = styled.View`
  background-color: white;
  width: 35px;
  height: 36px;
  position: absolute;
`;

const Container = styled.View`
  background-color: ${props => props.theme.homeBgColor};
  flex: 1;
`;

const IconContainer = styled.View`
  height: ${SCREEN_HEIGHT / 2 + 50}px;
  align-items: center;
  justify-content: flex-end;
`;

const TextContainer = styled.View`
  height: ${SCREEN_HEIGHT / 2 - 50}px;
  align-items: center;
  justify-content: flex-end;
  padding-bottom: 50px;
`;

const customFonts = {
  "SpoqaHanSansNeo-Bold": require("../assets/fonts/SpoqaHanSansNeo-Bold.ttf"),
  "SpoqaHanSansNeo-Light": require("../assets/fonts/SpoqaHanSansNeo-Light.ttf"),
  "SpoqaHanSansNeo-Medium": require("../assets/fonts/SpoqaHanSansNeo-Medium.ttf"),
};


function Splash({ navigation: { navigate } }) {
  const {
    sineBusList,
    siweBusList,
    mjuCalendar,
    stationList,
    busTimeTable,
    setSineBusList,
    setSiweBusList,
    setMjuCalendar,
    setStationList, 
    setBusTimeTable
  } = React.useContext(MBAContext);

  const fadeAnim = useRef(new Animated.Value(0)).current;
  const [appIsReady, setAppIsReady] = useState(false);

  // 버스 리스트
  const { isLoading: buslistLoading, data: busListData } = useQuery(
    ["busList"],
    busApi.list,
  );

  // 학사일정
  const { isLoading: calendarLoading, data: calendarData } = useQuery(
    ["calendar"],
    calendarApi.calendar,
  );

  // 전체 정류장 목록
  const loading = buslistLoading || calendarLoading || stationList.length === 0;

  useEffect(() => {
    const getStationList = async (bus) => {
      const res = await busApi.route({"queryKey" :["", bus.id]});
      const stations = res.stations;
      setStationList([
        ...stationList,
        {
          id: bus.id,
          name: bus.name,
          stations,
        }
      ]);
    };

    if (!buslistLoading) { // 버스 리스트 로딩이 완료되면
      // 버스 리스트를 저장한다.
      setSineBusList(busListData.sine_bus_list);
      setSiweBusList(busListData.siwe_bus_list);

      // 버스별 정류장 목록을 가져온다.
      busListData.sine_bus_list.forEach(async (bus) => {
        await getStationList(bus);
      });

      // [시내]버스별 시간표를 가져온다.
      busListData.sine_bus_list.forEach(async (bus) => {
        const res = await busApi.timeTable({"queryKey" :["", bus.id]});
        setBusTimeTable([
          ...busTimeTable,
          {
            id: bus.id,
            name: bus.name,
            timeTable: res.stations,
          }
        ]);
      });

      // [시외]버스별 시간표를 가져온다.
      busListData.siwe_bus_list.forEach(async (bus) => {
        const res = await busApi.timeTable({"queryKey" :["", bus.id]});
        setBusTimeTable([
          ...busTimeTable,
          {
            id: bus.id,
            name: bus.name,
            timeTable: res.stations,
          }
        ]);
      });
    }
  }, [buslistLoading]);

  // 학사일정 가져오기
  useEffect(() => {
    if (!calendarLoading) {
      setMjuCalendar(calendarData);
    }
  }, [calendarLoading]);

  useEffect(() => {
    Animated.timing(fadeAnim, {
      toValue: 1,
      duration: 1000,
      useNativeDriver: true,
    }).start();
  }, [fadeAnim]);

  useEffect(() => {
    async function prepare() {
      try {
        // Pre-load fonts, make any API calls you need to do here
        await Font.loadAsync(customFonts);
        // Splash Screen 1초 보여주기
        // eslint-disable-next-line no-promise-executor-return
        await new Promise(resolve => setTimeout(resolve, 1000));
      } catch (e) {
        console.warn(e);
      } finally {
        //   // Tell the application to render
        setAppIsReady(true);
      }
    }

    prepare();
  }, []);

  useEffect(() => {
    const backAction = () => true;

    BackHandler.addEventListener("hardwareBackPress", backAction);

    if (appIsReady && !loading) {
      // This tells the splash screen to hide immediately! If we call this after
      // `setAppIsReady`, then we may see a blank screen while the app is
      // loading its initial state and rendering its first pixels. So instead,
      // we hide the splash screen once we know the root view has already
      // performed layout.
      navigate("HomeBottomTabs", {
        screen: "홈",
      });
    }
  }, [appIsReady, busListData, calendarData, loading, navigate, stationList]);

  return (
      <Container>
        <IconContainer>
          <Animated.View style={{ opacity: fadeAnim }}>
            <Circle>
              <Board />
              <Ionicons name="ios-bus" size={50} color="#7974E7" />
            </Circle>
          </Animated.View>
        </IconContainer>
        <TextContainer />
      </Container>
  );
}

export default Splash;
