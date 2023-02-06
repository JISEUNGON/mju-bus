import React, { useState } from "react";
import {TouchableOpacity, Image } from 'react-native';
import GoogleImage from "../../assets/image/google_login.png"
import { GoogleSignin } from '@react-native-google-signin/google-signin';

const GoogleLogin = () => {

    const [userInfo, setUserInfo] = useState(null);

    const onGoogleButtonPress = async () => { 
        signIn = async () => {
            try {
                GoogleSignin.configure({
                    offlineAccess: true, // if you want to access Google API on behalf of the user FROM YOUR SERVER
                    forceCodeForRefreshToken: true, // [Android] related to `serverAuthCode`, read the docs link below *.
                    webClientId: '62083011019-hh3mjlg5ksujp949pt71aoibri7mjhc3.apps.googleusercontent.com',
                    iosClientId: '62083011019-d2bldmt0gb4kqvb14r496ppuh3b5mme7.apps.googleusercontent.com', // [iOS] if you want to specify the client ID of type iOS (otherwise, it is taken from GoogleService-Info.plist)
                  });

                await GoogleSignin.hasPlayServices();
                const userInfo = await GoogleSignin.signIn();
                console.log(userInfo);
                setUserInfo({ userInfo });
            } catch (error) {
                if (error.code === statusCodes.SIGN_IN_CANCELLED) {
                    // user cancelled the login flow
                    console.log('user cancelled the login flow');
                } else if (error.code === statusCodes.IN_PROGRESS) {
                    // operation (e.g. sign in) is in progress already
                    console.log('operation (e.g. sign in) is in progress already');
                } else if (error.code === statusCodes.PLAY_SERVICES_NOT_AVAILABLE) {
                    // play services not available or outdated
                    console.log('play services not available or outdated');
                } else {
                    // some other error happened
                    console.log('some other error happened');
                }
            }
        };

        await signIn();
    }

    return (
        <TouchableOpacity onPress={onGoogleButtonPress}>
            <Image source={GoogleImage}/>
        </TouchableOpacity>
    )
};
export default GoogleLogin;