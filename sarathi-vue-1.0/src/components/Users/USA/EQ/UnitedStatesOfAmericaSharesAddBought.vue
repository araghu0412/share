<template>
  <div class="united-states-of-america-shares-add-bought">
    <div class="services-add-scripts-bought">
      <UnitedStatesOfAmericaSharesAddSharesHeader
        @onBackButtonClicked="onBackButtonClicked()"
      ></UnitedStatesOfAmericaSharesAddSharesHeader>
      <div class="services-add-scipts-box">
        <h3 class="services-add-scipts-heading">
          Fill the details below
        </h3>
        <div class="services-add-scipts-form-container">
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
                  id="bought-united-states-of-america-share-bonus-ratio"
                  placeholder="Bonus Ratio"
                  @focus="inputOnFocus('bought-united-states-of-america-share-bonus-ratio')"
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
                <span id="bought-united-states-of-america-share-bonus-date"></span>
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
                  id="bought-united-states-of-america-share-split-ratio"
                  placeholder="Split Ratio"
                  @focus="inputOnFocus('bought-united-states-of-america-share-split-ratio')"
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
                <span id="bought-united-states-of-america-share-split-date"></span>
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
            <div class="key">Yahoo Code</div>
            <div class="value">
              <input
                type="text"
                id="bought-united-states-of-america-share-yahoo-code"
                placeholder="Yahoo Code"
                @focus="inputOnFocus('bought-united-states-of-america-share-yahoo-code')"
                v-model="yahooCode"
                @change="getShareName()"
              />
              <InputFieldError
                v-if="errorFlagYahooCode"
                :errorMessage="errorMessageYahooCode"
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
                id="bought-united-states-of-america-share-isin-code"
                placeholder="ISIN Code"
                @focus="inputOnFocus('bought-united-states-of-america-share-isin-code')"
                v-model="isinCode"
              />
              <InputFieldError
                v-if="errorFlagIsinCode"
                :errorMessage="errorMessageIsinCode"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">CUSIP</div>
            <div class="value">
              <input
                type="text"
                id="bought-united-states-of-america-share-cusip"
                placeholder="CUSIP"
                @focus="inputOnFocus('bought-united-states-of-america-share-cusip')"
                v-model="cusip"
              />
              <InputFieldError
                v-if="errorFlagCusip"
                :errorMessage="errorMessageCusip"
              ></InputFieldError>
            </div>
          </div>
          <div class="services-add-scipts-form">
            <div class="key">Share Name</div>
            <div class="value">
              <div id="bought-united-states-of-america-share-script-name" class="services-add-scipts-script-name">
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
            <span id="bought-united-states-of-america-share-category"></span>
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
                id="bought-united-states-of-america-share-sector"
                placeholder="Sector"
                @focus="inputOnFocus('bought-united-states-of-america-share-sector')"
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
                id="bought-united-states-of-america-share-industry"
                placeholder="Industry"
                @focus="inputOnFocus('bought-united-states-of-america-share-industry')"
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
              <span id="bought-united-states-of-america-share-purchase-date"></span>
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
                id="bought-united-states-of-america-share-purchase-quantity"
                placeholder="Purchase Quantity"
                @focus="inputOnFocus('bought-united-states-of-america-share-purchase-quantity')"
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
                id="bought-united-states-of-america-share-purchase-price"
                placeholder="Purchase Price"
                @focus="inputOnFocus('bought-united-states-of-america-share-purchase-price')"
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
            <div class="key">Purchase Commission</div>
            <div class="value">
              <input
                type="text"
                id="bought-united-states-of-america-share-purchase-commission"
                placeholder="Purchase Commission"
                @focus="inputOnFocus('bought-united-states-of-america-share-purchase-commission')"
                v-model="purchaseCommission"
              />
              <InputFieldError
                v-if="errorFlagPurchaseCommission"
                :errorMessage="errorMessagePurchaseCommission"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Purchase Transaction Fees</div>
            <div class="value">
              <input
                type="text"
                id="bought-united-states-of-america-share-purchase-transaction-fees"
                placeholder="Purchase Transaction Fees"
                @focus="inputOnFocus('bought-united-states-of-america-share-purchase-transaction-fees')"
                v-model="purchaseTransactionFees"
              />
              <InputFieldError
                v-if="errorFlagPurchaseTransactionFees"
                :errorMessage="errorMessagePurchaseTransactionFees"
              ></InputFieldError>
            </div>
          </div>
          <div
            v-if="!isBonusCheckBoxSelected && !isSplitCheckBoxSelected"
            class="services-add-scipts-form"
          >
            <div class="key">Purchase Other Fees</div>
            <div class="value">
              <input
                type="text"
                id="bought-united-states-of-america-share-purchase-other-fees"
                placeholder="Purchase Other Fees"
                @focus="inputOnFocus('bought-united-states-of-america-share-purchase-other-fees')"
                v-model="purchaseOtherFees"
              />
              <InputFieldError
                v-if="errorFlagPurchaseOtherFees"
                :errorMessage="errorMessagePurchaseOtherFees"
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
    UnitedStatesOfAmericaSharesAddSharesHeader,
    MoneyManagerRadio,
    MoneyManagerButton,
    MoneyManagerCheckBox,
    InputFieldError,
    MoneyManagerDatePicker,
    MoneyManagerLoading,
    MoneyManagerDropdown,
    ErrorModal,
    SuccessModal
  },

  data() {
    return {
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
      yahooCode: "",
      isinCode: "",
      cusip: "",
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
      purchaseCommission: "",
      purchaseTransactionFees: "",
      purchaseOtherFees: "",

      // Error field flags
      errorFlagBonusDate: false,
      errorFlagBonusRatio: false,
      errorFlagSplitDate: false,
      errorFlagSplitRatio: false,
      errorFlagYahooCode: false,
      errorFlagIsinCode: false,
      errorFlagCusip: false,
      errorFlagShareName: false,
      errorFlagCategory: false,
      errorFlagSector: false,
      errorFlagIndustry: false,
      errorFlagPurchaseDate: false,
      errorFlagPurchaseQuantity: false,
      errorFlagPurchasePrice: false,
      errorFlagPurchaseCommission: false,
      errorFlagPurchaseTransactionFees: false,
      errorFlagPurchaseOtherFees: false,

      // Error field messages
      errorMessageBonusDate: "",
      errorMessageBonusRatio: "",
      errorMessageSplitDate: "",
      errorMessageSplitRatio: "",
      errorMessageYahooCode: "",
      errorMessageIsinCode: "",
      errorMessageCusip: "",
      errorMessageShareName: "",
      errorMessageCategory: "",
      errorMessageSector: "",
      errorMessageIndustry: "",
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

  created() {
    this.getBonusSplitCheckBoxList();
    this.getCategoryList();
    this.getShortTermInvestmentRadioList();
    this.getShortTermInvestmentDefaultRadio();
  },

  methods: {
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
      ]
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

    onBackButtonClicked() {
      this.$router.push("/users/USA/EQ/bought");
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

    onShortTermInvestmentSelect(selectedShortTermInvestment) {
      this.shortTermInvestmentResetRadio = false;
      this.shortTermInvestment = selectedShortTermInvestment.value;
    },

    onBonusDateSelect(selectedDate) {
      this.inputOnFocus('bought-united-states-of-america-share-bonus-date');
      this.bonusDate = selectedDate;
    },

    onSplitDateSelect(selectedDate) {
      this.inputOnFocus('bought-united-states-of-america-share-split-date');
      this.splitDate = selectedDate;
    },

    onCategorySelect(selectedCategory) {
      this.resetCategoryDropdown = false;
      this.inputOnFocus('bought-united-states-of-america-share-category');
      this.category = selectedCategory.value;
    },

    onPurchaseDateSelect(selectedPurchaseDate) {
      this.resetPurchaseDatePicker = false;
      this.inputOnFocus('bought-united-states-of-america-share-purchase-date');
      this.purchaseDate = selectedPurchaseDate;
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
      this.isinCode = "";
      this.cusip = "";
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
      this.purchaseCommission = "";
      this.purchaseTransactionFees = "";
      this.purchaseOtherFees = "";

      // Error flag fields
      this.errorFlagYahooCode = false;
      this.errorFlagIsinCode = false;
      this.errorFlagCusip = false;
      this.errorFlagCategory = false;
      this.errorFlagSector = false;
      this.errorFlagIndustry = false;
      this.errorFlagPurchaseDate = false;
      this.errorFlagPurchaseQuantity = false;
      this.errorFlagPurchasePrice = false;
      this.errorFlagPurchaseCommission = false;
      this.errorFlagPurchaseTransactionFees = false;
      this.errorFlagPurchaseOtherFees = false;

      // Error field messages
      this.errorMessageYahooCode = "";
      this.errorMessageIsinCode = "";
      this.errorMessageCusip = "";
      this.errorMessageCategory = "";
      this.errorMessageSector = "";
      this.errorMessageIndustry = "";
      this.errorMessagePurchaseDate = "";
      this.errorMessagePurchaseQuantity = "";
      this.errorMessagePurchasePrice = "";
      this.errorMessagePurchaseCommission = "";
      this.errorMessagePurchaseTransactionFees = "";
      this.errorMessagePurchaseOtherFees = "";
    },

    onUnitedStatesOfAmericaSharesAddSharesClear() {

      // In case of error, change input border color
      if (this.isBonusCheckBoxSelected) {
        document.getElementById("bought-united-states-of-america-share-bonus-ratio").style.border = "";
      }
      if (this.isSplitCheckBoxSelected) {
        document.getElementById("bought-united-states-of-america-share-split-ratio").style.border = "";
      }
      if (!this.isBonusCheckBoxSelected && !this.isSplitCheckBoxSelected) {
        // document.getElementById("bought-united-states-of-america-share-yahoo-code").style.border = "";
        document.getElementById("bought-united-states-of-america-share-isin-code").style.border = "";
        document.getElementById("bought-united-states-of-america-share-cusip").style.border = "";
        document.getElementById("bought-united-states-of-america-share-category").style.border = "";
        document.getElementById("bought-united-states-of-america-share-sector").style.border = "";
        document.getElementById("bought-united-states-of-america-share-industry").style.border = "";
        document.getElementById("bought-united-states-of-america-share-purchase-quantity").style.border = "";
        document.getElementById("bought-united-states-of-america-share-purchase-price").style.border = "";
        document.getElementById("bought-united-states-of-america-share-purchase-commission").style.border = "";
        document.getElementById("bought-united-states-of-america-share-purchase-transaction-fees").style.border = "";
        document.getElementById("bought-united-states-of-america-share-purchase-other-fees").style.border = "";
      }
      document.getElementById("bought-united-states-of-america-share-yahoo-code").style.border = "";
      document.getElementById("bought-united-states-of-america-share-script-name").style.border = "0.1rem solid black";

      if (this.isBonusCheckBoxSelected || this.isSplitCheckBoxSelected) {
        this.resetPurchaseDatePicker = false;
      } else if (!this.isBonusCheckBoxSelected  || !this.isSplitCheckBoxSelected) {
        this.purchaseDate = getDefaultDate("");
        this.resetPurchaseDatePicker = true;
      }

      this.resetBonusSplitCheckBox = true;
      this.isBonusCheckBoxSelected = false;
      this.bonusRatio = "";
      this.bonusDate = "";
      this.isSplitCheckBoxSelected = false;
      this.splitRatio = "";
      this.splitDate = "";
      this.getBonusSplitCheckBoxList();
      this.yahooCode = "";
      this.isinCode = "";
      this.cusip = "";
      this.shareName = "";
      this.category = "";
      this.resetCategoryDropdown = true;
      this.sector = "";
      this.industry = "";
      this.shortTermInvestmentResetRadio = true;
      this.shortTermInvestment = "No";
      this.purchaseQuantity = "";
      this.purchasePrice = "";
      this.purchaseCommission = "";
      this.purchaseTransactionFees = "";
      this.purchaseOtherFees = "";

      // Error field flags
      this.errorFlagBonusRatio = false;
      this.errorFlagBonusDate = false;
      this.errorFlagSplitRatio = false;
      this.errorFlagSplitDate = false;
      this.errorFlagYahooCode = false;
      this.errorFlagIsinCode = false;
      this.errorFlagCusip = false;
      this.errorFlagShareName = false;
      this.errorFlagCategory = false;
      this.errorFlagSector = false;
      this.errorFlagIndustry = false;
      this.errorFlagPurchaseDate = false;
      this.errorFlagPurchaseQuantity = false;
      this.errorFlagPurchasePrice = false;
      this.errorFlagPurchaseCommission = false;
      this.errorFlagPurchaseTransactionFees = false;
      this.errorFlagPurchaseOtherFees = false;

      // Error field messages
      this.errorMessageBonusRatio = "";
      this.errorMessageBonusRatio = "";
      this.errorMessageSplitRatio = "";
      this.errorMessageSplitRatio = "";
      this.errorMessageYahooCode = "";
      this.errorMessageIsinCode = "";
      this.errorMessageCusip = "";
      this.errorMessageShareName = "";
      this.errorMessageCategory = "";
      this.errorMessageSector = "";
      this.errorMessageIndustry = "";
      this.errorMessagePurchaseDate = "";
      this.errorMessagePurchaseQuantity = "";
      this.errorMessagePurchasePrice = "";
      this.errorMessagePurchaseCommission = "";
      this.errorMessagePurchaseTransactionFees = "";
      this.errorMessagePurchaseOtherFees = "";
    },

    onUnitedStatesOfAmericaSharesAddSharesSubmit() {
      const formData = {
        sharesBonusSelected: this.isBonusCheckBoxSelected,
        sharesBonusRatio: this.bonusRatio,
        sharesBonusDate: this.bonusDate,
        sharesSplitSelected: this.isSplitCheckBoxSelected,
        sharesSplitRatio: this.splitRatio,
        sharesSplitDate: this.splitDate,
        yahooCode: this.yahooCode,
        isinCode: this.isinCode,
        cusip: this.cusip,
        scriptName: this.shareName,
        category: this.category,
        sector: this.sector,
        industry: this.industry,
        shortTermInvestment: this.shortTermInvestment === "Yes",
        purchaseDate: this.purchaseDate,
        purchaseQuantity: this.purchaseQuantity,
        purchasePrice: this.purchasePrice,
        purchaseCommission: this.purchaseCommission,
        purchaseTransactionFees: this.purchaseTransactionFees,
        purchaseOtherFees: this.purchaseOtherFees
      };

      axios.post(services.unitedStatesOfAmericaSharesShares + "?type=bought", formData).then(response => {

        this.successMessage = response.data.message;
        this.showSuccess = true;

        if (this.isBonusCheckBoxSelected || this.isSplitCheckBoxSelected) {
          this.resetPurchaseDatePicker = false;
        } else if (!this.isBonusCheckBoxSelected  || !this.isSplitCheckBoxSelected) {
          this.purchaseDate = getDefaultDate("");
          this.resetPurchaseDatePicker = true;
        }
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
        this.yahooCode = "";
        this.isinCode = "";
        this.cusip = "";
        this.category = "";
        this.sector = "";
        this.industry = "";
        this.purchaseQuantity = "";
        this.purchasePrice = "";
        this.purchaseCommission = "";
        this.purchaseTransactionFees = "";
        this.purchaseOtherFees = "";

      }).catch(error => {

        // For displaying errors
        error.response.data.error.map(e => {
          if (e.uiField === "bought-united-states-of-america-share-bonus-ratio") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagBonusRatio = true;
            this.errorMessageBonusRatio = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-bonus-date") {
            this.errorFlagBonusDate = true;
            this.errorMessageBonusDate = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-split-ratio") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagSplitRatio = true;
            this.errorMessageSplitRatio = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-split-date") {
            this.errorFlagSplitDate = true;
            this.errorMessageSplitDate = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-yahoo-code") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagYahooCode = true;
            this.errorMessageYahooCode = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-isin-code") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagIsinCode = true;
            this.errorMessageIsinCode = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-cusip") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagCusip = true;
            this.errorMessageCusip = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-script-name") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagShareName = true;
            this.errorMessageShareName = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-category") {
            this.errorFlagCategory = true;
            this.errorMessageCategory = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-sector") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagSector = true;
            this.errorMessageSector = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-industry") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagIndustry = true;
            this.errorMessageIndustry = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-purchase-date") {
            this.errorFlagPurchaseDate = true;
            this.errorMessagePurchaseDate = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-purchase-quantity") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagPurchaseQuantity = true;
            this.errorMessagePurchaseQuantity = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-purchase-price") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagPurchasePrice = true;
            this.errorMessagePurchasePrice = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-purchase-commission") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagPurchaseCommission = true;
            this.errorMessagePurchaseCommission = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-purchase-transaction-fees") {
            document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
            this.errorFlagPurchaseTransactionFees = true;
            this.errorMessagePurchaseTransactionFees = e.errorMessage;
          } else if (e.uiField === "bought-united-states-of-america-share-purchase-other-fees") {
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
      if (id === "bought-united-states-of-america-share-bonus-ratio") {
        this.errorFlagBonusRatio = false;
        this.errorMessageBonusRatio = "";
      } else if (id === "bought-united-states-of-america-share-bonus-date") {
        this.errorFlagBonusDate = false;
        this.errorMessageBonusDate = "";
      } else if (id === "bought-united-states-of-america-share-split-ratio") {
        this.errorFlagSplitRatio = false;
        this.errorMessageSplitRatio = "";
      } else if (id === "bought-united-states-of-america-share-split-date") {
        this.errorFlagSplitDate = false;
        this.errorMessageSplitDate = "";
      } else if (id === "bought-united-states-of-america-share-yahoo-code") {
        this.errorFlagYahooCode = false;
        this.errorMessageYahooCode = "";

        document.getElementById("bought-united-states-of-america-share-script-name").style = "0.1rem solid black";
        this.errorFlagShareName = false;
        this.errorMessageShareName = "";
      } else if (id === "bought-united-states-of-america-share-isin-code") {
        this.errorFlagIsinCode = false;
        this.errorMessageIsinCode = "";
      } else if (id === "bought-united-states-of-america-share-cusip") {
        this.errorFlagCusip = false;
        this.errorMessageCusip = "";
      } else if (id === "bought-united-states-of-america-share-category") {
        this.errorFlagCategory = false;
        this.errorMessageCategory = "";
      } else if (id === "bought-united-states-of-america-share-sector") {
        this.errorFlagSector = false;
        this.errorMessageSector = "";
      } else if (id === "bought-united-states-of-america-share-industry") {
        this.errorFlagIndustry = false;
        this.errorMessageIndustry = "";
      } else if (id === "bought-united-states-of-america-share-purchase-date") {
        this.errorFlagPurchaseDate = false;
        this.errorMessagePurchaseDate = "";
      } else if (id === "bought-united-states-of-america-share-purchase-quantity") {
        this.errorFlagPurchaseQuantity = false;
        this.errorMessagePurchaseQuantity = "";
      }
      else if (id === "bought-united-states-of-america-share-purchase-price") {
        this.errorFlagPurchasePrice = false;
        this.errorMessagePurchasePrice = "";
      }
      else if (id === "bought-united-states-of-america-share-purchase-commission") {
        this.errorFlagPurchaseCommission = false;
        this.errorMessagePurchaseCommission = "";
      }
      else if (id === "bought-united-states-of-america-share-purchase-transaction-fees") {
        this.errorFlagPurchaseTransactionFees = false;
        this.errorMessagePurchaseTransactionFees = "";
      }
      else if (id === "bought-united-states-of-america-share-purchase-other-fees") {
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
      this.$router.push("/users/USA/EQ/bought");
    }
  }
}
</script>