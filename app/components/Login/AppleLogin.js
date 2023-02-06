import React from "react";
import {TouchableOpacity, Image } from 'react-native';
import AppleImage from "../../assets/image/apple_login.png"
import { appleAuth } from '@invertase/react-native-apple-authentication';

export default function AppleLogin () {
    const supported = appleAuth.isSupported;

    const onAppleButtonPress = async () => {
        if (!appleAuth.isSupported) {
            // 안드로이드인 경우 보여지지 않음. 
            console.log('Apple Authentication is not supported on this device.');
        } else {
            const appleAuthRequestResponse = await appleAuth.performRequest({
                requestedOperation: appleAuth.Operation.LOGIN,
                requestedScopes: [],
            });
            console.log(appleAuthRequestResponse);
        }
    }
    return (
        <TouchableOpacity onPress={onAppleButtonPress}>
            {supported && <Image source={AppleImage} />}
        </TouchableOpacity>
    );
}