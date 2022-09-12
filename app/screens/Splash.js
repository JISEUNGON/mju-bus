/* eslint-disable global-require */
import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import { Animated, BackHandler } from "react-native";
import * as Font from "expo-font";
import { useQuery } from "@tanstack/react-query";
import { Ionicons } from "@expo/vector-icons";
import { busApi, calendarApi } from "../api";

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
  background-color: white;
  flex: 1;
`;

const IconContainer = styled.View`
  align-items: center;
  justify-content: center;
  flex: 4;
`;

const customFonts = {
  "SpoqaHanSansNeo-Bold": require("../assets/fonts/SpoqaHanSansNeo-Bold.ttf"),
  "SpoqaHanSansNeo-Light": require("../assets/fonts/SpoqaHanSansNeo-Light.ttf"),
  "SpoqaHanSansNeo-Medium": require("../assets/fonts/SpoqaHanSansNeo-Medium.ttf"),
};

// eslint-disable-next-line react/prop-types
function Splash({ navigation: { navigate } }) {
  const fadeAnim = useRef(new Animated.Value(0)).current;

  const [appIsReady, setAppIsReady] = useState(false);

  const { isLoading: buslistLoading, data: busListData } = useQuery(
    ["busList"],
    busApi.list,
  );
  const { isLoading: calendarLoading, data: calendarData } = useQuery(
    ["calendar"],
    calendarApi.calendar,
  );
  const loading = buslistLoading || calendarLoading;

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
        // Tell the application to render
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
        params: {
          calendarData,
          busListData,
        },
      });
    }
  }, [appIsReady, busListData, calendarData, loading, navigate]);

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
    </Container>
  );
}

export default Splash;
