/* eslint-disable react/prop-types */
import React from "react";
import styled from "styled-components";
import { FlatList } from "react-native";
import Timer from "./Timer";

const ListContainer = styled.View`
  margin-left: -20px;
  margin-bottom: 20px;
`;

export const HListSeporator = styled.View`
  width: 15px;
`;

const Board = styled.View`
  height: 150px;
  width: 150px;
  background-color: white;
  border-radius: 20px;
  padding: 20px;
`;

const Station = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  color: gray;
  font-size: 15px;
`;

const BusNumber = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  margin-top: 5px;

  font-size: 16px;
`;
const RemainTime = styled.Text`
  margin-top: 30px;
  font-size: 18px;
  font-family: "SpoqaHanSansNeo-Bold";
  color: #ec6969;
`;

function RedBusList({ data }) {
  return (
    <ListContainer>
      {data.map(station => (
        <FlatList
          key={station?.data?.id}
          keyExtractor={item => item.id}
          horizontal
          showsHorizontalScrollIndicator={false}
          ItemSeparatorComponent={HListSeporator}
          data={station?.data?.busList}
          extraData={data}
          contentContainerStyle={{
            paddingHorizontal: 30,
            marginBottom: 20,
          }}
          renderItem={({ item }) => (
            <Board>
              <Station>{station?.data?.name}</Station>
              <BusNumber>{item.name}</BusNumber>
              <RemainTime>
                <Timer value={item.remains} />
              </RemainTime>
            </Board>
          )}
        />
      ))}
    </ListContainer>
  );
}

export default RedBusList;
