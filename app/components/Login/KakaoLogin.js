import React, { useEffect } from "react";
import {TouchableOpacity, Image, StyleSheet } from 'react-native';
import KakaoImage from "../../assets/image/kakao_login.png"
import KakaoSDK from '@actbase/react-kakaosdk';

function KakaoLogin() {
    useEffect(() => {
        async function init() {
            await KakaoSDK.init(process.env.REACT_APP_KAKAO_NATIVE_KEY);
        }
        init();
    }, []);

    const styles = StyleSheet.create({
        btn: {
            flex: 1,
            width: null,
            resizeMode: 'contain',
            height: 220
        },
    });

    const kakao_login = async () => {
        try {
            const token = await KakaoSDK.login();
            console.log(token);
        } catch (err) {
            console.log("Error ! ");
            console.log(err);
        }
    }

    return (
        <TouchableOpacity onPress={kakao_login}>
            <Image source={KakaoImage}  style={styles.btn}/>
        </TouchableOpacity>
    );
}

export default KakaoLogin;