import React, { useEffect, useState } from "react";
import {TouchableOpacity, Image } from 'react-native';
import GoogleImage from "../../assets/image/google_login.png"
import { loginApi } from "../../api";
import { GoogleSignin } from '@react-native-google-signin/google-signin';
import { useNavigation } from '@react-navigation/native';
import AsyncStorage from "@react-native-async-storage/async-storage";

const storeData = async (value) => {
    try {
      await AsyncStorage.setItem('GoogleAccessToken', JSON.stringify(value));
      console.log('token saved successfully');
    } catch (e) {
      console.log('token saved error : Asynce Storage');
    }
};

const GoogleLogin = () => {
    const [onLogin, setOnLogin] = useState(false);
    const [userInfo, setUserInfo] = useState(null);
    const navigation = useNavigation();

    useEffect(() => {
        GoogleSignin.configure({
            offlineAccess: true, // if you want to access Google API on behalf of the user FROM YOUR SERVER
            forceCodeForRefreshToken: true, // [Android] related to `serverAuthCode`, read the docs link below *.
            webClientId: '62083011019-hh3mjlg5ksujp949pt71aoibri7mjhc3.apps.googleusercontent.com',
            iosClientId: '62083011019-d2bldmt0gb4kqvb14r496ppuh3b5mme7.apps.googleusercontent.com', // [iOS] if you want to specify the client ID of type iOS (otherwise, it is taken from GoogleService-Info.plist)
        });
    }, []);

    const onGoogleButtonPress = async () => { 
        if (onLogin) return;
        setOnLogin(true);

        signIn = async () => {
            try {
                
                await GoogleSignin.hasPlayServices();
                const userInfo = await GoogleSignin.signIn();

                const payload = {
                    "id": userInfo.user.id,
                    "serverAuthCode": userInfo.serverAuthCode,
                }
                loginApi.google_login({ queryKey: {payload} }).then(res => {
                    setUserInfo(res);
                    storeData(res);
                });
                navigation.navigate("StudentAuth");
            } catch (error) {
                console.log(error);
                // if (error.code === statusCodes.SIGN_IN_CANCELLED) {
                //     // user cancelled the login flow
                //     console.log('user cancelled the login flow');
                // } else if (error.code === statusCodes.IN_PROGRESS) {
                //     // operation (e.g. sign in) is in progress already
                //     console.log('operation (e.g. sign in) is in progress already');
                // } else if (error.code === statusCodes.PLAY_SERVICES_NOT_AVAILABLE) {
                //     // play services not available or outdated
                //     console.log('play services not available or outdated');
                // } else {
                //     // some other error happened
                //     console.log('some other error happened');
                // }
            }
        };
        AsyncStorage.getItem('GoogleAccessToken').then(res =>
            console.log('Storage Token : ', res),
        );
        await signIn();
        setOnLogin(false);
    }
    // console.log(userInfo);
    return (
        <TouchableOpacity onPress={onGoogleButtonPress}> 
           <Image source={GoogleImage}/>
        </TouchableOpacity>
    )
};
export default GoogleLogin;