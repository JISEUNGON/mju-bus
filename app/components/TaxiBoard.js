import styled from "styled-components";
import React from "react";
function TaxiBoard(){
  return(
const Content = styled.View`
  flex-direction: column;
  margin-bottom: 25px;
  margin-left: 15px;
`;

const Board = styled.View`
  width: 140px;
  height: 120px;
  background-color: ${props => props.theme.taxiPartyColor};
  padding: 20px 20px;
  border-radius: 20px;
  flex-direction: column;
  margin-bottom: 10px;
`;
const Row = styled.View`
  flex-direction: row;
  justify-content: flex-end;
  align-items: baseline;
`;

const ProfileContent = styled.View`
  top: -23px;
`;

const NumOfPerson = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 15px;
  color: gray;
  margin-left: 3px;
`;

const Column = styled.View`
  margin-top: 15px;
`;
const ContentTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 17px;
  color: #353c49;
  top: -25px;
  margin-bottom: 5px;
`;
const PartyTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 16px;
  color: ${props => props.theme.mainTextColor};
  margin-left: 5px;
`;
const RemainingTime = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 13px;
  color: #dd5257;
  margin-left: 5px;
  margin-top: 5px;
`;
  )}

export default TaxiBoard;