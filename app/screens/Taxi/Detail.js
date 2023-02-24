import { Linking, View, ActivityIndicator } from "react-native";
import styled from "styled-components/native";
import { MaterialIcons } from "@expo/vector-icons";
import NaverMapView, { Marker } from "react-native-nmap";
import { useEffect, useState } from "react";
import moment from "moment";
import "moment/locale/ko";

//------------------------------------- 상세 정보 --------------------------
const Container = styled.View`
  flex: 1;
  background-color: #f7f7f7;
`;

//------------------------------------ 모집 내용 ---------------------------
const ContextContainer = styled.View`
  flex: 1.2;
  background-color: white;
  border-radius: 20px;
  margin: 20px 20px;
  box-shadow: 0.05px 0.05px 1px #d9d7d7;
`;

const ContextTitle = styled.Text`
  flex: 0.3;
  margin-top: 14px;
  margin-left: 20px;
  font-size: 15px;
  font-weight: bold;
  font-family: "SpoqaHanSansNeo-Bold";
`;

const ContextInsideContainer = styled.View`
  flex: 1;
  margin-bottom: 30px;
`;

const ContextMiniContainer = styled.View`
  flex: 1;
  flex-direction: row;
`;

const ContextApiText = styled.Text`
  margin-top: 5px;
  text-align: right;
  flex: 1;
  font-size: 16px;
  margin-right: 30px;
  font-family: "SpoqaHanSansNeo-Medium";
`;

const ContextTexts = styled.Text`
  flex: 1;
  font-weight: bold;
  font-size: 16px;
  margin-left: 30px;
  margin-top: 5px;
  font-family: "SpoqaHanSansNeo-Bold";
`;

//------------------------------------ 탑승지 상세 정보 ----------------------
const StartingPointContainer = styled.View`
  flex: 2;
  border-radius: 20px;
  background-color: white;
  margin: 5px 20px 40px;
  box-shadow: 0.05px 0.05px 1px #d9d7d7;
`;

const StartingPointTitle = styled.Text`
  flex: 1;
  font-size: 15px;
  font-weight: 700;
  margin-top: 14px;
  padding-left: 20px;
  margin-bottom: -20px;
  font-family: "SpoqaHanSansNeo-Bold";
  /* font-family: SpoqaHanSansNeo-Bold; */
`;

const DateContainer = styled.View`
  flex: 1;
`;

const DateText = styled.Text`
  flex: 1;
  font-size: 18px;
  font-weight: bold;
  padding-left: 34px;
  font-family: "SpoqaHanSansNeo-Bold";
`;

const TimeText = styled.Text`
  font-size: 18px;
  padding-left: 34px;
  flex: 1;
  font-family: "SpoqaHanSansNeo-Regular";
`;

const MapContainer = styled.View`
  flex: 3;
  background-color: pink;
  margin: 10px 25px;
`;

const AddressContainer = styled.View`
  flex: 1;
`;

const AddressText = styled.Text`
  flex: 0.6;
  font-size: 16px;
  margin-left: 34px;
  font-family: "SpoqaHanSansNeo-Medium";
`;

const AddressDetailText = styled.Text`
  flex: 1;
  font-size: 16px;
  color: #959595;
  margin-top: 3px;
  text-decoration: underline #959595;
  font-family: "SpoqaHanSansNeo-Regular";
`;

const Icon = styled.View`
  flex: 0.07;
`;

const IconTextContainer = styled.View`
  flex: 1;
  flex-direction: row;
  margin-left: 27px;
`;

