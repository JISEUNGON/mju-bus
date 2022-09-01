/* eslint-disable global-require */
import React, { useEffect, useState } from "react";
import { Text, View } from "react-native";
import * as Font from "expo-font";
import { useQuery } from "@tanstack/react-query";
import { busApi, calendarApi } from "../api";

const customFonts = {
  "SpoqaHanSansNeo-Bold": require("../assets/fonts/SpoqaHanSansNeo-Bold.ttf"),
  "SpoqaHanSansNeo-Light": require("../assets/fonts/SpoqaHanSansNeo-Light.ttf"),
  "SpoqaHanSansNeo-Medium": require("../assets/fonts/SpoqaHanSansNeo-Medium.ttf"),
};

// eslint-disable-next-line react/prop-types
function Splash({ navigation: { navigate } }) {
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
    async function prepare() {
      try {
        // Pre-load fonts, make any API calls you need to do here
        await Font.loadAsync(customFonts);
        // Splash Screen 2초 보여주기
        // await new Promise((resolve) => setTimeout(resolve, 2000));
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
    <View style={{ flex: 1, alignItems: "center", justifyContent: "center" }}>
      <Text>MBA SPLASH SCREEN!!🚌🚌</Text>
    </View>
  );
}

export default Splash;
