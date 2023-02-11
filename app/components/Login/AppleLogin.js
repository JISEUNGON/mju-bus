import React, { useState } from "react";
import {TouchableOpacity, Image } from 'react-native';
import AppleImage from "../../assets/image/apple_login.png"
import { appleAuth } from '@invertase/react-native-apple-authentication';
import { loginApi } from "../../api";

export default function AppleLogin () {
    const [onLogin, setOnLogin] = useState(false);
    const [user, setUser] = useState(null);
    const supported = appleAuth.isSupported;

    const onAppleButtonPress = async () => {
        if (onLogin) return;

        setOnLogin(true);
        if (!appleAuth.isSupported) {
            // 안드로이드인 경우 보여지지 않음. 
            console.log('Apple Authentication is not supported on this device.');
        } else {
            const appleAuthRequestResponse = await appleAuth.performRequest({
                requestedOperation: appleAuth.Operation.LOGIN,
                requestedScopes: [],
            }).catch(err => {
                console.log("Apple Auth failed : ", err);
                setOnLogin(false);
                return;
            });
            
            const payload = {
                'authorizationCode': appleAuthRequestResponse.authorizationCode,
                'identityToken': appleAuthRequestResponse.identityToken,
                'user': appleAuthRequestResponse.user,
            }

            loginApi.apple_login({ queryKey: {payload} }).then(res =>  setUser(res));   
        }

        setOnLogin(false);
    }
    return (
        <TouchableOpacity onPress={onAppleButtonPress}>
            {supported && <Image source={AppleImage} />}
        </TouchableOpacity>
    );
}

