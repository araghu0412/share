const bhaarathaSharesCompleteAnalysis = {
  state: () => ({
    bhaarathaSharesSectorMap: new Map(),
    bhaarathaSharesSectorLongTermOnlyMap: new Map(),
    bhaarathaSharesCategoryMap: new Map(),
    bhaarathaSharesCategoryLongTermOnlyMap: new Map(),
    bhaarathaSharesOneSectorDetailsMap: new Map(),
    bhaarathaSharesOneSectorDetailsLongTermOnlyMap: new Map(),
    bhaarathaSharesOneCategoryDetailsMap: new Map(),
    bhaarathaSharesOneCategoryDetailsLongTermOnlyMap: new Map()
  }),

  mutations: {
    SET_BHAARATHA_SHARES_SECTOR_MAP(state, bhaarathaSharesSectorMap) {
      state.bhaarathaSharesSectorMap = new Map();
      Object.entries(bhaarathaSharesSectorMap).forEach(([key, value]) => {
        state.bhaarathaSharesSectorMap.set(key, value);
      });
    },

    SET_BHAARATHA_SHARES_SECTOR_LONG_TERM_ONLY_MAP(state, bhaarathaSharesSectorLongTermOnlyMap) {
      state.bhaarathaSharesSectorLongTermOnlyMap = new Map();
      Object.entries(bhaarathaSharesSectorLongTermOnlyMap).forEach(([key, value]) => {
        state.bhaarathaSharesSectorLongTermOnlyMap.set(key, value);
      });
    },

    SET_BHAARATHA_SHARES_CATEGORY_MAP(state, bhaarathaSharesCategoryMap) {
      state.bhaarathaSharesCategoryMap = new Map();
      Object.entries(bhaarathaSharesCategoryMap).forEach(([key, value]) => {
        state.bhaarathaSharesCategoryMap.set(key, value);
      });
    },

    SET_BHAARATHA_SHARES_CATEGORY_LONG_TERM_ONLY_MAP(state, bhaarathaSharesCategoryLongTermOnlyMap) {
      state.bhaarathaSharesCategoryLongTermOnlyMap
      Object.entries(bhaarathaSharesCategoryLongTermOnlyMap).forEach(([key, value]) => {
        state.bhaarathaSharesCategoryLongTermOnlyMap.set(key, value);
      });
    },

    SET_BHAARATHA_SHARES_ONE_SECTOR_DETAILS_MAP(state, bhaarathaSharesOneSectorDetailsMap) {
      state.bhaarathaSharesOneSectorDetailsMap = new Map();
      Object.entries(bhaarathaSharesOneSectorDetailsMap).forEach(([key, value]) => {
        state.bhaarathaSharesOneSectorDetailsMap.set(key, value);
      });
    },

    SET_BHAARATHA_SHARES_ONE_SECTOR_DETAILS_LONG_TERM_ONLY_MAP(state, bhaarathaSharesOneSectorDetailsLongTermOnlyMap) {
      state.bhaarathaSharesOneSectorDetailsLongTermOnlyMap
      Object.entries(bhaarathaSharesOneSectorDetailsLongTermOnlyMap).forEach(([key, value]) => {
        state.bhaarathaSharesOneSectorDetailsLongTermOnlyMap.set(key, value);
      });
    },

    SET_BHAARATHA_SHARES_ONE_CATEGORY_DETAILS_MAP(state, bhaarathaSharesOneCategoryDetailsMap) {
      state.bhaarathaSharesOneCategoryDetailsMap = new Map();
      Object.entries(bhaarathaSharesOneCategoryDetailsMap).forEach(([key, value]) => {
        state.bhaarathaSharesOneCategoryDetailsMap.set(key, value);
      });
    },

    SET_BHAARATHA_SHARES_ONE_CATEGORY_DETAILS_LONG_TERM_ONLY_MAP(state, bhaarathaSharesOneCategoryDetailsLongTermOnlyMap) {
      state.bhaarathaSharesOneCategoryDetailsLongTermOnlyMap = new Map();
      Object.entries(bhaarathaSharesOneCategoryDetailsLongTermOnlyMap).forEach(([key, value]) => {
        state.bhaarathaSharesOneCategoryDetailsLongTermOnlyMap.set(key, value);
      });
    },
  },

  getters: {
    GET_BHAARATHA_SHARES_SECTOR_MAP(state) {
      return state.bhaarathaSharesSectorMap;
    },

    GET_BHAARATHA_SHARES_SECTOR_LONG_TERM_ONLY_MAP(state) {
      return state.bhaarathaSharesSectorLongTermOnlyMap;
    },

    GET_BHAARATHA_SHARES_CATEGORY_MAP(state) {
      return state.bhaarathaSharesCategoryMap;
    },

    GET_BHAARATHA_SHARES_CATEGORY_LONG_TERM_ONLY_MAP(state) {
      return state.bhaarathaSharesCategoryLongTermOnlyMap;
    },

    GET_BHAARATHA_SHARES_ONE_SECTOR_DETAILS_MAP(state) {
      return state.bhaarathaSharesOneSectorDetailsMap;
    },

    GET_BHAARATHA_SHARES_ONE_SECTOR_DETAILS_LONG_TERM_ONLY_MAP(state) {
      return state.bhaarathaSharesOneSectorDetailsLongTermOnlyMap;
    },

    GET_BHAARATHA_SHARES_ONE_CATEGORY_DETAILS_MAP(state) {
      return state.bhaarathaSharesOneCategoryDetailsMap;
    },

    GET_BHAARATHA_SHARES_ONE_CATEGORY_DETAILS_LONG_TERM_ONLY_MAP(state) {
      return state.bhaarathaSharesOneCategoryDetailsLongTermOnlyMap;
    },
  },

  namespaced: true
};

export default bhaarathaSharesCompleteAnalysis;
