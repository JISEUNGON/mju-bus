const URL = "http://localhost:8080";

export const busApi = {
  bus: ({ queryKey }) => {
    const [, id] = queryKey;
    return fetch(`${URL}/bus/${id}`).then(res => res.json());
  },
  route: ({ queryKey }) => {
    const [, id] = queryKey;
    return fetch(`${URL}/bus/${id}/route`).then(res => res.json());
  },
  status: ({ queryKey }) => {
    const [, id] = queryKey;
    return fetch(`${URL}/bus/${id}/status`).then(res => res.json());
  },
  timeTable: ({ queryKey }) => {
    const [, id] = queryKey;
    return fetch(`${URL}/bus/${id}/timeTable`).then(res => res.json());
  },
  list: () => fetch(`${URL}/bus/list`).then(res => res.json()),
};

export const calendarApi = {
  calendar: () => fetch(`${URL}/calendar/`).then(res => res.json()),
};

export const stationApi = {
  station: ({ queryKey }) => {
    const [, id] = queryKey;
    return fetch(`${URL}/station/${id}`).then(res => res.json());
  },
  remain: ({ queryKey }) => {
    const [, id, dest, redBus, toSchool] = queryKey;
    if (dest === undefined) {
      return fetch(
        `${URL}/station/${id}/bus-arrival?redBus=${redBus}&toSchool=${toSchool}`,
      ).then(res => res.json());
    }
    return fetch(
      `${URL}/station/${id}/bus-arrival?dest=${dest}&redBus=${redBus}&toSchool=${toSchool}`,
    ).then(res => res.json());
  },
};

export const pathApi = {
  path: ({ queryKey }) => {
    const [, busId, pathTarget, toSchool] = queryKey;

    return fetch(
      `${URL}/bus/${busId}/path?station=${pathTarget}&toSchool=${toSchool}`,
    ).then(res => res.json());
  },
};

export const loginApi = {
  apple_login : ({ queryKey }) => {
    const { payload } = queryKey;
    return fetch(
      `${URL}/login/apple`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      }
    ).then(res => res.json());
  },

  kakao_login : ({ queryKey }) => {
    const { payload } = queryKey;
    return fetch(
      `${URL}/login/kakao`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      }
    ).then(res => res.json());
  },

  google_login : ({ queryKey }) => {
    const { payload } = queryKey;
    return fetch(
      `${URL}/login/google`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      }
    ).then(res => res.json());
  },

  guest_login : () => {
    return fetch(
      `${URL}/login/`, {
        method: 'POST',
      }
    ).then(res => res.json());
  }
}