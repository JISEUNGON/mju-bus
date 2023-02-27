import React, { useEffect, useState, useContext, useCallback } from "react";
import { TouchableOpacity, Image } from "react-native";
import GoogleImage from "../../assets/image/google_login.png";
import { loginApi } from "../../api";
import { GoogleSignin } from "@react-native-google-signin/google-signin";
import { useNavigation } from "@react-navigation/native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import AuthContext from "../AuthContext";

const GoogleLogin = () => {
  const { googleSignin } = useContext(AuthContext);
  const navigation = useNavigation();

  const onGoogleButtonPress = useCallback(async () => {
    try {
      await googleSignin();
      navigation.navigate("StudentAuth");
    } catch (error) {
      console.log("google login 취소");
      return;
    }
  }, []);
  return (
    <TouchableOpacity onPress={onGoogleButtonPress}>
      <Image source={GoogleImage} />
    </TouchableOpacity>
  );
};
export default GoogleLogin;
