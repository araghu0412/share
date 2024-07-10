<template>
  <div class="bhaaratha-shares-short-term-investment">
    <BhaarathaSharesShortTermInvestmentHeader
      :isRefreshButtonDisabled="isRefreshButtonDisabled"
      @onRefreshClick="onRefreshClick()"
      @onAddShortTermInvestmentClick="onAddShortTermInvestmentClick()"
    ></BhaarathaSharesShortTermInvestmentHeader>
    <BhaarathaSharesShortTermInvestmentData
      :loading="loading"
      :noRecords="noRecords"
      :shortTermInvestmentDataList="shortTermInvestmentDataList"
      :shortTermInvestmentTotal="shortTermInvestmentTotal"
      @onShortTermInvestmentScriptClick="onShortTermInvestmentScriptClick($event)"
      @onDeleteShortTermInvestmentClick="onDeleteShortTermInvestmentClick($event)"
    ></BhaarathaSharesShortTermInvestmentData>

    <ConfirmationModal
      v-if="showConfirmation"
      :confirmationMessage="confirmationMessage"
      @onCloseConfirmation="onCloseConfirmation()"
      @onConfirmationContinue="onConfirmationContinue()"
    ></ConfirmationModal>

    <SuccessModal v-if="showSuccess" :successMessage="successMessage">
      <template v-slot:header>
        <h3 class="mm-success-modal-title">Success!!</h3>
        <span>
          <MoneyManagerButton
            :buttonClass="'btn-close'"
            @onButtonClick="closeSuccess()"
          >
          </MoneyManagerButton>
        </span>
      </template>
    </SuccessModal>

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
import ConfirmationModal from "@/components/common/ConfirmationModal.vue";
import SuccessModal from "@/components/common/SuccessModal.vue";
import ErrorModal from "@/components/common/ErrorModal.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import axios from 'axios';
import services from "@/assets/services/services.json";

export default {
  components: {
    BhaarathaSharesShortTermInvestmentHeader,
    BhaarathaSharesShortTermInvestmentData,
    ConfirmationModal,
    SuccessModal,
    ErrorModal,
    MoneyManagerButton
  },

  data() {
    return {
      loading: false,
      noRecords: false,
      isRefreshButtonDisabled: false,
      shortTermInvestmentDataList: [],
      shortTermInvestmentTotal: {},
      bseCodeToDelete: "",
      confirmationMessage: "",
      showConfirmation: false,
      showSuccess: false,
      successMessage: "",
      showError: false,
      errorMessage: ""
    }
  },

  created() {
    this.getShortTermInvestment();
  },

  methods: {
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

      axios.get(services.bhaarathaSharesShortTermInvestment).then(response => {
        if (response.status === 204) {
          this.noRecords = true;
          this.loading = false;
          this.isRefreshButtonDisabled = false;
        } else {
          this.shortTermInvestmentDataList = response.data.shortTermInvestmentScriptsList;
          this.shortTermInvestmentTotal = response.data.shortTermInvestmentTotal;
          this.loading = false;
          this.isRefreshButtonDisabled = false;
        }
      }).catch(error => {
        error.response.data.error.map((e) => {
          this.errorMessage = e.errorMessage;
          this.showError = true;
        });
      });
    },

    onShortTermInvestmentScriptClick(bseCode) {
      this.$router.push("/users/BHA/EQ/one-share-short-term-investment?bseCode=" + bseCode);
    },

    onDeleteShortTermInvestmentClick(scriptDetails) {
      this.bseCodeToDelete = scriptDetails.bseCode;
      this.confirmationMessage = "You are about to delete a script [" + scriptDetails.shareName + "] from the Short Term Investment. This will delete all BSE and NSE stocks.";
      this.showConfirmation = true;
    },

    onCloseConfirmation() {
      this.showConfirmation = false;
      this.bseCodeToDelete = "";
    },

    onConfirmationContinue() {

      this.showConfirmation = false;
      axios.delete(services.bhaarathaSharesShortTermInvestment + "?bseCode=" + this.bseCodeToDelete).then(response => {
        if (response.status === 204) {
          this.errorMessage = "Short term investment does not exist for script code " + this.bseCodeToDelete;
          this.showError = true;
        } else {
          this.showSuccess = response.data.status;
          this.successMessage = response.data.message;
          this.bseCodeToDelete = "";
          this.getShortTermInvestment();
        }
      }).catch(error => {
        error.response.data.error.map((e) => {
          this.errorMessage = e.errorMessage;
          this.showError = true;
          this.bseCodeToDelete = "";
        });
      });
    },

    closeSuccess() {
      this.showSuccess = false;
      this.successMessage = "";
    },

    closeError() {
      this.showError = false;
      this.errorMessage = "";
    }
  }
}
</script>
