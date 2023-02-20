import { useCallback, useMemo, useState, useEffect } from "react";
import { loginApi } from "../api";
import { login } from "@react-native-seoul/kakao-login";
import AsyncStorage from "@react-native-async-storage/async-storage";
import AuthContext from "./AuthContext";
import { cos } from "react-native-reanimated";
import { KEY_TOKENS } from "../screens/StorageKey";

const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

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
      await AsyncStorage.setItem(KEY_TOKENS, " ");
    } catch (e) {
      console.log(e);
    }
  });

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
    };
  }, [kakoSignin, user, checkValidateToken, guestSignin]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export default AuthProvider;
