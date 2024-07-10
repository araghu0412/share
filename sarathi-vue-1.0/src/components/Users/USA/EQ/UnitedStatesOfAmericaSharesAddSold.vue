<template>
  <div class="united-states-of-america-shares-add-sold">
    <div class="services-add-scripts-sold">
      <UnitedStatesOfAmericaSharesAddSharesHeader
        @onBackButtonClicked="onBackButtonClicked()"
      ></UnitedStatesOfAmericaSharesAddSharesHeader>
      <div class="services-add-scipts-box">
        <h3 class="services-add-scipts-heading">
          Fill the details below
        </h3>
        <div class="services-add-scipts-form-container">
          <div class="services-add-scipts-form">
            <div class="key">Yahoo Code</div>
            <div class="value">
              <input
                type="text"
                id="sold-united-states-of-america-share-yahoo-code"
                placeholder="Yahoo Code"
                @focus="inputOnFocus('sold-united-states-of-america-share-yahoo-code')"
                v-model="yahooCode"
                @change="getShareName()"
              />
              <InputFieldError
                v-if="errorFlagYahooCode"
                :errorMessage="errorMessageYahooCode"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Share Name</div>
            <div class="value">
              <div id="sold-united-states-of-america-share-script-name" class="services-add-scipts-script-name">
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
          <div class="services-add-scipts-form">
            <div class="key">Sell Date</div>
            <div class="value">
              <span id="sold-united-states-of-america-share-sell-date"></span>
              <MoneyManagerDatePicker
                :resetDatePicker="resetSellDatePicker"
                @onSelectedFullDate="onSellDateSelect($event)"
              ></MoneyManagerDatePicker>
              <InputFieldError
                v-if="errorFlagSellDate"
                :errorMessage="errorMessageSellDate"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Sell Quantity</div>
            <div class="value">
              <input
                type="text"
                id="sold-united-states-of-america-share-sell-quantity"
                placeholder="Sell Quantity"
                @focus="inputOnFocus('sold-united-states-of-america-share-sell-quantity')"
                v-model="sellQuantity"
              />
              <InputFieldError
                v-if="errorFlagSellQuantity"
                :errorMessage="errorMessageSellQuantity"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Sell Price</div>
            <div class="value">
              <input
                type="text"
                id="sold-united-states-of-america-share-sell-price"
                placeholder="Sell Price"
                @focus="inputOnFocus('sold-united-states-of-america-share-sell-price')"
                v-model="sellPrice"
              />
              <InputFieldError
                v-if="errorFlagSellPrice"
                :errorMessage="errorMessageSellPrice"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Sell Commission</div>
            <div class="value">
              <input
                type="text"
                id="sold-united-states-of-america-share-sell-commission"
                placeholder="Sell Commission"
                @focus="inputOnFocus('sold-united-states-of-america-share-sell-commission')"
                v-model="sellCommission"
              />
              <InputFieldError
                v-if="errorFlagSellCommission"
                :errorMessage="errorMessageSellCommission"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Sell Transaction Fees</div>
            <div class="value">
              <input
                type="text"
                id="sold-united-states-of-america-share-sell-transaction-fees"
                placeholder="Sell Transaction Fees"
                @focus="inputOnFocus('sold-united-states-of-america-share-sell-transaction-fees')"
                v-model="sellTransactionFees"
              />
              <InputFieldError
                v-if="errorFlagSellTransactionFees"
                :errorMessage="errorMessageSellTransactionFees"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Sell Other Fees</div>
            <div class="value">
              <input
                type="text"
                id="sold-united-states-of-america-share-sell-other-fees"
                placeholder="Sell Other Fees"
                @focus="inputOnFocus('sold-united-states-of-america-share-sell-other-fees')"
                v-model="sellOtherFees"
              />
              <InputFieldError
                v-if="errorFlagSellOtherFees"
                :errorMessage="errorMessageSellOtherFees"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form"></div>
          <div class="services-add-scipts-clear">
            <span>
              <MoneyManagerButton
                :buttonTitle="'Clear'"
                :buttonClass="'btn-secondary'"
                @onButtonClick="onUnitedStatesOfAmericaSharesAddSharesClear()"
              ></MoneyManagerButton>
            </span>
            <div class="services-add-scipts-submit">
              <MoneyManagerButton
                :buttonTitle="'Submit'"
                :buttonClass="'btn-primary'"
                @onButtonClick="onUnitedStatesOfAmericaSharesAddSharesSubmit()"
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
import UnitedStatesOfAmericaSharesAddSharesHeader from "@/components/Users/USA/EQ/BoughtSold/UnitedStatesOfAmericaSharesAddSharesHeader.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import InputFieldError from "@/components/common/InputFieldError";
import MoneyManagerDatePicker from "@/components/common/MoneyManagerDatePicker.vue";
import MoneyManagerLoading from "@/components/common/MoneyManagerLoading.vue";
import ErrorModal from "@/components/common/ErrorModal.vue";
import SuccessModal from "@/components/common/SuccessModal.vue";
import services from "@/assets/services/services.json";
import axios from 'axios';
import { getDefaultDate } from "@/common/utils/utils.js";

