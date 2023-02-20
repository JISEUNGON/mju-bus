const { createContext } = require("react");

//User : userId, email, name

const AuthContext = createContext({
  user: null,
  kakoSignin: async () => {},
  processingSignin: false,
  checkValidateToken: async () => {},
  guestSignin: async () => {},
});

export default AuthContext;
