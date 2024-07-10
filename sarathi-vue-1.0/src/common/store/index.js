import { createStore } from "vuex";
import preData from "./pre-data";
import userDetails from "./user-details";
import logout from "./logout";
import bhaarathaSharesCompleteAnalysis from "./bhaaratha-shares-complete-analysis";
import bhaarathaSharesDividendYieldAnalysis from "./bhaaratha-shares-dividend-yield-analysis";
import bhaarathaSharesInvestmentResearch from "./bhaaratha-shares-investment-research";
import unitedStatesOfAmericaSharesCompleteAnalysis from "./united-states-of-america-shares-complete-analysis";
import unitedStatesOfAmericaSharesDividendYieldAnalysis from "./united-states-of-america-shares-dividend-yield-analysis";
import unitedStatesOfAmericaSharesInvestmentResearch from "./united-states-of-america-shares-investment-research";

const moneyManagerStore = createStore({
  modules: {
    preData,
    userDetails,
    logout,
    bhaarathaSharesCompleteAnalysis,
    bhaarathaSharesDividendYieldAnalysis,
    bhaarathaSharesInvestmentResearch,
    unitedStatesOfAmericaSharesCompleteAnalysis,
    unitedStatesOfAmericaSharesDividendYieldAnalysis,
    unitedStatesOfAmericaSharesInvestmentResearch
  }
});

export default moneyManagerStore
