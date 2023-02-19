import React, { useState }from "react";
import {TouchableOpacity, Image } from 'react-native';
import KakaoImage from "../../assets/image/kakao_login.png"
import { loginApi } from "../../api";
import { login } from '@react-native-seoul/kakao-login';
import { useNavigation } from '@react-navigation/native';
import AsyncStorage from "@react-native-async-storage/async-storage";

const storeData = async (value) => {
    try {
      await AsyncStorage.setItem('KakaoAccessToken', JSON.stringify(value));
      console.log('token saved successfully');
    } catch (e) {
      console.log('token saved error : Asynce Storage');
    }
};

function KakaoLogin() {
    const [onLogin, setOnLogin] = useState(false);
    const [user, setUser] = useState(null);
    const navigation = useNavigation();

    const onKaKaoButtonPress = async () => {
        if (onLogin) return;
        setOnLogin(true);

        login().then(res => {
            const payload = {
                "accessToken": res.accessToken,
                "accessTokenExpiresAt": res.accessTokenExpiresAt,
                "refreshToken": res.refreshToken,
                "refreshTokenExpiresAt": res.refreshTokenExpiresAt,
            }

            loginApi.kakao_login({ queryKey: {payload} }).then(res => {
                setUser(res);
                storeData(res);
            });
            navigation.navigate("StudentAuth");
        
        }).catch(err => {
            console.log("Kakao Auth failed : ", err);
        });
        AsyncStorage.getItem('KakaoAccessToken').then(res =>
            console.log('Storage Token : ', res),
        );
        setOnLogin(false);
    
    }

    return (
        <TouchableOpacity onPress={onKaKaoButtonPress}>
            <Image source={KakaoImage} />
        </TouchableOpacity>
    );
}

export default KakaoLogin;