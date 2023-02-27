import { useCallback, useMemo, useState, useEffect } from "react";
import { loginApi } from "../api";
import { login } from "@react-native-seoul/kakao-login";
import { GoogleSignin } from "@react-native-google-signin/google-signin";
import AsyncStorage from "@react-native-async-storage/async-storage";
import AuthContext from "./AuthContext";
import { cos } from "react-native-reanimated";
import { KEY_TOKENS, FCM_TOKENS } from "../screens/StorageKey";
import { appleAuth } from "@invertase/react-native-apple-authentication";
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

  //게스트 로그인 콜백 함수 (토큰 값 : '')
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

  //카카오 로그인 콜백 함수
  const googleSignin = useCallback(async () => {
    try {
      GoogleSignin.configure({
        offlineAccess: true, // if you want to access Google API on behalf of the user FROM YOUR SERVER
        forceCodeForRefreshToken: true, // [Android] related to `serverAuthCode`, read the docs link below *.
        webClientId:
          "62083011019-hh3mjlg5ksujp949pt71aoibri7mjhc3.apps.googleusercontent.com",
        iosClientId:
          "62083011019-d2bldmt0gb4kqvb14r496ppuh3b5mme7.apps.googleusercontent.com", // [iOS] if you want to specify the client ID of type iOS (otherwise, it is taken from GoogleService-Info.plist)
      });
      await GoogleSignin.hasPlayServices();
      const userInfo = await GoogleSignin.signIn();

      const payload = {
        id: userInfo.user.id,
        serverAuthCode: userInfo.serverAuthCode,
      };

      loginApi.google_login({ queryKey: { payload } });
    } catch (e) {
      //로그인 취소할 경우
      if (e.code === "-5") {
        throw new Error("E_CANCELLED_OPERATION");
      }
    }
  }, []);

  //애플 로그인 콜백 함수
  const appleSignin = useCallback(async () => {
    try {
      if (!appleAuth.isSupported) {
        // 안드로이드인 경우 보여지지 않음.
        console.log("Apple Authentication is not supported on this device.");
      } else {
        const appleAuthRequestResponse = await appleAuth.performRequest({
          requestedOperation: appleAuth.Operation.LOGIN,
          requestedScopes: [],
        });

        const payload = {
          authorizationCode: appleAuthRequestResponse.authorizationCode,
          identityToken: appleAuthRequestResponse.identityToken,
          user: appleAuthRequestResponse.user,
        };

        loginApi.apple_login({ queryKey: { payload } }).then(async res => {
          setUser(res);
          await AsyncStorage.setItem(KEY_TOKENS, JSON.stringify(res));
        });
      }
    } catch (e) {
      //로그인 취소할 경우
      if (e.code === "1001") {
        throw new Error("E_CANCELLED_OPERATION");
      }
    }
  }, []);

  const value = useMemo(() => {
    return {
      user,
      kakoSignin,
      googleSignin,
      checkValidateToken,
      guestSignin,
      addFcmToken,
      appleSignin,
    };
  }, [
    kakoSignin,
    googleSignin,
    user,
    checkValidateToken,
    guestSignin,
    addFcmToken,
    appleSignin,
  ]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export default AuthProvider;
