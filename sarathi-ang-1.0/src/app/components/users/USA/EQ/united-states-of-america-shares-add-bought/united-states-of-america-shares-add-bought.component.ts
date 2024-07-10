import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import services from 'src/assets/services/services.json';
import { getDefaultDate } from 'src/app/common/utils/utils';

@Component({
  selector: 'app-united-states-of-america-shares-add-bought',
  templateUrl: './united-states-of-america-shares-add-bought.component.html',
  styleUrls: ['./united-states-of-america-shares-add-bought.component.css']
})
export class UnitedStatesOfAmericaSharesAddBoughtComponent implements OnInit {

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
  yahooCode: string = "";
  isinCode: string = "";
  cusip: string = "";
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
  purchaseCommission: string = "";
  purchaseTransactionFees: string = "";
  purchaseOtherFees: string = "";

  // Error field flags
  errorFlagBonusDate: boolean = false;
  errorFlagBonusRatio: boolean = false;
  errorFlagSplitDate: boolean = false;
  errorFlagSplitRatio: boolean = false;
  errorFlagYahooCode: boolean = false;
  errorFlagIsinCode: boolean = false;
  errorFlagCusip: boolean = false;
  errorFlagShareName: boolean = false;
  errorFlagCategory: boolean = false;
  errorFlagSector: boolean = false;
  errorFlagIndustry: boolean = false;
  errorFlagPurchaseDate: boolean = false;
  errorFlagPurchaseQuantity: boolean = false;
  errorFlagPurchasePrice: boolean = false;
  errorFlagPurchaseCommission: boolean = false;
  errorFlagPurchaseTransactionFees: boolean = false;
  errorFlagPurchaseOtherFees: boolean = false;

