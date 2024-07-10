import { createApp } from 'vue'
import App from './App.vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min.js'
import '@fortawesome/fontawesome-free/css/all.min.css'
import '@fortawesome/fontawesome-free/js/all.min.js'
import './assets/styles/sarathi-vue-money-manager.min.css'
import router from './common/router'
import './common/axios'
import moneyManagerStore from './common/store'
import directives from './common/directives'
import VueApexCharts from "vue3-apexcharts";

const app = createApp(App);

app.use(router);
app.use(moneyManagerStore);
app.use(VueApexCharts);

// Directives
Object.keys(directives).forEach(key => {
  app.directive(key, directives[key]);
});

app.mount('#app');
