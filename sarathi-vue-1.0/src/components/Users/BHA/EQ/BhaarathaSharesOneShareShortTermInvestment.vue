<template>
  <div class="bhaaratha-shares-one-share-short-term-investment">
    <BhaarathaSharesShortTermInvestmentHeader
      :isRefreshButtonDisabled="isRefreshButtonDisabled"
      :oneShareShortTermInvestment="true"
      @onBackButtonClick="onBackButtonClick()"
      @onRefreshClick="onRefreshClick()"
      @onAddShortTermInvestmentClick="onAddShortTermInvestmentClick()"
    ></BhaarathaSharesShortTermInvestmentHeader>

    <BhaarathaSharesShortTermInvestmentData
      :loading="loading"
      :noRecords="noRecords"
      :oneShareShortTermInvestment="true"
      :shortTermInvestmentDataList="shortTermInvestmentDataList"
      :shortTermInvestmentTotal="shortTermInvestmentTotal"
    ></BhaarathaSharesShortTermInvestmentData>

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
import BhaarathaSharesShortTermInvestmentHeader from "@/components/Users/BHA/EQ/ShortTermInvestment/BhaarathaSharesShortTermInvestmentHeader.vue";
import BhaarathaSharesShortTermInvestmentData from "@/components/Users/BHA/EQ/ShortTermInvestment/BhaarathaSharesShortTermInvestmentData.vue";
import ErrorModal from "@/components/common/ErrorModal.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import axios from 'axios';
import services from "@/assets/services/services.json";

export default {
  components: {
    BhaarathaSharesShortTermInvestmentHeader,
    BhaarathaSharesShortTermInvestmentData,
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
      bseCode: "",
      showError: false,
      errorMessage: ""
    }
  },

  created() {
    this.bseCode = this.$route.query.bseCode;
    this.getShortTermInvestment();
  },

  methods: {
    onBackButtonClick() {
      this.$router.push("/users/BHA/EQ/short-term-investment")
    },

    onRefreshClick() {
      this.getShortTermInvestment();
    },

    onAddShortTermInvestmentClick() {
      this.$router.push("/users/BHA/EQ/add-short-term-investment");
    },

    getShortTermInvestment() {
      this.loading = true;
      this.noRecords = false;
      this.shortTermInvestmentDataList = [];
      this.shortTermInvestmentTotal = {};
      this.isRefreshButtonDisabled = true;

      axios.get(services.bhaarathaSharesShortTermInvestment + "?bseCode=" + this.bseCode).then(response => {
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