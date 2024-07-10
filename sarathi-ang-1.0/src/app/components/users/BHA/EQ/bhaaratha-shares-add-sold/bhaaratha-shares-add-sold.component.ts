import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { getDefaultDate } from 'src/app/common/utils/utils';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-bhaaratha-shares-add-sold',
  templateUrl: './bhaaratha-shares-add-sold.component.html',
  styleUrls: ['./bhaaratha-shares-add-sold.component.css']
})
export class BhaarathaSharesAddSoldComponent implements OnInit {

  stockExchangeRadioList: Array<any> =  [];
  resetStockExchangeRadio: boolean = false;
  stockExchange: string = "";
  bseScriptCode: string = "";
  nseScriptCode: string = "";
  moneycontrolCode: string = "";
  shareName: string = "";
  shareNameLoading: boolean = false;
  sellDate: string = getDefaultDate("");
  resetSellDatePicker: boolean = false;
  sellQuantity: string = "";
  sellPrice: string = "";
  sellBrokerage: string = "";
  sellSTT: string = "";
  sellExpenditure: string = "";
  sellNonExpenditure: string = "";

  // Error field flags
  errorFlagStockExchange: boolean = false;
  errorFlagBseScriptCode: boolean = false;
  errorFlagNseScriptCode: boolean = false;
  errorFlagMoneycontrolCode: boolean = false;
  errorFlagShareName: boolean = false;
  errorFlagSellDate: boolean = false;
  errorFlagSellQuantity: boolean = false;
  errorFlagSellPrice: boolean = false;
  errorFlagSellBrokerage: boolean = false;
  errorFlagSellSTT: boolean = false;
  errorFlagSellExpenditure: boolean = false;
  errorFlagSellNonExpenditure: boolean = false;

