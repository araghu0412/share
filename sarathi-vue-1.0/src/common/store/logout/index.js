import router from "../../../common/router";
import localStorageObj from "../../utils/local-storage";

const logout = {
  actions: {
    clearStore(context) {
      context.commit("userDetails/SET_USER_DETAILS", {}, {root: true});
      context.commit("userDetails/SET_TOKEN_DETAILS", {}, {root: true});

      context.commit("bhaarathaSharesCompleteAnalysis/SET_BHAARATHA_SHARES_SECTOR_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesCompleteAnalysis/SET_BHAARATHA_SHARES_SECTOR_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesCompleteAnalysis/SET_BHAARATHA_SHARES_CATEGORY_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesCompleteAnalysis/SET_BHAARATHA_SHARES_CATEGORY_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesCompleteAnalysis/SET_BHAARATHA_SHARES_ONE_SECTOR_DETAILS_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesCompleteAnalysis/SET_BHAARATHA_SHARES_ONE_SECTOR_DETAILS_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesCompleteAnalysis/SET_BHAARATHA_SHARES_ONE_CATEGORY_DETAILS_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesCompleteAnalysis/SET_BHAARATHA_SHARES_ONE_CATEGORY_DETAILS_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesDividendYieldAnalysis/SET_BHAARATHA_SHARES_SECTOR_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesDividendYieldAnalysis/SET_BHAARATHA_SHARES_SECTOR_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesDividendYieldAnalysis/SET_BHAARATHA_SHARES_CATEGORY_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesDividendYieldAnalysis/SET_BHAARATHA_SHARES_CATEGORY_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesDividendYieldAnalysis/SET_BHAARATHA_SHARES_ONE_SECTOR_DETAILS_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesDividendYieldAnalysis/SET_BHAARATHA_SHARES_ONE_SECTOR_DETAILS_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesDividendYieldAnalysis/SET_BHAARATHA_SHARES_ONE_CATEGORY_DETAILS_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesDividendYieldAnalysis/SET_BHAARATHA_SHARES_ONE_CATEGORY_DETAILS_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("bhaarathaSharesInvestmentResearch/SET_BHAARATHA_SHARES_INVESTMENT_RESEARCH_LIST", [], {root: true});
      
      context.commit("unitedStatesOfAmericaSharesCompleteAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesCompleteAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesCompleteAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesCompleteAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesCompleteAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_SECTOR_DETAILS_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesCompleteAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_SECTOR_DETAILS_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesCompleteAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_CATEGORY_DETAILS_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesCompleteAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_CATEGORY_DETAILS_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_SECTOR_DETAILS_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_SECTOR_DETAILS_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_CATEGORY_DETAILS_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_CATEGORY_DETAILS_LONG_TERM_ONLY_MAP", new Map(), {root: true});
      context.commit("unitedStatesOfAmericaSharesInvestmentResearch/SET_UNITED_STATES_OF_AMERICA_SHARES_INVESTMENT_RESEARCH_LIST", [], {root: true});

      clearInterval(localStorageObj.localStorageTimer);
      localStorage.removeItem("refreshTokenTimer");
      localStorage.removeItem("accessTokenTimer");
    },

    clearStoreAndLogout(context) {
      context.dispatch("userDetails/removeHttpHeaders", '', {root: true});
      context.dispatch("clearStore");
      router.push("/");
    }
  },

  namespaced: true
}

export default logout
