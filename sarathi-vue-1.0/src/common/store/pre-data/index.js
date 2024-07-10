const preData = {
  state: () => ({
    countryDetailsMap: new Map(),
    servicesOfferedDetailsMap: new Map(),
    countriesAndServicesOfferedDetailsMap: new Map(),
    genderDetailsMap: new Map(),
    subServicesDetailsMap: new Map(),
    subServicesByServiceCodeDetailsMap: new Map()
  }),

  mutations: {
    SET_COUNTRY_DETAILS_MAP(state, countryDetailsMap) {
      state.countryDetailsMap = new Map();
      Object.entries(countryDetailsMap).forEach(([key, value]) => {
        state.countryDetailsMap.set(key, value);
      });
    },

    SET_SERVICES_OFFERED_DETAILS_MAP(state, servicesOfferedDetailsMap) {
      state.servicesOfferedDetailsMap = new Map();
      Object.entries(servicesOfferedDetailsMap).forEach(([key, value]) => {
        state.servicesOfferedDetailsMap.set(key, value);
      });
    },

    SET_COUNTRIES_AND_SERVICES_OFFERED_DETAILS_MAP(state, countriesAndServicesOfferedDetailsMap) {
      state.countriesAndServicesOfferedDetailsMap = new Map();
      Object.entries(countriesAndServicesOfferedDetailsMap).forEach(([key, value]) => {
        state.countriesAndServicesOfferedDetailsMap.set(key, value);
      });
    },

    SET_GENDER_DETAILS_MAP(state, genderDetailsMap) {
      state.genderDetailsMap = new Map();
      Object.entries(genderDetailsMap).forEach(([key, value]) => {
        state.genderDetailsMap.set(key, value);
      });
    },

    SET_SUB_SERVICES_DETAILS_MAP(state, subServicesDetailsMap) {
      state.subServicesDetailsMap = new Map();
      Object.entries(subServicesDetailsMap).forEach(([key, value]) => {
        state.subServicesDetailsMap.set(key, value);
      });
    },

    SET_SUB_SERVICES_BY_SERVICE_CODE_DETAILS_MAP(state, subServicesByServiceCodeDetailsMap) {
      state.subServicesByServiceCodeDetailsMap = new Map();
      Object.entries(subServicesByServiceCodeDetailsMap).forEach(([key, value]) => {
        state.subServicesByServiceCodeDetailsMap.set(key, value);
      });
    }
  },

  getters: {
    GET_COUNTRY_DETAILS_MAP(state) {
      return state.countryDetailsMap;
    },

    GET_SERVICES_OFFERED_DETAILS_MAP(state) {
      return state.servicesOfferedDetailsMap;
    },

    GET_COUNTRIES_AND_SERVICES_OFFERED_DETAILS_MAP(state) {
      return state.countriesAndServicesOfferedDetailsMap;
    },

    GET_GENDER_DETAILS_MAP(state) {
      return state.genderDetailsMap;
    },

    GET_SUB_SERVICES_DETAILS_MAP(state) {
      return state.subServicesDetailsMap;
    },

    GET_SUB_SERVICES_BY_SERVICE_CODE_DETAILS_MAP(state) {
      return state.subServicesByServiceCodeDetailsMap;
    }
  },

  namespaced: true
};

export default preData
