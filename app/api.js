const URL = "http://direct-api.mju-bus.com";

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
    const [, id] = queryKey;
    return fetch(`${URL}/station/${id}/bus-arrival`).then(res => res.json());
  },
};
