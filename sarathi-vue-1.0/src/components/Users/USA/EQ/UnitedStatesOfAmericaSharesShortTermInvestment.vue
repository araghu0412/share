<template>
  <div class="united-states-of-america-shares-short-term-investment">
    <UnitedStatesOfAmericaSharesShortTermInvestmentHeader
      :isRefreshButtonDisabled="isRefreshButtonDisabled"
      @onRefreshClick="onRefreshClick()"
      @onAddShortTermInvestmentClick="onAddShortTermInvestmentClick()"
    ></UnitedStatesOfAmericaSharesShortTermInvestmentHeader>
    <UnitedStatesOfAmericaSharesShortTermInvestmentData
      :loading="loading"
      :noRecords="noRecords"
      :shortTermInvestmentDataList="shortTermInvestmentDataList"
      :shortTermInvestmentTotal="shortTermInvestmentTotal"
      @onShortTermInvestmentScriptClick="onShortTermInvestmentScriptClick($event)"
      @onDeleteShortTermInvestmentClick="onDeleteShortTermInvestmentClick($event)"
    ></UnitedStatesOfAmericaSharesShortTermInvestmentData>

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
import UnitedStatesOfAmericaSharesShortTermInvestmentHeader from "@/components/Users/USA/EQ/ShortTermInvestment/UnitedStatesOfAmericaSharesShortTermInvestmentHeader.vue";
import UnitedStatesOfAmericaSharesShortTermInvestmentData from "@/components/Users/USA/EQ/ShortTermInvestment/UnitedStatesOfAmericaSharesShortTermInvestmentData.vue";
import ConfirmationModal from "@/components/common/ConfirmationModal.vue";
import SuccessModal from "@/components/common/SuccessModal.vue";
import ErrorModal from "@/components/common/ErrorModal.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import axios from 'axios';
import services from "@/assets/services/services.json";

export default {
  components: {
    UnitedStatesOfAmericaSharesShortTermInvestmentHeader,
    UnitedStatesOfAmericaSharesShortTermInvestmentData,
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
      yahooCodeToDelete: "",
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
      this.$router.push("/users/USA/EQ/add-short-term-investment");
    },

    getShortTermInvestment() {
      this.loading = true;
      this.noRecords = false;
      this.shortTermInvestmentDataList = [];
      this.shortTermInvestmentTotal = {};
      this.isRefreshButtonDisabled = true;

      axios.get(services.unitedStatesOfAmericaSharesShortTermInvestment).then(response => {
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

    onShortTermInvestmentScriptClick(yahooCode) {
      this.$router.push("/users/USA/EQ/one-share-short-term-investment?yahooCode=" + yahooCode);
    },

    onDeleteShortTermInvestmentClick(scriptDetails) {
      this.yahooCodeToDelete = scriptDetails.yahooCode;
      this.confirmationMessage = "You are about to delete a script [" + scriptDetails.scriptName + "] from the Short Term Investment. This will delete all stocks.";
      this.showConfirmation = true;
    },

    onCloseConfirmation() {
      this.showConfirmation = false;
      this.yahooCodeToDelete = "";
    },

    onConfirmationContinue() {

      this.showConfirmation = false;
      axios.delete(services.unitedStatesOfAmericaSharesShortTermInvestment + "?yahooCode=" + this.yahooCodeToDelete).then(response => {
        if (response.status === 204) {
          this.errorMessage = "Short term investment does not exist for script code " + this.yahooCodeToDelete;
          this.showError = true;
        } else {
          this.showSuccess = response.data.status;
          this.successMessage = response.data.message;
          this.yahooCodeToDelete = "";
          this.getShortTermInvestment();
        }
      }).catch(error => {
        error.response.data.error.map((e) => {
          this.errorMessage = e.errorMessage;
          this.showError = true;
          this.yahooCodeToDelete = "";
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