  // Error field messages
  errorMessageStockExchange: string = "";
  errorMessageBseScriptCode: string = "";
  errorMessageNseScriptCode: string = "";
  errorMessageMoneycontrolCode: string = "";
  errorMessageShareName: string = "";
  errorMessageSellDate: string = "";
  errorMessageSellQuantity: string = "";
  errorMessageSellPrice: string = "";
  errorMessageSellBrokerage: string = "";
  errorMessageSellSTT: string = "";
  errorMessageSellExpenditure: string = "";
  errorMessageSellNonExpenditure: string = "";

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
  }

  onBackButtonClicked() {
    this.router.navigate(["/users/BHA/EQ/sold"]);
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

  onSellDateSelect(selectedSellDate: string) {
    this.resetSellDatePicker = false;
    this.inputOnFocus('sold-bhaaratha-share-sell-date');
    this.sellDate = selectedSellDate;
  }

  // Incase of the error, change the border from red to original
  inputOnFocus(id: string) {
    (document.getElementById(id) as HTMLElement).style.border = "";

    // Resetting error flags and error messages
    if (id === "sold-bhaaratha-share-stock-exchange") {
      this.errorFlagStockExchange = false;
      this.errorMessageStockExchange = "";

      (document.getElementById("sold-bhaaratha-share-script-name") as HTMLElement).style.border = "0.1rem solid black";
      this.errorFlagShareName = false;
      this.errorMessageShareName = "";
    } else if (id === "sold-bhaaratha-share-bse-script-code") {
      this.errorFlagBseScriptCode = false;
      this.errorMessageBseScriptCode = "";

      (document.getElementById("sold-bhaaratha-share-script-name") as HTMLElement).style.border = "0.1rem solid black";
      this.errorFlagShareName = false;
      this.errorMessageShareName = "";
    } else if (id === "sold-bhaaratha-share-nse-script-code") {
      this.errorFlagNseScriptCode = false;
      this.errorMessageNseScriptCode = "";
    } else if (id === "sold-bhaaratha-share-money-control-code") {
      this.errorFlagMoneycontrolCode = false;
      this.errorMessageMoneycontrolCode = "";

      (document.getElementById("sold-bhaaratha-share-script-name") as HTMLElement).style.border = "0.1rem solid black";
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
    } else if (id === "sold-bhaaratha-share-sell-non-expenditure") {
      this.errorFlagSellNonExpenditure = false;
      this.errorMessageSellNonExpenditure = "";
    }
  }

  onBhaarathaSharesAddSharesClear() {

    (document.getElementById("sold-bhaaratha-share-bse-script-code") as HTMLElement).style.border = "";
    (document.getElementById("sold-bhaaratha-share-nse-script-code") as HTMLElement).style.border = "";
    (document.getElementById("sold-bhaaratha-share-money-control-code") as HTMLElement).style.border = "";
    (document.getElementById("sold-bhaaratha-share-script-name") as HTMLElement).style.border = "";
    (document.getElementById("sold-bhaaratha-share-sell-quantity") as HTMLElement).style.border = "";
    (document.getElementById("sold-bhaaratha-share-sell-price") as HTMLElement).style.border = "";
    (document.getElementById("sold-bhaaratha-share-sell-brokerage") as HTMLElement).style.border = "";
    (document.getElementById("sold-bhaaratha-share-sell-stt") as HTMLElement).style.border = "";
    (document.getElementById("sold-bhaaratha-share-sell-expenditure") as HTMLElement).style.border = "";
    (document.getElementById("sold-bhaaratha-share-sell-non-expenditure") as HTMLElement).style.border = "";

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
  }

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

    this.http.post(services.bhaarathaSharesShares + "?type=sold", formData).subscribe((response: any) => {
      this.successMessage = response.message;
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
    }, (error: any) => {

      error.error.error.map((e: any) => {
        if (e.uiField === "sold-bhaaratha-share-stock-exchange") {
          this.errorFlagStockExchange = true;
          this.errorMessageStockExchange = e.errorMessage;
        } else if (e.uiField === "sold-bhaaratha-share-bse-script-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagBseScriptCode = true;
          this.errorMessageBseScriptCode = e.errorMessage;
        } else if (e.uiField === "sold-bhaaratha-share-nse-script-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagNseScriptCode = true;
          this.errorMessageNseScriptCode = e.errorMessage;
        } else if (e.uiField === "sold-bhaaratha-share-money-control-code") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagMoneycontrolCode = true;
          this.errorMessageMoneycontrolCode = e.errorMessage;
        } else if (e.uiField === "sold-bhaaratha-share-script-name") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagShareName = true;
          this.errorMessageShareName = e.errorMessage;
        } else if (e.uiField === "sold-bhaaratha-share-sell-date") {
          this.errorFlagSellDate = true;
          this.errorMessageSellDate = e.errorMessage;
        } else if (e.uiField === "sold-bhaaratha-share-sell-quantity") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagSellQuantity = true;
          this.errorMessageSellQuantity = e.errorMessage;
        } else if (e.uiField === "sold-bhaaratha-share-sell-price") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagSellPrice = true;
          this.errorMessageSellPrice = e.errorMessage;
        } else if (e.uiField === "sold-bhaaratha-share-sell-brokerage") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagSellBrokerage = true;
          this.errorMessageSellBrokerage = e.errorMessage;
        } else if (e.uiField === "sold-bhaaratha-share-sell-stt") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagSellSTT = true;
          this.errorMessageSellSTT = e.errorMessage;
        } else if (e.uiField === "sold-bhaaratha-share-sell-expenditure") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagSellExpenditure = true;
          this.errorMessageSellExpenditure = e.errorMessage;
        } else if (e.uiField === "sold-bhaaratha-share-sell-non-expenditure") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagSellNonExpenditure = true;
          this.errorMessageSellNonExpenditure = e.errorMessage;
        } else {
          this.showError = true;
          this.errorMessage = e.errorMessage;
        }
      });
    });
  }

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }

  closeSuccess() {
    this.showSuccess = false;
    this.successMessage = "";
    this.router.navigate(["/users/BHA/EQ/sold"]);
  }

}
