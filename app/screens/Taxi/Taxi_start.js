import React, { useState } from "react";
import {
  StyleSheet,
  Text,
  View,
  TextInput,
  TouchableOpacity,
  FlatList,
} from "react-native";
import { AntDesign } from "@expo/vector-icons";
import { useQuery } from "@tanstack/react-query";

const searchingAddress = ({ queryKey }) => {
  const [_, query] = queryKey;
  return fetch(
    `http://dapi.kakao.com/v2/local/search/keyword.json?query=${query}&x=127.18727613711644&y=37.222093463550834&radius=10000`,
    {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: "KakaoAK 1f4722e96b4151a0b6e69efa388112d8",
      },
    },
  ).then(res => res.json());
};

function Taxi_start({ navigation }) {
  const [query, setQuery] = useState("");
  const onChangeText = text => setQuery(text);
  const { isLoading, data, refetch } = useQuery(
    ["searchingAddress", query],
    searchingAddress,
    { enabled: false },
  );
  const onSubmit = () => {
    if (query === "") {
      return;
    }
    refetch();
  };

  const renderResult = ({ item }) => (
    <View style={styles.listView}>
      <View style={{ flexDirection: "column" }}>
        <Text style={styles.inputAddress}>{item.place_name}</Text>
        <Text style={styles.addressDetail}>{item.road_address_name}</Text>
      </View>

      <TouchableOpacity
        style={styles.startBtn}
        onPress={() => navigation.navigate("Taxi_destination")}
      >
        <Text style={styles.startText}>출발</Text>
      </TouchableOpacity>
    </View>
  );

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
      <Text style={styles.title}>택시 출발지를 설정하세요</Text>
      <TextInput
        onChangeText={onChangeText}
        placeholder="출발지 검색"
        placeholderTextColor="#AAB2BB"
        style={styles.searchBox}
        returnKeyType="search"
        onSubmitEditing={onSubmit}
      />
      <Text style={styles.searchResult}>검색 결과</Text>
      {isLoading ? null : (
        <FlatList
          showsVerticalScrollIndicator={false}
          data={data?.documents}
          renderItem={renderResult}
        />
      )}
    </View>
  );
}

export default Taxi_start;

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
  searchResult: {
    fontSize: 15,
    fontWeight: "500",
    color: "#AAB2BB",
    marginTop: 26,
    marginBottom: 15,
  },
  listView: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginBottom: 10,
  },
  inputAddress: {
    fontSize: 15,
    fontWeight: "500",
    marginBottom: 3,
  },
  addressDetail: {
    fontSize: 13,
    fontWeight: "500",
    color: "#AAB2BB",
  },
  startBtn: {
    backgroundColor: "#F2F3F4",
    paddingHorizontal: 25,
    justifyContent: "center",
  },
  startText: {
    fontSize: 12,
    fontWeight: "700",
    color: "#58606D",
  },
  line: {
    borderBottomColor: "#AAB2BB",
    borderBottomWidth: 0.5,
    marginVertical: 10,
  },
});
