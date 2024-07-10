<template>
  <div class="united-states-of-america-shares-one-share-short-term-investment">
    <UnitedStatesOfAmericaSharesShortTermInvestmentHeader
      :isRefreshButtonDisabled="isRefreshButtonDisabled"
      :oneShareShortTermInvestment="true"
      @onBackButtonClick="onBackButtonClick()"
      @onRefreshClick="onRefreshClick()"
      @onAddShortTermInvestmentClick="onAddShortTermInvestmentClick()"
    ></UnitedStatesOfAmericaSharesShortTermInvestmentHeader>

    <UnitedStatesOfAmericaSharesShortTermInvestmentData
      :loading="loading"
      :noRecords="noRecords"
      :oneShareShortTermInvestment="true"
      :shortTermInvestmentDataList="shortTermInvestmentDataList"
      :shortTermInvestmentTotal="shortTermInvestmentTotal"
    ></UnitedStatesOfAmericaSharesShortTermInvestmentData>

    <ErrorModal
      v-if="showError"
      :errorMessage="errorMessage"
    >
      <template v-slot:header>
        <h3 class="mm-error-modal-title">Error!!</h3>
        <span>
          <MoneyManagerButton
            :buttonClass="'btn-close'"
            @onButtonClick="closeError()"
          >
          </MoneyManagerButton>
        </span>
      </template>
    </ErrorModal>
  </div>
</template>

<script>
import UnitedStatesOfAmericaSharesShortTermInvestmentHeader from "@/components/Users/USA/EQ/ShortTermInvestment/UnitedStatesOfAmericaSharesShortTermInvestmentHeader.vue";
import UnitedStatesOfAmericaSharesShortTermInvestmentData from "@/components/Users/USA/EQ/ShortTermInvestment/UnitedStatesOfAmericaSharesShortTermInvestmentData.vue";
import ErrorModal from "@/components/common/ErrorModal.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import axios from 'axios';
import services from "@/assets/services/services.json";

export default {
  components: {
    UnitedStatesOfAmericaSharesShortTermInvestmentHeader,
    UnitedStatesOfAmericaSharesShortTermInvestmentData,
    ErrorModal,
    MoneyManagerButton
  },

  data() {
    return {
      isRefreshButtonDisabled: false,
      loading: false,
      noRecords: false,
      shortTermInvestmentDataList: [],
      shortTermInvestmentTotal: {},
      yahooCode: "",
      showError: false,
      errorMessage: ""
    }
  },

  created() {
    this.yahooCode = this.$route.query.yahooCode;
    this.getShortTermInvestment();
  },

  methods: {
    onBackButtonClick() {
      this.$router.push("/users/USA/EQ/short-term-investment")
    },

    onRefreshClick() {
      this.getShortTermInvestment();
    },

    onAddShortTermInvestmentClick() {
      this.$router.push("/users/USA/EQ/add-short-term-investment");
    },

    getShortTermInvestment() {
      this.loading = true;
      this.noRecords = false;
      this.shortTermInvestmentDataList = [];
      this.shortTermInvestmentTotal = {};
      this.isRefreshButtonDisabled = true;

      axios.get(services.unitedStatesOfAmericaSharesShortTermInvestment + "?yahooCode=" + this.yahooCode).then(response => {
        if (response.status === 204) {
          this.noRecords = true;
          this.loading = false;
          this.isRefreshButtonDisabled = false;
        } else {
          this.shortTermInvestmentDataList = response.data.shortTermInvestmentScriptsList;
          this.shortTermInvestmentTotal = response.data.shortTermInvestmentTotal
          this.loading = false;
          this.isRefreshButtonDisabled = false;
        }
      }).catch(error => {
        error.response.data.error.map((e) => {
          this.errorMessage = e.errorMessage;
          this.showError = true;
        });
      });
    }
  },

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }
}
</script>
