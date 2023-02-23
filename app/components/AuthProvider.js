import { useCallback, useMemo, useState, useEffect } from "react";
import { loginApi } from "../api";
import { login } from "@react-native-seoul/kakao-login";
import AsyncStorage from "@react-native-async-storage/async-storage";
import AuthContext from "./AuthContext";
import { cos } from "react-native-reanimated";
import { KEY_TOKENS, FCM_TOKENS } from "../screens/StorageKey";

const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  const addFcmToken = useCallback(
    async token => {
      if (user != null) {
        // 현재는 AsyncStorage에 토큰을 저장한다 -> DB저장 로직으로 변경
        await AsyncStorage.setItem(FCM_TOKENS, JSON.stringify(token));
      }
    },
    [user],
  );

  const checkValidateToken = useCallback(async () => {
    try {
      const tokens = await AsyncStorage.getItem(KEY_TOKENS);
      if (tokens !== null) {
        setUser(tokens);
      }
    } catch (e) {
      console.log(e);
    }
  }, []);

  const guestSignin = useCallback(async () => {
    try {
      await AsyncStorage.setItem(KEY_TOKENS, "");
    } catch (e) {
      console.log(e);
    }
  });

  //카카오 로그인 콜백 함수
  const kakoSignin = useCallback(async () => {
    try {
      await login().then(res => {
        const payload = {
          accessToken: res.accessToken,
          accessTokenExpiresAt: res.accessTokenExpiresAt,
          refreshToken: res.refreshToken,
          refreshTokenExpiresAt: res.refreshTokenExpiresAt,
        };

        loginApi.kakao_login({ queryKey: { payload } }).then(async res => {
          setUser(res);
          await AsyncStorage.setItem(KEY_TOKENS, JSON.stringify(res));
          console.log(res);
        });
      });
    } catch (e) {
      //카카오 로그인 취소할 경우
      if (e.code === "RNKakaoLogins") {
        throw new Error("E_CANCELLED_OPERATION");
      }
    }
  }, []);

  const value = useMemo(() => {
    return {
      user,
      kakoSignin,
      checkValidateToken,
      guestSignin,
      addFcmToken,
    };
  }, [kakoSignin, user, checkValidateToken, guestSignin, addFcmToken]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export default AuthProvider;
