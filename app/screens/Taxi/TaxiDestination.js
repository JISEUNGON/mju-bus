import React from 'react';
import { StyleSheet, Text, View, TextInput, TouchableOpacity, FlatList } from 'react-native';
import { AntDesign } from '@expo/vector-icons';

function TaxiDestination({navigation}) {
  const campusAddress = ["정문 앞", "채플관", "명진당", "함박관", "제 1공학관", "제 2공학관", "제 3공학관", "제 5공학관", "차세대 과학관"];

  const renderResult = ({item}) => 
  (<View style={styles.listView}>
    <View>    
      <Text style={styles.campusAddress}>{item}</Text>
    </View>
    <TouchableOpacity style={styles.destBtn}>
      <Text style={styles.destText}>도착</Text>
    </TouchableOpacity>
  </View>);

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.back} onPress={() => {navigation.goBack()}}>
        <AntDesign name="left" size={25} color="black" />
      </TouchableOpacity>
      <Text style={styles.title}>
        택시 도착지를 설정하세요
      </Text>
      {/* <TextInput 
          // onChangeText={}
          // value={}
          placeholder="교내 도착지 검색"
          placeholderTextColor= "#AAB2BB"
          style={styles.searchBox}
      /> */}
      <FlatList
            showsVerticalScrollIndicator={false}
            data={campusAddress}
            renderItem={renderResult}/>     
    </View>
  );
}

export default TaxiDestination;

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
    marginBottom: 30,
  },
  searchBox: {
    backgroundColor: "#F5F6F8",
    marginTop: 16,
    borderRadius: 10,
    fontSize: 15,
    padding: 13,
    marginBottom: 30,
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
  campusAddress: {
    fontSize: 17,
    fontWeight: "500",
    marginVertical: 5,
  },
  destBtn: {
    backgroundColor: "#F2F3F4", 
    paddingHorizontal: 25,
    justifyContent: "center",
  },
  destText: {
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