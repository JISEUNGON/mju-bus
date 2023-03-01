import axios from "axios";
import { KEY_TOKENS } from "./screens/StorageKey";
import AsyncStorage from "@react-native-async-storage/async-storage";

let accessToken = null;
let refreshToken = null;
const URL = "http://localhost:8080";

const getTokens = async () => {
  let tokens = await AsyncStorage.getItem(KEY_TOKENS);

  if (tokens === null || tokens.accessToken === null || tokens.refreshToken === null)
    return ;

  tokens = JSON.parse(tokens);
  accessToken = tokens.accessToken;
  refreshToken = tokens.refreshToken;
};

// 요청 인터셉터 추가하기
axios.interceptors.request.use((config) => {
  // 요청이 전달되기 전에 작업 수행
  console.log(config.url);
  getTokens();
  if (accessToken !== null) {
    config.headers.Authorization = `Bearer ${accessToken}`;
    config.headers["Content-Type"] = "application/json";
  }

  return config;
}, (error) => {
  // 요청 오류가 있는 작업 수행
  console.log("error -> ", error);
  return Promise.reject(error);
});

// 응답 인터셉터 추가하기
axios.interceptors.response.use((response) => {
  // 2xx 범위에 있는 상태 코드는 이 함수를 트리거 합니다.
  return response;
}, (error) => {
  // 2xx 외의 범위에 있는 상태 코드는 이 함수를 트리거 합니다.
  // if (error.response.status === 401) {
  //   const { accessToken, refreshToken } = getTokens();
  //   // refresh token으로 access token 재발급
  //   axios.post(`${URL}/member/refresh`, {
  //     refreshToken: refreshToken,
  //   }).then(res => {
  //     // 재발급 받은 토큰을 저장 
  //     const { accessToken } = res.data;
  //     const tokens = {
  //       accessToken,
  //       refreshToken,
  //     };
  //     AsyncStorage.setItem(KEY_TOKENS, JSON.stringify(tokens));

  //     // 재발급 받은 토큰으로 요청 재전송
  //     const originalRequest = error.config;
  //     originalRequest.headers.Authorization = `Bearer ${accessToken}`;
  //     return axios(originalRequest);
  //   });
  
  console.log("error ->", error);
  console.log("error url ->", error.config.url);
  return Promise.reject(error);
});



export const busApi = {
  bus: ({ queryKey }) => {
    const [, id] = queryKey;
    return axios.get(`${URL}/bus/${id}`).then(res => res.data);
  },
  route: ({ queryKey }) => {
    const [, id] = queryKey;
    return axios.get(`${URL}/bus/${id}/route`).then(res => res.data);
  },
  status: ({ queryKey }) => {
    const [, id] = queryKey;
    return axios.get(`${URL}/bus/${id}/status`).then(res => res.data);
  },
  timeTable: ({ queryKey }) => {
    const [, id] = queryKey;
    return axios.get(`${URL}/bus/${id}/timeTable`).then(res => res.data);
  },
  list: () => axios.get(`${URL}/bus/list`).then(res => res.data),
};

export const calendarApi = {
  calendar: () => axios.get(`${URL}/calendar/`).then(res => res.data),
};

export const stationApi = {
  station: ({ queryKey }) => {
    const [, id] = queryKey;
    return axios.get(`${URL}/station/${id}`).then(res => res.data);
  },
  remain: ({ queryKey }) => {
    const [, id, dest, redBus, toSchool] = queryKey;
    if (dest === undefined) {
      return axios.get(`${URL}/station/${id}/bus-arrival?redBus=${redBus}&toSchool=${toSchool}`).then(res => res.data);
    }
    return axios.get(`${URL}/station/${id}/bus-arrival?dest=${dest}&redBus=${redBus}&toSchool=${toSchool}`).then(res => res.data);
  },
};

export const pathApi = {
  path: ({ queryKey }) => {
    const [, busId, pathTarget, toSchool] = queryKey;

    return axios.get(`${URL}/bus/${busId}/path?station=${pathTarget}&toSchool=${toSchool}`).then(res => res.data);
  },
};

export const memberApi = {
  member: () => axios.get(`${URL}/member/`).then(res => res.data),
  mjuAuth: (payload) => axios.post(`${URL}/member/auth/mju`, payload).then(res => res.data),
}

export const loginApi = {
  apple_login: ({ queryKey }) => {
    const { payload } = queryKey;
    return fetch(`${URL}/login/apple`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    }).then(res => res.json());
  },

  kakao_login: ({ queryKey }) => {
    const { payload } = queryKey;
    return fetch(`${URL}/login/kakao`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    }).then(res => res.json());
  },

  google_login: ({ queryKey }) => {
    const { payload } = queryKey;
    return fetch(`${URL}/login/google`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    }).then(res => res.json());
  },

  guest_login: () => {
    return fetch(`${URL}/login/`, {
      method: "POST",
    }).then(res => res.json());
  },

  refresh: ({ refresh_token }) => {
    return fetch(`${URL}/refresh`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ 
        "refresh_token" : refresh_token 
      }),
    }).then(res => res.json());
  }
};
