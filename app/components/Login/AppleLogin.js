import React, { useState, useContext, useCallback } from "react";
import { TouchableOpacity, Image } from "react-native";
import AppleImage from "../../assets/image/apple_login.png";
import { appleAuth } from "@invertase/react-native-apple-authentication";
import { useNavigation } from "@react-navigation/native";
import AuthContext from "../AuthContext";

export default function AppleLogin() {
  const supported = appleAuth.isSupported;
  const { appleSignin } = useContext(AuthContext);
  const navigation = useNavigation();

  const onAppleButtonPress = useCallback(async () => {
    try {
      await appleSignin();
      navigation.navigate("StudentAuth");
    } catch (error) {
      console.log("apple login 취소");
      return;
    }
  }, []);
  return (
    <TouchableOpacity onPress={onAppleButtonPress}>
      {supported && <Image source={AppleImage} />}
    </TouchableOpacity>
  );
}
