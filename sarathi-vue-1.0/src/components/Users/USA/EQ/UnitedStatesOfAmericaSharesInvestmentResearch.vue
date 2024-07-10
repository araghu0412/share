<template>
  <div class="united-states-of-america-shares-investment-research">
    <UnitedStatesOfAmericaSharesInvestmentResearchHeader
      :isRefreshButtonDisabled="isRefreshButtonDisabled"
      @onRefreshClick="onRefreshClick()"
      @onBackButtonClick="onBackButtonClick()"
    ></UnitedStatesOfAmericaSharesInvestmentResearchHeader>
    <UnitedStatesOfAmericaSharesInvestmentResearchData
      :loading="loading"
      :noRecords="noRecords"
      :investmentResearchList="investmentResearchList"
      :sortingColumn="sortingColumn"
      :sortingType="sortingType"
      @sortInvestmentResearch="sortInvestmentResearch($event)"
    ></UnitedStatesOfAmericaSharesInvestmentResearchData>
  </div>
</template>

<script>
import UnitedStatesOfAmericaSharesInvestmentResearchHeader from "@/components/Users/USA/EQ/InvestmentResearch/UnitedStatesOfAmericaSharesInvestmentResearchHeader.vue";
import UnitedStatesOfAmericaSharesInvestmentResearchData from "@/components/Users/USA/EQ/InvestmentResearch/UnitedStatesOfAmericaSharesInvestmentResearchData.vue";
import services from "@/assets/services/services.json";
import axios from "axios";

export default {
  components: {
    UnitedStatesOfAmericaSharesInvestmentResearchHeader,
    UnitedStatesOfAmericaSharesInvestmentResearchData
  },

  data() {
    return {
      loading: false,
      isRefreshButtonDisabled: false,
      noRecords: false,
      defaultInvestmentResearchList: [],
      investmentResearchList: [],
      sortInvestmentResearchMap: new Map(),
      sortingColumn: "",
      sortingType: ""
    }
  },

  created() {
    this.defaultSortInvestmentResearchMap()
    this.getInvestmentResearch();
  },

  methods: {
    defaultSortInvestmentResearchMap() {
      this.sortInvestmentResearchMap.set("scriptName", "");
      this.sortInvestmentResearchMap.set("dailyRange", "");
      this.sortInvestmentResearchMap.set("52WRange", "");
      this.sortInvestmentResearchMap.set("ttmDividendYield", "");
    },

    getInvestmentResearch() {
      this.loading = true;
      this.isRefreshButtonDisabled = true;
      this.noRecords = false;

      if (this.$store.getters["unitedStatesOfAmericaSharesInvestmentResearch/GET_UNITED_STATES_OF_AMERICA_SHARES_INVESTMENT_RESEARCH_LIST"].length === 0) {
        axios.get(services.unitedStatesOfAmericaSharesInvestmentResearch).then(response => {
          if (response.status === 204) {
            this.noRecords = true;
            this.loading = false;
            this.isRefreshButtonDisabled = false;
          } else {
            this.$store.commit("unitedStatesOfAmericaSharesInvestmentResearch/SET_UNITED_STATES_OF_AMERICA_SHARES_INVESTMENT_RESEARCH_LIST", response.data.unitedStatesOfAmericaSharesInvestmentResearchList);
            this.noRecords = false;
            this.loading = false;
            this.isRefreshButtonDisabled = false;
          }

          this.defaultInvestmentResearchList = this.$store.getters["unitedStatesOfAmericaSharesInvestmentResearch/GET_UNITED_STATES_OF_AMERICA_SHARES_INVESTMENT_RESEARCH_LIST"];
          this.buildInvestmentResearchList();
        }).catch(error => {
          console.log(error);
        });
      } else {
        this.defaultInvestmentResearchList = this.$store.getters["unitedStatesOfAmericaSharesInvestmentResearch/GET_UNITED_STATES_OF_AMERICA_SHARES_INVESTMENT_RESEARCH_LIST"];
        this.buildInvestmentResearchList();

        this.noRecords = false;
        this.loading = false;
        this.isRefreshButtonDisabled = false;
      }
    },

    buildInvestmentResearchList() {
      this.investmentResearchList = [];
      this.defaultInvestmentResearchList.forEach(defaultInvestmentResearch => {
        this.investmentResearchList.push(defaultInvestmentResearch);
      });
    },

    sortInvestmentResearch(column) {
      this.loading = true;
      this.isRefreshButtonDisabled = true;
      
      this.sortingColumn = column;
      this.sortingType = this.sortInvestmentResearchMap.get(column);

      this.defaultSortInvestmentResearchMap();

      if (this.sortingType === "") {
        this.sortingType = "ASC";
      } else if (this.sortingType === "ASC") {
        this.sortingType = "DESC";
      } else if (this.sortingType === "DESC") {
        this.sortingType = "ASC";
      }

      this.sortInvestmentResearchMap.set(this.sortingColumn, this.sortingType);

      if (this.sortingColumn === "scriptName") {
        if (this.sortingType === "ASC") {
          this.investmentResearchList.sort((a, b) => {
            return a.scriptName.localeCompare(b.scriptName);
          });
        } else if (this.sortingType === "DESC") {
          this.investmentResearchList.sort((a, b) => {
            return b.scriptName.localeCompare(a.scriptName);
          });
        }
      } else if (this.sortingColumn === "dailyRange") {
        if (this.sortingType === "ASC") {
          this.investmentResearchList.sort((a, b) => {
            return a.dailyRange - b.dailyRange;
          });
        } else if (this.sortingType === "DESC") {
          this.investmentResearchList.sort((a, b) => {
            return b.dailyRange - a.dailyRange;
          });
        }
      } else if (this.sortingColumn === "52WRange") {
        if (this.sortingType === "ASC") {
          this.investmentResearchList.sort((a, b) => {
            return a.fiftyTwoWeekRange - b.fiftyTwoWeekRange;
          });
        } else if (this.sortingType === "DESC") {
          this.investmentResearchList.sort((a, b) => {
            return b.fiftyTwoWeekRange - a.fiftyTwoWeekRange;
          });
        }
      } else if (this.sortingColumn === "ttmDividendYield") {
        if (this.sortingType === "ASC") {
          this.investmentResearchList.sort((a, b) => {
            return a.ttmDividendYield - b.ttmDividendYield;
          });
        } else if (this.sortingType === "DESC") {
          this.investmentResearchList.sort((a, b) => {
            return b.ttmDividendYield - a.ttmDividendYield;
          });
        }
      }

      this.loading = false;
      this.isRefreshButtonDisabled = false;
    },

    onRefreshClick() {
      this.defaultSortInvestmentResearchMap();
      this.sortingColumn = "";
      this.sortingType = "";
      this.$store.commit("unitedStatesOfAmericaSharesInvestmentResearch/SET_UNITED_STATES_OF_AMERICA_SHARES_INVESTMENT_RESEARCH_LIST", []);
      this.getInvestmentResearch();
    },

    onBackButtonClick() {
      this.$router.push("/users/USA/EQ/analysis");
    }
  }
}
</script>