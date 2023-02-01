import { React, useRef } from "react";
import analytics from "@react-native-firebase/analytics";
import {
  NavigationContainer,
  useNavigationContainerRef,
} from "@react-navigation/native";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ThemeProvider } from "styled-components/native";
import { useColorScheme } from "react-native";
import CodePush from "react-native-code-push";
import Root from "./navigation/Root";
import { darkTheme, lightTheme } from "./styled";
import useCodePush from "./hooks";
import SyncProgressView from "./screens/SyncProgressView";

const queryClient = new QueryClient();

function App() {
  const navigationRef = useNavigationContainerRef();
  const routeNameRef = useRef();
  const isDark = useColorScheme() === "dark";
  const [isUpdating, syncProgress] = useCodePush();
  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={isDark ? darkTheme : lightTheme}>
        {isUpdating ? (
          <SyncProgressView syncProgress={syncProgress} />
        ) : (
          <NavigationContainer
            ref={navigationRef}
            onReady={() => {
              routeNameRef.current =
                navigationRef.current.getCurrentRoute()?.name;
            }}
            onStateChange={() => {
              const previousScreenName = routeNameRef.current;

              const currentRoute = navigationRef.current.getCurrentRoute();

              const currentScreenName = `${currentRoute?.name}_${Object.values(
                currentRoute?.params || {},
              ).join("/")}`;

              if (currentRoute && previousScreenName !== currentScreenName) {
                analytics().logScreenView({
                  screen_name: currentScreenName,
                  screen_class: currentRoute.name,
                });
              }
              // Save the current route name for later comparision
              routeNameRef.current = currentScreenName;
            }}
          >
            <Root />
          </NavigationContainer>
        )}
      </ThemeProvider>
    </QueryClientProvider>
  );
}

export default CodePush(App);
