<template>
  <div class="bhaaratha-shares-add-sold">
    <div class="services-add-scripts-sold">
      <BhaarathaSharesAddSharesHeader
        @onBackButtonClicked="onBackButtonClicked()"
      ></BhaarathaSharesAddSharesHeader>
      <div class="services-add-scipts-box">
        <h3 class="services-add-scipts-heading">
          Fill the details below
        </h3>
        <div class="services-add-scipts-form-container">
          <div class="services-add-scipts-form">
            <div class="key">Stock Exchange</div>
            <div class="value">
              <span
                id="sold-bhaaratha-share-stock-exchange"
                @change="inputOnFocus('sold-bhaaratha-share-stock-exchange')"
              >
                <MoneyManagerRadio
                  :resetRadio="resetStockExchangeRadio"
                  :radioList="stockExchangeRadioList"
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
          <div class="services-add-scipts-form">
            <div class="key">BSE Script Code</div>
            <div class="value">
              <input
                type="text"
                id="sold-bhaaratha-share-bse-script-code"
                placeholder="BSE Script Code"
                @focus="inputOnFocus('sold-bhaaratha-share-bse-script-code')"
                v-model="bseScriptCode"
              />
              <InputFieldError
                v-if="errorFlagBseScriptCode"
                :errorMessage="errorMessageBseScriptCode"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">NSE Script Code</div>
            <div class="value">
              <input
                type="text"
                id="sold-bhaaratha-share-nse-script-code"
                placeholder="NSE Script Code"
                @focus="inputOnFocus('sold-bhaaratha-share-nse-script-code')"
                v-model="nseScriptCode"
              />
              <InputFieldError
                v-if="errorFlagNseScriptCode"
                :errorMessage="errorMessageNseScriptCode"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Moneycontrol Code</div>
            <div class="value">
              <input
                type="text"
                id="sold-bhaaratha-share-money-control-code"
                placeholder="Moneycontrol Code"
                @focus="inputOnFocus('sold-bhaaratha-share-money-control-code')"
                v-model="moneycontrolCode"
                @change="getShareNameByScriptCode()"
              />
              <InputFieldError
                v-if="errorFlagMoneycontrolCode"
                :errorMessage="errorMessageMoneycontrolCode"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Share Name</div>
            <div class="value">
              <div id="sold-bhaaratha-share-script-name" class="services-add-scipts-script-name">
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
              <span id="sold-bhaaratha-share-sell-date"></span>
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
                id="sold-bhaaratha-share-sell-quantity"
                placeholder="Sell Quantity"
                @focus="inputOnFocus('sold-bhaaratha-share-sell-quantity')"
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
                id="sold-bhaaratha-share-sell-price"
                placeholder="Sell Price"
                @focus="inputOnFocus('sold-bhaaratha-share-sell-price')"
                v-model="sellPrice"
              />
              <InputFieldError
                v-if="errorFlagSellPrice"
                :errorMessage="errorMessageSellPrice"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Sell Brokerage</div>
            <div class="value">
              <input
                type="text"
                id="sold-bhaaratha-share-sell-brokerage"
                placeholder="Sell Brokerage"
                @focus="inputOnFocus('sold-bhaaratha-share-sell-brokerage')"
                v-model="sellBrokerage"
              />
              <InputFieldError
                v-if="errorFlagSellBrokerage"
                :errorMessage="errorMessageSellBrokerage"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Sell STT</div>
            <div class="value">
              <input
                type="text"
                id="sold-bhaaratha-share-sell-stt"
                placeholder="Sell STT"
                @focus="inputOnFocus('sold-bhaaratha-share-sell-stt')"
                v-model="sellSTT"
              />
              <InputFieldError
                v-if="errorFlagSellSTT"
                :errorMessage="errorMessageSellSTT"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Sell Expenditure</div>
            <div class="value">
              <input
                type="text"
                id="sold-bhaaratha-share-sell-expenditure"
                placeholder="Sell Expenditure"
                @focus="inputOnFocus('sold-bhaaratha-share-sell-expenditure')"
                v-model="sellExpenditure"
              />
              <InputFieldError
                v-if="errorFlagSellExpenditure"
                :errorMessage="errorMessageSellExpenditure"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Sell Non Expenditure</div>
            <div class="value">
              <input
                type="text"
                id="sold-bhaaratha-share-sell-non-expenditure"
                placeholder="Sell Non Expenditure"
                @focus="inputOnFocus('sold-bhaaratha-share-sell-non-expenditure')"
                v-model="sellNonExpenditure"
              />
              <InputFieldError
                v-if="errorFlagSellNonExpenditure"
                :errorMessage="errorMessageSellNonExpenditure"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form"></div>
          <div class="services-add-scipts-clear">
            <span>
              <MoneyManagerButton
                :buttonTitle="'Clear'"
                :buttonClass="'btn-secondary'"
                @onButtonClick="onBhaarathaSharesAddSharesClear()"
              ></MoneyManagerButton>
            </span>
            <div class="services-add-scipts-submit">
              <MoneyManagerButton
                :buttonTitle="'Submit'"
                :buttonClass="'btn-primary'"
                @onButtonClick="onBhaarathaSharesAddSharesSubmit()"
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
import BhaarathaSharesAddSharesHeader from "@/components/Users/BHA/EQ/BoughtSold/BhaarathaSharesAddSharesHeader.vue";
import MoneyManagerRadio from "@/components/common/MoneyManagerRadio.vue";
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
    BhaarathaSharesAddSharesHeader,
    MoneyManagerRadio,
    MoneyManagerButton,
    InputFieldError,
    MoneyManagerDatePicker,
    MoneyManagerLoading,
    ErrorModal,
    SuccessModal
  },

  data () {
    return {
      stockExchangeRadioList: [],
      resetStockExchangeRadio: false,
      stockExchange: "",
      bseScriptCode: "",
      nseScriptCode: "",
      moneycontrolCode: "",
      shareName: "",
      shareNameLoading: false,
      sellDate: getDefaultDate(""),
      resetSellDatePicker: false,
      sellQuantity: "",
      sellPrice: "",
      sellBrokerage: "",
      sellSTT: "",
      sellExpenditure: "",
      sellNonExpenditure: "",

      // Error field flags
      errorFlagStockExchange: false,
      errorFlagBseScriptCode: false,
      errorFlagNseScriptCode: false,
      errorFlagMoneycontrolCode: false,
      errorFlagShareName: false,
      errorFlagSellDate: false,
      errorFlagSellQuantity: false,
      errorFlagSellPrice: false,
      errorFlagSellBrokerage: false,
      errorFlagSellSTT: false,
      errorFlagSellExpenditure: false,
      errorFlagSellNonExpenditure: false,

      // Error field messages
      errorMessageStockExchange: "",
      errorMessageBseScriptCode: "",
      errorMessageNseScriptCode: "",
      errorMessageMoneycontrolCode: "",
      errorMessageShareName: "",
      errorMessageSellDate: "",
      errorMessageSellQuantity: "",
      errorMessageSellPrice: "",
      errorMessageSellBrokerage: "",
      errorMessageSellSTT: "",
      errorMessageSellExpenditure: "",
      errorMessageSellNonExpenditure: "",

      // Error/Success Modal
      showError: false,
      errorMessage: "",
      showSuccess: false,
      successMessage: ""
    }
  },

  created() {
    this.getStockExchangeList();
  },

  methods: {
    onBackButtonClicked() {
      this.$router.push("/users/BHA/EQ/sold");
    },

    getStockExchangeList() {
      this.stockExchangeRadioList = [
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

    onSellDateSelect(selectedSellDate) {
      this.resetSellDatePicker = false;
      this.inputOnFocus('sold-bhaaratha-share-sell-date');
      this.sellDate = selectedSellDate;
    },

    // Incase of the error, change the border from red to original
    inputOnFocus(id) {
      document.getElementById(id).style.border = "";

      // Resetting error flags and error messages
      if (id === "sold-bhaaratha-share-stock-exchange") {
        this.errorFlagStockExchange = false;
        this.errorMessageStockExchange = "";

        document.getElementById("sold-bhaaratha-share-script-name").style = "0.1rem solid black";
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "sold-bhaaratha-share-bse-script-code") {
        this.errorFlagBseScriptCode = false;
        this.errorMessageBseScriptCode = "";

        document.getElementById("sold-bhaaratha-share-script-name").style = "0.1rem solid black";
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "sold-bhaaratha-share-nse-script-code") {
        this.errorFlagNseScriptCode = false;
        this.errorMessageNseScriptCode = "";
      } else if (id === "sold-bhaaratha-share-money-control-code") {
        this.errorFlagMoneycontrolCode = false;
        this.errorMessageMoneycontrolCode = "";

        document.getElementById("sold-bhaaratha-share-script-name").style = "0.1rem solid black";
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "sold-bhaaratha-share-sell-date") {
        this.errorFlagSellDate = false;
        this.errorMessageSellDate = "";
      } else if (id === "sold-bhaaratha-share-sell-quantity") {
        this.errorFlagSellQuantity = false;
        this.errorMessageSellQuantity = "";
      } else if (id === "sold-bhaaratha-share-sell-price") {
        this.errorFlagSellPrice = false;
        this.errorMessageSellPrice = "";
      } else if (id === "sold-bhaaratha-share-sell-brokerage") {
        this.errorFlagSellBrokerage = false;
        this.errorMessageSellBrokerage = "";
      } else if (id === "sold-bhaaratha-share-sell-stt") {
        this.errorFlagSellSTT = false;
        this.errorMessageSellSTT = "";
      } else if (id === "sold-bhaaratha-share-sell-expenditure") {
        this.errorFlagSellExpenditure = false;
        this.errorMessageSellExpenditure = "";
      }  else if (id === "sold-bhaaratha-share-sell-non-expenditure") {
        this.errorFlagSellNonExpenditure = false;
        this.errorMessageSellNonExpenditure = "";
      }
    },

    onBhaarathaSharesAddSharesClear() {

      document.getElementById("sold-bhaaratha-share-bse-script-code").style.border = "";
      document.getElementById("sold-bhaaratha-share-nse-script-code").style.border = "";
      document.getElementById("sold-bhaaratha-share-money-control-code").style.border = "";
      document.getElementById("sold-bhaaratha-share-script-name").style.border = "";
      document.getElementById("sold-bhaaratha-share-sell-quantity").style.border = "";
      document.getElementById("sold-bhaaratha-share-sell-price").style.border = "";
      document.getElementById("sold-bhaaratha-share-sell-brokerage").style.border = "";
      document.getElementById("sold-bhaaratha-share-sell-stt").style.border = "";
      document.getElementById("sold-bhaaratha-share-sell-expenditure").style.border = "";
      document.getElementById("sold-bhaaratha-share-sell-non-expenditure").style.border = "";

      this.resetStockExchangeRadio = true;
      this.stockExchange = "";
      this.getStockExchangeList();
      this.bseScriptCode = "";
      this.nseScriptCode = "";
      this.moneycontrolCode = "";
      this.shareName = "";
      this.resetSellDatePicker = true;
      this.sellDate = getDefaultDate("");
      this.sellQuantity = "";
      this.sellPrice = "";
      this.sellBrokerage = "";
      this.sellSTT = "";
      this.sellExpenditure = "";
      this.sellNonExpenditure = "";

      // Error field flags
      this.errorFlagStockExchange = false;
      this.errorFlagBseScriptCode = false;
      this.errorFlagNseScriptCode = false;
      this.errorFlagMoneycontrolCode = false;
      this.errorFlagShareName = false;
      this.errorFlagSellDate = false;
      this.errorFlagSellQuantity = false;
      this.errorFlagSellPrice = false;
      this.errorFlagSellBrokerage = false;
      this.errorFlagSellSTT = false;
      this.errorFlagSellExpenditure = false;
      this.errorFlagSellNonExpenditure = false;

      // Error field messages
      this.errorMessageStockExchange = "";
      this.errorMessageBseScriptCode = "";
      this.errorMessageNseScriptCode = "";
      this.errorMessageMoneycontrolCode = "";
      this.errorMessageShareName = "";
      this.errorMessageSellDate = "";
      this.errorMessageSellQuantity = ""
      this.errorMessageSellPrice = "";
      this.errorMessageSellBrokerage = "";
      this.errorMessageSellSTT = "";
      this.errorMessageSellExpenditure = "";
      this.errorMessageSellNonExpenditure = "";
    },

    onBhaarathaSharesAddSharesSubmit () {

      const formData = {
        stockExchange: this.stockExchange,
        bseScriptCode: this.bseScriptCode,
        nseScriptCode: this.nseScriptCode,
        moneycontrolCode: this.moneycontrolCode,
        scriptName: this.shareName,
        sellDate: this.sellDate,
        sellQuantity: this.sellQuantity,
        sellPrice: this.sellPrice,
        sellBrokerage: this.sellBrokerage,
        sellSTT: this.sellSTT,
        sellExpenditure: this.sellExpenditure,
        sellNonExpenditure: this.sellNonExpenditure
      };

      axios.post(services.bhaarathaSharesShares + "?type=sold", formData).then(response => {

        this.successMessage = response.data.message;
        this.showSuccess = true;

        this.resetStockExchangeRadio = true;
        this.stockExchange = "";
        this.getStockExchangeList();
        this.bseScriptCode = "";
        this.nseScriptCode = "";
        this.moneycontrolCode = "";
        this.shareName = "";
        this.resetSellDatePicker = true;
        this.sellDate = getDefaultDate("");
        this.sellQuantity = "";
        this.sellPrice = "";
        this.sellBrokerage = "";
        this.sellSTT = "";
        this.sellExpenditure = "";
        this.sellNonExpenditure = "";

      }).catch(error => {

        error.response.data.error.map(e => {
          if (e.uiField === "sold-bhaaratha-share-stock-exchange") {
            this.errorFlagStockExchange = true;
            this.errorMessageStockExchange = e.errorMessage;
          } else if (e.uiField === "sold-bhaaratha-share-bse-script-code") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagBseScriptCode = true;
            this.errorMessageBseScriptCode = e.errorMessage;
          } else if (e.uiField === "sold-bhaaratha-share-nse-script-code") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagNseScriptCode = true;
            this.errorMessageNseScriptCode = e.errorMessage;
          } else if (e.uiField === "sold-bhaaratha-share-money-control-code") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagMoneycontrolCode = true;
            this.errorMessageMoneycontrolCode = e.errorMessage;
          } else if (e.uiField === "sold-bhaaratha-share-script-name") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagShareName = true;
            this.errorMessageShareName = e.errorMessage;
          } else if (e.uiField === "sold-bhaaratha-share-sell-date") {
            this.errorFlagSellDate = true;
            this.errorMessageSellDate = e.errorMessage;
          } else if (e.uiField === "sold-bhaaratha-share-sell-quantity") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagSellQuantity = true;
            this.errorMessageSellQuantity = e.errorMessage;
          } else if (e.uiField === "sold-bhaaratha-share-sell-price") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagSellPrice = true;
            this.errorMessageSellPrice = e.errorMessage;
          } else if (e.uiField === "sold-bhaaratha-share-sell-brokerage") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagSellBrokerage = true;
            this.errorMessageSellBrokerage = e.errorMessage;
          } else if (e.uiField === "sold-bhaaratha-share-sell-stt") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagSellSTT = true;
            this.errorMessageSellSTT = e.errorMessage;
          } else if (e.uiField === "sold-bhaaratha-share-sell-expenditure") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagSellExpenditure = true;
            this.errorMessageSellExpenditure = e.errorMessage;
          } else if (e.uiField === "sold-bhaaratha-share-sell-non-expenditure") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagSellNonExpenditure = true;
            this.errorMessageSellNonExpenditure = e.errorMessage;
          } else {
            this.showError = true;
            this.errorMessage = e.errorMessage;
          }
        });
      });
    },

    closeError() {
      this.showError = false;
      this.errorMessage = "";
    },

    closeSuccess() {
      this.showSuccess = false;
      this.successMessage = "";
      this.$router.push("/users/BHA/EQ/sold");
    }
  }
}
</script>
