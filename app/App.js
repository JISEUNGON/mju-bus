import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ThemeProvider } from "styled-components/native";
import { useColorScheme } from "react-native";
import CodePush from "react-native-code-push";
import Root from "./navigation/Root";
import { darkTheme, lightTheme } from "./styled";
import useCodePush from "./hooks/useCodePush";
import SyncProgressView from "./screens/SyncProgressView";
import AppProvider from "./components/AppProvider";
import AuthProvider from "./components/AuthProvider";

const queryClient = new QueryClient();

function App() {
  const isDark = useColorScheme() === "dark";
  const [isUpdating, syncProgress] = useCodePush();

  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={isDark ? darkTheme : lightTheme}>
        {isUpdating ? (
          <SyncProgressView syncProgress={syncProgress} />
        ) : (
          <NavigationContainer>
            <AppProvider>
              <AuthProvider>
                  <Root />
              </AuthProvider>
            </AppProvider>
          </NavigationContainer>
        )}
      </ThemeProvider>
    </QueryClientProvider>
  );
}

export default CodePush(App);
