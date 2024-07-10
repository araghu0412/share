const unitedStatesOfAmericaSharesInvestmentResearch = {
    state: () => ({
      unitedStatesOfAmericaSharesInvestmentResearchList: []
    }),
  
    mutations: {
      SET_UNITED_STATES_OF_AMERICA_SHARES_INVESTMENT_RESEARCH_LIST(state, unitedStatesOfAmericaSharesInvestmentResearchList) {
        state.unitedStatesOfAmericaSharesInvestmentResearchList = [];
        unitedStatesOfAmericaSharesInvestmentResearchList.forEach((unitedStatesOfAmericaSharesInvestmentResearch) => {
          state.unitedStatesOfAmericaSharesInvestmentResearchList.push(unitedStatesOfAmericaSharesInvestmentResearch);
        });
      }
    },
  
    getters: {
      GET_UNITED_STATES_OF_AMERICA_SHARES_INVESTMENT_RESEARCH_LIST(state) {
        return state.unitedStatesOfAmericaSharesInvestmentResearchList;
      }
    },
  
    namespaced: true
  };
  
  export default unitedStatesOfAmericaSharesInvestmentResearch;