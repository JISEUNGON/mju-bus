import React, { useContext, useEffect, useRef, useState } from "react";
import styled from "styled-components";
import { Animated, BackHandler, Dimensions } from "react-native";
import * as Font from "expo-font";
import { Ionicons } from "@expo/vector-icons";
import AuthContext from "../components/AuthContext";
import usePushNotification from "../hooks/usePushNotification";
import AppContext from "../components/AppContext";
import { memberApi } from "../api";
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
  usePushNotification();
  const {} = React.useContext(AppContext); // 데이터를 가져오기 위해 사용

  const { checkValidateToken, user } = useContext(AuthContext);
  const [userRole, setUserRole] = useState(null);
  const fadeAnim = useRef(new Animated.Value(0)).current;
  const [appIsReady, setAppIsReady] = useState(false);



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

        await checkValidateToken();

        memberApi.member().then(res => {
          setUserRole(res.role);
        }).catch(err => {});
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

    if (appIsReady) {
      // This tells the splash screen to hide immediately! If we call this after
      // `setAppIsReady`, then we may see a blank screen while the app is
      // loading its initial state and rendering its first pixels. So instead,
      // we hide the splash screen once we know the root view has already
      // performed layout.

      if (user !== null) {
        if (userRole === null || userRole === "GUEST") { // 로그인 되어있지만, 명지대 인증이 안되어있을 때
          navigate("StudentAuth");
        } else { // 로그인 되어있고, 명지대 인증이 되어있을 때
          navigate("HomeBottomTabs", {
            screen: "Home",
          });
        }
      } else { // 로그인 안되어있을 때
        navigate("Login");
      }
    }
  }, [appIsReady]);

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
