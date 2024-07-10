import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { getDefaultDate } from 'src/app/common/utils/utils';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-united-states-of-america-shares-add-short-term-investment',
  templateUrl: './united-states-of-america-shares-add-short-term-investment.component.html',
  styleUrls: ['./united-states-of-america-shares-add-short-term-investment.component.css']
})
export class UnitedStatesOfAmericaSharesAddShortTermInvestmentComponent implements OnInit {

  yahooCode: string = "";
  shareName: string = "";
  resetPurchaseDatePicker: boolean = false;
  purchaseDate: string = getDefaultDate("");
  purchaseQuantity: string = "";
  purchasePrice: string = "";
  purchaseCommission: string = "";
  purchaseTransactionFees: string = "";
  purchaseOtherFees: string = "";
  shareNameLoading: boolean = false;

  // Error field flags
  errorFlagYahooCode: boolean = false;
  errorFlagShareName: boolean = false;
  errorFlagPurchaseDate: boolean = false;
  errorFlagPurchaseQuantity: boolean = false;
  errorFlagPurchasePrice: boolean = false;
  errorFlagPurchaseCommission: boolean = false;
  errorFlagPurchaseTransactionFees: boolean = false;
  errorFlagPurchaseOtherFees: boolean = false;

  // Error field messages
  errorMessageYahooCode: string = "";
  errorMessageShareName: string = "";
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

  onPurchaseDateSelect(selectedPurchaseDate: string) {
    this.resetPurchaseDatePicker = false;
    this.inputOnFocus('sti-united-states-of-america-share-purchase-date');
    this.purchaseDate = selectedPurchaseDate;
  }

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
    (document.getElementById("sti-united-states-of-america-share-yahoo-code") as HTMLElement).style.border = "";
    (document.getElementById("sti-united-states-of-america-share-script-name") as HTMLElement).style.border = "0.1rem solid black";
    (document.getElementById("sti-united-states-of-america-share-purchase-quantity") as HTMLElement).style.border = "";
    (document.getElementById("sti-united-states-of-america-share-purcahse-price") as HTMLElement).style.border = "";
    (document.getElementById("sti-united-states-of-america-share-purchase-commission") as HTMLElement).style.border = "";
    (document.getElementById("sti-united-states-of-america-share-purchase-transaction-fees") as HTMLElement).style.border = "";
    (document.getElementById("sti-united-states-of-america-share-purchase-other-fees") as HTMLElement).style.border = "";
  }

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

    this.http.post(services.unitedStatesOfAmericaSharesShortTermInvestment, formData).subscribe((response: any) => {
      this.successMessage = response.message;
      this.showSuccess = true;

      this.yahooCode = "";
      this.shareName = "";
      this.resetPurchaseDatePicker = true;
      this.purchaseDate = getDefaultDate("");
      this.purchaseQuantity = "";
      this.purchasePrice = "";
      this.purchaseCommission = "";
      this.purchaseTransactionFees = "";
      this.purchaseOtherFees = "";
    }, (error: any) => {

      // For displaying error
      error.error.error.map((e: any) => {
        if (e.uiField === "sti-united-states-of-america-share-yahoo-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagYahooCode = true;
          this.errorMessageYahooCode = e.errorMessage;
        } else if (e.uiField === "sti-united-states-of-america-share-script-name") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagShareName = true;
          this.errorMessageShareName = e.errorMessage;
        } else if (e.uiField === "sti-united-states-of-america-share-purchase-date") {
          this.errorFlagPurchaseDate = true;
          this.errorMessagePurchaseDate = e.errorMessage;
        } else if (e.uiField === "sti-united-states-of-america-share-purchase-quantity") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagPurchaseQuantity = true;
          this.errorMessagePurchaseQuantity = e.errorMessage;
        } else if (e.uiField === "sti-united-states-of-america-share-purcahse-price") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagPurchasePrice = true;
          this.errorMessagePurchasePrice = e.errorMessage;
        } else if (e.uiField === "sti-united-states-of-america-share-purchase-commission") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagPurchaseCommission = true;
          this.errorMessagePurchaseCommission = e.errorMessage;
        } else if (e.uiField === "sti-united-states-of-america-share-purchase-transaction-fees") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagPurchaseTransactionFees = true;
          this.errorMessagePurchaseTransactionFees = e.errorMessage;
        } else if (e.uiField === "sti-united-states-of-america-share-purchase-other-fees") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
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
    if (id === "sti-united-states-of-america-share-yahoo-code") {
      this.errorFlagYahooCode = false;
      this.errorMessageYahooCode = "";

      (document.getElementById("sti-united-states-of-america-share-script-name") as HTMLElement).style.border = "";
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
  }

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }

  closeSuccess() {
    this.showSuccess = false;
    this.successMessage = "";
    this.router.navigate(["/users/USA/EQ/short-term-investment"]);
  }

  onBackButtonClick() {
    this.router.navigate(["/users/USA/EQ/short-term-investment"]);
  }

}
