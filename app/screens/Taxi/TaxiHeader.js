import React from "react";
import styled from "styled-components/native";
import { useState, useEffect, useContext } from "react";
import { Text, View, TouchableOpacity } from "react-native";
import { AntDesign } from "@expo/vector-icons";
import { useNavigation } from "@react-navigation/native";
import { TaxiChatContext } from "./Taxicontext";

const HeaderContainer = styled.View`
  flex: 0.1;
  background-color: white;
`;
const DataContainer = styled.View`
  flex: 1;
  flex-direction: row;
`;

const DataTextView = styled.View`
  flex: auto;
  justify-content: space-between;
  align-items: center;
  flex-direction: row;
`;

const DataText = styled.Text`
  padding-right: 10px;
  font-size: 19px;
  font-weight: 800;
  margin-left: -10px;
  font-family: "SpoqaHanSansNeo-Bold";
`;
const DataIconView = styled.View`
  flex: auto;

  justify-content: center;
`;
const DataDate = styled.View`
  flex: 0.7;
  justify-content: center;
  align-items: center;
`;

const IngIcon = styled.View`
  flex: 0.6;
  background-color: #e8f3e6;
  width: 60px;
  align-items: center;
  justify-content: center;
  border-radius: 20px;
`;

const EndIcon = styled.View`
  flex: 0.6;
  background-color: #f2f3f4;
  width: 70px;
  align-items: center;
  justify-content: center;
  border-radius: 20px;
`;

function TaxiHeader(props) {
  // TaxiTabs 내비게이터로 부터 item 받아옴
  const item = props.item;
  const location = props.location;

  // useContext 사용부
  const { focused, setFocused } = useContext(TaxiChatContext);

  // api 호출 부분
  const [DetailData, setDetailData] = useState(null);
  useEffect(() => {
    fetch(`http://staging-api.mju-bus.com:80/taxi/${item.id}/`)
      .then(res => res.json())
      .then(data => setDetailData(data));
  }, [item]);

  const [formattedDate, setFormattedDate] = useState(null);
  // 시간 정하는 부분
  useEffect(() => {
    if (DetailData !== null) {
      let dateStr = DetailData.end_at;
      let date = new Date(dateStr);
      let hours = date.getHours();
      let ampm = hours >= 12 ? "오후" : "오전";
      hours %= 12;
      hours = hours ? hours : 12;

      setFormattedDate(
        `${ZeroFunc(date.getMonth())}${date.getMonth() + 1}/${ZeroFunc(
          date.getDate(),
        )}${date.getDate()} ${ampm} ${ZeroFunc(hours)}${hours}:${ZeroFunc(
          date.getMinutes(),
        )}${date.getMinutes()}`,
      );
    }
  }, [DetailData]);

  const navigation = useNavigation();

  function ZeroFunc(x) {
    x = x.toString();
    num = x.length;
    return num === 1 ? 0 : "";
  }

  return (
    <>
      {location !== null ? (
        <HeaderContainer>
          <DataContainer>
            <DataTextView>
              <View style={{ marginLeft: 5 }}>
                <TouchableOpacity
                  onPress={() => {
                    if (focused === true) {
                      navigation.navigate("참석 인원");
                    } else {
                      navigation.goBack();
                    }
                  }}
                >
                  <AntDesign name="left" size={25} color="black" />
                </TouchableOpacity>
              </View>
              <DataText>{location} → 학교</DataText>
            </DataTextView>
            <DataIconView>
              {item.status === "ON_GOING" ? (
                <IngIcon>
                  <Text
                    style={{
                      color: "#4F8645",
                      fontFamily: "SpoqaHanSansNeo-Bold",
                    }}
                  >
                    모집중
                  </Text>
                </IngIcon>
              ) : (
                <EndIcon>
                  <Text
                    style={{
                      color: "#58606D",
                      fontFamily: "SpoqaHanSansNeo-Bold",
                    }}
                  >
                    모집 마감
                  </Text>
                </EndIcon>
              )}
            </DataIconView>
          </DataContainer>
          <DataDate>
            <Text
              style={{
                color: "#929292",
                fontFamily: "SpoqaHanSansNeo-Regular",
              }}
            >
              {formattedDate}
            </Text>
          </DataDate>
        </HeaderContainer>
      ) : (
        <></>
      )}
    </>
  );
}

export default TaxiHeader;
