import React, { useEffect } from "react";
import {TouchableOpacity, Image, StyleSheet } from 'react-native';
import AppleImage from "../../assets/image/apple_login.png"
import { appleAuth } from '@invertase/react-native-apple-authentication';

export default function AppleLogin () {

    const onAppleButtonPress = async () => {
        if (!appleAuth.isSupported) {
            // FIX-ME - show some UI 
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
            <Image source={AppleImage} />
        </TouchableOpacity>
    );
}