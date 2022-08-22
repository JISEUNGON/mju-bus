package com.mjubus.busserver.scheduler;

import lombok.Getter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

@Getter
public class RData {

  /**
   * 모든 데이터를 담은 클래스
   * */
  private static MyData myData;

  public void setStationId(String next) {
    myData.setStationId(next);
  }

  /**
   * consturctor
   */
  public RData() {
    myData = new MyData();
  }

  public void makeUrl() throws UnsupportedEncodingException, MalformedURLException {
    StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalList"); /*URL*/

    urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + myData.getServiceKey()); /*Service Key*/

    urlBuilder.append("&" + URLEncoder.encode("stationId", "UTF-8") + "=" + URLEncoder.encode(myData.getStationId(), "UTF-8")); /*정류소ID*/

    myData.setUrl(urlBuilder.toString());
  }

  public void getXml() throws ParserConfigurationException, TransformerException, IOException, SAXException {
    DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
    myData.setDoc(dBuilder.parse(myData.getUrl()));

    //버스 목록 정보 가져오기
    Transformer tf = TransformerFactory.newInstance().newTransformer();
    tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    tf.setOutputProperty(OutputKeys.INDENT, "yes");
    Writer out = new StringWriter();
    tf.transform(new DOMSource((Node) myData.getDoc()), new StreamResult(out));

    //System.out.println(out.toString());
  }

  /**
   * PredictTime1
   */
  public void setPredictTime1() {
    myData.setNList(myData.getDoc().getElementsByTagName("busArrivalList"));

    System.out.println("Tag 개수: " + myData.getNList().getLength());

    for (int i = 0; i < myData.getNList().getLength(); i++) {
      Node nNode = myData.getNList().item(i);

      Element eElement = (Element) nNode;

      /*if (getTagValue("predictTime1", eElement) == null) {
        predictTime1.add("Error");
      } else {
        predictTime1.add(getTagValue("predictTime1", eElement));
      }*/

      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        eElement = (Element) nNode;
        myData.getPredictTime1().add(getTagValue("predictTime1", eElement));
      }
    }
  }

  /**
   * PredictTime2
   */
  public void setPredictTime2() {
    for (int i = 0; i < myData.getNList().getLength(); i++) {
      Node nNode = myData.getNList().item(i);

      Element eElement = (Element) nNode;

      //predictTime2.add(getTagValue("predictTime2", eElement));

      /*if (getTagValue("predictTime2", eElement) == null) {
        predictTime2.add("Error");
      } else {
        predictTime2.add(getTagValue("predictTime2", eElement));
      }*/

      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        eElement = (Element) nNode;
        myData.getPredictTime2().add(getTagValue("predictTime2", eElement));
      }

    }
  }

  /**
   * station order
   */
  public void setStaOrder() {
    for (int i = 0; i < myData.getNList().getLength(); i++) {
      Node nNode = myData.getNList().item(i);

      Element eElement = (Element) nNode;

      /*if (getTagValue("staOrder", eElement) == null) {
        staOrder.add("Error");
      } else {
        staOrder.add(getTagValue("staOrder", eElement));
      }*/

      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        eElement = (Element) nNode;
        myData.getStaOrder().add(getTagValue("staOrder", eElement));
      }
    }
  }

  public void setRouteId() {
    for (int i = 0; i < myData.getNList().getLength(); i++) {
      Node nNode = myData.getNList().item(i);

      Element eElement;

      /*if (getTagValue("routeId", eElement) == null) {
        routeId.add("Error");
      } else {
        routeId.add(getTagValue("routeId", eElement));
      }*/

      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        eElement = (Element) nNode;
        myData.getRouteId().add(getTagValue("routeId", eElement));
      }
    }
  }

  public String getTagValue(String tag, Element eElement) {
    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

    if(myData.getNList() != null) {
      Node nValue = (Node) nlList.item(0);
      if (nValue == null) {
        return "Error";
      } else {
        return nValue.getNodeValue();
      }
    }

    return "nList is null";
  }

  public void printAll() {
    System.out.println("Station id: " + myData.getStationId());

    System.out.println("rouite id 길이" + myData.getRouteId().size());

    for(int i = 0; i < myData.getNList().getLength(); i++) {
      System.out.println("Station order: " + myData.getStaOrder().get(i));
      System.out.println("predictTime1: " + String.valueOf(myData.getPredictTime1(i)) + "초 남음");
      System.out.println("predictTime2: " + String.valueOf(myData.getPredictTime2(i)) + "초 남음");
      System.out.println("Route id: " + myData.getRouteId(i) + "\n");
    }
  }

  public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException {
    RData RData = new RData(); //서비스 키 초기화

    Scanner scanner = new Scanner(System.in);

    System.out.print("Station ID: ");
    RData.setStationId(scanner.next());

    /**
     * 버스 도착 목록 정보 URL
     * */
    RData.makeUrl();//url 생성

    RData.getXml();

    System.out.println("=====================");

    RData.setPredictTime1();
    RData.setPredictTime2();
    RData.setRouteId();
    RData.setStaOrder();

    RData.printAll();
  }

  public MyData getMyData() {
    return myData;
  }
}
