package com.mjubus.busserver.scheduler;

import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

@Getter
@Setter
public class MyData {
  private String serviceKey;
  private String url;
  private String stationId;
  private Document doc;

  private NodeList nList;

  private ArrayList<String> predictTime1, predictTime2, routeId, staOrder;

  public int getPredictTime1(int i) {
    return Integer.parseInt(predictTime1.get(i)) * 60;
  }

  public int getPredictTime2(int i) {
    if (predictTime2.get(i).equals("Error")) {
      return -1;
    }
    return Integer.parseInt(predictTime2.get(i)) * 60;
  }

  public String getRouteId(int i) {
    return routeId.get(i);
  }

  /**
   * 예상 시간1, 정류소 id, 예상 시간2, 노선 id, 노선 순번
   */

  public MyData() {
    serviceKey = "yQq%2Be14nh3vj26ZCYfipTfJ5RfwgHo1hQipbS3%2F4v0IeoUkIqN4H4fzIWrWouRMrj89n9ctR1WT9mtn2qkzffg%3D%3D";
    predictTime1 = new ArrayList<>();
    predictTime2 = new ArrayList<>();
    routeId = new ArrayList<>();
    staOrder = new ArrayList<>();
  }
}




