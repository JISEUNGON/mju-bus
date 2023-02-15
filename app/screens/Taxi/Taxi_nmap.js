import React, { useState } from "react";
import {
  StyleSheet,
  Text,
  View,
  TextInput,
  TouchableOpacity,
} from "react-native";
import { AntDesign } from "@expo/vector-icons";
import NaverMapView, { Marker } from "react-native-nmap";

function Taxi_nmap({ navigation }) {
  const [currentLocation, setCurrentLocation] = useState({
    latitude: 0,
    longitude: 0,
  });
  const locationHandler = e => setCurrentLocation(e);
  const [startName, setStartName] = useState("");
  const onChangeStartName = startName => setStartName(startName);
  const mju = { latitude: 37.224704, longitude: 127.1878498 };

  const reverseGc = (longitude, latitude) => {
    fetch(
      `https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?coords=${longitude},${latitude}&sourcecrs=epsg:4326&output=json&orders=roadaddr`,
      {
        method: "GET",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          "X-NCP-APIGW-API-KEY-ID": "hih6rx7sco",
          "X-NCP-APIGW-API-KEY": "wq1YYF89GAXM52vvuwNxCXpsv9og94MWscBvqRYr",
        },
      },
    )
      .then(response => response.json())
      .then(result => {
        setStartName(
          result.results[0].region.area1.name +
            " " +
            result.results[0].region.area2.name +
            " " +
            result.results[0].land.name +
            " " +
            result.results[0].land.number1 +
            " " +
            result.results[0].land.number2,
        );
        console.log(
          result.results[0].region.area1.name,
          result.results[0].region.area2.name,
          result.results[0].land.name,
          result.results[0].land.number1,
          result.results[0].land.number2,
        );
      })
      .catch(error => console.log(error));
  };

  return (
    <View style={styles.container}>
      <View style={{ paddingHorizontal: 20 }}>
        <TouchableOpacity
          style={styles.back}
          onPress={() => {
            navigation.goBack();
          }}
        >
          <AntDesign name="left" size={25} color="black" />
        </TouchableOpacity>
        <Text style={styles.title}>택시 출발지를 설정하세요</Text>
        <TouchableOpacity
          onPress={() => {
            navigation.navigate("Taxi_start");
          }}
        >
          <TextInput
            onChangeText={onChangeStartName}
            value={startName}
            placeholder="출발지 검색"
            placeholderTextColor="#AAB2BB"
            style={styles.searchBox}
            editable={false}
            selectTextOnFocus={false}
          />
        </TouchableOpacity>
      </View>

      <NaverMapView
        style={{ width: "100%", height: "100%", marginVertical: 20 }}
        showsMyLocationButton={true}
        center={{ ...mju, zoom: 16 }}
        onMapClick={e => {
          locationHandler(e);
          reverseGc(e.longitude, e.latitude);
        }}
        useTextureView={true}
      >
        <Marker coordinate={currentLocation} />
      </NaverMapView>
      {currentLocation.longitude >= 1 && currentLocation.latitude >= 1 && (
        <TouchableOpacity
          style={styles.nextBtn}
          onPress={() => navigation.navigate("Taxi_destination")}
        >
          <Text style={styles.nextText}>다음</Text>
        </TouchableOpacity>
      )}
    </View>
  );
}

export default Taxi_nmap;

const styles = StyleSheet.create({
  container: {
    backgroundColor: "white",
    flex: 1,
    justifyContent: "space-between",
  },
  back: {
    marginBottom: 10,
    marginTop: 30,
  },
  title: {
    fontSize: 20,
    fontWeight: "700",
  },
  searchBox: {
    backgroundColor: "#F5F6F8",
    marginTop: 16,
    borderRadius: 10,
    fontSize: 15,
    padding: 13,
  },
  nextBtn: {
    backgroundColor: "#7974E7",
    justifyContent: "center",
    alignItems: "center",
    position: "absolute",
    bottom: 0,
    left: 0,
    right: 0,
  },
  nextText: {
    color: "white",
    fontSize: 16,
    fontWeight: "700",
    marginVertical: 20,
  },
});
