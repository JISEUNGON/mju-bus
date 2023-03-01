import { createContext } from "react";

//User : userId, email, name

const AuthContext = createContext({
  user: null,
  loadUser: async () => {},
  kakoSignin: async () => {},
  googleSignin: async () => {},
  appleSignin: async () => {},
  processingSignin: false,
  checkValidateToken: async () => {},
  guestSignin: async () => {},
  addFcmToken: async () => {},
});

export default AuthContext;
