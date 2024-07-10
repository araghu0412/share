import { Component, OnInit } from '@angular/core';
import services from 'src/assets/services/services.json';
import { getDefaultDate } from 'src/app/common/utils/utils';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-united-states-of-america-shares-add-sold',
  templateUrl: './united-states-of-america-shares-add-sold.component.html',
  styleUrls: ['./united-states-of-america-shares-add-sold.component.css']
})
export class UnitedStatesOfAmericaSharesAddSoldComponent implements OnInit {

  yahooCode: string = "";
  shareName: string = "";
  shareNameLoading: boolean = false;
  sellDate: string = "";
  resetSellDatePicker: boolean = false;
  sellQuantity: string = "";
  sellPrice: string = "";
  sellCommission: string = "";
  sellTransactionFees: string = "";
  sellOtherFees: string = "";

  // Error field flags
  errorFlagYahooCode: boolean = false;
  errorFlagShareName: boolean = false;
  errorFlagSellDate: boolean = false;
  errorFlagSellQuantity: boolean = false;
  errorFlagSellPrice: boolean = false;
  errorFlagSellCommission: boolean = false;
  errorFlagSellTransactionFees: boolean = false;
  errorFlagSellOtherFees: boolean = false;

  // Error field messages
  errorMessageYahooCode: string = "";
  errorMessageShareName: string = "";
  errorMessageSellDate: string = "";
  errorMessageSellQuantity: string = "";
  errorMessageSellPrice: string = "";
  errorMessageSellCommission: string = "";
  errorMessageSellTransactionFees: string = "";
  errorMessageSellOtherFees: string = "";

  // Error/Success Modal
  showError: boolean = false;
  errorMessage: string = "";
  showSuccess: boolean = false;
  successMessage: string = "";

  constructor(
    private router: Router,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
  }

  onBackButtonClicked() {
    this.router.navigate(["/users/USA/EQ/sold"]);
  }

  onSellDateSelect(selectedSellDate: string) {
    this.resetSellDatePicker = false;
    this.inputOnFocus('sold-united-states-of-america-share-sell-date');
    this.sellDate = selectedSellDate;
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

  onUnitedStatesOfAmericaSharesAddSharesClear() {

    // In case of error, change input border color
    (document.getElementById("sold-united-states-of-america-share-yahoo-code") as HTMLElement).style.border = "";
    (document.getElementById("sold-united-states-of-america-share-script-name") as HTMLElement).style.border = "";
    (document.getElementById("sold-united-states-of-america-share-sell-quantity") as HTMLElement).style.border = "";
    (document.getElementById("sold-united-states-of-america-share-sell-price") as HTMLElement).style.border = "";
    (document.getElementById("sold-united-states-of-america-share-sell-commission") as HTMLElement).style.border = "";
    (document.getElementById("sold-united-states-of-america-share-sell-transaction-fees") as HTMLElement).style.border = "";
    (document.getElementById("sold-united-states-of-america-share-sell-other-fees") as HTMLElement).style.border = "";

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
  }

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

    this.http.post(services.unitedStatesOfAmericaSharesShares + "?type=sold", formData).subscribe((response: any) => {

      this.successMessage = response.message;
      this.showSuccess = true;

      this.sellQuantity = "";
      this.sellPrice = "";
      this.sellCommission = "";
      this.sellTransactionFees = "";
      this.sellOtherFees = "";
      this.sellDate = getDefaultDate("");
      this.resetSellDatePicker = true;

    }, error => {
      error.error.error.map((e: any) => {
        if (e.uiField === "sold-united-states-of-america-share-yahoo-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagYahooCode = true;
          this.errorMessageYahooCode = e.errorMessage;
        } else if (e.uiField === "sold-united-states-of-america-share-script-name") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagShareName = true;
          this.errorMessageShareName = e.errorMessage;
        } else if (e.uiField === "sold-united-states-of-america-share-sell-date") {
          this.errorFlagSellDate = true;
          this.errorMessageSellDate = e.errorMessage;
        } else if (e.uiField === "sold-united-states-of-america-share-sell-quantity") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagSellQuantity = true;
          this.errorMessageSellQuantity = e.errorMessage;
        } else if (e.uiField === "sold-united-states-of-america-share-sell-price") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagSellPrice = true;
          this.errorMessageSellPrice = e.errorMessage;
        } else if (e.uiField === "sold-united-states-of-america-share-sell-commission") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagSellCommission = true;
          this.errorMessageSellCommission = e.errorMessage;
        } else if (e.uiField === "sold-united-states-of-america-share-sell-transaction-fees") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagSellTransactionFees = true;
          this.errorMessageSellTransactionFees = e.errorMessage;
        } else if (e.uiField === "sold-united-states-of-america-share-sell-other-fees") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagSellOtherFees = true;
          this.errorMessageSellOtherFees = e.errorMessage;
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
    if (id === "sold-united-states-of-america-share-yahoo-code") {
      this.errorFlagYahooCode = false;
      this.errorMessageYahooCode = "";

      (document.getElementById("sold-united-states-of-america-share-script-name") as HTMLElement).style.border = "";
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
  }

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }

  closeSuccess() {
    this.showSuccess = false;
    this.successMessage = "";
    this.router.navigate(["/users/USA/EQ/sold"]);
  }
}
