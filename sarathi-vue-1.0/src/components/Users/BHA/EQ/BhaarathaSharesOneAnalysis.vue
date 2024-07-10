<template>
  <div class="bhaaratha-shares-one-shares-analysis">
    <BhaarathaSharesOneAnalysisHeader
      @onBackButtonClick="onBackButtonClick()"
    ></BhaarathaSharesOneAnalysisHeader>

    <BhaarathaSharesSectorCategoryAnalysisData
      :loading="loading"
      :noRecords="noRecords"
      :chartSeries="chartSeries"
      :chartOptions="chartOptions"
      :total="total"
    ></BhaarathaSharesSectorCategoryAnalysisData>
  </div>
</template>

<script>
import BhaarathaSharesOneAnalysisHeader from "@/components/Users/BHA/EQ/Analysis/BhaarathaSharesOneAnalysisHeader.vue";
import BhaarathaSharesSectorCategoryAnalysisData from "@/components/Users/BHA/EQ/Analysis/BhaarathaSharesSectorCategoryAnalysisData.vue";

export default {
  components: {
    BhaarathaSharesOneAnalysisHeader,
    BhaarathaSharesSectorCategoryAnalysisData,
  },

  data() {
    return {
      loading: false,
      noRecords: false,
      isLongTermOnly: false,
      isValueInPercentage: false,
      chartSeries: [],
      chartOptions: {},
      total: {},
      analysisService: ""
    }
  },

  created() {
    this.isLongTermOnly = this.$route.query.isLongTermOnly === "Yes" ? true : false;
    this.isValueInPercentage = this.$route.query.isValueInPercentage === "Yes" ? true : false;
    this.analysisService = this.$route.query.analysisService

    this.getOneAnalysisDetails();
  },

  methods: {
    onBackButtonClick() {
      this.$router.push("/users/BHA/EQ/" + this.analysisService);
    },

    getOneAnalysisDetails() {
      this.loading = true;
      this.noRecords = false;

      let investedValueList = [];
      let currentValueList = [];
      let xAxisTitlesList = [];
      let chartTitle = this.$route.query.name;

      let oneAnalysisMap = new Map();
      let numberOfScripts = 0;

      if (this.isLongTermOnly) {
        chartTitle = chartTitle + " and Long Term Only";
      }

      if (this.isValueInPercentage) {
        chartTitle = chartTitle + " (%)";
      }

      if (this.analysisService === "complete-analysis") {
        if (this.$route.query.type === "sector" && !this.isLongTermOnly) {
          for (let [key, val] of this.$store.getters["bhaarathaSharesCompleteAnalysis/GET_BHAARATHA_SHARES_ONE_SECTOR_DETAILS_MAP"]) {
            if (key === this.$route.query.name) {
              Object.entries(val).forEach(([k, v]) => {
                oneAnalysisMap.set(k, v);
                numberOfScripts++;
              });
            }
          }
        } else if (this.$route.query.type === "sector" && this.isLongTermOnly) {
          for (let [key, val] of this.$store.getters["bhaarathaSharesCompleteAnalysis/GET_BHAARATHA_SHARES_ONE_SECTOR_DETAILS_LONG_TERM_ONLY_MAP"]) {
            if (key === this.$route.query.name) {
              Object.entries(val).forEach(([k, v]) => {
                oneAnalysisMap.set(k, v);
                numberOfScripts++;
              });
            }
          }
        } else if (this.$route.query.type === "category" && !this.isLongTermOnly) {
          for (let [key, val] of this.$store.getters["bhaarathaSharesCompleteAnalysis/GET_BHAARATHA_SHARES_ONE_CATEGORY_DETAILS_MAP"]) {
            if (key === this.$route.query.name) {
              Object.entries(val).forEach(([k, v]) => {
                oneAnalysisMap.set(k, v);
                numberOfScripts++;
              });
            }
          }
        } else if (this.$route.query.type === "category" && this.isLongTermOnly) {
          for (let [key, val] of this.$store.getters["bhaarathaSharesCompleteAnalysis/GET_BHAARATHA_SHARES_ONE_CATEGORY_DETAILS_LONG_TERM_ONLY_MAP"]) {
            if (key === this.$route.query.name) {
              Object.entries(val).forEach(([k, v]) => {
                oneAnalysisMap.set(k, v);
                numberOfScripts++;
              });
            }
          }
        }
      } else if (this.analysisService === "dividend-yield-analysis") {
        if (this.$route.query.type === "sector" && !this.isLongTermOnly) {
          for (let [key, val] of this.$store.getters["bhaarathaSharesDividendYieldAnalysis/GET_BHAARATHA_SHARES_ONE_SECTOR_DETAILS_MAP"]) {
            if (key === this.$route.query.name) {
              Object.entries(val).forEach(([k, v]) => {
                oneAnalysisMap.set(k, v);
                numberOfScripts++;
              });
            }
          }
        } else if (this.$route.query.type === "sector" && this.isLongTermOnly) {
          for (let [key, val] of this.$store.getters["bhaarathaSharesDividendYieldAnalysis/GET_BHAARATHA_SHARES_ONE_SECTOR_DETAILS_LONG_TERM_ONLY_MAP"]) {
            if (key === this.$route.query.name) {
              Object.entries(val).forEach(([k, v]) => {
                oneAnalysisMap.set(k, v);
                numberOfScripts++;
              });
            }
          }
        } else if (this.$route.query.type === "category" && !this.isLongTermOnly) {
          for (let [key, val] of this.$store.getters["bhaarathaSharesDividendYieldAnalysis/GET_BHAARATHA_SHARES_ONE_CATEGORY_DETAILS_MAP"]) {
            if (key === this.$route.query.name) {
              Object.entries(val).forEach(([k, v]) => {
                oneAnalysisMap.set(k, v);
                numberOfScripts++;
              });
            }
          }
        } else if (this.$route.query.type === "category" && this.isLongTermOnly) {
          for (let [key, val] of this.$store.getters["bhaarathaSharesDividendYieldAnalysis/GET_BHAARATHA_SHARES_ONE_CATEGORY_DETAILS_LONG_TERM_ONLY_MAP"]) {
            if (key === this.$route.query.name) {
              Object.entries(val).forEach(([k, v]) => {
                oneAnalysisMap.set(k, v);
                numberOfScripts++;
              });
            }
          }
        }
      }

      // Decrementing number of scripts count to ignore total
      numberOfScripts--;

      for (let [key, val] of oneAnalysisMap) {
        if (key !== "total") {
          xAxisTitlesList.push(val.scriptName);

          if (!this.isValueInPercentage) {
            investedValueList.push(val.investedValue);
            currentValueList.push(val.currentValue);
          } else {
            investedValueList.push(val.investedValuePercentage);
            currentValueList.push(val.currentValuePercentage);
          }
        } else {
          this.total = {
            numberOfScripts: numberOfScripts,
            investedValue: val.investedValue,
            currentValue: val.currentValue,
            profitLoss: val.profitLoss,
            profitLossPercentage: val.profitLossPercentage
          }
        }
      }

      this.buildChartData(investedValueList, currentValueList, xAxisTitlesList, chartTitle);

      if (oneAnalysisMap.size === 0) {
        this.noRecords = true;
      }
      this.loading = false;
    },

    buildChartData(investedValueList, currentValueList, xAxisTitlesList, chartTitle) {
      this.chartSeries = [
        {
          name: "Invested Value",
          data: investedValueList
        },
        {
          name: "Current Value",
          data: currentValueList
        }
      ];

      this.chartOptions = {
        title: {
          text: chartTitle,
          align: 'center'
        },

        states: {
          hover: {
            filter: {
              type: 'none'
            }
          },

          active: {
            filter: {
              type: 'none'
            }
          }
        },

        chart: {
          type: 'bar',
        },

        colors: ['#8B0000', '#1AA260'],

        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '40%',
          },
        },

        dataLabels: {
          enabled: false
        },

        xaxis: {
          categories: xAxisTitlesList
        },

        fill: {
          colors: ['#8B0000', '#1AA260'],
          opacity: 1
        },

        tooltip: {
          enabled: true,
        }
      };
    }
  }
}
</script>
