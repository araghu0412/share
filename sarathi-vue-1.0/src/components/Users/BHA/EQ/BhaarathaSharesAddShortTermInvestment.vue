<template>
  <div class="bhaaratha-shares-add-short-term-investment">
    <div class="services-add-short-term-investment">
      <BhaarathaSharesAddShortTermInvestmentHeader
        @onBackButtonClick="onBackButtonClick()"
      ></BhaarathaSharesAddShortTermInvestmentHeader>
      <div class="add-short-term-investment-box">
        <h3 class="add-short-term-investment-heading">
          Short Term Investment
        </h3>
        <div class="add-short-term-investment-form-container">
          <div class="add-short-term-investment-form">
            <div class="key">Stock Exchange</div>
            <div class="value">
              <span id="sti-bhaaratha-stock-exchange" @change="inputOnFocus('sti-bhaaratha-stock-exchange')">
                <MoneyManagerRadio
                  :resetRadio="resetStockExchangeRadio"
                  :radioList="stockExchangeList"
                  @onRadioSelect="onStockExchangeSelect($event)"
                ></MoneyManagerRadio>
              </span>
              <div>
                <InputFieldError
                  v-if="errorFlagStockExchange"
                  :errorMessage="errorMessageStockExchange"
                ></InputFieldError>
              </div>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">BSE Code</div>
            <div class="value">
              <input
                type="text"
                id="sti-bhaaratha-bse-code"
                placeholder="BSE Code"
                @focus="inputOnFocus('sti-bhaaratha-bse-code')"
                v-model="bseCode"
              />
              <InputFieldError
                v-if="errorFlagBseCode"
                :errorMessage="errorMessageBseCode"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Yahoo BSE Code</div>
            <div class="value">
              <input
                type="text"
                id="sti-bhaaratha-yahoo-bse-code"
                placeholder="Yahoo BSE Code"
                @focus="inputOnFocus('sti-bhaaratha-yahoo-bse-code')"
                v-model="yahooBseCode"
              />
              <InputFieldError
                v-if="errorFlagYahooBseCode"
                :errorMessage="errorMessageYahooBseCode"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">NSE Code</div>
            <div class="value">
              <input
                type="text"
                id="sti-bhaaratha-nse-code"
                placeholder="NSE Code"
                @focus="inputOnFocus('sti-bhaaratha-nse-code')"
                v-model="nseCode"
              />
              <InputFieldError
                v-if="errorFlagNseCode"
                :errorMessage="errorMessageNseCode"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Yahoo NSE Code</div>
            <div class="value">
              <input
                type="text"
                id="sti-bhaaratha-yahoo-nse-code"
                placeholder="Yahoo NSE Code"
                @focus="inputOnFocus('sti-bhaaratha-yahoo-nse-code')"
                v-model="yahooNseCode"
              />
              <InputFieldError
                v-if="errorFlagYahooNseCode"
                :errorMessage="errorMessageYahooNseCode"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Moneycontrol Code</div>
            <div class="value">
              <input
                type="text"
                id="sti-bhaaratha-moneycontrol-code"
                placeholder="Moneycontrol Code"
                @focus="inputOnFocus('sti-bhaaratha-moneycontrol-code')"
                v-model="moneycontrolCode"
                @change="getShareNameByScriptCode()"
              />
              <InputFieldError
                v-if="errorFlagMoneycontrolCode"
                :errorMessage="errorMessageMoneycontrolCode"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Share Name</div>
            <div class="value">
              <div id="sti-bhaaratha-share-name" class="services-short-term-investment-script-name">
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
              <span id="sti-bhaaratha-purchase-date"></span>
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
                id="sti-bhaaratha-purchase-quantity"
                placeholder="Purchase Quantity"
                @focus="inputOnFocus('sti-bhaaratha-purchase-quantity')"
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
                id="sti-bhaaratha-purchase-price"
                placeholder="Purchase Price"
                @focus="inputOnFocus('sti-bhaaratha-purchase-price')"
                v-model="purchasePrice"
              />
              <InputFieldError
                v-if="errorFlagPurchasePrice"
                :errorMessage="errorMessagePurchasePrice"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Purchase Brokerage</div>
            <div class="value">
              <input
                type="text"
                id="sti-bhaaratha-purchase-brokerage"
                placeholder="Purchase Brokerage"
                @focus="inputOnFocus('sti-bhaaratha-purchase-brokerage')"
                v-model="purchaseBrokerage"
              />
              <InputFieldError
                v-if="errorFlagPurchaseBrokerage"
                :errorMessage="errorMessagePurchaseBrokerage"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Purchase STT</div>
            <div class="value">
              <input
                type="text"
                id="sti-bhaaratha-purchase-stt"
                placeholder="Purchase STT"
                @focus="inputOnFocus('sti-bhaaratha-purchase-stt')"
                v-model="purchaseSTT"
              />
              <InputFieldError
                v-if="errorFlagPurchaseSTT"
                :errorMessage="errorMessagePurchaseSTT"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Purchase Expenditure</div>
            <div class="value">
              <input
                type="text"
                id="sti-bhaaratha-purchase-expenditure"
                placeholder="Purchase Expenditure"
                @focus="inputOnFocus('sti-bhaaratha-purchase-expenditure')"
                v-model="purchaseExpenditure"
              />
              <InputFieldError
                v-if="errorFlagPurchaseExpenditure"
                :errorMessage="errorMessagePurchaseExpenditure"
              ></InputFieldError>
            </div>
          </div>
          <div class="add-short-term-investment-form">
            <div class="key">Purchase Non Expenditure</div>
            <div class="value">
              <input
                type="text"
                id="sti-bhaaratha-purchase-non-expenditure"
                placeholder="Purchase Non Expenditure"
                @focus="inputOnFocus('sti-bhaaratha-purchase-non-expenditure')"
                v-model="purchaseNonExpenditure"
              />
              <InputFieldError
                v-if="errorFlagPurchaseNonExpenditure"
                :errorMessage="errorMessagePurchaseNonExpenditure"
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
import BhaarathaSharesAddShortTermInvestmentHeader from "@/components/Users/BHA/EQ/ShortTermInvestment/BhaarathaSharesAddShortTermInvestmentHeader.vue";
import MoneyManagerRadio from "@/components/common/MoneyManagerRadio.vue";
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
    BhaarathaSharesAddShortTermInvestmentHeader,
    MoneyManagerRadio,
    MoneyManagerButton,
    MoneyManagerLoading,
    MoneyManagerDatePicker,
    InputFieldError,
    ErrorModal,
    SuccessModal
  },

  data() {
    return {
      stockExchangeList: [],
      resetStockExchangeRadio: false,
      stockExchange: "",
      bseCode: "",
      yahooBseCode: "",
      nseCode: "",
      yahooNseCode: "",
      moneycontrolCode: "",
      shareName: "",
      resetPurchaseDatePicker: false,
      purchaseDate: getDefaultDate(""),
      purchaseQuantity: "",
      purchasePrice: "",
      purchaseBrokerage: "",
      purchaseSTT: "",
      purchaseExpenditure: "",
      purchaseNonExpenditure: "",
      shareNameLoading: false,

      // Error field flags
      errorFlagStockExchange: false,
      errorFlagBseCode: false,
      errorFlagYahooBseCode: false,
      errorFlagNseCode: false,
      errorFlagYahooNseCode: false,
      errorFlagMoneycontrolCode: false,
      errorFlagShareName: false,
      errorFlagPurchaseDate: false,
      errorFlagPurchaseQuantity: false,
      errorFlagPurchasePrice: false,
      errorFlagPurchaseBrokerage: false,
      errorFlagPurchaseSTT: false,
      errorFlagPurchaseExpenditure: false,
      errorFlagPurchaseNonExpenditure: false,

      // Error field messages
      errorMessageStockExchange: "",
      errorMessageBseCode: "",
      errorMessageYahooBseCode: "",
      errorMessageNseCode: "",
      errorMessageYahooNseCode: "",
      errorMessageMoneycontrolCode: "",
      errorMessageShareName: "",
      errorMessagePurchaseDate: "",
      errorMessagePurchaseQuantity: "",
      errorMessagePurchasePrice: "",
      errorMessagePurchaseBrokerage: "",
      errorMessagePurchaseSTT: "",
      errorMessagePurchaseExpenditure: "",
      errorMessagePurchaseNonExpenditure: "",

      // Error/Success Modal
      showError: false,
      errorMessage: "",
      showSuccess: false,
      successMessage: ""
    }
  },

  created() {
    this.stockExchangeList = [
      {
        id: "bse",
        value: "BSE"
      },
      {
        id: "nse",
        value: "NSE"
      }
    ];
  },

  methods: {
    onStockExchangeSelect(stockExchange) {
      this.resetStockExchangeRadio = false;
      this.stockExchange = stockExchange.value;
      this.getShareNameByStockExchange();
    },

    getShareNameByStockExchange() {
      if (!this.shareName && this.moneycontrolCode) {
        this.getShareName(this.stockExchange, this.moneycontrolCode);
      }
    },

    getShareNameByScriptCode() {
      if (this.stockExchange) {
        this.getShareName(this.stockExchange, this.moneycontrolCode);
      }
    },

    getShareName(stockExchange, scriptCode) {
      this.shareNameLoading = true;

      axios.get(services.bhaarathaShareNameDetails + "?stockExchange=" + stockExchange + "&scriptCode=" + scriptCode).then(response => {
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
      this.inputOnFocus('sti-bhaaratha-purchase-date');
      this.purchaseDate = selectedPurchaseDate;
    },

    onAddShortTermInvestmentClear() {
      this.resetStockExchangeRadio = true;
      this.stockExchange = "";
      this.bseCode = "";
      this.yahooBseCode = "";
      this.nseCode = "";
      this.yahooNseCode = "";
      this.moneycontrolCode = "";
      this.shareName = "";
      this.resetPurchaseDatePicker = true;
      this.purchaseDate = getDefaultDate("");
      this.purchaseQuantity = "";
      this.purchasePrice = "";
      this.purchaseBrokerage = "";
      this.purchaseSTT = "";
      this.purchaseExpenditure = "";
      this.purchaseNonExpenditure = "";
      this.shareNameLoading = false;

      // Error field flags
      this.errorFlagStockExchange = false;
      this.errorFlagBseCode = false;
      this.errorFlagYahooBseCode = false;
      this.errorFlagNseCode = false;
      this.errorFlagYahooNseCode = false;
      this.errorFlagMoneycontrolCode = false;
      this.errorFlagShareName = false;
      this.errorFlagPurchaseDate = false;
      this.errorFlagPurchaseQuantity = false;
      this.errorFlagPurchasePrice = false;
      this.errorFlagPurchaseBrokerage = false;
      this.errorFlagPurchaseSTT = false;
      this.errorFlagPurchaseExpenditure = false;
      this.errorFlagPurchaseNonExpenditure = false;

      // Error field messages
      this.errorMessageStockExchange = "";
      this.errorMessageBseCode = "";
      this.errorMessageYahooBseCode = "";
      this.errorMessageNseCode = "";
      this.errorMessageYahooNseCode = "";
      this.errorMessageMoneycontrolCode = "";
      this.errorMessageShareName = "";
      this.errorMessagePurchaseDate = "";
      this.errorMessagePurchaseQuantity = "";
      this.errorMessagePurchasePrice = "";
      this.errorMessagePurchaseBrokerage = "";
      this.errorMessagePurchaseSTT = "";
      this.errorMessagePurchaseExpenditure = "";
      this.errorMessagePurchaseNonExpenditure = "";

      // In case of error, change input border color
      document.getElementById("sti-bhaaratha-bse-code").style.border = "";
      document.getElementById("sti-bhaaratha-yahoo-bse-code").style.border = "";
      document.getElementById("sti-bhaaratha-nse-code").style.border = "";
      document.getElementById("sti-bhaaratha-yahoo-nse-code").style.border = "";
      document.getElementById("sti-bhaaratha-moneycontrol-code").style.border = "";
      document.getElementById("sti-bhaaratha-share-name").style.border = "0.1rem solid black";
      document.getElementById("sti-bhaaratha-purchase-quantity").style.border = "";
      document.getElementById("sti-bhaaratha-purchase-price").style.border = "";
      document.getElementById("sti-bhaaratha-purchase-brokerage").style.border = "";
      document.getElementById("sti-bhaaratha-purchase-stt").style.border = "";
      document.getElementById("sti-bhaaratha-purchase-expenditure").style.border = "";
      document.getElementById("sti-bhaaratha-purchase-non-expenditure").style.border = "";
    },

    onAddShortTermInvestmentSubmit() {
      const formData = {
        stockExchange: this.stockExchange,
        bseCode: this.bseCode,
        yahooBseCode: this.yahooBseCode,
        nseCode: this.nseCode,
        yahooNseCode: this.yahooNseCode,
        moneycontrolCode: this.moneycontrolCode,
        shareName: this.shareName,
        purchaseDate: this.purchaseDate,
        purchaseQuantity: this.purchaseQuantity,
        purchasePrice: this.purchasePrice,
        purchaseBrokerage: this.purchaseBrokerage,
        purchaseSTT: this.purchaseSTT,
        purchaseExpenditure: this.purchaseExpenditure,
        purchaseNonExpenditure: this.purchaseNonExpenditure
      };

      axios.post(services.bhaarathaSharesShortTermInvestment, formData).then(response => {

        this.successMessage = response.data.message;
        this.showSuccess = true;

        this.stockExchange = "";
        this.bseCode = "";
        this.nseCode = "";
        this.moneycontrolCode = "";
        this.shareName = "";
        this.resetPurchaseDatePicker = true;
        this.purchaseDate = getDefaultDate("");
        this.purchaseQuantity = "";
        this.purchasePrice = "";
        this.purchaseBrokerage = "";
        this.purchaseSTT = "";
        this.purchaseExpenditure = "";
        this.purchaseNonExpenditure = "";
      }).catch(error => {

        // For displaying errors
        error.response.data.error.map((e) => {
          if (e.uiField === "sti-bhaaratha-stock-exchange") {
            this.errorFlagStockExchange = true;
            this.errorMessageStockExchange = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-bse-code") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagBseCode = true;
            this.errorMessageBseCode = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-yahoo-bse-code") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagYahooBseCode = true;
            this.errorMessageYahooBseCode = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-nse-code") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagNseCode = true;
            this.errorMessageNseCode = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-yahoo-nse-code") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagYahooNseCode = true;
            this.errorMessageYahooNseCode = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-moneycontrol-code") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagMoneycontrolCode = true;
            this.errorMessageMoneycontrolCode = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-share-name") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagShareName = true;
            this.errorMessageShareName = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-purchase-date") {
            this.errorFlagPurchaseDate = true;
            this.errorMessagePurchaseDate = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-purchase-quantity") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagPurchaseQuantity = true;
            this.errorMessagePurchaseQuantity = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-purchase-price") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagPurchasePrice = true;
            this.errorMessagePurchasePrice = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-purchase-brokerage") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagPurchaseBrokerage = true;
            this.errorMessagePurchaseBrokerage = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-purchase-stt") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagPurchaseSTT = true;
            this.errorMessagePurchaseSTT = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-purchase-expenditure") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagPurchaseExpenditure = true;
            this.errorMessagePurchaseExpenditure = e.errorMessage;
          } else if (e.uiField === "sti-bhaaratha-purchase-non-expenditure") {
            document.getElementById(e.uiField).style.border =
              "0.1rem solid red";
            this.errorFlagPurchaseNonExpenditure = true;
            this.errorMessagePurchaseNonExpenditure = e.errorMessage;
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
      if (id === "sti-bhaaratha-stock-exchange") {
        this.errorFlagStockExchange = false;
        this.errorMessageStockExchange = "";

        document.getElementById("sti-bhaaratha-share-name").style = "0.1rem solid black";
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "sti-bhaaratha-bse-code") {
        this.errorFlagBseCode = false;
        this.errorMessageBseCode = "";
      } else if (id === "sti-bhaaratha-yahoo-bse-code") {
        this.errorFlagYahooBseCode = false;
        this.errorMessageYahooBseCode = "";
      } else if (id === "sti-bhaaratha-nse-code") {
        this.errorFlagNseCode = false;
        this.errorMessageNseCode = "";
      } else if (id === "sti-bhaaratha-yahoo-nse-code") {
        this.errorFlagYahooNseCode = false;
        this.errorMessageYahooNseCode = "";
      } else if (id === "sti-bhaaratha-moneycontrol-code") {
        this.errorFlagMoneycontrolCode = false;
        this.errorMessageMoneycontrolCode = "";

        document.getElementById("sti-bhaaratha-share-name").style = "0.1rem solid black";
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "sti-bhaaratha-share-name") {
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "sti-bhaaratha-purchase-date") {
        this.errorFlagPurchaseDate = false;
        this.errorMessagePurchaseDate = "";
      } else if (id === "sti-bhaaratha-purchase-quantity") {
        this.errorFlagPurchaseQuantity = false;
        this.errorMessagePurchaseQuantity = "";
      } else if (id === "sti-bhaaratha-purchase-price") {
        this.errorFlagPurchasePrice = false;
        this.errorMessagePurchasePrice = "";
      } else if (id === "sti-bhaaratha-purchase-brokerage") {
        this.errorFlagPurchaseBrokerage = false;
        this.errorMessagePurchaseBrokerage = "";
      } else if (id === "sti-bhaaratha-purchase-stt") {
        this.errorFlagPurchaseSTT = false;
        this.errorMessagePurchaseSTT = "";
      } else if (id === "sti-bhaaratha-purchase-expenditure") {
        this.errorFlagPurchaseExpenditure = false;
        this.errorMessagePurchaseExpenditure = "";
      } else if (id === "sti-bhaaratha-purchase-non-expenditure") {
        this.errorFlagPurchaseNonExpenditure = false;
        this.errorMessagePurchaseNonExpenditure = "";
      }
    },

    closeError() {
      this.showError = false;
      this.errorMessage = "";
    },

    closeSuccess() {
      this.showSuccess = false;
      this.successMessage = "";
      this.$router.push("/users/BHA/EQ/short-term-investment");
    },

    onBackButtonClick() {
      this.$router.push("/users/BHA/EQ/short-term-investment");
    }
  }
}
</script>
