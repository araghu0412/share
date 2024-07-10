<template>
  <div class="united-states-of-america-shares-add-short-term-investment">
    <div class="services-add-short-term-investment">
      <UnitedStatesOfAmericaSharesAddShortTermInvestmentHeader
        @onBackButtonClick="onBackButtonClick()"
      ></UnitedStatesOfAmericaSharesAddShortTermInvestmentHeader>
      <div class="add-short-term-investment-box">
        <h3 class="add-short-term-investment-heading">
          Short Term Investment
        </h3>
        <div class="add-short-term-investment-form-container">
          <div class="add-short-term-investment-form">
            <div class="key">Yahoo Code</div>
            <div class="value">
              <input
                type="text"
                id="sti-united-states-of-america-share-yahoo-code"
                placeholder="Yahoo Code"
                @focus="inputOnFocus('sti-united-states-of-america-share-yahoo-code')"
                v-model="yahooCode"
                @change="getShareName()"
              />
              <InputFieldError
                v-if="errorFlagYahooCode"
                :errorMessage="errorMessageYahooCode"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Share Name</div>
            <div class="value">
              <div id="sti-united-states-of-america-share-script-name" class="services-short-term-investment-script-name">
                <template v-if="shareNameLoading">
                  <label>
                    <MoneyManagerLoading></MoneyManagerLoading>
                  </label>
                </template>
                <template v-else>
                  <label>
                    {{ shareName }}
                  </label>
                </template>
              </div>
              <InputFieldError
                v-if="errorFlagShareName"
                :errorMessage="errorMessageShareName"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Purchase Date</div>
            <div class="value">
              <span id="sti-united-states-of-america-share-purchase-date"></span>
              <MoneyManagerDatePicker
                :resetDatePicker="resetPurchaseDatePicker"
                @onSelectedFullDate="onPurchaseDateSelect($event)"
              ></MoneyManagerDatePicker>
              <InputFieldError
                v-if="errorFlagPurchaseDate"
                :errorMessage="errorMessagePurchaseDate"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Purchase Quantity</div>
            <div class="value">
              <input
                type="text"
                id="sti-united-states-of-america-share-purchase-quantity"
                placeholder="Purchase Quantity"
                @focus="inputOnFocus('sti-united-states-of-america-share-purchase-quantity')"
                v-model="purchaseQuantity"
              />
              <InputFieldError
                v-if="errorFlagPurchaseQuantity"
                :errorMessage="errorMessagePurchaseQuantity"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Purchase Price</div>
            <div class="value">
              <input
                type="text"
                id="sti-united-states-of-america-share-purcahse-price"
                placeholder="Purchase Price"
                @focus="inputOnFocus('sti-united-states-of-america-share-purcahse-price')"
                v-model="purchasePrice"
              />
              <InputFieldError
                v-if="errorFlagPurchasePrice"
                :errorMessage="errorMessagePurchasePrice"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Purchase Commission</div>
            <div class="value">
              <input
                type="text"
                id="sti-united-states-of-america-share-purchase-commission"
                placeholder="Purchase Commission"
                @focus="inputOnFocus('sti-united-states-of-america-share-purchase-commission')"
                v-model="purchaseCommission"
              />
              <InputFieldError
                v-if="errorFlagPurchaseCommission"
                :errorMessage="errorMessagePurchaseCommission"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Purchase Transaction Fees</div>
            <div class="value">
              <input
                type="text"
                id="sti-united-states-of-america-share-purchase-transaction-fees"
                placeholder="Purchase Transaction Fees"
                @focus="inputOnFocus('sti-united-states-of-america-share-purchase-transaction-fees')"
                v-model="purchaseTransactionFees"
              />
              <InputFieldError
                v-if="errorFlagPurchaseTransactionFees"
                :errorMessage="errorMessagePurchaseTransactionFees"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Purchase Other Fees</div>
            <div class="value">
              <input
                type="text"
                id="sti-united-states-of-america-share-purchase-other-fees"
                placeholder="Purchase Other Fees"
                @focus="inputOnFocus('sti-united-states-of-america-share-purchase-other-fees')"
                v-model="purchaseOtherFees"
              />
              <InputFieldError
                v-if="errorFlagPurchaseOtherFees"
                :errorMessage="errorMessagePurchaseOtherFees"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form"></div>
          <div class="add-short-term-investment-clear">
            <span>
              <MoneyManagerButton
                :buttonTitle="'Clear'"
                :buttonClass="'btn-secondary'"
                @onButtonClick="onAddShortTermInvestmentClear()"
              ></MoneyManagerButton>
            </span>
            <div class="add-short-term-investment-submit">
              <MoneyManagerButton
                :buttonTitle="'Submit'"
                :buttonClass="'btn-primary'"
                @onButtonClick="onAddShortTermInvestmentSubmit()"
              ></MoneyManagerButton>
            </div>
          </div>
        </div>
      </div>
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
    </div>
  </div>
