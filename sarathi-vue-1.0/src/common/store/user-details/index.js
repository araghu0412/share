import axios from "axios";

const userDetails = {
  state: () => ({
    userDetails: {},
    tokenDetails: {}
  }),

  mutations: {
    SET_USER_DETAILS(state, userDetails) {
      state.userDetails = userDetails;
    },

    SET_TOKEN_DETAILS(state, tokenDetails) {
      state.tokenDetails = tokenDetails;
    }
  },

  getters: {
    GET_USER_DETAILS(state) {
      return state.userDetails;
    },

    GET_TOKEN_DETAILS(state) {
      return state.tokenDetails;
    }
  },

  actions: {
    setHttpHeaders(context) {
      axios.defaults.headers.common["loggedInUserEmailId"] = context.state.userDetails.email;
      axios.defaults.headers.common["Authorization"] = context.state.tokenDetails.tokenType + " " + context.state.tokenDetails.accessToken;
    },

    removeHttpHeaders() {
      delete axios.defaults.headers.common["loggedInUserEmailId"];
      delete axios.defaults.headers.common["Authorization"];
    }
  },

  namespaced: true
};

export default userDetails
