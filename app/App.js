import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ThemeProvider } from "styled-components/native";
import { useColorScheme } from "react-native";
import CodePush from "react-native-code-push";
import Root from "./navigation/Root";
import { darkTheme, lightTheme } from "./styled";
import useCodePush from "./hooks";
import SyncProgressView from "./screens/SyncProgressView";
import TaxiTabs from "./navigation/TaxiDetailTabs";
import { TaxiChatProvider } from "./screens/Taxi/Taxicontext";


const queryClient = new QueryClient();


function App() {
  const isDark = useColorScheme() === "dark";
  const [isUpdating, syncProgress] = useCodePush();
  return (
    <TaxiChatProvider>
      <QueryClientProvider client={queryClient}>
        <ThemeProvider theme={isDark ? darkTheme : lightTheme}>
          {isUpdating ? (
            <SyncProgressView syncProgress={syncProgress} />
          ) : (
            <NavigationContainer>
              <Root />
            </NavigationContainer>
          )}
        </ThemeProvider>
      </QueryClientProvider>
    </TaxiChatProvider>
  );
}

export default CodePush(App);
