import React, { useState } from "react";

const TaxiChatContext = React.createContext();

function TaxiChatProvider({ children }) {
  const [goChat, setGoChat] = useState(false);
  const [join, setJoin] = useState(false);
  const [focused, setFocused] = useState(true);
  const [client, setClient] = useState(null);
  const [subscribe, setSubscribe] = useState(null);
  const [subscription, setSubscription] = useState(null);
  const [out, setOut] = useState(true);
  return (
    <TaxiChatContext.Provider
      value={{
        goChat,
        setGoChat,
        join,
        setJoin,
        focused,
        setFocused,
        client,
        setClient,
        subscribe,
        setSubscribe,
        subscription,
        setSubscription,
        out,
        setOut,
      }}
    >
      {children}
    </TaxiChatContext.Provider>
  );
}

export { TaxiChatContext, TaxiChatProvider };
