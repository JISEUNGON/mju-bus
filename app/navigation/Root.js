import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import HomeBottomTabs from "./HomeBottomTabs";
import Splash from "../screens/Splash";
import SearchStack from "./SearchStack";
import NoticeStack from "./NoticeStack";
import Login from "../components/Login/Login";
import Home from "../screens/Home/Home";
import StudentAuth from "../components/Login/StudentAuth";
import AddPartyStack from "./AddPartyStack";
import TaxiStack from "./TaxiStack";

console.disableYellowBox = true;

const Nav = createNativeStackNavigator();

export const MBAContext = React.createContext();
function Root() {
  // 시내버스, 시외버스
  // [{
  //   "charge": 0,
  //   "id": 20,
  //   "name": "시내",
  // }, ... ]
  const [sineBusList, setSineBusList] = React.useState([]);
  const [siweBusList, setSiweBusList] = React.useState([]); 

  // 학사일정
  // {
  //  "id": 2,
  //  "name": "여름방학",
  //  "time": "2023-02-19T01:55:26.583954",
  // }
  const [mjuCalendar, setMjuCalendar] = React.useState({});

  // 버스 정류장
  // [
  //   Object {
  //     "id": 20,
  //     "name": "시내",
  //     "stations": Array [
  //       Object {
  //         "id": 1,
  //         "latitude": 37.2241606,
  //         "longitude": 127.1875812,
  //         "name": "채플관 앞",
  //         "route_order": 1,
  //       } ...] 
  //   } ...]
  const [stationList, setStationList] = React.useState([]);

  // 버스 시간표
  // [
  //   Object {
  //     "id": 20,
  //     "name": "시내",
  //     "timeList": Array [
  //       "name": "진입로"
  //       Object {
  //         "depart_at": "08:20:00",
  //         "arrive_at": "08:45:00"
  //       } ...]
  //   } ...]
  //
  const [busTimeTable, setBusTimeTable] = React.useState([]);
  return (
    <MBAContext.Provider
      value={{
        sineBusList,
        siweBusList,
        mjuCalendar,
        stationList,
        busTimeTable,
        setSineBusList,
        setSiweBusList,
        setMjuCalendar,
        setStationList,
        setBusTimeTable,
      }}
    >
      <Nav.Navigator
        initialRouteName="Login"
        screenOptions={{
          headerShown: false,
        }}
      >
        <Nav.Screen name="Login" component={Login} />
        <Nav.Screen name="Splash" component={Splash} />
        <Nav.Screen name="StudentAuth" component={StudentAuth} />
        <Nav.Screen
          name="HomeBottomTabs"
          component={HomeBottomTabs}
          options={{ gestureEnabled: false }}
        />
        <Nav.Screen
          name="SearchStack"
          component={SearchStack}
          options={{ headerShown: false }}
        />

        <Nav.Screen name="NoticeStack" component={NoticeStack} />
        <Nav.Screen name="AddPartyStack" component={AddPartyStack} />
        <Nav.Screen name="TaxiStack" component={TaxiStack}/>
      </Nav.Navigator>
    </MBAContext.Provider>
  );
}

export default Root;
