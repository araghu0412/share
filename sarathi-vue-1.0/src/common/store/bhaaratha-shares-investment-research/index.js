const bhaarathaSharesInvestmentResearch = {
    state: () => ({
      bhaarathaSharesInvestmentResearchList: []
    }),
  
    mutations: {
      SET_BHAARATHA_SHARES_INVESTMENT_RESEARCH_LIST(state, bhaarathaSharesInvestmentResearchList) {
        state.bhaarathaSharesInvestmentResearchList = [];
        bhaarathaSharesInvestmentResearchList.forEach((bhaarathaSharesInvestmentResearch) => {
          state.bhaarathaSharesInvestmentResearchList.push(bhaarathaSharesInvestmentResearch);
        });
      }
    },
  
    getters: {
      GET_BHAARATHA_SHARES_INVESTMENT_RESEARCH_LIST(state) {
        return state.bhaarathaSharesInvestmentResearchList;
      }
    },
  
    namespaced: true
  };
  
  export default bhaarathaSharesInvestmentResearch;