import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ThemeProvider } from "styled-components/native";
import { useColorScheme } from "react-native";
import CodePush from "react-native-code-push";
import Root from "./navigation/Root";
import { darkTheme, lightTheme } from "./styled";

const queryClient = new QueryClient();

function App() {
  const isDark = useColorScheme() === "dark";
  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={isDark ? darkTheme : lightTheme}>
        <NavigationContainer>
          <Root />
        </NavigationContainer>
      </ThemeProvider>
    </QueryClientProvider>
  );
}

const codePushOptions = {
  checkFrequency: CodePush.CheckFrequency.ON_APP_START,
  updateDialog: {
    title: "...",
    optionalUpdateMessage: "...",
    optionalInstallButtonLabel: "업데이트",
    optionalIgnoreButtonLabel: "아니요.",
  },
  installMode: CodePush.InstallMode.IMMEDIATE,
};
export default CodePush(codePushOptions)(App);
