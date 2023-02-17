import { Image, TouchableOpacity, View,Text } from "react-native";
import KakaoLogin from "./KakaoLogin";
import AppleLogin from './AppleLogin';
import GoogleLogin from './GoogleLogin';
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import styled from "styled-components/native";
import StudentAuth from './StudentAuth';

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
const SimpleLoginTextView = styled.View`
  justify-content: center;
  align-items: center;
  flexDirection: row;
  margin-bottom: 15px;
`;
const SimpleLoginView = styled.View`
  justify-content: center;
  align-items: center;
  flexDirection: row;
  margin-bottom: 40px;
`;
const SimpleLoginText = styled.Text`
    color: #AAB2BB;
    font-size: 12px;
    font-weight: 700;
`;


function Login({navigation: { navigate }}) {
    return (
        <SafeAreaProvider>
            <SafeAreaView edges={["top"]} style={{ flex: 1 }}>
                <View style={{flex: 1, backgroundColor:"#ffffff", justifyContent: "flex-end"}}>
                    <View style={{justifyContent:"center", alignItems:"center"}}>
                        <Image
                            style={{width:"80%", resizeMode: "contain"}} 
                            source={require("../../assets/image/login_logo_new.png")}>
                        </Image>
                    </View>
                    

                    <View>     
                        <TouchableOpacity onPress={() => navigate("Splash")}>
                            <GuestStartView>
                                <GuestStartText>게스트로 시작하기</GuestStartText>
                            </GuestStartView>      
                        </TouchableOpacity>

                        <SimpleLoginTextView>
                            <SimpleLoginText>────────  간편 로그인  ────────</SimpleLoginText>
                        </SimpleLoginTextView>

                        <SimpleLoginView>
                            <GoogleLogin/>
                            <AppleLogin/>
                            <KakaoLogin/>
                            <TouchableOpacity onPress={() => navigate("StudentAuth")}><Text>테스트재학생인증</Text></TouchableOpacity>
                        </SimpleLoginView>       
                    </View>         
                </View>   
            </SafeAreaView>
        </SafeAreaProvider>
    );
}
export default Login;