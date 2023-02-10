import React, { useState } from "react";

const TaxiChatContext = React.createContext();

function TaxiChatProvider({ children }) {
  const [goChat, setGoChat] = useState(false);
  const [join, setJoin] = useState(false);
  const [focused, setFocused] = useState();
  return (
    <TaxiChatContext.Provider
      value={{ goChat, setGoChat, join, setJoin, focused, setFocused }}
    >
      {children}
    </TaxiChatContext.Provider>
  );
}

export { TaxiChatContext, TaxiChatProvider };
