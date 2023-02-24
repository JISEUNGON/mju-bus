const URL = "http://api.mju-bus.com";

const URLS = "http://staging-api.mju-bus.com";

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

export const taxiApi = {
  taxi: ({ queryKey }) => {
    const [, id] = queryKey;
    return fetch(`${URLS}/taxi/${id}`).then(res => res.json());
  },
  // location: ({ queryKey }) => {
  //   const [, id] = queryKey;
  //   return fetch(`${URLS}/taxi/list`)
  //     .then(res => res.json())
  //     .then(data => {
  //       const id = data.id;
  //       return fetch(`${URLS}/taxi/${id}/location`);
  //     })
  //     .then(res => res.json());
  // },
  // location: ({ queryKey }) => {
  //   const [, id] = queryKey;
  //   return fetch(`${URLS}/taxi/${id}/location`).then(res => res.json());
  // },
  taxiList: () => fetch(`${URLS}/taxi/list`).then(res => res.json()),
};