export default {
  components: {
    UnitedStatesOfAmericaSharesAddSharesHeader,
    MoneyManagerButton,
    InputFieldError,
    MoneyManagerDatePicker,
    MoneyManagerLoading,
    ErrorModal,
    SuccessModal
  },

  data() {
    return {
      yahooCode: "",
      shareName: "",
      shareNameLoading: false,
      sellDate: "",
      resetSellDatePicker: false,
      sellQuantity: "",
      sellPrice: "",
      sellCommission: "",
      sellTransactionFees: "",
      sellOtherFees: "",

      // Error field flags
      errorFlagYahooCode: false,
      errorFlagShareName: false,
      errorFlagSellDate: false,
      errorFlagSellQuantity: false,
      errorFlagSellPrice: false,
      errorFlagSellCommission: false,
      errorFlagSellTransactionFees: false,
      errorFlagSellOtherFees: false,

      // Error field messages
      errorMessageYahooCode: "",
      errorMessageShareName: "",
      errorMessageSellDate: "",
      errorMessageSellQuantity: "",
      errorMessageSellPrice: "",
      errorMessageSellCommission: "",
      errorMessageSellTransactionFees: "",
      errorMessageSellOtherFees: "",

      // Error/Success Modal
      showError: false,
      errorMessage: "",
      showSuccess: false,
      successMessage: ""
    }
  },

  methods: {
    onBackButtonClicked() {
      this.$router.push("/users/USA/EQ/sold");
    },

    onSellDateSelect(selectedSellDate) {
      this.resetSellDatePicker = false;
      this.inputOnFocus('sold-united-states-of-america-share-sell-date');
      this.sellDate = selectedSellDate;
    },

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

    onUnitedStatesOfAmericaSharesAddSharesClear() {

      // In case of error, change input border color
      document.getElementById("sold-united-states-of-america-share-yahoo-code").style.border = "";
      document.getElementById("sold-united-states-of-america-share-script-name").style.border = "0.1rem solid black";
      document.getElementById("sold-united-states-of-america-share-sell-quantity").style.border = "";
      document.getElementById("sold-united-states-of-america-share-sell-price").style.border = "";
      document.getElementById("sold-united-states-of-america-share-sell-commission").style.border = "";
      document.getElementById("sold-united-states-of-america-share-sell-transaction-fees").style.border = "";
      document.getElementById("sold-united-states-of-america-share-sell-other-fees").style.border = "";

      this.yahooCode = "";
      this.shareName = "";
      this.sellQuantity = "";
      this.sellPrice = "";
      this.sellCommission = "";
      this.sellTransactionFees = "";
      this.sellOtherFees = "";
      this.sellDate = getDefaultDate("");
      this.resetSellDatePicker = true;

      // Error field flags
      this.errorFlagYahooCode = false;
      this.errorFlagShareName = false;
      this.errorFlagSellDate = false;
      this.errorFlagSellQuantity = false;
      this.errorFlagSellPrice = false;
      this.errorFlagSellCommission = false;
      this.errorFlagSellTransactionFees = false;
      this.errorFlagSellOtherFees = false;

      // Error field messages
      this.errorMessageYahooCode = "";
      this.errorMessageShareName = "";
      this.errorMessageSellDate = "";
      this.errorMessageSellQuantity = "";
      this.errorMessageSellPrice = "";
      this.errorMessageSellCommission = "";
      this.errorMessageSellTransactionFees = "";
      this.errorMessageSellOtherFees = "";
    },

    onUnitedStatesOfAmericaSharesAddSharesSubmit() {
      const formData = {
        yahooCode: this.yahooCode,
        scriptName: this.shareName,
        sellDate: this.sellDate,
        sellQuantity: this.sellQuantity,
        sellPrice: this.sellPrice,
        sellCommission: this.sellCommission,
        sellTransactionFees: this.sellTransactionFees,
        sellOtherFees: this.sellOtherFees
      };

      axios.post(services.unitedStatesOfAmericaSharesShares + "?type=sold", formData).then(response => {

        this.successMessage = response.data.message;
        this.showSuccess = true;

        this.sellQuantity = "";
        this.sellPrice = "";
        this.sellCommission = "";
        this.sellTransactionFees = "";
        this.sellOtherFees = "";
        this.sellDate = getDefaultDate("");
        this.resetSellDatePicker = true;

      }).catch(error => {

        // For displaying errors
        error.response.data.error.map(e => {
          if (e.uiField === "sold-united-states-of-america-share-yahoo-code") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagYahooCode = true;
            this.errorMessageYahooCode = e.errorMessage;
          } else if (e.uiField === "sold-united-states-of-america-share-script-name") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagShareName = true;
            this.errorMessageShareName = e.errorMessage;
          } else if (e.uiField === "sold-united-states-of-america-share-sell-date") {
            this.errorFlagSellDate = true;
            this.errorMessageSellDate = e.errorMessage;
          } else if (e.uiField === "sold-united-states-of-america-share-sell-quantity") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagSellQuantity = true;
            this.errorMessageSellQuantity = e.errorMessage;
          } else if (e.uiField === "sold-united-states-of-america-share-sell-price") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagSellPrice = true;
            this.errorMessageSellPrice = e.errorMessage;
          } else if (e.uiField === "sold-united-states-of-america-share-sell-commission") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagSellCommission = true;
            this.errorMessageSellCommission = e.errorMessage;
          } else if (e.uiField === "sold-united-states-of-america-share-sell-transaction-fees") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagSellTransactionFees = true;
            this.errorMessageSellTransactionFees = e.errorMessage;
          } else if (e.uiField === "sold-united-states-of-america-share-sell-other-fees") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagSellOtherFees = true;
            this.errorMessageSellOtherFees = e.errorMessage;
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
      if (id === "sold-united-states-of-america-share-yahoo-code") {
        this.errorFlagYahooCode = false;
        this.errorMessageYahooCode = "";

        document.getElementById("sold-united-states-of-america-share-script-name").style = "0.1rem solid black";
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "sold-united-states-of-america-share-sell-date") {
        this.errorFlagSellDate = false;
        this.errorMessageSellDate = "";
      } else if (id === "sold-united-states-of-america-share-sell-quantity") {
        this.errorFlagSellQuantity = false;
        this.errorMessageSellQuantity = "";
      }
      else if (id === "sold-united-states-of-america-share-sell-price") {
        this.errorFlagSellPrice = false;
        this.errorMessageSellPrice = "";
      }
      else if (id === "sold-united-states-of-america-share-sell-commission") {
        this.errorFlagSellCommission = false;
        this.errorMessageSellCommission = "";
      }
      else if (id === "sold-united-states-of-america-share-sell-transaction-fees") {
        this.errorFlagSellTransactionFees = false;
        this.errorMessageSellTransactionFees = "";
      }
      else if (id === "sold-united-states-of-america-share-sell-other-fees") {
        this.errorFlagSellOtherFees = false;
        this.errorMessageSellOtherFees = "";
      }
    },

    closeError() {
      this.showError = false;
      this.errorMessage = "";
    },

    closeSuccess() {
      this.showSuccess = false;
      this.successMessage = "";
      this.$router.push("/users/USA/EQ/sold");
    }
  }
}
</script>