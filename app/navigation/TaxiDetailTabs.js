import React, { useContext, useState, useEffect } from "react";
import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";
import Chat from "../screens/Taxi/Chat";
import Detail from "../screens/Taxi/Detail";
import Member from "../screens/Taxi/Member";
import styled from "styled-components/native";
import TaxiHeader from "../screens/Taxi/TaxiHeader";
import { TaxiChatContext } from "../screens/Taxi/Taxicontext";
import Chatting from "../screens/Taxi/Chatting";

const Tab_taxi = createMaterialTopTabNavigator();

const SafeContainer = styled.SafeAreaView``;

function TaxiTabs({ route }) {
  // chat / chatting 컴포넌트의 분할 렌더링을 위해 사용
  const { join } = useContext(TaxiChatContext);
  // 택시 리스트 화면에서 item으로 객체 받아옴
  const item = route.params.item;
  const [location, setLocation] = useState(null);

  useEffect(() => {
    fetch(`http://staging-api.mju-bus.com/taxi/${item.id}/location`)
      .then(res => res.json())
      .then(data => setLocation(data.name));
  }, []);

  return (
    <>
      {location !== null ? (
        <>
          <SafeContainer
            edges={["top"]}
            style={{ flex: 0.01, backgroundColor: "white" }}
          />

          <TaxiHeader item={item} location={location} />

          <Tab_taxi.Navigator
            initialRouteName="참석 인원"
            initialLayout={{ height: 190 }}
            screenOptions={{
              tabBarLabelStyle: {
                fontSize: 15,
                fontFamily: "SpoqaHanSansNeo-Medium",
              },
            }}
          >
            <Tab_taxi.Screen
              name="상세 정보"
              component={Detail}
              initialParams={{ item: item, location: location }}
            />
            <Tab_taxi.Screen
              name="참석 인원"
              component={Member}
              initialParams={{ item: item }}
            />
            {join ? (
              <Tab_taxi.Screen
                name="채팅"
                component={Chatting}
                initialParams={{ item: item }}
              />
            ) : (
              <Tab_taxi.Screen
                name="채팅"
                component={Chat}
                initialParams={{ item: item }}
              />
            )}
          </Tab_taxi.Navigator>
        </>
      ) : (
        <></>
      )}
    </>
  );
}

export default TaxiTabs;
