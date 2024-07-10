<template>
  <div class="mm-users-header">
    <div class="mm-users-header-container">
      <div class="users-nav">
        <div
          @click="onUsersHomeClick()"
        >
          <div
            v-if="homeRouterActive"
            class="router active"
          >
            Home
          </div>
          <div
            v-else
            class="router"
          >
            Home
          </div>
        </div>

        <div
          v-for="[key, value] of countryDetailsMap" :key="key"
          @click="onUsersServicesClick(value)"
        >
          <div
            v-if="servicesRouterActiveMap.get(value.countryId)"
            class="router active"
          >
            {{ value.countryCode }}
            <span class="arrow">
              <span v-if="showUserServicesMap.get(value.countryId)" class="arrow-up"></span>
              <span v-if="!showUserServicesMap.get(value.countryId)" class="arrow-down"></span>
            </span>
          </div>
          <div
            v-else
            class="router"
          >
            {{ value.countryCode }}
            <span class="arrow">
              <span v-if="showUserServicesMap.get(value.countryId)" class="arrow-up"></span>
              <span v-if="!showUserServicesMap.get(value.countryId)" class="arrow-down"></span>
            </span>
          </div>
        </div>

        <div
          class="user-nav-right"
          :tabindex="0"
          @blur="onUserDropdownInfoBlur()"
        >
          <div>
            <span
              class="user-dropdown"
              @click="onUserDropdownInfoClick()"
            >
              {{ userName }}
              <span class="arrow">
                <span v-if="showUserDropdownInfo" class="arrow-up"></span>
                <span v-if="!showUserDropdownInfo" class="arrow-down"></span>
              </span>
            </span>
          </div>
          <div v-if="showUserDropdownInfo" class="user-services-dropdown-info">
            <div
              class="user-dropdown-router"
              @click="onUserProfileClick()"
            >
              Profile
            </div>
            <div
              class="user-dropdown-router"
              @click="onUserLogoutClick()"
            >
              Logout
            </div>
          </div>
        </div>
      </div>

      <div
        v-for="[key, value] of showUserServicesMap" :key="key"
        class="user-services-nav"
      >
        <div v-if="value">
          <div
            v-if="showUserServicesMap.get(key)"
          >
            <div
              @click="onServiceSelectClick(countryDetailsMap.get(key).countryId, 'home')"
              class="router"
            >
              Home
            </div>
            <div
              v-for="userOptedServiceIdByCountry of userOptedServiceIdsByCountryMap.get(key).userOptedServiceIdsList"
              :key="userOptedServiceIdByCountry"
              @click="onServiceSelectClick(countryDetailsMap.get(key).countryId, servicesOfferedDetailsMap.get(userOptedServiceIdByCountry).serviceCode)"
            >
              <div
                class="router"
              >
                {{ servicesOfferedDetailsMap.get(userOptedServiceIdByCountry).serviceName }}
                <span class="arrow">
                  <span v-if="showUserSubServicesMap.get(servicesOfferedDetailsMap.get(userOptedServiceIdByCountry).serviceCode)" class="arrow-up"></span>
                  <span v-if="!showUserSubServicesMap.get(servicesOfferedDetailsMap.get(userOptedServiceIdByCountry).serviceCode)" class="arrow-down"></span>
              </span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div
        v-if="showUserSubServices"
        class="user-services-nav"
      >
        <div
          v-for="userSubServices of userSubServicesObject.userSubServicesList"
          :key="userSubServices.subServiceId"
        >
          <div
            @click="onSubServiceSelectClick(userSubServicesObject.countryId, userSubServicesObject.countryCode, userSubServicesObject.serviceCode, userSubServices.subServiceName)"
            class="router"
          >
            {{ userSubServices.subServiceName }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import services from "@/assets/services/services.json";

export default {
  name: "UsersHeader",

  data() {
    return {
      homeRouterActive: false,
      servicesRouterActiveMap: new Map(),
      showUserServicesMap: new Map(),
      showUserSubServicesMap: new Map(),
      showUserDropdownInfo: false,
      userName: "",
      userSubServicesList: [],
      userOptedCountryIdsList: [],
      userOptedServiceIdsByCountryMap: new Map(),
      countryDetailsMap: new Map(),
      servicesOfferedDetailsMap: new Map(),
      userSubServicesObject: {},
      showUserSubServices: false
    }
  },

  created() {
    this.userName = this.$store.getters["userDetails/GET_USER_DETAILS"].preferredName
      ? this.$store.getters["userDetails/GET_USER_DETAILS"].preferredName
      : this.$store.getters["userDetails/GET_USER_DETAILS"].firstName +
        " " +
        this.$store.getters["userDetails/GET_USER_DETAILS"].lastName;

    for (let userOptedService of this.$store.getters["userDetails/GET_USER_DETAILS"].userOptedServicesList) {
      this.userOptedServiceIdsByCountryMap.set(userOptedService.countryId, userOptedService);
    }

    this.userOptedCountryIdsList = this.$store.getters["userDetails/GET_USER_DETAILS"].userOptedCountryIdsList;
    this.countryDetailsMap = this.$store.getters["preData/GET_COUNTRY_DETAILS_MAP"];
    this.servicesOfferedDetailsMap = this.$store.getters["preData/GET_SERVICES_OFFERED_DETAILS_MAP"];

    for (let [key] of this.countryDetailsMap) {
      this.servicesRouterActiveMap.set(key, false);
      this.showUserServicesMap.set(key, false);
    }

    this.homeRouterActive = true;
    this.$router.push("/users/home");
  },

  methods: {
    onUsersHomeClick() {
      this.activeRouter("homeRouter");
      this.$router.push("/users/home");
    },

    onUsersServicesClick(country) {
      this.showUserSubServices = false;
      this.showUserSubServicesMap = new Map();
      for (let [key, value] of this.showUserServicesMap) {
        if (key === country.countryId) {
          this.showUserServicesMap.set(key, !value);
          if (!this.showUserServicesMap.get(key)) {
            this.userSubServicesObject = {};
          }
        } else {
          this.showUserServicesMap.set(key, false);
        }
      }
    },

    activeRouter(selectedRouter) {
      if (selectedRouter === "homeRouter") {
        for (let [key] of this.servicesRouterActiveMap) {
          this.servicesRouterActiveMap.set(key, false);
        }
        for (let [key] of this.showUserServicesMap) {
          this.showUserServicesMap.set(key, false);
        }
        this.userSubServicesObject = {};
        this.showUserSubServices = false;
        this.homeRouterActive = true;
      } else {
        this.homeRouterActive = false;
        for (let [key] of this.servicesRouterActiveMap) {
          if (key === selectedRouter) {
            this.servicesRouterActiveMap.set(key, true);
          } else {
            this.servicesRouterActiveMap.set(key, false);
          }
        }
      }
    },

    onUserDropdownInfoBlur() {
      this.showUserDropdownInfo = false;
    },

    onUserDropdownInfoClick() {
      this.showUserDropdownInfo = !this.showUserDropdownInfo;
      for (let [key] of this.showUserServicesMap) {
        this.showUserServicesMap.set(key, false);
      }
      this.userSubServicesObject = {};
      this.showUserSubServices = false;
      this.showUserSubServicesMap = new Map();
    },

    onUserProfileClick() {
      this.showUserDropdownInfo = false;
      this.homeRouterActive = false;
      this.$router.push("/users/profile");
    },

    onUserLogoutClick() {
      this.showUserDropdownInfo = false;
      axios.get(services.logout, {});
      this.$store.dispatch("logout/clearStoreAndLogout");
    },

    onServiceSelectClick(countryId, serviceCode) {
      this.showUserSubServicesMap = new Map();

      if ("home" === serviceCode) {
        let countryCode = this.$store.getters["preData/GET_COUNTRY_DETAILS_MAP"].get(countryId).countryCode;
        this.$router.push("/users/" + countryCode + "/" + serviceCode.toLowerCase());
        this.activeRouter(this.countryDetailsMap.get(countryId).countryId);

        for (let [key, value] of this.showUserServicesMap) {
          if (value === true) {
            this.showUserServicesMap.set(key, false);
          }
        }

        this.userSubServicesObject = {};
        this.showUserSubServices = false;
        this.showUserSubServicesMap = new Map();
        return;
      }

      let userSubServicesList = [];
      this.$store.getters["preData/GET_SUB_SERVICES_BY_SERVICE_CODE_DETAILS_MAP"].get(serviceCode).subServicesDetailsList.forEach(subServicesDetail => {
        userSubServicesList.push(this.$store.getters["preData/GET_SUB_SERVICES_DETAILS_MAP"].get(subServicesDetail));
      });

      if (this.userSubServicesObject.countryId === this.$store.getters["preData/GET_COUNTRY_DETAILS_MAP"].get(countryId).countryId && this.userSubServicesObject.serviceCode === serviceCode) {
        this.userSubServicesObject = {};
        this.showUserSubServices = false;
        this.showUserSubServicesMap = new Map();
        return;
      }

      this.userSubServicesObject = {
        countryId: this.$store.getters["preData/GET_COUNTRY_DETAILS_MAP"].get(countryId).countryId,
        countryCode: this.$store.getters["preData/GET_COUNTRY_DETAILS_MAP"].get(countryId).countryCode,
        serviceCode: serviceCode,
        userSubServicesList: userSubServicesList
      };

      this.showUserSubServicesMap.set(serviceCode, true);
      this.showUserSubServices = true;
    },

    onSubServiceSelectClick(countryId, countryCode, serviceCode, subServiceName) { 

      this.userSubServicesObject = {};
      this.showUserSubServices = false;
      this.showUserSubServicesMap = new Map();
      for (let [key] of this.countryDetailsMap) {
        this.servicesRouterActiveMap.set(key, false);
        this.showUserServicesMap.set(key, false);
      }
      this.activeRouter(countryId);
      this.$router.push("/users/" + countryCode + "/" + serviceCode + "/" + subServiceName.toLowerCase());
    }
  }
}
</script>
