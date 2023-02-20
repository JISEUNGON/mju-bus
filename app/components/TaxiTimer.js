import {Text} from "react-native";
import styled from "styled-components/native";


const RemainingTime = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 13px;
  color: #dd5257;
  margin-left: 5px;
  margin-top: 5px;
`;

const TaxiTimer = (props) => {
  return (
    <RemainingTime>
      {props.end_at}
    </RemainingTime>
  )
}
export default TaxiTimer;