</template>

<script>
import UnitedStatesOfAmericaSharesAddShortTermInvestmentHeader from "@/components/Users/USA/EQ/ShortTermInvestment/UnitedStatesOfAmericaSharesAddShortTermInvestmentHeader.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import MoneyManagerLoading from "@/components/common/MoneyManagerLoading.vue";
import MoneyManagerDatePicker from "@/components/common/MoneyManagerDatePicker.vue";
import InputFieldError from "@/components/common/InputFieldError";
import ErrorModal from "@/components/common/ErrorModal.vue";
import SuccessModal from "@/components/common/SuccessModal.vue";
import services from "@/assets/services/services.json";
import axios from 'axios';
import { getDefaultDate } from "@/common/utils/utils.js";

export default {
  components: {
    UnitedStatesOfAmericaSharesAddShortTermInvestmentHeader,
    MoneyManagerButton,
    MoneyManagerLoading,
    MoneyManagerDatePicker,
    InputFieldError,
    ErrorModal,
    SuccessModal
  },

  data() {
    return {
      yahooCode: "",
      shareName: "",
      resetPurchaseDatePicker: false,
      purchaseDate: getDefaultDate(""),
      purchaseQuantity: "",
      purchasePrice: "",
      purchaseCommission: "",
      purchaseTransactionFees: "",
      purchaseOtherFees: "",
      shareNameLoading: false,

      // Error field flags
      errorFlagYahooCode: false,
      errorFlagShareName: false,
      errorFlagPurchaseDate: false,
      errorFlagPurchaseQuantity: false,
      errorFlagPurchasePrice: false,
      errorFlagPurchaseCommission: false,
      errorFlagPurchaseTransactionFees: false,
      errorFlagPurchaseOtherFees: false,

      // Error field messages
      errorMessageYahooCode: "",
      errorMessageShareName: "",
      errorMessagePurchaseDate: "",
      errorMessagePurchaseQuantity: "",
      errorMessagePurchasePrice: "",
      errorMessagePurchaseCommission: "",
      errorMessagePurchaseTransactionFees: "",
      errorMessagePurchaseOtherFees: "",

      // Error/Success Modal
      showError: false,
      errorMessage: "",
      showSuccess: false,
      successMessage: ""
    }
  },

  methods: {
    getShareName() {
      this.shareNameLoading = true;

      axios.get(services.unitedStatesOfAmericaShareNameDetails + "?scriptCode=" + this.yahooCode).then(response => {
        if (response.status === 204) {
          this.shareName = "";
          this.shareNameLoading = false;
        } else {
          this.shareName = response.data.scriptName;
          this.shareNameLoading = false;
        }
      }).catch(error => {
        error.response.data.error.map((e) => {
          this.errorMessage = e.errorMessage;
          this.showError = true;
        });
      });
    },

    onPurchaseDateSelect(selectedPurchaseDate) {
      this.resetPurchaseDatePicker = false;
      this.inputOnFocus('sti-united-states-of-america-share-purchase-date');
      this.purchaseDate = selectedPurchaseDate;
    },

    onAddShortTermInvestmentClear() {
      this.yahooCode = "";
      this.shareName = "";
      this.resetPurchaseDatePicker = true;
      this.purchaseDate = getDefaultDate("");
      this.purchaseQuantity = "";
      this.purchasePrice = "";
      this.purchaseCommission = "";
      this.purchaseTransactionFees = "";
      this.purchaseOtherFees = "";
      this.shareNameLoading = false;

      // Error field flags
      this.errorFlagYahooCode = false;
      this.errorFlagShareName = false;
      this.errorFlagPurchaseDate = false;
      this.errorFlagPurchaseQuantity = false;
      this.errorFlagPurchasePrice = false;
      this.errorFlagPurchaseCommission = false;
      this.errorFlagPurchaseTransactionFees = false;
      this.errorFlagPurchaseOtherFees = false;

      // Error field messages
      this.errorMessageYahooCode = "";
      this.errorMessageShareName = "";
      this.errorMessagePurchaseDate = "";
      this.errorMessagePurchaseQuantity = "";
      this.errorMessagePurchasePrice = "";
      this.errorMessagePurchaseCommission = "";
      this.errorMessagePurchaseTransactionFees = "";
      this.errorMessagePurchaseOtherFees = "";

      // In case of error, change input border color
      document.getElementById("sti-united-states-of-america-share-yahoo-code").style.border = "";
      document.getElementById("sti-united-states-of-america-share-script-name").style.border = "0.1rem solid black";
      document.getElementById("sti-united-states-of-america-share-purchase-quantity").style.border = "";
      document.getElementById("sti-united-states-of-america-share-purcahse-price").style.border = "";
      document.getElementById("sti-united-states-of-america-share-purchase-commission").style.border = "";
      document.getElementById("sti-united-states-of-america-share-purchase-transaction-fees").style.border = "";
      document.getElementById("sti-united-states-of-america-share-purchase-other-fees").style.border = "";
    },

    onAddShortTermInvestmentSubmit() {
      const formData = {
        yahooCode: this.yahooCode,
        scriptName: this.shareName,
        purchaseDate: this.purchaseDate,
        purchaseQuantity: this.purchaseQuantity,
        purchasePrice: this.purchasePrice,
        purchaseCommission: this.purchaseCommission,
        purchaseTransactionFees: this.purchaseTransactionFees,
        purchaseOtherFees: this.purchaseOtherFees,
      };

      axios.post(services.unitedStatesOfAmericaSharesShortTermInvestment, formData).then(response => {

        this.successMessage = response.data.message;
        this.showSuccess = true;

        this.yahooCode = "";
        this.scriptName = "";
        this.resetPurchaseDatePicker = true;
        this.purchaseDate = getDefaultDate("");
        this.purchaseQuantity = "";
        this.purchasePrice = "";
        this.purchaseCommission = "";
        this.purchaseTransactionFees = "";
        this.purchaseOtherFees = "";
      }).catch(error => {

        // For displaying errors
        error.response.data.error.map((e) => {
          if (e.uiField === "sti-united-states-of-america-share-yahoo-code") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagYahooCode = true;
            this.errorMessageYahooCode = e.errorMessage;
          } else if (e.uiField === "sti-united-states-of-america-share-script-name") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagShareName = true;
            this.errorMessageShareName = e.errorMessage;
          } else if (e.uiField === "sti-united-states-of-america-share-purchase-date") {
            this.errorFlagPurchaseDate = true;
            this.errorMessagePurchaseDate = e.errorMessage;
          } else if (e.uiField === "sti-united-states-of-america-share-purchase-quantity") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagPurchaseQuantity = true;
            this.errorMessagePurchaseQuantity = e.errorMessage;
          } else if (e.uiField === "sti-united-states-of-america-share-purcahse-price") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagPurchasePrice = true;
            this.errorMessagePurchasePrice = e.errorMessage;
          } else if (e.uiField === "sti-united-states-of-america-share-purchase-commission") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagPurchaseCommission = true;
            this.errorMessagePurchaseCommission = e.errorMessage;
          } else if (e.uiField === "sti-united-states-of-america-share-purchase-transaction-fees") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagPurchaseTransactionFees = true;
            this.errorMessagePurchaseTransactionFees = e.errorMessage;
          } else if (e.uiField === "sti-united-states-of-america-share-purchase-other-fees") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagPurchaseOtherFees = true;
            this.errorMessagePurchaseOtherFees = e.errorMessage;
          } else {
            this.showError = true;
            this.errorMessage = e.errorMessage;
          }
        });
      });
    },

    // Incase of the error, change the border from red to original
    inputOnFocus(id) {
      document.getElementById(id).style.border = "";

      // Resetting error flags and error messages
      if (id === "sti-united-states-of-america-share-yahoo-code") {
        this.errorFlagYahooCode = false;
        this.errorMessageYahooCode = "";

        document.getElementById("sti-united-states-of-america-share-script-name").style = "0.1rem solid black";
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "sti-united-states-of-america-share-script-name") {
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "sti-united-states-of-america-share-purchase-date") {
        this.errorFlagPurchaseDate = false;
        this.errorMessagePurchaseDate = "";
      } else if (id === "sti-united-states-of-america-share-purchase-quantity") {
        this.errorFlagPurchaseQuantity = false;
        this.errorMessagePurchaseQuantity = "";
      } else if (id === "sti-united-states-of-america-share-purcahse-price") {
        this.errorFlagPurchasePrice = false;
        this.errorMessagePurchasePrice = "";
      } else if (id === "sti-united-states-of-america-share-purchase-commission") {
        this.errorFlagPurchaseCommission = false;
        this.errorMessagePurchaseCommission = "";
      } else if (id === "sti-united-states-of-america-share-purchase-transaction-fees") {
        this.errorFlagPurchaseTransactionFees = false;
        this.errorMessagePurchaseTransactionFees = "";
      } else if (id === "sti-united-states-of-america-share-purchase-other-fees") {
        this.errorFlagPurchaseOtherFees = false;
        this.errorMessagePurchaseOtherFees = "";
      }
    },

    closeError() {
      this.showError = false;
      this.errorMessage = "";
    },

    closeSuccess() {
      this.showSuccess = false;
      this.successMessage = "";
      this.$router.push("/users/USA/EQ/short-term-investment");
    },

    onBackButtonClick() {
      this.$router.push("/users/USA/EQ/short-term-investment");
    }
  }
}
</script>
