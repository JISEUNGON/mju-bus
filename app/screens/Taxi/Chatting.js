import React, {
  useCallback,
  useContext,
  useEffect,
  useRef,
  useState,
} from "react";
import {
  GiftedChat,
  Send,
  Bubble,
  InputToolbar,
  isSameDay,
  isSameUser,
} from "react-native-gifted-chat";
import { Client } from "@stomp/stompjs";
import { View, Text, AppState, ActivityIndicator } from "react-native";
import styled from "styled-components/native";
import { Keyboard } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import uuid from "react-native-uuid";
import { TaxiChatContext } from "./Taxicontext";
import { useIsFocused } from "@react-navigation/native";

// 아이폰에서 채팅입력창부분 가려지는것을 방지하기 위한 View
const BottomView = styled.View`
  height: 25px;
  background-color: white;
`;
const Chatting = props => {
  const [keyboardStatus, setKeyboardStatus] = useState("");
  const { focused, setFocused, out, setOut, join, setJoin } =
    useContext(TaxiChatContext);
  setFocused(useIsFocused());

  // 키보드 렌더여부 체크
  const showSubscription = Keyboard.addListener("keyboardWillShow", () => {
    setKeyboardStatus(true);
  });
  const hideSubscription = Keyboard.addListener("keyboardWillHide", () => {
    setKeyboardStatus(false);
  });

  const [messages, setMessages] = useState([]);

  const [client, setClient] = useState(new Client());

  let textDecoder = useRef({});

  const [subscription, setSubscription] = useState(null);

  const onSend = useCallback(msg => {
    setMessages(previousMessages => GiftedChat.append(previousMessages, msg));
    console.log(messages.length, ": ", msg);

    const chattingDto = {
      roomId: "9",
      sender: msg[0].user.name,
      message: msg[0].text,
      imgUrl: msg[0].user.avatar,
      memberId: msg[0].user._id,
    };
    publish(chattingDto);
  }, []);

  // client 초기세팅
  useEffect(() => {
    textDecoder = new TextDecoder();
    client.configure({
      brokerURL: "ws://staging-api.mju-bus.com:80/chat",
      forceBinaryWSFrames: true,
      appendMissingNULLonIncoming: true,
      onConnect: str => {
        if (background === false && focused === true) {
          console.log("onConnect : ", str);
          subscribe();
          console.log("onConnect 될때 실행되는 subscribe");
        } else {
          console.log("onConnect이지만 focused가 false이므로 subscribe 안됨");
        }
      },
      heartbeatIncoming: 400,
      heartbeatOutgoing: 400,
      reconnectDelay: 1,
      debug: str => {
        console.log("debug: ", new Date(), str);
      },
      onWebSocketClose: str => {
        console.log("onWebSocketClose : ", str);
      },
    });
  }, [background, focused]);

  // 최초 접속시 client active
  useEffect(() => {
    client.activate();
  }, []);

  const subscribe = () => {
    setSubscription(
      client.subscribe(
        "/sub/9",
        message => {
          let nav = JSON.parse(textDecoder.decode(message._binaryBody));
          const chattingLog = {
            _id: uuid.v4() + nav.name,
            // text: Base64.decode(nav.message),
            text: nav.message,
            createdAt: new Date(),
            user: {
              _id: nav._id,
              avatar: nav.imgUrl,
              // avatar: message.imgUrl,
              // name: Base64.decode(nav.sender),
              name: nav.sender,
            },
          };

          if (chattingLog.user.name !== "멋쟁이라이언") {
            setMessages(previousMessages =>
              GiftedChat.append(previousMessages, chattingLog),
            );
          }
        },
        { id: "sub-" + 1 },
      ),
    );
  };

  // publish
  function publish(message) {
    client.publish({
      destination: "/pub/chatting-service",
      body: JSON.stringify(message),
    });
  }

  // 보내기 버튼 설정
  function SendButton(props) {
    return (
      <Send {...props}>
        <View
          style={{
            marginRight: 10,
            marginBottom: 10,
            marginRight: 15,
          }}
        >
          <Ionicons name="paper-plane" size={24} color="#4F8645" />
        </View>
      </Send>
    );
  }

  function renderInputToolbar(props) {
    return <InputToolbar {...props} containerStyle={{ paddingLeft: 10 }} />;
  }

  // 말풍선 설정
  function renderBubble(props) {
    if (
      isSameUser(props.currentMessage, props.previousMessage) &&
      isSameDay(props.currentMessage, props.previousMessage)
    ) {
      return (
        <Bubble
          {...props}
          textStyle={{
            right: {
              color: "black",
            },
          }}
          wrapperStyle={{
            left: {
              backgroundColor: "#E0E0E0",
            },
            right: {
              backgroundColor: "#7A9471",
            },
          }}
        />
      );
    } else
      return (
        <View style={{ marginBottom: 0 }}>
          {props.currentMessage.user.name !== "" ? (
            <Text
              style={{
                color: "grey",
                textAlign: "left",
                marginBottom: 5,
                marginLeft: 1,
              }}
            >
              {props.currentMessage.user.name === "멋쟁이라이언"
                ? ""
                : props.currentMessage.user.name}
            </Text>
          ) : (
            <></>
          )}
          <Bubble
            {...props}
            textStyle={{
              right: {
                color: "black",
              },
            }}
            wrapperStyle={{
              left: {
                backgroundColor: "#E0E0E0",
              },
              right: {
                backgroundColor: "#7A9471",
              },
            }}
          />
        </View>
      );
  }
  const [background, setBackGround] = useState(false);

  // background의 여부에 따라 subscribe & unsubscribe 결정
  useEffect(() => {
    const handleAppStateChange = nextAppState => {
      if (
        nextAppState === "background" &&
        subscription !== null &&
        client.connected === true
      ) {
        subscription.unsubscribe({ id: "9/sub-1" });
        setBackGround(true);
        console.log("백그라운드에서 실행되는 unsubscribe");
      }
      if (nextAppState === "active") {
        setBackGround(false);
      }
    };

    AppState.addEventListener("change", handleAppStateChange);

    return () => {
      AppState.removeEventListener("change", handleAppStateChange);
    };
  }, [focused, client.connected]);

  //focus 의 여부에 따라 subscribe & unsubsribe 결정
  useEffect(() => {
    if (focused === true && background === false) {
      if (client.connected === true) {
        subscribe();
        console.log("focused에서 실행되는 subscribe");
      }
    } else if (focused === false && subscription !== null) {
      subscription.unsubscribe({ id: "9/sub-1" });
      console.log("focused 에서 실행되는 unsubscribe");
    }
  }, [focused, background]);

  // 파티나가기 했을때 client deactive 설정
  useEffect(() => {
    if (out === true && client.connected === true) client.deactivate();
    console.log("파티나가기 버튼 동작으로 disconnect");
  }, [out]);

  return (
    <>
      {client.connected ? (
        <>
          <GiftedChat
            messages={messages}
            showAvatarForEveryMessage={false}
            onSend={onSend}
            wrapInSafeArea={false}
            alwaysShowSend={true}
            renderSend={SendButton}
            renderBubble={renderBubble}
            placeholder="메시지 입력"
            renderInputToolbar={renderInputToolbar}
            user={{
              _id: 1,
              avatar:
                "https://d2v80xjmx68n4w.cloudfront.net/gigs/JaqkS1637331647.jpg",
              name: "멋쟁이라이언",
            }}
          />
          {keyboardStatus ? <></> : <BottomView></BottomView>}
        </>
      ) : (
        <View
          style={{ justifyContent: "center", alignItems: "center", flex: 1 }}
        >
          <ActivityIndicator style={{ marginBottom: 10 }} />
          <Text
            style={{ fontFamily: "SpoqaHanSansNeo-Regular", color: "#959595" }}
          >
            잠시만 기다려주세요
          </Text>
        </View>
      )}
    </>
  );
};
export default Chatting;