  // Error field messages
  errorMessageBonusDate: string = "";
  errorMessageBonusRatio: string = "";
  errorMessageSplitDate: string = "";
  errorMessageSplitRatio: string = "";
  errorMessageYahooCode: string = "";
  errorMessageIsinCode: string = "";
  errorMessageCusip: string = "";
  errorMessageShareName: string = "";
  errorMessageCategory: string = "";
  errorMessageSector: string = "";
  errorMessageIndustry: string = "";
  errorMessagePurchaseDate: string = "";
  errorMessagePurchaseQuantity: string = "";
  errorMessagePurchasePrice: string = "";
  errorMessagePurchaseCommission: string = "";
  errorMessagePurchaseTransactionFees: string = "";
  errorMessagePurchaseOtherFees: string = "";

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
    this.getBonusSplitCheckBoxList();
    this.getCategoryList();
    this.getShortTermInvestmentRadioList();
    this.getShortTermInvestmentDefaultRadio();
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
    ]
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

  onBackButtonClicked() {
    this.router.navigate(["/users/USA/EQ/bought"]);
  }

  getShareName() {
    this.shareNameLoading = true;

    this.http.get(services.unitedStatesOfAmericaShareNameDetails + "?scriptCode=" + this.yahooCode).subscribe((response: any) => {
      if (response === null) {
        this.shareName = "";
        this.shareNameLoading = false;
      } else {
        this.shareName = response.scriptName;
        this.shareNameLoading = false;
      }
    }, error => {
      error.error.error.map((e: any) => {
        this.errorMessage = e.errorMessage;
        this.showError = true;
      });
    });
  }

  onShortTermInvestmentSelect(selectedShortTermInvestment: any) {
    this.shortTermInvestmentResetRadio = false;
    this.shortTermInvestment = selectedShortTermInvestment.value;
  }

  onBonusDateSelect(selectedDate: string) {
    this.inputOnFocus('bought-united-states-of-america-share-bonus-date');
    this.bonusDate = selectedDate;
  }

  onSplitDateSelect(selectedDate: string) {
    this.inputOnFocus('bought-united-states-of-america-share-split-date');
    this.splitDate = selectedDate;
  }

  onCategorySelect(selectedCategory: any) {
    this.resetCategoryDropdown = false;
    this.inputOnFocus('bought-united-states-of-america-share-category');
    this.category = selectedCategory.value;
  }

  onPurchaseDateSelect(selectedPurchaseDate: string) {
    this.resetPurchaseDatePicker = false;
    this.inputOnFocus('bought-united-states-of-america-share-purchase-date');
    this.purchaseDate = selectedPurchaseDate;
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
  }

  onUnitedStatesOfAmericaSharesAddSharesClear() {

    // In case of error, change input border color
    if (this.isBonusCheckBoxSelected) {
      (document.getElementById("bought-united-states-of-america-share-bonus-ratio") as HTMLElement).style.border = "";
    }
    if (this.isSplitCheckBoxSelected) {
      (document.getElementById("bought-united-states-of-america-share-split-ratio") as HTMLElement).style.border = "";
    }
    if (!this.isBonusCheckBoxSelected && !this.isSplitCheckBoxSelected) {
      (document.getElementById("bought-united-states-of-america-share-isin-code") as HTMLElement).style.border = "";
      (document.getElementById("bought-united-states-of-america-share-cusip") as HTMLElement).style.border = "";
      (document.getElementById("bought-united-states-of-america-share-category") as HTMLElement).style.border = "";
      (document.getElementById("bought-united-states-of-america-share-sector") as HTMLElement).style.border = "";
      (document.getElementById("bought-united-states-of-america-share-industry") as HTMLElement).style.border = "";
      (document.getElementById("bought-united-states-of-america-share-purchase-quantity") as HTMLElement).style.border = "";
      (document.getElementById("bought-united-states-of-america-share-purchase-price") as HTMLElement).style.border = "";
      (document.getElementById("bought-united-states-of-america-share-purchase-commission") as HTMLElement).style.border = "";
      (document.getElementById("bought-united-states-of-america-share-purchase-transaction-fees") as HTMLElement).style.border = "";
      (document.getElementById("bought-united-states-of-america-share-purchase-other-fees") as HTMLElement).style.border = "";
    }
    (document.getElementById("bought-united-states-of-america-share-yahoo-code") as HTMLElement).style.border = "";
    (document.getElementById("bought-united-states-of-america-share-script-name") as HTMLElement).style.border = "";

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
  }

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

    this.http.post(services.unitedStatesOfAmericaSharesShares + "?type=bought", formData).subscribe((response: any) => {

      this.successMessage = response.message;
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

    }, error => {
      // For displaying errors
      error.error.error.map((e: any) => {
        if (e.uiField === "bought-united-states-of-america-share-bonus-ratio") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagBonusRatio = true;
          this.errorMessageBonusRatio = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-bonus-date") {
          this.errorFlagBonusDate = true;
          this.errorMessageBonusDate = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-split-ratio") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagSplitRatio = true;
          this.errorMessageSplitRatio = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-split-date") {
          this.errorFlagSplitDate = true;
          this.errorMessageSplitDate = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-yahoo-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagYahooCode = true;
          this.errorMessageYahooCode = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-isin-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagIsinCode = true;
          this.errorMessageIsinCode = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-cusip") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagCusip = true;
          this.errorMessageCusip = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-script-name") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagShareName = true;
          this.errorMessageShareName = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-category") {
          this.errorFlagCategory = true;
          this.errorMessageCategory = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-sector") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagSector = true;
          this.errorMessageSector = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-industry") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagIndustry = true;
          this.errorMessageIndustry = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-purchase-date") {
          this.errorFlagPurchaseDate = true;
          this.errorMessagePurchaseDate = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-purchase-quantity") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagPurchaseQuantity = true;
          this.errorMessagePurchaseQuantity = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-purchase-price") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagPurchasePrice = true;
          this.errorMessagePurchasePrice = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-purchase-commission") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagPurchaseCommission = true;
          this.errorMessagePurchaseCommission = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-purchase-transaction-fees") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagPurchaseTransactionFees = true;
          this.errorMessagePurchaseTransactionFees = e.errorMessage;
        } else if (e.uiField === "bought-united-states-of-america-share-purchase-other-fees") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagPurchaseOtherFees = true;
          this.errorMessagePurchaseOtherFees = e.errorMessage;
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

      (document.getElementById("bought-united-states-of-america-share-script-name") as HTMLElement).style.border = "";
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
  }

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }

  closeSuccess() {
    this.showSuccess = false;
    this.successMessage = "";
    this.router.navigate(["/users/USA/EQ/bought"]);
  }
}
