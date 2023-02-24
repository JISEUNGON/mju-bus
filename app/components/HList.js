import styled from "styled-components/native";
import { StyleSheet } from "react-native";

const ListContainer = styled.View`
  margin-top: -10px;
  //background-color: aqua;
  
`;

const SubListTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Medium";
  font-size: 13px;
  color: #505866;
  margin-left: 25px;
  margin-bottom: 5px;
`;

const ListTitle = styled.Text`
  font-family: "SpoqaHanSansNeo-Bold";
  font-size: 20px;
  color: ${props => props.theme.mainTextColor};
  margin-left: 25px;
  margin-bottom: 15px;
`;

const Styles = StyleSheet.create({
  background: {
    backgroundColor: "#888888",
  },
  
})
function HList({ title, subtitle , isOpen}) {
  return (
    <ListContainer style={[isOpen ? Styles.background : null]}>
      <SubListTitle>{subtitle}</SubListTitle>
      <ListTitle>{title}</ListTitle>
    </ListContainer>
  );
}
export default HList;
