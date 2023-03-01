import { createContext } from "react";

const AppContext = createContext({
    // 버스 일정부터 버스 시간표까지 모든 데이터를 로드 
    loadAppData: async () => {},
});

export default AppContext;