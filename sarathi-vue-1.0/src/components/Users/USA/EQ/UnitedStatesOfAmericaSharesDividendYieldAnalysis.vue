<template>
  <div class="united-states-of-america-shares-dividend-yield-analysis">
    <UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeader
      :isRefreshButtonDisabled="isRefreshButtonDisabled"
      :isTypeDropdownDisabled="isTypeDropdownDisabled"
      :typeDropdownList="typeDropdownList"
      :typeDropdownPlaceholder="typeDropdownPlaceholder"
      :isSettingsButtonDisabled="isSettingsButtonDisabled"
      :oneAnalysisDropdownList="oneAnalysisDropdownList"
      :showOneAnalysis="showOneAnalysis"
      @onRefreshClick="onRefreshClick()"
      @onSettingsClick="onSettingsClick()"
      @onSectorAnalysisSelect="onSectorAnalysisSelect()"
      @onCategoryAnalysisSelect="onCategoryAnalysisSelect()"
      @onOneAnalysisDropdownSelect="onOneAnalysisDropdownSelect($event)"
      @onBackButtonClick="onbackButtonClick()"
    ></UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeader>

    <UnitedStatesOfAmericaSharesSectorCategoryAnalysisData
      :loading="loading"
      :noRecords="noRecords"
      :chartSeries="chartSeries"
      :chartOptions="chartOptions"
      :total="total"
    ></UnitedStatesOfAmericaSharesSectorCategoryAnalysisData>

    <UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModal
      v-if="showSettings"
      :longTermRadioList="longTermRadioList"
      :longTermRadioDefault="longTermRadioDefault"
      :valueInPercentageRadioList="valueInPercentageRadioList"
      :valueInPercentageRadioDefault="valueInPercentageRadioDefault"
      @onCloseSettingsClick="onCloseSettingsClick()"
      @onConfirmSettingsClick="onConfirmSettingsClick($event)"
    ></UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModal>
  </div>
</template>

<script>
import UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeader from "@/components/Users/USA/EQ/Analysis/UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeader.vue";
import UnitedStatesOfAmericaSharesSectorCategoryAnalysisData from "@/components/Users/USA/EQ/Analysis/UnitedStatesOfAmericaSharesSectorCategoryAnalysisData.vue";
import UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModal from "@/components/Users/USA/EQ/Analysis/UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModal.vue";
import services from "@/assets/services/services.json";
import axios from "axios";

