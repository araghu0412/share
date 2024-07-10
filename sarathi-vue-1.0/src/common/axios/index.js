import axios from 'axios';
import moneyManagerStore from "../store";

axios.defaults.baseURL = process.env.VUE_APP_SECRET_URL

axios.interceptors.response.use(response => {
  return response;
}, error => {
  if (error.response.status === 401) {
    moneyManagerStore.dispatch("logout/clearStoreAndLogout");
  }
  return Promise.reject(error);
});

export default axios
