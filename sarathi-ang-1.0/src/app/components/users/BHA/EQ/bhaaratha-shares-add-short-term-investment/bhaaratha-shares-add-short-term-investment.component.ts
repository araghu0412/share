import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { getDefaultDate } from 'src/app/common/utils/utils';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-bhaaratha-shares-add-short-term-investment',
  templateUrl: './bhaaratha-shares-add-short-term-investment.component.html',
  styleUrls: ['./bhaaratha-shares-add-short-term-investment.component.css']
})
export class BhaarathaSharesAddShortTermInvestmentComponent implements OnInit {

  stockExchangeList: Array<any> = [];
  resetStockExchangeRadio: boolean = false;
  stockExchange: string = "";
  bseCode: string = "";
  yahooBseCode: string = "";
  nseCode: string = "";
  yahooNseCode: string = "";
  moneycontrolCode: string = "";
  shareName: string = "";
  resetPurchaseDatePicker: boolean = false;
  purchaseDate: string = getDefaultDate("");
  purchaseQuantity: string = "";
  purchasePrice: string = "";
  purchaseBrokerage: string = "";
  purchaseSTT: string = "";
  purchaseExpenditure: string = "";
  purchaseNonExpenditure: string = "";
  shareNameLoading: boolean = false;

  // Error field flags
  errorFlagStockExchange: boolean = false;
  errorFlagBseCode: boolean = false;
  errorFlagYahooBseCode: boolean = false;
  errorFlagNseCode: boolean = false;
  errorFlagYahooNseCode: boolean = false;
  errorFlagMoneycontrolCode: boolean = false;
  errorFlagShareName: boolean = false;
  errorFlagPurchaseDate: boolean = false;
  errorFlagPurchaseQuantity: boolean = false;
  errorFlagPurchasePrice: boolean = false;
  errorFlagPurchaseBrokerage: boolean = false;
  errorFlagPurchaseSTT: boolean = false;
  errorFlagPurchaseExpenditure: boolean = false;
  errorFlagPurchaseNonExpenditure: boolean = false;

  // Error field messages
  errorMessageStockExchange: string = "";
  errorMessageBseCode: string = "";
  errorMessageYahooBseCode: string = "";
  errorMessageNseCode: string = "";
  errorMessageYahooNseCode: string = "";
  errorMessageMoneycontrolCode: string = "";
  errorMessageShareName: string = "";
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

  onPurchaseDateSelect(selectedPurchaseDate: string) {
    this.resetPurchaseDatePicker = false;
    this.inputOnFocus('sti-bhaaratha-purchase-date');
    this.purchaseDate = selectedPurchaseDate;
  }

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
    (document.getElementById("sti-bhaaratha-bse-code") as HTMLElement).style.border = "";
    (document.getElementById("sti-bhaaratha-yahoo-bse-code") as HTMLElement).style.border = "";
    (document.getElementById("sti-bhaaratha-nse-code") as HTMLElement).style.border = "";
    (document.getElementById("sti-bhaaratha-yahoo-nse-code") as HTMLElement).style.border = "";
    (document.getElementById("sti-bhaaratha-moneycontrol-code") as HTMLElement).style.border = "";
    (document.getElementById("sti-bhaaratha-share-name") as HTMLElement).style.border = "0.1rem solid black";
    (document.getElementById("sti-bhaaratha-purchase-quantity") as HTMLElement).style.border = "";
    (document.getElementById("sti-bhaaratha-purchase-price") as HTMLElement).style.border = "";
    (document.getElementById("sti-bhaaratha-purchase-brokerage") as HTMLElement).style.border = "";
    (document.getElementById("sti-bhaaratha-purchase-stt") as HTMLElement).style.border = "";
    (document.getElementById("sti-bhaaratha-purchase-expenditure") as HTMLElement).style.border = "";
    (document.getElementById("sti-bhaaratha-purchase-non-expenditure") as HTMLElement).style.border = "";
  }

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

    this.http.post(services.bhaarathaSharesShortTermInvestment, formData).subscribe((response: any) => {
      this.successMessage = response.message;
      this.showSuccess = true;

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
    }, (error: any) => {

      // For displaying error
      error.error.error.map((e: any) => {
        if (e.uiField === "sti-bhaaratha-stock-exchange") {
          this.errorFlagStockExchange = true;
          this.errorMessageStockExchange = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-bse-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagBseCode = true;
          this.errorMessageBseCode = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-yahoo-bse-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagYahooBseCode = true;
          this.errorMessageYahooBseCode = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-nse-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagNseCode = true;
          this.errorMessageNseCode = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-yahoo-nse-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagYahooNseCode = true;
          this.errorMessageYahooNseCode = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-moneycontrol-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagMoneycontrolCode = true;
          this.errorMessageMoneycontrolCode = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-share-name") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagShareName = true;
          this.errorMessageShareName = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-purchase-date") {
          this.errorFlagPurchaseDate = true;
          this.errorMessagePurchaseDate = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-purchase-quantity") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagPurchaseQuantity = true;
          this.errorMessagePurchaseQuantity = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-purchase-price") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagPurchasePrice = true;
          this.errorMessagePurchasePrice = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-purchase-brokerage") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagPurchaseBrokerage = true;
          this.errorMessagePurchaseBrokerage = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-purchase-stt") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagPurchaseSTT = true;
          this.errorMessagePurchaseSTT = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-purchase-expenditure") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagPurchaseExpenditure = true;
          this.errorMessagePurchaseExpenditure = e.errorMessage;
        } else if (e.uiField === "sti-bhaaratha-purchase-non-expenditure") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
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
    if (id === "sti-bhaaratha-stock-exchange") {
      this.errorFlagStockExchange = false;
      this.errorMessageStockExchange = "";

      (document.getElementById("sti-bhaaratha-share-name") as HTMLElement).style.border = "";
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

      (document.getElementById("sti-bhaaratha-share-name") as HTMLElement).style.border = "";
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
  }

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }

  closeSuccess() {
    this.showSuccess = false;
    this.successMessage = "";
    this.router.navigate(["/users/BHA/EQ/short-term-investment"]);
  }

  onBackButtonClick() {
    this.router.navigate(["/users/BHA/EQ/short-term-investment"]);
  }

}
