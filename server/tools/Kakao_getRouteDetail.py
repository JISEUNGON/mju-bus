import requests
import json

KEY = "KakaoAK e5faece78decd0580c5bb9f24bcb39f6"

url = "https://apis-navi.kakaomobility.com/v1/directions?origin=%s,%s&destination=%s,%s&waypoints=&priority=RECOMMEND&car_fuel=GASOLINE&car_hipass=false&alternatives=false&road_details=false"

stationInfo = {
        "명지대": [37.224284, 127.187286],
        "상공회의소": [37.23068, 127.188246],
        "진입로": [37.233993,127.188858],
        "명지대역": [37.238531,127.189599],
        "진입로(명지대방향)": [37.23400397430913,127.18860239872971],
        "이마트": [37.230473,127.187983],
        "명진당": [37.222184, 127.188953],
        "제3공학관": [37.219509,127.182992],
        "동부경찰서": [37.2347414,127.1986742],
        "용인시장": [37.23537,127.2064454],
        "중앙공영주차장": [37.2336199,127.2088728],
        "제1공학관": [37.222711,127.186784],
        "시외버스 정류장" : [37.224183,127.188367],
        "기흥역" : [37.274635,127.115793]
    }

headers = {"Authorization": KEY}

origin_long = stationInfo["명지대역"][1]
origin_lat = stationInfo["명지대역"][0]

dest_long = stationInfo["진입로(명지대방향)"][1]
dest_lat = stationInfo["진입로(명지대방향)"][0]

url = url % (origin_long, origin_lat, dest_long, dest_lat)
print(url)
res = requests.get(url, headers=headers)
json_object = json.loads(res.text)
print(json_object["routes"][0]["summary"]["duration"])