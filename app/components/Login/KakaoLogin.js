import React, { useEffect } from "react";
import {TouchableOpacity, Image, StyleSheet } from 'react-native';
import KakaoImage from "../../assets/image/kakao_login.png"
import { login } from '@react-native-seoul/kakao-login';

function KakaoLogin() {

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
            const token = await login();
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