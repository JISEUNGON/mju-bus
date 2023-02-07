import React, { useState }from "react";
import {TouchableOpacity, Image } from 'react-native';
import KakaoImage from "../../assets/image/kakao_login.png"
import base64 from 'base-64'
import { loginApi } from "../../api";
import { login } from '@react-native-seoul/kakao-login';

function KakaoLogin() {
    const [onLogin, setOnLogin] = useState(false);
    const [user, setUser] = useState(null);

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

            const encodedPayLoad = base64.encode(JSON.stringify(payload));
            loginApi.kakao_login({ queryKey: {encodedPayLoad} }).then(res => setUser(res));
        
        }).catch(err => {
            console.log("Kakao Auth failed : ", err);
        });
        setOnLogin(false);
    
    }

    return (
        <TouchableOpacity onPress={onKaKaoButtonPress}>
            <Image source={KakaoImage} />
        </TouchableOpacity>
    );
}

export default KakaoLogin;