module.exports = {
  env: {
    es2021: true,
    node: true,
    parser: "babel-eslint",
  },
  extends: ["airbnb", "airbnb/hooks", "prettier"],
  parserOptions: {
    ecmaFeatures: {
      jsx: true,
    },
    ecmaVersion: "latest",
    sourceType: "module",
  },
  ignorePatterns: ["node_modules/"],
  plugins: ["react"],
  rules: {
    "import/prefer-default-export": "off",
    "react/jsx-filename-extension": [1, { extensions: [".js", ".jsx"] }],
    "react/prop-types": ["error"],
    "react/function-component-definition": [
      2,
      { namedcomponents: "arrow-function" },
    ],
    "arrow-body-style": ["error", "always"],
    "react/prop-types": ["error"],
  },
};
