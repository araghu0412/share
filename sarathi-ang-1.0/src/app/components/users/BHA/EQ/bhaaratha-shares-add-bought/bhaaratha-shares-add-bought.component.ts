import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import services from 'src/assets/services/services.json';
import { getDefaultDate } from 'src/app/common/utils/utils';

@Component({
  selector: 'app-bhaaratha-shares-add-bought',
  templateUrl: './bhaaratha-shares-add-bought.component.html',
  styleUrls: ['./bhaaratha-shares-add-bought.component.css']
})
export class BhaarathaSharesAddBoughtComponent implements OnInit {

  stockExchangeRadioList: Array<any>  = [];
  resetStockExchangeRadio: boolean = false;
  stockExchange: string = "";
  bonusSplitCheckBoxList: Array<any> = [];
  resetBonusSplitCheckBox: boolean = false;
  isBonusCheckBoxSelected: boolean = false;
  isSplitCheckBoxSelected: boolean = false;
  resetBonusDatePicker: boolean = false;
  resetSplitDatePicker: boolean = false;
  bonusDate: string = "";
  bonusRatio: string = "";
  splitDate: string = "";
  splitRatio: string = "";
  bseScriptCode: string = "";
  yahooBseScriptCode: string = "";
  nseScriptCode: string = "";
  yahooNseScriptCode: string = "";
  moneycontrolCode: string = "";
  isinCode: string = "";
  shareName: string = "";
  shareNameLoading: boolean = false;
  categoryList: Array<any> = [];
  category: string = "";
  resetCategoryDropdown: boolean = false;
  sector: string = "";
  industry: string = "";
  shortTermInvestmentRadioList: Array<any> = [];
  shortTermInvestmentResetRadio: boolean = false;
  shortTermInvestmentDefaultRadio: any = {};
  shortTermInvestment: string = "No";
  purchaseDate: string = "";
  resetPurchaseDatePicker: boolean = false;
  purchaseQuantity: string = "";
  purchasePrice: string = "";
  purchaseBrokerage: string = "";
  purchaseSTT: string = "";
  purchaseExpenditure: string = "";
  purchaseNonExpenditure: string = "";

  // Error field flags
  errorFlagStockExchange: boolean = false;
  errorFlagBonusDate: boolean = false;
  errorFlagBonusRatio: boolean = false;
  errorFlagSplitDate: boolean = false;
  errorFlagSplitRatio: boolean = false;
  errorFlagBseScriptCode: boolean = false;
  errorFlagYahooBseScriptCode: boolean = false;
  errorFlagNseScriptCode: boolean = false;
  errorFlagYahooNseScriptCode: boolean = false;
  errorFlagMoneycontrolCode: boolean = false;
  errorFlagIsinCode: boolean = false;
  errorFlagShareName: boolean = false;
  errorFlagCategory: boolean = false;
  errorFlagSector: boolean = false;
  errorFlagIndustry: boolean = false;
  errorFlagPurchaseDate: boolean = false;
  errorFlagPurchaseQuantity: boolean = false;
  errorFlagPurchasePrice: boolean = false;
  errorFlagPurchaseBrokerage: boolean = false;
  errorFlagPurchaseSTT: boolean = false;
  errorFlagPurchaseExpenditure: boolean = false;
  errorFlagPurchaseNonExpenditure: boolean = false;

  // Error field messages
  errorMessageStockExchange: string = "";
  errorMessageBonusDate: string = "";
  errorMessageBonusRatio: string = "";
  errorMessageSplitDate: string = "";
  errorMessageSplitRatio: string = "";
  errorMessageBseScriptCode: string = "";
  errorMessageYahooBseScriptCode: string = "";
  errorMessageNseScriptCode: string = "";
  errorMessageYahooNseScriptCode: string = "";
  errorMessageMoneycontrolCode: string = "";
  errorMessageIsinCode: string = "";
  errorMessageShareName: string = "";
  errorMessageCategory: string = "";
  errorMessageSector: string = "";
  errorMessageIndustry: string = "";
  errorMessagePurchaseDate: string = "";
  errorMessagePurchaseQuantity: string = "";
  errorMessagePurchasePrice: string = "";
  errorMessagePurchaseBrokerage: string = "";
  errorMessagePurchaseSTT: string = "";
  errorMessagePurchaseExpenditure: string = "";
  errorMessagePurchaseNonExpenditure: string = "";

