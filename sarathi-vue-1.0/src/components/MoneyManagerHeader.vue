<template>
  <div class="mm-header">
    <img class="mm-header-logo" src="../assets/logo/money-manager-full-logo.jpg" />
    <div class="mm-header-right">
      <div>{{ sarathiAppDetails }}</div>
      <div>{{ anukyaAppDetails }}</div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import services from "@/assets/services/services.json";

export default {
  data() {
    return {
      sarathiAppDetails: "",
      anukyaAppDetails: ""
    }
  },
  mounted() {
    axios.get(services.preData).then(response => {
      // Store - Country Details
      this.$store.commit("preData/SET_COUNTRY_DETAILS_MAP", response.data.countryDetailsMap);

      // Store - Services Offered Details Map
      this.$store.commit("preData/SET_SERVICES_OFFERED_DETAILS_MAP", response.data.servicesOfferedDetailsMap);

      // Store - Countries and Services Offered Details Map
      this.$store.commit("preData/SET_COUNTRIES_AND_SERVICES_OFFERED_DETAILS_MAP", response.data.countriesAndServicesOfferedDetailsMap);

      // Store - Gender Details Map
      this.$store.commit("preData/SET_GENDER_DETAILS_MAP", response.data.genderDetailsMap);

      // Store - Sub Services Details Map
      this.$store.commit("preData/SET_SUB_SERVICES_DETAILS_MAP", response.data.subServicesDetailsMap);

      // Store - Sub Services By Service Code Details Map
      this.$store.commit("preData/SET_SUB_SERVICES_BY_SERVICE_CODE_DETAILS_MAP", response.data.subServicesByServiceCodeDetailsMap);
    });

    this.sarathiAppDetails = "SARATHI (" + process.env.VUE_APP_ACTIVE_PROFILE + "): " + "v" + process.env.VUE_APP_VERSION;

    axios.get(services.appDetails).then(response => {
      this.anukyaAppDetails = response.data;
    }).catch(error => {
      console.log(error);
      this.anukyaAppDetails = "ERROR"
    })
  }
}
</script>
