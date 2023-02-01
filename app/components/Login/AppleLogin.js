import React, { useEffect } from "react";
import {TouchableOpacity, Image, StyleSheet } from 'react-native';
import AppleImage from "../../assets/image/apple_login.png"
import { appleAuth } from '@invertase/react-native-apple-authentication';

export default function AppleLogin () {
    const styles = StyleSheet.create({
        btn: {
            flex: 1,
            width: null,
            resizeMode: 'contain',
            height: 220
        },
    });

    const apple_login = async () => {
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
        <TouchableOpacity onPress={apple_login}>
            <Image source={AppleImage}  style={styles.btn}/>
        </TouchableOpacity>
    );
}