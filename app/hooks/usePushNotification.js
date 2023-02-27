import { useCallback, useEffect, useState, useContext } from "react";
import { requestNotifications, RESULTS } from "react-native-permissions";
import { Alert } from "react-native";
import messaging from "@react-native-firebase/messaging";
import AuthContext from "../components/AuthContext";

const usePushNotification = () => {
  const [fcmToken, setFcmToken] = useState(null);

  const { user, addFcmToken } = useContext(AuthContext);

  // 앱이 켜졌을 때 디바이스별 FCM 토큰을 가져온다
  useEffect(() => {
    messaging()
      .getToken()
      .then(token => {
        setFcmToken(token);
      });
  }, []);

  // 앱 사용 도중 토큰이 Refresh 될 경우
  useEffect(() => {
    const unsubscribe = messaging().onTokenRefresh(token => {
      setFcmToken(token);
    });
    return () => {
      unsubscribe();
    };
  }, []);

  // 토큰 저장
  useEffect(() => {
    if (user != null && fcmToken != null) {
      addFcmToken(fcmToken);
    }
  }, [user, fcmToken, addFcmToken]);

  //권한 요청
  const requestPermission = useCallback(async () => {
    const { status } = await requestNotifications([]);
    const enabled = status === RESULTS.GRANTED;

    //알람 권한 꺼져있을 때
    if (!enabled) {
      Alert.alert("알림 권한을 허용해주세요!");
    }
  }, []);

  useEffect(() => {
    requestPermission();
  }, [requestPermission]);
};

export default usePushNotification;
