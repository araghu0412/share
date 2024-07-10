<template>
  <div class="bhaaratha-shares-add-bought">
    <div class="services-add-scripts-bought">
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
                id="bought-bhaaratha-share-stock-exchange"
                @change="inputOnFocus('bought-bhaaratha-share-stock-exchange')"
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
            <div class="key">Split/Bonus</div>
            <div class="value">
              <MoneyManagerCheckBox
                :resetCheckBox="resetBonusSplitCheckBox"
                :checkBoxList="bonusSplitCheckBoxList"
                @onCheckBoxSelect="onBonusSplitSelect($event)"
              ></MoneyManagerCheckBox>
            </div>
          </div>
          <div v-if="isBonusCheckBoxSelected">
            <div class="services-add-scipts-form">
              <div class="key">Bonus Ratio</div>
              <div class="value">
                <input
                  type="text"
                  id="bought-bhaaratha-share-bonus-ratio"
                  placeholder="Bonus Ratio"
                  @focus="inputOnFocus('bought-bhaaratha-share-bonus-ratio')"
                  v-model="bonusRatio"
                />
                <InputFieldError
                  v-if="errorFlagBonusRatio"
                  :errorMessage="errorMessageBonusRatio"
                ></InputFieldError>
              </div>
            </div>
            <div class="services-add-scipts-form">
              <div class="key">Bonus Date</div>
              <div class="value">
                <span id="bought-bhaaratha-share-bonus-date"></span>
                <MoneyManagerDatePicker
                  :resetDatePicker="resetBonusDatePicker"
                  @onSelectedFullDate="onBonusDateSelect($event)"
                ></MoneyManagerDatePicker>
                <InputFieldError
                  v-if="errorFlagBonusDate"
                  :errorMessage="errorMessageBonusDate"
                ></InputFieldError>
              </div>
            </div>
          </div>
          <div v-if="isSplitCheckBoxSelected">
            <div class="services-add-scipts-form">
              <div class="key">Split Ratio</div>
              <div class="value">
                <input
                  type="text"
                  id="bought-bhaaratha-share-split-ratio"
                  placeholder="Split Ratio"
                  @focus="inputOnFocus('bought-bhaaratha-share-split-ratio')"
                  v-model="splitRatio"
                />
                <InputFieldError
                  v-if="errorFlagSplitRatio"
                  :errorMessage="errorMessageSplitRatio"
                ></InputFieldError>
              </div>
            </div>
            <div class="services-add-scipts-form">
              <div class="key">Split Date</div>
              <div class="value">
                <span id="bought-bhaaratha-share-split-date"></span>
                <MoneyManagerDatePicker
                  :resetDatePicker="resetSplitDatePicker"
                  @onSelectedFullDate="onSplitDateSelect($event)"
                ></MoneyManagerDatePicker>
                <InputFieldError
                  v-if="errorFlagSplitDate"
                  :errorMessage="errorMessageSplitDate"
                ></InputFieldError>
              </div>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">BSE Script Code</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-bse-script-code"
                placeholder="BSE Script Code"
                @focus="inputOnFocus('bought-bhaaratha-share-bse-script-code')"
                v-model="bseScriptCode"
              />
              <InputFieldError
                v-if="errorFlagBseScriptCode"
                :errorMessage="errorMessageBseScriptCode"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Yahoo Code (BSE)</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-yahoo-bse-script-code"
                placeholder="Yahoo BSE Script Code"
                @focus="inputOnFocus('bought-bhaaratha-share-yahoo-bse-script-code')"
                v-model="yahooBseScriptCode"
              />
              <InputFieldError
                v-if="errorFlagYahooBseScriptCode"
                :errorMessage="errorMessageYahooBseScriptCode"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">NSE Script Code</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-nse-script-code"
                placeholder="NSE Script Code"
                @focus="inputOnFocus('bought-bhaaratha-share-nse-script-code')"
                v-model="nseScriptCode"
              />
              <InputFieldError
                v-if="errorFlagNseScriptCode"
                :errorMessage="errorMessageNseScriptCode"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Yahoo Code (NSE)</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-yahoo-nse-script-code"
                placeholder="Yahoo NSE Script Code"
                @focus="inputOnFocus('bought-bhaaratha-share-yahoo-nse-script-code')"
                v-model="yahooNseScriptCode"
              />
              <InputFieldError
                v-if="errorFlagYahooNseScriptCode"
                :errorMessage="errorMessageYahooNseScriptCode"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">ISIN Code</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-isin-code"
                placeholder="ISIN Code"
                @focus="inputOnFocus('bought-bhaaratha-share-isin-code')"
                v-model="isinCode"
              />
              <InputFieldError
                v-if="errorFlagIsinCode"
                :errorMessage="errorMessageIsinCode"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Moneycontrol Code</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-money-control-code"
                placeholder="Moneycontrol Code"
                @focus="inputOnFocus('bought-bhaaratha-share-money-control-code')"
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
              <div id="bought-bhaaratha-share-script-name" class="services-add-scipts-script-name">
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
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Category</div>
            <span id="bought-bhaaratha-share-category"></span>
            <div class="value">
              <MoneyManagerDropdown
                :resetDropdown="resetCategoryDropdown"
                :dropdownList="categoryList"
                @onDropdownSelect="onCategorySelect($event)"
              ></MoneyManagerDropdown>
              <div>
                <InputFieldError
                  v-if="errorFlagCategory"
                  :errorMessage="errorMessageCategory"
                ></InputFieldError>
              </div>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Sector</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-sector"
                placeholder="Sector"
                @focus="inputOnFocus('bought-bhaaratha-share-sector')"
                v-model="sector"
              />
              <InputFieldError
                v-if="errorFlagSector"
                :errorMessage="errorMessageSector"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Industry</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-industry"
                placeholder="Industry"
                @focus="inputOnFocus('bought-bhaaratha-share-industry')"
                v-model="industry"
              />
              <InputFieldError
                v-if="errorFlagIndustry"
                :errorMessage="errorMessageIndustry"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Short Term Investment</div>
            <div class="value">
              <span>
                <MoneyManagerRadio
                  :resetRadio="shortTermInvestmentResetRadio"
                  :radioList="shortTermInvestmentRadioList"
                  :radioDefault="shortTermInvestmentDefaultRadio"
                  @onRadioSelect="onShortTermInvestmentSelect($event)"
                ></MoneyManagerRadio>
              </span>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Purchase Date</div>
            <div class="value">
              <span id="bought-bhaaratha-share-purchase-date"></span>
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
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Purchase Quantity</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-purchase-quantity"
                placeholder="Purchase Quantity"
                @focus="inputOnFocus('bought-bhaaratha-share-purchase-quantity')"
                v-model="purchaseQuantity"
              />
              <InputFieldError
                v-if="errorFlagPurchaseQuantity"
                :errorMessage="errorMessagePurchaseQuantity"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Purchase Price</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-purchase-price"
                placeholder="Purchase Price"
                @focus="inputOnFocus('bought-bhaaratha-share-purchase-price')"
                v-model="purchasePrice"
              />
              <InputFieldError
                v-if="errorFlagPurchasePrice"
                :errorMessage="errorMessagePurchasePrice"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Purchase Brokerage</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-purchase-brokerage"
                placeholder="Purchase Brokerage"
                @focus="inputOnFocus('bought-bhaaratha-share-purchase-brokerage')"
                v-model="purchaseBrokerage"
              />
              <InputFieldError
                v-if="errorFlagPurchaseBrokerage"
                :errorMessage="errorMessagePurchaseBrokerage"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Purchase STT</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-purchase-stt"
                placeholder="Purchase STT"
                @focus="inputOnFocus('bought-bhaaratha-share-purchase-stt')"
                v-model="purchaseSTT"
              />
              <InputFieldError
                v-if="errorFlagPurchaseSTT"
                :errorMessage="errorMessagePurchaseSTT"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Purchase Expenditure</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-purchase-expenditure"
                placeholder="Purchase Expenditure"
                @focus="inputOnFocus('bought-bhaaratha-share-purchase-expenditure')"
                v-model="purchaseExpenditure"
              />
              <InputFieldError
                v-if="errorFlagPurchaseExpenditure"
                :errorMessage="errorMessagePurchaseExpenditure"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Purchase Non Expenditure</div>
            <div class="value">
              <input
                type="text"
                id="bought-bhaaratha-share-purchase-non-expenditure"
                placeholder="Purchase Non Expenditure"
                @focus="inputOnFocus('bought-bhaaratha-share-purchase-non-expenditure')"
                v-model="purchaseNonExpenditure"
              />
              <InputFieldError
                v-if="errorFlagPurchaseNonExpenditure"
                :errorMessage="errorMessagePurchaseNonExpenditure"
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
import MoneyManagerCheckBox from "@/components/common/MoneyManagerCheckBox.vue";
import InputFieldError from "@/components/common/InputFieldError";
import MoneyManagerDatePicker from "@/components/common/MoneyManagerDatePicker.vue";
import MoneyManagerLoading from "@/components/common/MoneyManagerLoading.vue";
import MoneyManagerDropdown from "@/components/common/MoneyManagerDropdown.vue";
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
    MoneyManagerCheckBox,
    MoneyManagerDatePicker,
    InputFieldError,
    MoneyManagerLoading,
    MoneyManagerDropdown,
    ErrorModal,
    SuccessModal
  },

  data() {
    return {
      stockExchangeRadioList: [],
      resetStockExchangeRadio: false,
      stockExchange: "",
      bonusSplitCheckBoxList: [],
      resetBonusSplitCheckBox: false,
      isBonusCheckBoxSelected: false,
      isSplitCheckBoxSelected: false,
      resetBonusDatePicker: false,
      resetSplitDatePicker: false,
      bonusDate: "",
      bonusRatio: "",
      splitDate: "",
      splitRatio: "",
      bseScriptCode: "",
      yahooBseScriptCode: "",
      nseScriptCode: "",
      yahooNseScriptCode: "",
      moneycontrolCode: "",
      isinCode: "",
      shareName: "",
      shareNameLoading: false,
      categoryList: [],
      category: "",
      resetCategoryDropdown: false,
      sector: "",
      industry: "",
      shortTermInvestmentRadioList: [],
      shortTermInvestmentResetRadio: false,
      shortTermInvestmentDefaultRadio: {},
      shortTermInvestment: "No",
      purchaseDate: "",
      resetPurchaseDatePicker: false,
      purchaseQuantity: "",
      purchasePrice: "",
      purchaseBrokerage: "",
      purchaseSTT: "",
      purchaseExpenditure: "",
      purchaseNonExpenditure: "",

      // Error field flags
      errorFlagStockExchange: false,
      errorFlagBonusDate: false,
      errorFlagBonusRatio: false,
      errorFlagSplitDate: false,
      errorFlagSplitRatio: false,
      errorFlagBseScriptCode: false,
      errorFlagYahooBseScriptCode: false,
      errorFlagNseScriptCode: false,
      errorFlagYahooNseScriptCode: false,
      errorFlagMoneycontrolCode: false,
      errorFlagIsinCode: false,
      errorFlagShareName: false,
      errorFlagCategory: false,
      errorFlagSector: false,
      errorFlagIndustry: false,
      errorFlagPurchaseDate: false,
      errorFlagPurchaseQuantity: false,
      errorFlagPurchasePrice: false,
      errorFlagPurchaseBrokerage: false,
      errorFlagPurchaseSTT: false,
      errorFlagPurchaseExpenditure: false,
      errorFlagPurchaseNonExpenditure: false,

      // Error field messages
      errorMessageStockExchange: "",
      errorMessageBonusDate: "",
      errorMessageBonusRatio: "",
      errorMessageSplitDate: "",
      errorMessageSplitRatio: "",
      errorMessageBseScriptCode: "",
      errorMessageYahooBseScriptCode: "",
      errorMessageNseScriptCode: "",
      errorMessageYahooNseScriptCode: "",
      errorMessageMoneycontrolCode: "",
      errorMessageIsinCode: "",
      errorMessageShareName: "",
      errorMessageCategory: "",
      errorMessageSector: "",
      errorMessageIndustry: "",
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
    this.getStockExchangeList();
    this.getBonusSplitCheckBoxList();
    this.getCategoryList();
    this.getShortTermInvestmentRadioList();
    this.getShortTermInvestmentDefaultRadio();
  },

  methods: {
    onBackButtonClicked() {
      this.$router.push("/users/BHA/EQ/bought");
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

    getBonusSplitCheckBoxList() {
      this.bonusSplitCheckBoxList = [
        {
          id: "bonus",
          value: "Bonus",
          isDisabled: this.isSplitCheckBoxSelected
        },
        {
          id: "split",
          value: "Split",
          isDisabled: this.isBonusCheckBoxSelected
        }
      ];
    },

    getCategoryList() {
      this.categoryList = [
        {
          id: "L",
          value: "Large Cap"
        },
        {
          id: "M",
          value: "Mid Cap"
        },
        {
          id: "S",
          value: "Small Cap"
        }
      ];
    },

    getShortTermInvestmentRadioList() {
      this.shortTermInvestmentRadioList = [
        {
          id: "Y",
          value: "Yes"
        },
        {
          id: "N",
          value: "No"
        }
      ];
    },

    getShortTermInvestmentDefaultRadio() {
      this.shortTermInvestmentDefaultRadio = this.shortTermInvestmentRadioList[1];
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

    onBonusSplitSelect(selectedBonusSplitList) {
      this.resetBonusSplitCheckBox = false;

      if (selectedBonusSplitList.length === 0) {
        this.isBonusCheckBoxSelected = false;
        this.bonusRatio = "";
        this.bonusDate = "";
        this.errorFlagBonusDate = false,
        this.resetBonusDatePicker = true,
        this.errorFlagBonusRatio = false,
        this.errorMessageBonusDate = "";
        this.errorMessageBonusRatio = "";

        this.isSplitCheckBoxSelected = false;
        this.splitRatio = "";
        this.splitDate = "";
        this.errorFlagSplitDate = false,
        this.resetSplitDatePicker = true,
        this.errorFlagSplitRatio = false,
        this.errorMessageSplitDate = "";
        this.errorMessageSplitRatio = "";

        this.resetPurchaseDatePicker = false;
      } else {
        this.clearNonBonusSplitData()
      }

      for (let checkBoxSelected of selectedBonusSplitList) {
        if (checkBoxSelected.id === "bonus") {
          this.isBonusCheckBoxSelected = true;
        } else if (checkBoxSelected.id === "split") {
          this.isSplitCheckBoxSelected = true;
        }
      }

      for (let bonusSplitCheckBox of this.bonusSplitCheckBoxList) {
        if (bonusSplitCheckBox.id === "bonus") {
          bonusSplitCheckBox.isDisabled = this.isSplitCheckBoxSelected;
        } else if (bonusSplitCheckBox.id === "split") {
          bonusSplitCheckBox.isDisabled = this.isBonusCheckBoxSelected;
        }
      }
    },

    clearNonBonusSplitData() {
      this.yahooBseScriptCode = "";
      this.yahooNseScriptCode = "";
      this.isinCode = "";
      this.category = "";
      this.resetCategoryDropdown = true;
      this.sector = "";
      this.industry = "";
      this.shortTermInvestmentResetRadio = true;
      this.shortTermInvestment = "No";
      this.purchaseDate = "";
      this.resetPurchaseDatePicker = true;
      this.purchaseQuantity = "";
      this.purchasePrice = "";
      this.purchaseBrokerage = "";
      this.purchaseSTT = "";
      this.purchaseExpenditure = "";
      this.purchaseNonExpenditure = "";

      // Error flag fields
      this.errorFlagYahooBseScriptCode = false;
      this.errorFlagYahooNseScriptCode = false;
      this.errorFlagIsinCode = false;
      this.errorFlagCategory = false;
      this.errorFlagSector = false;
      this.errorFlagIndustry = false;
      this.errorFlagPurchaseDate = false;
      this.errorFlagPurchaseQuantity = false;
      this.errorFlagPurchasePrice = false;
      this.errorFlagPurchaseBrokerage = false;
      this.errorFlagPurchaseSTT = false;
      this.errorFlagPurchaseExpenditure = false;
      this.errorFlagPurchaseNonExpenditure = false;

      // Error field messages
      this.errorMessageYahooBseScriptCode = "";
      this.errorMessageYahooNseScriptCode = "";
      this.errorMessageIsinCode = "";
      this.errorMessageCategory = "";
      this.errorMessageSector = "";
      this.errorMessageIndustry = "";
      this.errorMessagePurchaseDate = "";
      this.errorMessagePurchaseQuantity = "";
      this.errorMessagePurchasePrice = "";
      this.errorMessagePurchaseBrokerage = "";
      this.errorMessagePurchaseSTT = "";
      this.errorMessagePurchaseExpenditure = "";
      this.errorMessagePurchaseNonExpenditure = "";
    },

    onShortTermInvestmentSelect(selectedShortTermInvestment) {
      this.shortTermInvestmentResetRadio = false;
      this.shortTermInvestment = selectedShortTermInvestment.value;
    },

    onBonusDateSelect(selectedDate) {
      this.inputOnFocus('bought-bhaaratha-share-bonus-date');
      this.bonusDate = selectedDate;
    },

    onSplitDateSelect(selectedDate) {
      this.inputOnFocus('bought-bhaaratha-share-split-date');
      this.splitDate = selectedDate;
    },

    onCategorySelect(selectedCategory) {
      this.resetCategoryDropdown = false;
      this.inputOnFocus('bought-bhaaratha-share-category');
      this.category = selectedCategory.value;
    },

    onPurchaseDateSelect(selectedPurchaseDate) {
      this.resetPurchaseDatePicker = false;
      this.inputOnFocus('bought-bhaaratha-share-purchase-date');
      this.purchaseDate = selectedPurchaseDate;
    },

    onBhaarathaSharesAddSharesClear() {

      // In case of error, change input border color
      if (this.isBonusCheckBoxSelected) {
        document.getElementById("bought-bhaaratha-share-bonus-ratio").style.border = "";
      }
      if (this.isSplitCheckBoxSelected) {
        document.getElementById("bought-bhaaratha-share-split-ratio").style.border = "";
      }
      if (!this.isBonusCheckBoxSelected && !this.isSplitCheckBoxSelected) {
        document.getElementById("bought-bhaaratha-share-yahoo-bse-script-code").style.border = "";
        document.getElementById("bought-bhaaratha-share-yahoo-nse-script-code").style.border = "";
        document.getElementById("bought-bhaaratha-share-isin-code").style.border = "";
        document.getElementById("bought-bhaaratha-share-category").style.border = "";
        document.getElementById("bought-bhaaratha-share-sector").style.border = "";
        document.getElementById("bought-bhaaratha-share-industry").style.border = "";
        document.getElementById("bought-bhaaratha-share-purchase-quantity").style.border = "";
        document.getElementById("bought-bhaaratha-share-purchase-price").style.border = "";
        document.getElementById("bought-bhaaratha-share-purchase-brokerage").style.border = "";
        document.getElementById("bought-bhaaratha-share-purchase-stt").style.border = "";
        document.getElementById("bought-bhaaratha-share-purchase-expenditure").style.border = "";
        document.getElementById("bought-bhaaratha-share-purchase-non-expenditure").style.border = "";
      }
      document.getElementById("bought-bhaaratha-share-bse-script-code").style.border = "";
      document.getElementById("bought-bhaaratha-share-nse-script-code").style.border = "";
      document.getElementById("bought-bhaaratha-share-money-control-code").style.border = "";
      document.getElementById("bought-bhaaratha-share-script-name").style.border = "0.1rem solid black";

      if (this.isBonusCheckBoxSelected || this.isSplitCheckBoxSelected) {
        this.resetPurchaseDatePicker = false;
      } else if (!this.isBonusCheckBoxSelected  || !this.isSplitCheckBoxSelected) {
        this.purchaseDate = getDefaultDate("");
        this.resetPurchaseDatePicker = true;
      }

      this.resetStockExchangeRadio = true;
      this.stockExchange = "";
      this.resetBonusSplitCheckBox = true;
      this.isBonusCheckBoxSelected = false;
      this.bonusRatio = "";
      this.bonusDate = "";
      this.isSplitCheckBoxSelected = false;
      this.splitRatio = "";
      this.splitDate = "";
      this.getBonusSplitCheckBoxList();
      this.bseScriptCode = "";
      this.yahooBseScriptCode = "";
      this.nseScriptCode = "";
      this.yahooNseScriptCode = "";
      this.moneycontrolCode = "";
      this.isinCode = "";
      this.shareName = "";
      this.category = "";
      this.resetCategoryDropdown = true;
      this.sector = "";
      this.industry = "";
      this.shortTermInvestmentResetRadio = true;
      this.shortTermInvestment = "No";
      this.purchaseQuantity = "";
      this.purchasePrice = "";
      this.purchaseBrokerage = "";
      this.purchaseSTT = "";
      this.purchaseExpenditure = "";
      this.purchaseNonExpenditure = "";

      // Error field flags
      this.errorFlagStockExchange = false;
      this.errorFlagBonusRatio = false;
      this.errorFlagBonusDate = false;
      this.errorFlagSplitRatio = false;
      this.errorFlagSplitDate = false;
      this.errorFlagBseScriptCode = false;
      this.errorFlagYahooBseScriptCode = false;
      this.errorFlagNseScriptCode = false;
      this.errorFlagYahooNseScriptCode = false;
      this.errorFlagMoneycontrolCode = false;
      this.errorFlagIsinCode = false;
      this.errorFlagShareName = false;
      this.errorFlagCategory = false;
      this.errorFlagSector = false;
      this.errorFlagIndustry = false;
      this.errorFlagPurchaseDate = false;
      this.errorFlagPurchaseQuantity = false;
      this.errorFlagPurchasePrice = false;
      this.errorFlagPurchaseBrokerage = false;
      this.errorFlagPurchaseSTT = false;
      this.errorFlagPurchaseExpenditure = false;
      this.errorFlagPurchaseNonExpenditure = false;

      // Error field messages
      this.errorMessageStockExchange = "";
      this.errorMessageBonusRatio = "";
      this.errorMessageBonusRatio = "";
      this.errorMessageSplitRatio = "";
      this.errorMessageSplitRatio = "";
      this.errorMessageBseScriptCode = "";
      this.errorMessageYahooBseScriptCode = "";
      this.errorMessageNseScriptCode = "";
      this.errorMessageYahooNseScriptCode = "";
      this.errorMessageMoneycontrolCode = "";
      this.errorMessageIsinCode = "";
      this.errorMessageShareName = "";
      this.errorMessageCategory = "";
      this.errorMessageSector = "";
      this.errorMessageIndustry = "";
      this.errorMessagePurchaseDate = "";
      this.errorMessagePurchaseQuantity = "";
      this.errorMessagePurchasePrice = "";
      this.errorMessagePurchaseBrokerage = "";
      this.errorMessagePurchaseSTT = "";
      this.errorMessagePurchaseExpenditure = "";
      this.errorMessagePurchaseNonExpenditure = "";
    },

    onBhaarathaSharesAddSharesSubmit() {
      const formData = {
        stockExchange: this.stockExchange,
        sharesBonusSelected: this.isBonusCheckBoxSelected,
        sharesBonusRatio: this.bonusRatio,
        sharesBonusDate: this.bonusDate,
        sharesSplitSelected: this.isSplitCheckBoxSelected,
        sharesSplitRatio: this.splitRatio,
        sharesSplitDate: this.splitDate,
        bseScriptCode: this.bseScriptCode,
        yahooBseScriptCode: this.yahooBseScriptCode,
        nseScriptCode: this.nseScriptCode,
        yahooNseScriptCode: this.yahooNseScriptCode,
        moneycontrolCode: this.moneycontrolCode,
        isinCode: this.isinCode,
        scriptName: this.shareName,
        category: this.category,
        sector: this.sector,
        industry: this.industry,
        shortTermInvestment: this.shortTermInvestment === "Yes",
        purchaseDate: this.purchaseDate,
        purchaseQuantity: this.purchaseQuantity,
        purchasePrice: this.purchasePrice,
        purchaseBrokerage: this.purchaseBrokerage,
        purchaseSTT: this.purchaseSTT,
        purchaseExpenditure: this.purchaseExpenditure,
        purchaseNonExpenditure: this.purchaseNonExpenditure
      };

      axios.post(services.bhaarathaSharesShares + "?type=bought", formData).then(response => {

        this.successMessage = response.data.message;
        this.showSuccess = true;

        if (this.isBonusCheckBoxSelected || this.isSplitCheckBoxSelected) {
          this.resetPurchaseDatePicker = false;
        } else if (!this.isBonusCheckBoxSelected  || !this.isSplitCheckBoxSelected) {
          this.purchaseDate = getDefaultDate("");
          this.resetPurchaseDatePicker = true;
        }
        this.stockExchange = "";
        this.resetStockExchangeRadio = true;
        this.shareName = "";
        this.category = "";
        this.resetCategoryDropdown = true;
        this.isBonusCheckBoxSelected = false;
        this.bonusRatio = "";
        this.bonusDate = "";
        this.isSplitCheckBoxSelected = false;
        this.splitRatio = "";
        this.splitDate = "";
        this.resetBonusSplitCheckBox = true;
        this.getBonusSplitCheckBoxList();
        this.bseScriptCode = "";
        this.yahooBseScriptCode = "";
        this.nseScriptCode = "";
        this.yahooNseScriptCode = "";
        this.moneycontrolCode = "";
        this.isinCode = "";
        this.category = "";
        this.sector = "";
        this.industry = "";
        this.purchaseQuantity = "";
        this.purchasePrice = "";
        this.purchaseBrokerage = "";
        this.purchaseSTT = "";
        this.purchaseExpenditure = "";
        this.purchaseNonExpenditure = "";

      }).catch(error => {

        // For displaying errors
        error.response.data.error.map(e => {
          if (e.uiField === "bought-bhaaratha-share-stock-exchange") {
            this.errorFlagStockExchange = true;
            this.errorMessageStockExchange = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-bonus-ratio") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagBonusRatio = true;
            this.errorMessageBonusRatio = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-bonus-date") {
            this.errorFlagBonusDate = true;
            this.errorMessageBonusDate = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-split-ratio") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagSplitRatio = true;
            this.errorMessageSplitRatio = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-split-date") {
            this.errorFlagSplitDate = true;
            this.errorMessageSplitDate = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-bse-script-code") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagBseScriptCode = true;
            this.errorMessageBseScriptCode = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-yahoo-bse-script-code") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagYahooBseScriptCode = true;
            this.errorMessageYahooBseScriptCode = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-nse-script-code") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagNseScriptCode = true;
            this.errorMessageNseScriptCode = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-yahoo-nse-script-code") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagYahooNseScriptCode = true;
            this.errorMessageYahooNseScriptCode = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-money-control-code") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagMoneycontrolCode = true;
            this.errorMessageMoneycontrolCode = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-isin-code") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagIsinCode = true;
            this.errorMessageIsinCode = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-script-name") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagShareName = true;
            this.errorMessageShareName = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-category") {
            this.errorFlagCategory = true;
            this.errorMessageCategory = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-sector") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagSector = true;
            this.errorMessageSector = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-industry") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagIndustry = true;
            this.errorMessageIndustry = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-purchase-date") {
            this.errorFlagPurchaseDate = true;
            this.errorMessagePurchaseDate = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-purchase-quantity") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagPurchaseQuantity = true;
            this.errorMessagePurchaseQuantity = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-purchase-price") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagPurchasePrice = true;
            this.errorMessagePurchasePrice = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-purchase-brokerage") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagPurchaseBrokerage = true;
            this.errorMessagePurchaseBrokerage = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-purchase-stt") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagPurchaseSTT = true;
            this.errorMessagePurchaseSTT = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-purchase-expenditure") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagPurchaseExpenditure = true;
            this.errorMessagePurchaseExpenditure = e.errorMessage;
          } else if (e.uiField === "bought-bhaaratha-share-purchase-non-expenditure") {
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
      if (id === "bought-bhaaratha-share-stock-exchange") {
        this.errorFlagStockExchange = false;
        this.errorMessageStockExchange = "";

        document.getElementById("bought-bhaaratha-share-script-name").style = "0.1rem solid black";
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "bought-bhaaratha-share-bonus-ratio") {
        this.errorFlagBonusRatio = false;
        this.errorMessageBonusRatio = "";
      } else if (id === "bought-bhaaratha-share-bonus-date") {
        this.errorFlagBonusDate = false;
        this.errorMessageBonusDate = "";
      } else if (id === "bought-bhaaratha-share-split-ratio") {
        this.errorFlagSplitRatio = false;
        this.errorMessageSplitRatio = "";
      } else if (id === "bought-bhaaratha-share-split-date") {
        this.errorFlagSplitDate = false;
        this.errorMessageSplitDate = "";
      } else if (id === "bought-bhaaratha-share-bse-script-code") {
        this.errorFlagBseScriptCode = false;
        this.errorMessageBseScriptCode = "";

        document.getElementById("bought-bhaaratha-share-script-name").style = "0.1rem solid black";
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "bought-bhaaratha-share-yahoo-bse-script-code") {
        this.errorFlagYahooBseScriptCode = false;
        this.errorMessageYahooBseScriptCode = "";
      } else if (id === "bought-bhaaratha-share-nse-script-code") {
        this.errorFlagNseScriptCode = false;
        this.errorMessageNseScriptCode = "";
      } else if (id === "bought-bhaaratha-share-yahoo-nse-script-code") {
        this.errorFlagYahooNseScriptCode = false;
        this.errorMessageYahooNseScriptCode = "";
      } else if (id === "bought-bhaaratha-share-isin-code") {
        this.errorFlagIsinCode = false;
        this.errorMessageIsinCode = "";
      } else if (id === "bought-bhaaratha-share-money-control-code") {
        this.errorFlagMoneycontrolCode = false;
        this.errorMessageMoneycontrolCode = "";

        document.getElementById("bought-bhaaratha-share-script-name").style = "0.1rem solid black";
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "bought-bhaaratha-share-category") {
        this.errorFlagCategory = false;
        this.errorMessageCategory = "";
      } else if (id === "bought-bhaaratha-share-sector") {
        this.errorFlagSector = false;
        this.errorMessageSector = "";
      } else if (id === "bought-bhaaratha-share-industry") {
        this.errorFlagIndustry = false;
        this.errorMessageIndustry = "";
      } else if (id === "bought-bhaaratha-share-purchase-date") {
        this.errorFlagPurchaseDate = false;
        this.errorMessagePurchaseDate = "";
      } else if (id === "bought-bhaaratha-share-purchase-quantity") {
        this.errorFlagPurchaseQuantity = false;
        this.errorMessagePurchaseQuantity = "";
      }
      else if (id === "bought-bhaaratha-share-purchase-price") {
        this.errorFlagPurchasePrice = false;
        this.errorMessagePurchasePrice = "";
      }
      else if (id === "bought-bhaaratha-share-purchase-brokerage") {
        this.errorFlagPurchaseBrokerage = false;
        this.errorMessagePurchaseBrokerage = "";
      }
      else if (id === "bought-bhaaratha-share-purchase-stt") {
        this.errorFlagPurchaseSTT = false;
        this.errorMessagePurchaseSTT = "";
      }
      else if (id === "bought-bhaaratha-share-purchase-expenditure") {
        this.errorFlagPurchaseExpenditure = false;
        this.errorMessagePurchaseExpenditure = "";
      }
      else if (id === "bought-bhaaratha-share-purchase-non-expenditure") {
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
      this.$router.push("/users/BHA/EQ/bought");
    }
  }
}
</script>
