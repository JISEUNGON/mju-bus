import { useEffect, useRef, useState } from "react";

function Timer(props) {
  // eslint-disable-next-line react/prop-types
  const { value, toSchool } = props;
  const [min, setMin] = useState(0);
  const [sec, setSec] = useState(0);
  const timerId = useRef(null);
  const time = useRef(value);

  useEffect(() => {
    time.current = value;
  }, [props, value]);

  useEffect(() => {
    timerId.current = setInterval(() => {
      setMin(Math.floor(time.current / 60));
      setSec(time.current % 60);
      time.current -= 1;
    }, 1000);

    return () => clearInterval(timerId.current);
  }, []);
  if (time.current < 120) {
    if(toSchool){
      return "곧 도착";
    }
    
    return "곧 출발";
  }
  return `${min}분${sec}초`;
}

export default Timer;
