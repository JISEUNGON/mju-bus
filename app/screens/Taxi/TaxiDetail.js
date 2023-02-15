import React, { useState, useCallback } from "react";
import {
  Text,
  View,
  TouchableOpacity,
  StyleSheet,
  TextInput,
} from "react-native";
import { AntDesign } from "@expo/vector-icons";
import styled from "styled-components/native";
import { Dimensions } from "react-native";
import Modal from "react-native-modal";
import MultiSlider from "@ptomasroos/react-native-multi-slider";
import DatePicker from "react-native-date-picker";

const SubContainer = styled.View`
  background-color: white;
  flex-direction: column;
  padding-top: 10px;
  padding-bottom: 10px;
`;

const Title = styled.Text`
  font-size: 20px;
  font-family: "SpoqaHanSansNeo-Bold";
  color: #353c49;
`;
const SubTitle = styled.Text`
  font-size: 15px;
  font-family: "SpoqaHanSansNeo-Bold";
  color: #353c49;
  margin-bottom: 5px;
`;
const DetailTitle = styled.Text`
  font-size: 9.5px;
  font-family: "SpoqaHanSansNeo-Bold";
  color: #aab2bb;
  margin-bottom: 5px;
`;
const InputText = styled.Text`
  font-size: 13px;
  font-family: "SpoqaHanSansNeo-Bold";
  color: black;
  width: auto;
`;

// 모집 마감 관련
const TimeBt = styled.TouchableOpacity`
  border-radius: 5px;
  border-width: 2px;
  border-color: #aab2bb;
  width: 70%;
  height: 30px;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding-left: 10px;
  padding-right: 10px;
`;
// 탑승 상세 위치
const InputPosition = styled.TextInput`
  border-radius: 5px;
  border-width: 2px;
  border-color: #aab2bb;
  width: 70%;
  height: 50px;
  margin-bottom: 5px;
`;

// 파티 생성 버튼
const CreatePartyBt = styled.TouchableOpacity`
  border-radius: 15px;
  border-width: 2px;
  width: 85%;
  height: 50px;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  padding-left: 10px;
  padding-right: 10px;
  background-color: #e8f3e6;
  border-color: #e8f3e6;
`;
const BtText = styled.Text`
  font-size: 23px;
  font-family: "SpoqaHanSansNeo-Bold";
  color: #4f8645;
`;

const LabelContainer = styled.View`
  height: 50px;
  width: 30px;
  border-radius: 20px;
  justify-items: center;
  align-items: center;

  background-color: #e8f3e6;
`;

// Modal 부분
const ModalConatiner = styled.View`
  height: 350px;
  width: 100%;

  flex-direction: column;
  align-items: flex-start;
  border-radius: 20px;
  background-color: white;

  padding-left: 20px;
  padding-right: 20px;

  padding-top: 30px;
`;

const SubModalContainer = styled.View`
  height: 50px;
  width: 100%;

  flex-direction: row;
  align-items: flex-start;

  background-color: white;

  padding-left: 20px;
  padding-right: 20px;
`;

const SubModalTitleContainer = styled.View`
  height: 100%;
  width: 20%;

  align-items: flex-start;
  justify-content: center;
  background-color: white;
`;

const SubModalDetailContainer = styled.View`
  height: 100%;
  width: 80%;

  align-items: flex-end;
  justify-content: center;
  background-color: white;
`;

const ModalTitleText = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 13px;
  color: black;
`;
const ModalDetialText = styled.Text`
  font-family: "SpoqaHanSansNeo";
  font-size: 13px;
  color: black;

  text-align: right;
`;

const ModalSelectBt = styled.TouchableOpacity`
  height: 50px;
  width: 90%;
  background-color: #e8f3e6;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
`;

const ModalCancelBt = styled.TouchableOpacity`
  height: 50px;
  width: 90%;
  background-color: #f2f3f4;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
`;

const ModalSelectBtText = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 17px;
  color: #4f8645;
`;
const ModalCancelBtText = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 17px;
  color: #58606d;
