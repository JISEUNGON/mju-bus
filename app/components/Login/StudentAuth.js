import { Entypo } from "@expo/vector-icons";
import Checkbox from "expo-checkbox";
import { useState } from "react";
import { Linking, TouchableOpacity, View } from "react-native";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import styled from "styled-components/native";

const Title = styled.Text`
  margin-left: 40px;
  margin-top: 40px;
  font-size: 24px;
  font-weight: 700;
`;

const InputText = styled.Text`
  margin-left: 40px;
  margin-top: 20px;
  margin-bottom: 10px;
  color: #AAB2BB;
  font-size: 15px;
  font-weight: 500;
`;

const InputBox = styled.TextInput`
  margin-left: 40px;
  margin-right: 40px;
  background-color: #F5F6F8;
  font-size: 15px;
  font-weight: 500;
  padding-vertical: 15px;
  padding-horizontal: 18px;
  border-radius: 10px;
`;

const CheckText = styled.Text`
  margin-left: 40px;
  margin-top: 5px;
  color: red;
  font-weight: 500;
  font-size: 12px;
`;

const AgreeView = styled.View`
  margin-left: 40px;
  margin-right: 70px;
  margin-top: 30px;
  flex-direction: row;
`;

const AgreeText = styled.Text`
  margin-left: 5px;
  color: #58606D;
  font-weight: 500;
  font-size: 15px;
`;

const LinkText = styled.Text`
  color: blue;
  font-weight: 500;
  font-size: 15px;
`;

const NextBtn = styled.View`
  margin-left: 40px;
  margin-right: 40px;
  margin-top: 100px;
  border-radius: 10px;
  justify-content: center;
  align-items: center;
  padding-vertical: 15px;
`;

const NextText = styled.Text`
  color: #FBFBFB;
  font-weight: 700;
  font-size: 16px;
`;

function StudentAuth({navigation}) {
    const [name, setName] = useState("");
    const onChangeName = (name) => {
      if(name === "") {
        setCheckName(false);
      }else {
        setCheckName(true);
      }
      setName(name);
    };
    const [checkBirthday, setCheckBirthday] = useState(false);
    const [birthday, setBirthday] = useState("");
    

    const birthdayCheck = (birthday) => {
      const month = Number(birthday.substring(2,4));
      const day = Number(birthday.substring(4,6));

      if(birthday.length === 6) {
          if (month < 1 || month > 12) {
            return false;
          } else if (day < 1 || day > 31) {
            return false;
          } else if ((month==4 || month==6 || month==9 || month==11) && day==31) {
            return false;   
          } else {
            return true;
          }
      } else {
          return false;
        }
    };


    const onChangeBirthday = (birthday) => {
      if(!birthdayCheck(birthday)) {
        setCheckBirthday(false);
      }else {
        setCheckBirthday(true);
      }
      setBirthday(birthday);
    };

    const [agree, setAgree] = useState(false);
    const onChangeAgree = () => setAgree(!agree)
    const [checkName, setCheckName] = useState(false);
    return (
        <SafeAreaProvider>
            <SafeAreaView edges={["top"]} style={{ flex: 1 }}>
                <View style={{flex: 1, backgroundColor:"#ffffff"}}>
                    <TouchableOpacity onPress={() => navigation.goBack()}>
                        <Entypo name="chevron-left" size={30} color="gray" style={{marginTop:20, marginLeft:10}}/>
                    </TouchableOpacity>
                    <Title>재학생 인증</Title>
                    <InputText>이름</InputText>
                    <InputBox placeholder="이름을 입력하세요" placeholderTextColor={'#AAB2BB'} onChangeText={onChangeName} value={name}/>
                    <InputText>생년월일</InputText>
                    <InputBox placeholder="생년월일을 입력하세요 ex)980901" placeholderTextColor={'#AAB2BB'} onChangeText={onChangeBirthday} value={birthday}/>
                    {(checkBirthday)? null: birthday.length === 0 ? null : <CheckText>형식에 맞게 입력하세요</CheckText>}
                    <AgreeView>
                        <Checkbox value={agree} onValueChange={onChangeAgree} color={agree ? '#4630EB' : undefined}/>
                        <View>
                          <AgreeText>명지 메이트의 서비스를 이용하는데 필요한 
                            <TouchableOpacity onPress={() => Linking.openURL('https://www.notion.so/mju-bus/608f331746544e49ae5f352a856ac3c2')}>
                              <LinkText>
                                개인정보처리방침
                              </LinkText>
                            </TouchableOpacity>
                            에 동의합니다.
                          </AgreeText>            
                        </View>         
                    </AgreeView>
                    <TouchableOpacity disabled={!checkBirthday || !agree || !checkName} onPress={() => navigation.navigate("Splash")}>
                        <NextBtn style={{backgroundColor: checkBirthday && agree && checkName ? "#7974E7": "grey"}}>
                            <NextText>다음</NextText>
                        </NextBtn>
                    </TouchableOpacity>
                </View>   
            </SafeAreaView>
        </SafeAreaProvider>
    );
}
export default StudentAuth;