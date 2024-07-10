const unitedStatesOfAmericaSharesCompleteAnalysis = {
  state: () => ({
    unitedStatesOfAmericaSharesSectorMap: new Map(),
    unitedStatesOfAmericaSharesSectorLongTermOnlyMap: new Map(),
    unitedStatesOfAmericaSharesCategoryMap: new Map(),
    unitedStatesOfAmericaSharesCategoryLongTermOnlyMap: new Map(),
    unitedStatesOfAmericaSharesOneSectorDetailsMap: new Map(),
    unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap: new Map(),
    unitedStatesOfAmericaSharesOneCategoryDetailsMap: new Map(),
    unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap: new Map()
  }),

  mutations: {
    SET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_MAP(state, unitedStatesOfAmericaSharesSectorMap) {
      state.unitedStatesOfAmericaSharesSectorMap = new Map();
      Object.entries(unitedStatesOfAmericaSharesSectorMap).forEach(([key, value]) => {
        state.unitedStatesOfAmericaSharesSectorMap.set(key, value);
      });
    },

    SET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_LONG_TERM_ONLY_MAP(state, unitedStatesOfAmericaSharesSectorLongTermOnlyMap) {
      state.unitedStatesOfAmericaSharesSectorLongTermOnlyMap = new Map();
      Object.entries(unitedStatesOfAmericaSharesSectorLongTermOnlyMap).forEach(([key, value]) => {
        state.unitedStatesOfAmericaSharesSectorLongTermOnlyMap.set(key, value);
      });
    },

    SET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_MAP(state, unitedStatesOfAmericaSharesCategoryMap) {
      state.unitedStatesOfAmericaSharesCategoryMap = new Map();
      Object.entries(unitedStatesOfAmericaSharesCategoryMap).forEach(([key, value]) => {
        state.unitedStatesOfAmericaSharesCategoryMap.set(key, value);
      });
    },

    SET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_LONG_TERM_ONLY_MAP(state, unitedStatesOfAmericaSharesCategoryLongTermOnlyMap) {
      state.unitedStatesOfAmericaSharesCategoryLongTermOnlyMap
      Object.entries(unitedStatesOfAmericaSharesCategoryLongTermOnlyMap).forEach(([key, value]) => {
        state.unitedStatesOfAmericaSharesCategoryLongTermOnlyMap.set(key, value);
      });
    },

    SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_SECTOR_DETAILS_MAP(state, unitedStatesOfAmericaSharesOneSectorDetailsMap) {
      state.unitedStatesOfAmericaSharesOneSectorDetailsMap = new Map();
      Object.entries(unitedStatesOfAmericaSharesOneSectorDetailsMap).forEach(([key, value]) => {
        state.unitedStatesOfAmericaSharesOneSectorDetailsMap.set(key, value);
      });
    },

    SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_SECTOR_DETAILS_LONG_TERM_ONLY_MAP(state, unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap) {
      state.unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap
      Object.entries(unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap).forEach(([key, value]) => {
        state.unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap.set(key, value);
      });
    },

    SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_CATEGORY_DETAILS_MAP(state, unitedStatesOfAmericaSharesOneCategoryDetailsMap) {
      state.unitedStatesOfAmericaSharesOneCategoryDetailsMap = new Map();
      Object.entries(unitedStatesOfAmericaSharesOneCategoryDetailsMap).forEach(([key, value]) => {
        state.unitedStatesOfAmericaSharesOneCategoryDetailsMap.set(key, value);
      });
    },

    SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_CATEGORY_DETAILS_LONG_TERM_ONLY_MAP(state, unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap) {
      state.unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap = new Map();
      Object.entries(unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap).forEach(([key, value]) => {
        state.unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap.set(key, value);
      });
    },
  },

  getters: {
    GET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_MAP(state) {
      return state.unitedStatesOfAmericaSharesSectorMap;
    },

    GET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_LONG_TERM_ONLY_MAP(state) {
      return state.unitedStatesOfAmericaSharesSectorLongTermOnlyMap;
    },

    GET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_MAP(state) {
      return state.unitedStatesOfAmericaSharesCategoryMap;
    },

    GET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_LONG_TERM_ONLY_MAP(state) {
      return state.unitedStatesOfAmericaSharesCategoryLongTermOnlyMap;
    },

    GET_UNITED_STATES_OF_AMERICA_SHARES_ONE_SECTOR_DETAILS_MAP(state) {
      return state.unitedStatesOfAmericaSharesOneSectorDetailsMap;
    },

    GET_UNITED_STATES_OF_AMERICA_SHARES_ONE_SECTOR_DETAILS_LONG_TERM_ONLY_MAP(state) {
      return state.unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap;
    },

    GET_UNITED_STATES_OF_AMERICA_SHARES_ONE_CATEGORY_DETAILS_MAP(state) {
      return state.unitedStatesOfAmericaSharesOneCategoryDetailsMap;
    },

    GET_UNITED_STATES_OF_AMERICA_SHARES_ONE_CATEGORY_DETAILS_LONG_TERM_ONLY_MAP(state) {
      return state.unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap;
    },
  },

  namespaced: true
};

export default unitedStatesOfAmericaSharesCompleteAnalysis;