`;
function LeftLabelBase(props) {
  const { position, value } = props;

  return (
    <LabelContainer
      style={[
        styles.sliderLabel, // this one is position absolute
        {
          left: position - width / 2,
        },
      ]}
    >
      <Text style={styles.sliderLabelText2}>최소 </Text>
      <Text style={styles.sliderLabelText}>{value}</Text>
    </LabelContainer>
  );
}

function RightLabelBase(props) {
  const { position, value } = props;

  return (
    <LabelContainer
      style={[
        styles.sliderLabel, // this one is position absolute
        {
          left: position - width / 2,
        },
      ]}
    >
      <Text style={styles.sliderLabelText2}>최대 </Text>
      <Text style={styles.sliderLabelText}>{value}</Text>
    </LabelContainer>
  );
}

const width = 50;
function SliderCustomLabel(textTransformer: (value: number) => string) {
  return function (props) {
    // these props are coming from the package
    const {
      oneMarkerValue,
      twoMarkerValue,
      oneMarkerLeftPosition,
      twoMarkerLeftPosition,
    } = props;

    return (
      <View>
        <LeftLabelBase
          position={oneMarkerLeftPosition}
          value={textTransformer(oneMarkerValue)}
        />
        {twoMarkerValue ? (
          <RightLabelBase
            position={twoMarkerLeftPosition}
            value={textTransformer(twoMarkerValue)}
          />
        ) : null}
      </View>
    );
  };
}

function initTime(date) {
  const temp = `${date.getFullYear()}년 ${date.getMonth()}월 ${date.getDay()}일 ${date.getHours()}:${date.getMinutes()}`;

  return temp;
}

function TaxiDetail({ navigation, route }) {
  const [isModalVisible, setModalVisible] = useState(false);

  const toggleModal = () => {
    setModalVisible(!isModalVisible);
  };

  const isCreate = () => {
    if (position == "") {
    } else {
      //console.log("test");
      {
        setModalVisible(true);
      }
    }
  };

  const [date, setDate] = useState(new Date());
  const [selectTime, setSelcteTime] = useState(initTime(date));
  const [dateopen, setDateopen] = useState(false);
  const [position, setPosition] = useState("");

  const People = { min: 2, max: 4 };
  const SliderPad = 10;
  const { min, max } = People;
  const [selected, setSelected] = useState(null);
  const [minPeople, setMinPeople] = useState(min);
  const [maxPeople, setMaxPeople] = useState(max);

  const WIDTH = Dimensions.get("window").width * 0.6;

  if (!selected) {
    setSelected([min, max]);
  }
  const onValuesChangeFinish = values => {
    setSelected(values);

    setMinPeople(values[0]);
    setMaxPeople(values[1]);
  };
  const textTransformerPeople = value => (value = value + "명");
  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={styles.back}
        onPress={() => {
          navigation.goBack();
        }}
      >
        <AntDesign name="left" size={25} color="black" />
      </TouchableOpacity>
      <Title>택시 파티 모집 등록</Title>
      <SubContainer>
        <SubTitle>모집 마감 일시</SubTitle>
        <DetailTitle>해당 일 시까지 파티 모집이 진행 돼요!</DetailTitle>
        <TimeBt onPress={() => setDateopen(true)}>
          <InputText>{selectTime}</InputText>
          <AntDesign name="down" size={13} color="black" />
        </TimeBt>
        <DatePicker
          mode="time"
          modal
          open={dateopen}
          date={date}
          onConfirm={date => {
            setDateopen(false);
            setDate(date);
            const temp = `${date.getFullYear()}년 ${date.getMonth()}월 ${date.getDay()}일 ${date.getHours()}:${date.getMinutes()}`;
            setSelcteTime(temp);
          }}
          onCancel={() => {
            setDateopen(false);
          }}
        />
      </SubContainer>
      <SubContainer>
        <SubTitle>최소/최대 인원 설정</SubTitle>
        <DetailTitle>
          파티 모집 종료 시까지 최소 인원이 모이면 파티가 성사 돼요!
        </DetailTitle>
        <View style={{ marginBottom: 10, marginLeft: 20 }}>
          <MultiSlider
            min={min}
            max={max}
            allowOverlap
            values={selected}
            sliderLength={WIDTH}
            enableLabel
            onValuesChangeFinish={onValuesChangeFinish}
            trackStyle={{
              height: 8,
              borderRadius: 8,
            }}
            markerOffsetY={5}
            selectedStyle={{
              backgroundColor: "#86A780",
            }}
            unselectedStyle={{
              backgroundColor: "#D9D9D9",
            }}
            markerStyle={{
              backgroundColor: "#4F8645",
            }}
            customLabel={SliderCustomLabel(textTransformerPeople)}
          />
        </View>
      </SubContainer>
      <SubContainer>
        <SubTitle>탑승 상세 위치</SubTitle>
        <DetailTitle>
          파티원이 참고하도록 탑승지에 간단한 위치 설명을 적어주세요!
        </DetailTitle>
        <InputPosition
          autoCorrect={false}
          onChangeText={value => setPosition(value)}
        />
        <DetailTitle>ex) 기흥역 3번 출구 앞 </DetailTitle>
      </SubContainer>
      <View style={{ justifyContent: "center", alignItems: "center" }}>
        <CreatePartyBt onPress={isCreate}>
          <BtText>파티 생성하기</BtText>
        </CreatePartyBt>
      </View>
      <View>
        <Modal isVisible={isModalVisible}>
          <ModalConatiner>
            <SubModalContainer>
              <SubModalTitleContainer>
                <ModalTitleText>파티장</ModalTitleText>
              </SubModalTitleContainer>
              <SubModalDetailContainer>
                <ModalDetialText>미정</ModalDetialText>
              </SubModalDetailContainer>
            </SubModalContainer>

            <SubModalContainer>
              <SubModalTitleContainer>
                <ModalTitleText>탑승지</ModalTitleText>
              </SubModalTitleContainer>
              <SubModalDetailContainer>
                <ModalDetialText>
                  {route.params.start}
                  {"\n"}({position})
                </ModalDetialText>
              </SubModalDetailContainer>
            </SubModalContainer>

            <SubModalContainer>
              <SubModalTitleContainer>
                <ModalTitleText>하차지</ModalTitleText>
              </SubModalTitleContainer>
              <SubModalDetailContainer>
                <ModalDetialText>{route.params.destination}</ModalDetialText>
              </SubModalDetailContainer>
            </SubModalContainer>

            <SubModalContainer>
              <SubModalTitleContainer>
                <ModalTitleText>모집인원</ModalTitleText>
              </SubModalTitleContainer>
              <SubModalDetailContainer>
                <ModalDetialText>
                  {maxPeople}명 (최소 {minPeople}명)
                </ModalDetialText>
              </SubModalDetailContainer>
            </SubModalContainer>

            <SubModalContainer>
              <SubModalTitleContainer>
                <ModalTitleText>모집 마감</ModalTitleText>
              </SubModalTitleContainer>
              <SubModalDetailContainer>
                <ModalDetialText>{selectTime}</ModalDetialText>
              </SubModalDetailContainer>
            </SubModalContainer>

            <SubModalContainer>
              <View style={{ flex: 1, alignItems: "flex-start" }}>
                <ModalSelectBt
                  onPress={() =>
                    navigation.navigate("TaxiPartyCreate", {
                      start: route.params.start,
                      startDetail: position,
                      destination: route.params.destination,
                      time: selectTime,
                      minPeople: minPeople,
                      maxPeople: maxPeople,
                    })
                  }
                >
                  <ModalSelectBtText>확인</ModalSelectBtText>
                </ModalSelectBt>
              </View>
              <View style={{ flex: 1, alignItems: "flex-end" }}>
                <ModalCancelBt onPress={toggleModal}>
                  <ModalCancelBtText>취소</ModalCancelBtText>
                </ModalCancelBt>
              </View>
            </SubModalContainer>
          </ModalConatiner>
        </Modal>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: "white",
    flex: 1,
    paddingHorizontal: 20,
  },
  back: {
    marginBottom: 10,
    marginTop: 30,
  },
  sliderLabel: {
    position: "absolute",
    justifyContent: "center",
    top: 30,
    width: 80,
    height: 30,
    marginTop: 10,
    alignItems: "center",
    flexDirection: "row",
  },
  sliderLabelText: {
    justifyContent: "center",
    alignItems: "center",
    color: "#4F8645",
    fontFamily: "SpoqaHanSansNeo-Bold",
    fontSize: 15,
  },
  sliderLabelText2: {
    justifyContent: "center",
    alignItems: "center",
    color: "#4F8645",
    fontFamily: "SpoqaHanSansNeo-Bold",
    fontSize: 10,
  },
});

export default TaxiDetail;
