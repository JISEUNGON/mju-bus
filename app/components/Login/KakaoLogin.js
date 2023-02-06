import React, { useEffect } from "react";
import {TouchableOpacity, Image, StyleSheet } from 'react-native';
import KakaoImage from "../../assets/image/kakao_login.png"
import { login } from '@react-native-seoul/kakao-login';

function KakaoLogin() {

    const onKaKaoButtonPress = async () => {
        try {
            const token = await login();
            console.log(token);
        } catch (err) {
            console.log("Error ! ");
            console.log(err);
        }
    }

    return (
        <TouchableOpacity onPress={onKaKaoButtonPress}>
            <Image source={KakaoImage} />
        </TouchableOpacity>
    );
}

export default KakaoLogin;