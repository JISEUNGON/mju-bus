import React from 'react';
import { StyleSheet, Text, View, TextInput, TouchableOpacity, ScrollView } from 'react-native';
import { AntDesign } from '@expo/vector-icons';

function Taxi_destination({navigation}) {
  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.back} onPress={() => {navigation.goBack()}}>
        <AntDesign name="left" size={25} color="black" />
      </TouchableOpacity>
      <Text style={styles.title}>
        택시 도착지를 설정하세요
      </Text>
      <TextInput 
          // onChangeText={}
          // value={}
          placeholder="교내 도착지 검색"
          placeholderTextColor= "#AAB2BB"
          style={styles.searchBox}
      />

      <ScrollView>
        <View style={styles.listView}>
          <View><Text style={styles.campusAddress}>정문 앞</Text></View>
          <TouchableOpacity style={styles.destBtn}>
            <Text style={styles.destText}>도착</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.line}/>

        <View style={styles.listView}>
          <Text style={styles.campusAddress}>채플관</Text>
          <TouchableOpacity style={styles.destBtn}>
            <Text style={styles.destText}>도착</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.line}/>

        <View style={styles.listView}>
          <Text style={styles.campusAddress}>명진당</Text>
          <TouchableOpacity style={styles.destBtn}>
            <Text style={styles.destText}>도착</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.line}/>

        <View style={styles.listView}>
          <Text style={styles.campusAddress}>함박관</Text>
          <TouchableOpacity style={styles.destBtn}>
            <Text style={styles.destText}>도착</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.line}/>

        <View style={styles.listView}>
          <Text style={styles.campusAddress}>제 1공학관</Text>
          <TouchableOpacity style={styles.destBtn}>
            <Text style={styles.destText}>도착</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.line}/>

        <View style={styles.listView}>
          <Text style={styles.campusAddress}>제 2공학관</Text>
          <TouchableOpacity style={styles.destBtn}>
            <Text style={styles.destText}>도착</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.line}/>

        <View style={styles.listView}>
          <Text style={styles.campusAddress}>제 3공학관</Text>
          <TouchableOpacity style={styles.destBtn}>
            <Text style={styles.destText}>도착</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.line}/>

        <View style={styles.listView}>
          <Text style={styles.campusAddress}>제 5공학관</Text>
          <TouchableOpacity style={styles.destBtn}>
            <Text style={styles.destText}>도착</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.line}/>

        <View style={styles.listView}>
          <Text style={styles.campusAddress}>차세대 과학관</Text>
          <TouchableOpacity style={styles.destBtn}>
            <Text style={styles.destText}>도착</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.line}/>
      </ScrollView>
        
    </View>
  );
}

export default Taxi_destination;

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