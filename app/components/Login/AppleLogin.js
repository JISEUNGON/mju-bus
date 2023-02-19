import React, { useState } from "react";
import {TouchableOpacity, Image } from 'react-native';
import AppleImage from "../../assets/image/apple_login.png"
import { appleAuth } from '@invertase/react-native-apple-authentication';
import { loginApi } from "../../api";
import { useNavigation } from '@react-navigation/native';

const storeData = async (value) => {
    try {
      await AsyncStorage.setItem('AppleAccessToken', JSON.stringify(value));
      console.log('token saved successfully');
    } catch (e) {
      console.log('token saved error : Asynce Storage');
    }
};

export default function AppleLogin () {
    const [onLogin, setOnLogin] = useState(false);
    const [user, setUser] = useState(null);
    const supported = appleAuth.isSupported;
    const navigation = useNavigation();

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

            loginApi.apple_login({ queryKey: {payload} }).then(res =>  {
                setUser(res);
                storeData(res);
            });  
            navigation.navigate("StudentAuth"); 
        }
        AsyncStorage.getItem('AppleAccessToken').then(res =>
            console.log('Storage Token : ', res),
        );
        setOnLogin(false);
    }
    return (
        <TouchableOpacity onPress={onAppleButtonPress}>
            {supported && <Image source={AppleImage} />}
        </TouchableOpacity>
    );
}

