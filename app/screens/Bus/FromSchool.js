import React from "react";
import { View, Text, TouchableOpacity } from "react-native";

function FromSchool({ navigation: { navigate } }) {
  return (
    <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Text>ToSchool</Text>
      <TouchableOpacity
        onPress={() => navigate("SearchStack")}
        style={{
          padding: 10,
          justifyContent: "center",
          alignContent: "center",
          marginTop: 10,

          backgroundColor: "#7974E7",
        }}
      >
        <Text
          style={{
            fontSize: 13,
            fontFamily: "SpoqaHanSansNeo-Medium",
            color: "white",
          }}
        >
          버스 검색하기
        </Text>
      </TouchableOpacity>
    </View>
  );
}

export default FromSchool;