function Detail({ route }) {
  const item = route.params.item;
  const location = route.params.location;

  function MyMap() {
    const p0 = {
      latitude: data.meeting_latitude,
      longitude: data.meeting_longitude,
    };

    return (
      <NaverMapView
        style={{ width: "100%", height: "100%" }}
        center={{ ...p0, zoom: 16 }}
      >
        <Marker coordinate={p0} />
      </NaverMapView>
    );
  }
  function ZeroFunc(x) {
    x = x.toString();
    num = x.length;
    return num === 1 ? 0 : "";
  }

  async function gotoWebPage() {
    const isIOS = Platform.OS === "ios";
    const canOpenNaverMap = await Linking.canOpenURL("nmap://");
    const canOpenKakaoMap = await Linking.canOpenURL("kakaomap://");
    // can open naver map
    if (canOpenNaverMap) {
      console.log("NAVER MAP INSTALL");
      await Linking.openURL(
        `nmap://place?lat=${data.meeting_latitude}&lng=${data.meeting_longitude}&name=${data.memo}&appname=`,
      );
    } else if (canOpenKakaoMap) {
      // can open kakao map
      console.log("KAKAO MAP INSTALL");
      await Linking.openURL(
        `kakaomap://look?p=${data.meeting_latitude},${data.meeting_longitude}`,
      );
    } else {
      if (isIOS) {
        // IOS
        console.log("IOS");
        await Linking.openURL(
          "https://apps.apple.com/us/app/naver-map-navigation/id311867728",
        );
      } else {
        // ANDROID
        console.log("ANDROID");
        await Linking.openURL(
          "https://play.google.com/store/apps/details?id=com.nhn.android.nmap&hl=ko&gl=US",
        );
      }
    }
  }

  // api 호출
  const [data, setData] = useState(null);
  useEffect(() => {
    fetch(`http://staging-api.mju-bus.com:80/taxi/${item.id}/`)
      .then(res => res.json())
      .then(data => setData(data));
  }, []);

  // timedata 가공
  const [formattedDate, setFormattedDate] = useState(null);
  const [formattedDay, setFormattedDay] = useState(null);
  const [day, setDay] = useState(null);
  useEffect(() => {
    if (data !== null) {
      let dateStr = data.end_at;
      let date = new Date(dateStr);
      let hours = date.getHours();
      let ampm = hours >= 12 ? "오후" : "오전";
      hours %= 12;
      hours = hours ? hours : 12;

      setFormattedDate(
        `${ampm} ${ZeroFunc(hours)}${hours}:${ZeroFunc(
          date.getMinutes(),
        )}${date.getMinutes()} `,
      );
      setDay(moment(dateStr).format("dddd"));

      setFormattedDay(
        `${date.getFullYear()}년 ${ZeroFunc(date.getMonth())}${
          date.getMonth() + 1
        }월 ${ZeroFunc(date.getDate())}${date.getDate()}일 `,
      );
    }
  }, [data]);

  return (
    <>
      {data !== null && day !== null && location !== null ? (
        <Container>
          <ContextContainer>
            <ContextTitle>모집 내용</ContextTitle>
            <ContextInsideContainer>
              <ContextMiniContainer>
                <ContextTexts>탑승지</ContextTexts>
                <ContextApiText>{location}</ContextApiText>
              </ContextMiniContainer>
              <ContextMiniContainer>
                <ContextTexts>하차지</ContextTexts>
                <ContextApiText>
                  {/* 일단 도착지는 학교 고정 */}
                  {/* {TaxiDetailData.basicsData.endingPoint} */}
                  학교
                </ContextApiText>
              </ContextMiniContainer>
              <ContextMiniContainer>
                <ContextTexts>모집인원</ContextTexts>
                <ContextApiText>
                  {data.max_member}명(최소 {data.min_member}명)
                </ContextApiText>
              </ContextMiniContainer>
              <ContextMiniContainer>
                <ContextTexts>결제 방법</ContextTexts>
                <ContextApiText>만나서 N빵</ContextApiText>
              </ContextMiniContainer>
            </ContextInsideContainer>
          </ContextContainer>
          <StartingPointContainer>
            <StartingPointTitle>탑승지 상세 정보</StartingPointTitle>
            <DateContainer>
              <DateText>
                {formattedDay}
                {day}
              </DateText>
              <TimeText>{formattedDate}</TimeText>
            </DateContainer>
            <MapContainer>
              <MyMap />
            </MapContainer>
            <AddressContainer>
              <AddressText>{data.memo}</AddressText>
              <IconTextContainer>
                <Icon>
                  <MaterialIcons name="location-on" size={24} color="#959595" />
                </Icon>
                <AddressDetailText onPress={gotoWebPage}>
                  {data.meeting_place}
                </AddressDetailText>
              </IconTextContainer>
            </AddressContainer>
          </StartingPointContainer>
        </Container>
      ) : (
        <View
          style={{
            width: "100%",
            height: "100%",
            justifyContent: "center",
            alignItems: "center",
            backgroundColor: "white",
          }}
        >
          <ActivityIndicator />
        </View>
      )}
    </>
  );
}
export default Detail;