  // Error/Success Modal
  showError: boolean = false;
  errorMessage: string = "";
  showSuccess: boolean = false;
  successMessage: string = "";

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getStockExchangeList();
    this.getBonusSplitCheckBoxList();
    this.getCategoryList();
    this.getShortTermInvestmentRadioList();
    this.getShortTermInvestmentDefaultRadio();
  }


  onBackButtonClicked() {
    this.router.navigate(["/users/BHA/EQ/bought"]);
  }

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
  }

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
  }

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
  }

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
  }
  
  getShortTermInvestmentDefaultRadio() {
    this.shortTermInvestmentDefaultRadio = this.shortTermInvestmentRadioList[1];
  }

  onStockExchangeSelect(stockExchange: any) {
    this.resetStockExchangeRadio = false;
    this.stockExchange = stockExchange.value;
    this.getShareNameByStockExchange();
  }

  getShareNameByStockExchange() {
    if (!this.shareName && this.moneycontrolCode) {
      this.getShareName(this.stockExchange, this.moneycontrolCode);
    }
  }

  getShareNameByScriptCode() {
    if (this.stockExchange) {
      this.getShareName(this.stockExchange, this.moneycontrolCode);
    }
  }

  getShareName(stockExchange: string, scriptCode: string) {
    this.shareNameLoading = true;

    this.http.get(services.bhaarathaShareNameDetails + "?stockExchange=" + stockExchange + "&scriptCode=" + scriptCode).subscribe((response: any) => {
      if (response === null) {
        this.shareName = "";
        this.shareNameLoading = false;
      } else {
        this.shareName = response.scriptName;
        this.shareNameLoading = false;
      }
    }, (error: any) => {
      error.error.error.map((e: any) => {
        this.errorMessage = e.errorMessage;
        this.showError = true;
      });
    });
  }

  onBonusSplitSelect(selectedBonusSplitList: any) {
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
  }

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
  }

  onShortTermInvestmentSelect(selectedShortTermInvestment: any) {
    this.shortTermInvestmentResetRadio = false;
    this.shortTermInvestment = selectedShortTermInvestment.value;
  }

  onBonusDateSelect(selectedDate: string) {
    this.inputOnFocus('bought-bhaaratha-share-bonus-date');
    this.bonusDate = selectedDate;
  }

  onSplitDateSelect(selectedDate: string) {
    this.inputOnFocus('bought-bhaaratha-share-split-date');
    this.splitDate = selectedDate;
  }

  onCategorySelect(selectedCategory: any) {
    this.resetCategoryDropdown = false;
    this.inputOnFocus('bought-bhaaratha-share-category');
    this.category = selectedCategory.value;
  }

  onPurchaseDateSelect(selectedPurchaseDate: string) {
    this.resetPurchaseDatePicker = false;
    this.inputOnFocus('bought-bhaaratha-share-purchase-date');
    this.purchaseDate = selectedPurchaseDate;
  }

  onBhaarathaSharesAddSharesClear() {

    // In case of error, change input border color
    if (this.isBonusCheckBoxSelected) {
      (document.getElementById("bought-bhaaratha-share-bonus-ratio") as HTMLElement).style.border = "";
    }
    if (this.isSplitCheckBoxSelected) {
      (document.getElementById("bought-bhaaratha-share-split-ratio") as HTMLElement).style.border = "";
    }
    if (!this.isBonusCheckBoxSelected && !this.isSplitCheckBoxSelected) {
      (document.getElementById("bought-bhaaratha-share-yahoo-bse-script-code") as HTMLElement).style.border = "";
      (document.getElementById("bought-bhaaratha-share-yahoo-nse-script-code") as HTMLElement).style.border = "";
      (document.getElementById("bought-bhaaratha-share-isin-code") as HTMLElement).style.border = "";
      (document.getElementById("bought-bhaaratha-share-category") as HTMLElement).style.border = "";
      (document.getElementById("bought-bhaaratha-share-sector") as HTMLElement).style.border = "";
      (document.getElementById("bought-bhaaratha-share-industry") as HTMLElement).style.border = "";
      (document.getElementById("bought-bhaaratha-share-purchase-quantity") as HTMLElement).style.border = "";
      (document.getElementById("bought-bhaaratha-share-purchase-price") as HTMLElement).style.border = "";
      (document.getElementById("bought-bhaaratha-share-purchase-brokerage") as HTMLElement).style.border = "";
      (document.getElementById("bought-bhaaratha-share-purchase-stt") as HTMLElement).style.border = "";
      (document.getElementById("bought-bhaaratha-share-purchase-expenditure") as HTMLElement).style.border = "";
      (document.getElementById("bought-bhaaratha-share-purchase-non-expenditure") as HTMLElement).style.border = "";
    }
    (document.getElementById("bought-bhaaratha-share-bse-script-code") as HTMLElement).style.border = "";
    (document.getElementById("bought-bhaaratha-share-nse-script-code") as HTMLElement).style.border = "";
    (document.getElementById("bought-bhaaratha-share-money-control-code") as HTMLElement).style.border = "";
    (document.getElementById("bought-bhaaratha-share-script-name") as HTMLElement).style.border = "";

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
  }

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

    this.http.post(services.bhaarathaSharesShares + "?type=bought", formData).subscribe((response: any) => {
      this.successMessage = response.message;
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
    }, (error: any) => {
      error.error.error.map((e: any) => {
        if (e.uiField === "bought-bhaaratha-share-stock-exchange") {
          this.errorFlagStockExchange = true;
          this.errorMessageStockExchange = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-bonus-ratio") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagBonusRatio = true;
          this.errorMessageBonusRatio = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-bonus-date") {
          this.errorFlagBonusDate = true;
          this.errorMessageBonusDate = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-split-ratio") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagSplitRatio = true;
          this.errorMessageSplitRatio = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-split-date") {
          this.errorFlagSplitDate = true;
          this.errorMessageSplitDate = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-bse-script-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagBseScriptCode = true;
          this.errorMessageBseScriptCode = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-yahoo-bse-script-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagYahooBseScriptCode = true;
          this.errorMessageYahooBseScriptCode = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-nse-script-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagNseScriptCode = true;
          this.errorMessageNseScriptCode = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-yahoo-nse-script-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagYahooNseScriptCode = true;
          this.errorMessageYahooNseScriptCode = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-money-control-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagMoneycontrolCode = true;
          this.errorMessageMoneycontrolCode = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-isin-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagIsinCode = true;
          this.errorMessageIsinCode = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-script-name") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagShareName = true;
          this.errorMessageShareName = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-category") {
          this.errorFlagCategory = true;
          this.errorMessageCategory = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-sector") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagSector = true;
          this.errorMessageSector = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-industry") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagIndustry = true;
          this.errorMessageIndustry = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-purchase-date") {
          this.errorFlagPurchaseDate = true;
          this.errorMessagePurchaseDate = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-purchase-quantity") {
          this.errorFlagPurchaseQuantity = true;
          this.errorMessagePurchaseQuantity = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-purchase-price") {
          this.errorFlagPurchasePrice = true;
          this.errorMessagePurchasePrice = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-purchase-brokerage") {
          this.errorFlagPurchaseBrokerage = true;
          this.errorMessagePurchaseBrokerage = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-purchase-stt") {
          this.errorFlagPurchaseSTT = true;
          this.errorMessagePurchaseSTT = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-purchase-expenditure") {
          this.errorFlagPurchaseExpenditure = true;
          this.errorMessagePurchaseExpenditure = e.errorMessage;
        } else if (e.uiField === "bought-bhaaratha-share-purchase-non-expenditure") {
          this.errorFlagPurchaseNonExpenditure = true;
          this.errorMessagePurchaseNonExpenditure = e.errorMessage;
        } else {
          this.showError = true;
          this.errorMessage = e.errorMessage;
        }
      });
    });
  }

  // Incase of the error, change the border from red to original
  inputOnFocus(id: string) {
    (document.getElementById(id) as HTMLElement).style.border = "";

    // Resetting error flags and error messages
    if (id === "bought-bhaaratha-share-stock-exchange") {
      this.errorFlagStockExchange = false;
      this.errorMessageStockExchange = "";

      (document.getElementById("bought-bhaaratha-share-script-name") as HTMLElement).style.border = "0.1rem solid black";
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

      (document.getElementById("bought-bhaaratha-share-script-name") as HTMLElement).style.border = "0.1rem solid black";
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

      (document.getElementById("bought-bhaaratha-share-script-name") as HTMLElement).style.border = "0.1rem solid black";
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
  }

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }

  closeSuccess() {
    this.showSuccess = false;
    this.successMessage = "";
    this.router.navigate(["/users/BHA/EQ/bought"]);
  }
}
