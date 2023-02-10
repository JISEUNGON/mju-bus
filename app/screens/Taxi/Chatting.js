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
import { Base64 } from "js-base64";
import { View, Text } from "react-native";
import styled from "styled-components/native";
import { Keyboard } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import uuid from "react-native-uuid";

const BottomView = styled.View`
  height: 25px;
  background-color: white;
`;
const Chatting = () => {
  const [keyboardStatus, setKeyboardStatus] = useState("");

  const showSubscription = Keyboard.addListener("keyboardWillShow", () => {
    setKeyboardStatus(true);
  });
  const hideSubscription = Keyboard.addListener("keyboardWillHide", () => {
    setKeyboardStatus(false);
  });

  const [messages, setMessages] = useState([]);
  let client = useRef({});

  let textDecoder = useRef({});

  const subscribe = () => {
    client.subscribe("/sub/9", message => {
      let nav = JSON.parse(textDecoder.decode(message._binaryBody));
      const chattingLog = {
        _id: uuid.v4(),
        text: Base64.decode(nav.message),
        createdAt: new Date(),
        user: {
          _id: nav.memberId,
          avatar: nav.imgUrl,
          name: Base64.decode(nav.sender),
        },
      };

      if (chattingLog.user.name !== "멋쟁이라이언") {
        setMessages(previousMessages =>
          GiftedChat.append(previousMessages, chattingLog),
        );
      }
    });
  };
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
  useEffect(() => {
    textDecoder = new TextDecoder();
    client = new Client();
    client.configure({
      brokerURL: "ws://staging-api.mju-bus.com:80/chat",
      forceBinaryWSFrames: true,
      appendMissingNULLonIncoming: true,
      onConnect: str => {
        console.log("onConnect : ", str);
        subscribe();
      },
      debug: str => {
        console.log("debug: ", new Date(), str);
      },
      onWebSocketClose: str => {
        console.log("onWebSocketClose : ", str);
      },
    });
    client.activate();
  }, []);
  //건들지말고 그대로 써라.
  const publish = message => {
    client.publish({
      destination: "/pub/chatting-service",
      body: JSON.stringify(message),
    });
  };

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

  // function renderComposer(props) {
  //   return <Composer {...props} placeholder="메시지 입력" />;
  // }
  function renderInputToolbar(props) {
    return <InputToolbar {...props} containerStyle={{ paddingLeft: 10 }} />;
  }
  // function renderUsername(){
  //   return (

  //   )
  // }
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
  return (
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
  );
};
export default Chatting;
