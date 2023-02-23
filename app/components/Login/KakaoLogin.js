import React, { useCallback, useContext, useState } from "react";
import { TouchableOpacity, Image } from "react-native";
import KakaoImage from "../../assets/image/kakao_login.png";
import { loginApi } from "../../api";
import { login } from "@react-native-seoul/kakao-login";
import { useNavigation } from "@react-navigation/native";
import AuthContext from "../AuthContext";

function KakaoLogin() {
  const navigation = useNavigation();

  const { kakoSignin } = useContext(AuthContext);

  const onPressKakoSigninButton = useCallback(async () => {
    try {
      await kakoSignin();
      navigation.navigate("StudentAuth");
    } catch (e) {
      if (e.message === "E_CANCELLED_OPERATION") {
        // 그대로
        console.log("kako login 취소");
        return;
      }
    }
  }, []);

  return (
    <TouchableOpacity onPress={onPressKakoSigninButton}>
      <Image source={KakaoImage} />
    </TouchableOpacity>
  );
}

export default KakaoLogin;