export default {
  components: {
    UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeader,
    UnitedStatesOfAmericaSharesSectorCategoryAnalysisData,
    UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModal
  },

  data() {
    return {
      isRefreshButtonDisabled: false,
      isTypeDropdownDisabled: false,
      typeDropdownList: [
        {
          id: "sector",
          value: "Sector"
        },
        {
          id: "category",
          value: "Category"
        }
      ],
      typeDropdownPlaceholder: "Sector",
      loading: false,
      isLongTermOnly: false,
      isValueInPercentage: false,
      noRecords: false,
      isSectorAnalysis: false,
      isCategoryAnalysis: false,
      chartSeries: [],
      chartOptions: {},
      longTermRadioList: [
        {
          id: "Y",
          value: "Yes"
        },
        {
          id: "N",
          value: "No"
        }
      ],
      longTermRadioDefault: {},
      valueInPercentageRadioList: [
        {
          id: "Y",
          value: "Yes"
        },
        {
          id: "N",
          value: "No"
        }
      ],
      valueInPercentageRadioDefault: {},
      showSettings: false,
      isSettingsButtonDisabled: false,
      total: {},
      oneAnalysisDropdownList: [],
      showOneAnalysis: false
    }
  },

  created() {
    this.longTermRadioDefault = this.longTermRadioList[0];
    this.valueInPercentageRadioDefault = this.valueInPercentageRadioList[1];
    this.isLongTermOnly = this.longTermRadioDefault.value === "Yes" ? true : false;
    this.isValueInPercentage = this.valueInPercentageRadioDefault.value === "Yes" ? true : false;
    // First time is sector analysis
    this.isSectorAnalysis = true;
    this.getAnalysisDetails();
  },

  methods: {
    getAnalysisDetails() {

      this.loading = true;
      this.isRefreshButtonDisabled = true;
      this.isTypeDropdownDisabled = true;
      this.noRecords = false;
      this.isSettingsButtonDisabled = true;
      this.showOneAnalysis = false;

      if(this.$store.getters["unitedStatesOfAmericaSharesDividendYieldAnalysis/GET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_MAP"].size === 0) {
        axios.get(services.unitedStatesOfAmericaSharesDividendYieldAnalysis).then(response => {
          if (response.status === 204) {
            this.noRecords = true;
            this.loading = false;
            this.isRefreshButtonDisabled = false;
            this.isTypeDropdownDisabled = false;
            this.isSettingsButtonDisabled = false;
          } else {
            this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_MAP", response.data.unitedStatesOfAmericaSharesSectorMap);
            this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_LONG_TERM_ONLY_MAP", response.data.unitedStatesOfAmericaSharesSectorLongTermOnlyMap);
            this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_MAP", response.data.unitedStatesOfAmericaSharesCategoryMap);
            this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_LONG_TERM_ONLY_MAP", response.data.unitedStatesOfAmericaSharesCategoryLongTermOnlyMap);
            this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_SECTOR_DETAILS_MAP", response.data.unitedStatesOfAmericaSharesOneSectorDetailsMap);
            this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_SECTOR_DETAILS_LONG_TERM_ONLY_MAP", response.data.unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap);
            this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_CATEGORY_DETAILS_MAP", response.data.unitedStatesOfAmericaSharesOneCategoryDetailsMap);
            this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_CATEGORY_DETAILS_LONG_TERM_ONLY_MAP", response.data.unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap);

            this.getAnalysisData();

            this.noRecords = false;
            this.loading = false;
            this.isRefreshButtonDisabled = false;
            this.isTypeDropdownDisabled = false;
            this.isSettingsButtonDisabled = false;
          }
        }).catch(error => {
          console.log(error);
        });
      } else {
        this.getAnalysisData();

        this.noRecords = false;
        this.loading = false;
        this.isRefreshButtonDisabled = false;
        this.isTypeDropdownDisabled = false;
        this.isSettingsButtonDisabled = false;
      }
    },

    getAnalysisData() {
      let investedValueList = [];
      let currentValueList = [];
      let xAxisTitlesList = [];
      let chartTitle = "";
      this.oneAnalysisDropdownList = [];

      if (this.isSectorAnalysis && !this.isLongTermOnly) {
        for (let [key, val] of this.$store.getters["unitedStatesOfAmericaSharesDividendYieldAnalysis/GET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_MAP"]) {
          if (key !== "total") {
            this.oneAnalysisDropdownList.push({ id: val.sectorName, value: val.sectorName});
            xAxisTitlesList.push(val.sectorName);
            if (!this.isValueInPercentage) {
              chartTitle = "Sector";
              investedValueList.push(val.investedValue);
              currentValueList.push(val.currentValue);
            } else {
              chartTitle = "Sector (%)";
              investedValueList.push(val.investedValuePercentage);
              currentValueList.push(val.currentValuePercentage);
            }
          } else {
            this.total = {
              numberOfScripts: val.numberOfScripts,
              investedValue: val.investedValue,
              currentValue: val.currentValue,
              profitLoss: val.profitLoss,
              profitLossPercentage: val.profitLossPercentage
            }
          }
        }
      } else if (this.isSectorAnalysis && this.isLongTermOnly) {
        for (let [key, val] of this.$store.getters["unitedStatesOfAmericaSharesDividendYieldAnalysis/GET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_LONG_TERM_ONLY_MAP"]) {
          if (key !== "total") {
            this.oneAnalysisDropdownList.push({ id: val.sectorName, value: val.sectorName});
            xAxisTitlesList.push(val.sectorName);
            if (!this.isValueInPercentage) {
              chartTitle = "Sector and Long Term Only";
              investedValueList.push(val.investedValue);
              currentValueList.push(val.currentValue);
            } else {
              chartTitle = "Sector and Long Term Only (%)";
              investedValueList.push(val.investedValuePercentage);
              currentValueList.push(val.currentValuePercentage);
            }
          } else {
            this.total = {
              numberOfScripts: val.numberOfScripts,
              investedValue: val.investedValue,
              currentValue: val.currentValue,
              profitLoss: val.profitLoss,
              profitLossPercentage: val.profitLossPercentage
            }
          }
        }
      } else if (this.isCategoryAnalysis && !this.isLongTermOnly) {
        for (let [key, val] of this.$store.getters["unitedStatesOfAmericaSharesDividendYieldAnalysis/GET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_MAP"]) {
          if (key !== "total") {
            this.oneAnalysisDropdownList.push({ id: val.categoryName, value: val.categoryName});
            xAxisTitlesList.push(val.categoryName);
            if (!this.isValueInPercentage) {
              chartTitle = "Category";
              investedValueList.push(val.investedValue);
              currentValueList.push(val.currentValue);
            } else {
              chartTitle = "Category (%)";
              investedValueList.push(val.investedValuePercentage);
              currentValueList.push(val.currentValuePercentage);
            }
          } else {
            this.total = {
              numberOfScripts: val.numberOfScripts,
              investedValue: val.investedValue,
              currentValue: val.currentValue,
              profitLoss: val.profitLoss,
              profitLossPercentage: val.profitLossPercentage
            }
          }
        }
      } else if (this.isCategoryAnalysis && this.isLongTermOnly) {
        for (let [key, val] of this.$store.getters["unitedStatesOfAmericaSharesDividendYieldAnalysis/GET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_LONG_TERM_ONLY_MAP"]) {
          if (key !== "total") {
            this.oneAnalysisDropdownList.push({ id: val.categoryName, value: val.categoryName});
            xAxisTitlesList.push(val.categoryName);
            if (!this.isValueInPercentage) {
              chartTitle = "Category and Long Term Only";
              investedValueList.push(val.investedValue);
              currentValueList.push(val.currentValue);
            } else {
              chartTitle = "Category and Long Term Only (%)";
              investedValueList.push(val.investedValuePercentage);
              currentValueList.push(val.currentValuePercentage);
            }
          } else {
            this.total = {
              numberOfScripts: val.numberOfScripts,
              investedValue: val.investedValue,
              currentValue: val.currentValue,
              profitLoss: val.profitLoss,
              profitLossPercentage: val.profitLossPercentage
            }
          }
        }
      }

      this.buildChartData(investedValueList, currentValueList, xAxisTitlesList, chartTitle);
      this.showOneAnalysis = true;
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
    },

    onRefreshClick() {
      // Clear store
      this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_MAP", new Map());
      this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_SECTOR_LONG_TERM_ONLY_MAP", new Map());
      this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_MAP", new Map());
      this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_CATEGORY_LONG_TERM_ONLY_MAP", new Map());
      this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_SECTOR_DETAILS_MAP", new Map());
      this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_SECTOR_DETAILS_LONG_TERM_ONLY_MAP", new Map());
      this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_CATEGORY_DETAILS_MAP", new Map());
      this.$store.commit("unitedStatesOfAmericaSharesDividendYieldAnalysis/SET_UNITED_STATES_OF_AMERICA_SHARES_ONE_CATEGORY_DETAILS_LONG_TERM_ONLY_MAP", new Map());

      this.getAnalysisDetails();
    },

    onSectorAnalysisSelect() {      
      if (!this.isSectorAnalysis) {
        this.isSectorAnalysis = true;
        this.isCategoryAnalysis = false;
      }

      this.getAnalysisDetails();
    },

    onCategoryAnalysisSelect() {
      if (!this.isCategoryAnalysis) {
        this.isCategoryAnalysis = true;
        this.isSectorAnalysis = false;
      }

      this.getAnalysisDetails();
    },

    onSettingsClick() {
      this.showSettings = true;
    },

    onCloseSettingsClick() {
      this.showSettings = false;
    },

    onConfirmSettingsClick(settings) {
      this.isLongTermOnly = settings.longTermRadio.value === "Yes" ? true : false;
      this.isValueInPercentage = settings.valueInPercentageRadio.value === "Yes" ? true : false;
      this.getAnalysisDetails();
      this.showSettings = false;
      this.longTermRadioDefault = settings.longTermRadio;
      this.valueInPercentageRadioDefault = settings.valueInPercentageRadio;
    },

    onOneAnalysisDropdownSelect(selectedType) {
      let type = "";
      if (this.isSectorAnalysis) {
        type = "sector";
      } else if (this.isCategoryAnalysis) {
        type = "category";
      }

      let isLongTermOnly = this.isLongTermOnly ? "Yes" : "No";
      let isValueInPercentage = this.isValueInPercentage ? "Yes" : "No";

      this.$router.push("/users/USA/EQ/one-analysis?type=" + type + "&name=" + selectedType.value + "&isLongTermOnly=" + isLongTermOnly + "&isValueInPercentage=" + isValueInPercentage + "&analysisService=dividend-yield-analysis");
    },

    onbackButtonClick() {
        this.$router.push("/users/USA/EQ/analysis");
    }
  }
}
</script>
