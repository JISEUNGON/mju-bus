import React, {useState } from "react";
import {TouchableOpacity} from 'react-native';
import { loginApi } from "../../api";
import { useNavigation } from '@react-navigation/native';
import AsyncStorage from "@react-native-async-storage/async-storage";
import styled from "styled-components/native";

const GuestStartView = styled.View`
  margin-top: -50px;
  margin-bottom: 20px;
  justify-content: center;
  align-items: center;
`;
const GuestStartText = styled.Text`
  font-size: 16px;
  color: #838181;
  text-decoration: underline solid #8e8e93;
  font-weight: 600;
`;

const storeData = async (value) => {
    try {
      await AsyncStorage.setItem('GuestAccessToken', JSON.stringify(value));
      console.log('token saved successfully');
    } catch (e) {
      console.log('token saved error : Asynce Storage');
    }
};

const GuestLogin = () => {
    const [onLogin, setOnLogin] = useState(false);
    const [userInfo, setUserInfo] = useState(null);
    const navigation = useNavigation();

    const onGuestButtonPress = async () => { 
        if (onLogin) return;
        setOnLogin(true);

        signIn = async () => {
            try {
                loginApi.guest_login().then(res => {
                    setUserInfo(res);
                    storeData(res);
                });
                navigation.navigate("Splash");
            } catch (error) {
                console.log(error);
             
            }
        };
        console.log("userInfo", userInfo);
        AsyncStorage.getItem('GuestAccessToken').then(res =>
            console.log('Storage Token : ', res),
        );
        await signIn();
        setOnLogin(false);
    }
    // console.log(userInfo);
    return (
        <TouchableOpacity onPress={onGuestButtonPress}> 
            <GuestStartView>
                <GuestStartText>게스트로 시작하기</GuestStartText>
            </GuestStartView> 
        </TouchableOpacity>
    )
};
export default GuestLogin;