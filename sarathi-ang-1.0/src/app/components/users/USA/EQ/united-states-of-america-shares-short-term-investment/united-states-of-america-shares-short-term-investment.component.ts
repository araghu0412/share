import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-united-states-of-america-shares-short-term-investment',
  templateUrl: './united-states-of-america-shares-short-term-investment.component.html',
  styleUrls: ['./united-states-of-america-shares-short-term-investment.component.css']
})
export class UnitedStatesOfAmericaSharesShortTermInvestmentComponent implements OnInit {

  loading: boolean = false;
  noRecords: boolean = false;
  isRefreshButtonDisabled: boolean = false;
  shortTermInvestmentDataList: Array<any> = [];
  shortTermInvestmentTotal: any = {};
  yahooCodeToDelete: string = "";
  confirmationMessage: string = "";
  showConfirmation: boolean = false;
  showSuccess: boolean = false;
  successMessage: string = "";
  showError: boolean = false;
  errorMessage: string = "";

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getShortTermInvestment();
  }

  onRefreshClick() {
    this.getShortTermInvestment();
  }

  onAddShortTermInvestmentClick() {
    this.router.navigate(["/users/USA/EQ/add-short-term-investment"]);
  }

  getShortTermInvestment() {
    this.loading = true;
    this.noRecords = false;
    this.shortTermInvestmentDataList = [];
    this.shortTermInvestmentTotal = {};
    this.isRefreshButtonDisabled = true;

    this.http.get(services.unitedStatesOfAmericaSharesShortTermInvestment).subscribe((response: any) => {
      if (response === null) {
        this.noRecords = true;
        this.loading = false;
        this.isRefreshButtonDisabled = false;
      } else {
        this.shortTermInvestmentDataList = response.shortTermInvestmentScriptsList;
        this.shortTermInvestmentTotal = response.shortTermInvestmentTotal;
        this.loading = false;
        this.isRefreshButtonDisabled = false;
      }
    }, error => {
      error.error.error.map((e: any) =>{
        this.errorMessage = e.errorMessage;
        this.showError = true;
      });
    });
  }

  onShortTermInvestmentScriptClick(yahooCode: string) {
    this.router.navigate(["/users/USA/EQ/one-share-short-term-investment"], { queryParams: { yahooCode: yahooCode } });
  }

  onDeleteShortTermInvestmentClick(scriptDetails: any) {
    this.yahooCodeToDelete = scriptDetails.yahooCode;
    this.confirmationMessage = "You are about to delete a script [" + scriptDetails.shareName + "] from the Short Term Investment. This will delete all stocks.";
    this.showConfirmation = true;
  }

  onCloseConfirmation() {
    this.showConfirmation = false;
    this.yahooCodeToDelete = "";
  }

  onConfirmationContinue() {

    this.showConfirmation = false;
    this.http.delete(services.unitedStatesOfAmericaSharesShortTermInvestment + "?yahooCode=" + this.yahooCodeToDelete).subscribe((response: any) => {
      if (response === null) {
        this.errorMessage = "Short term investment does not exist for script code " + this.yahooCodeToDelete;
        this.showError = true;
      } else {
        this.showSuccess = response.status;
        this.successMessage = response.message;
        this.yahooCodeToDelete = "";
        this.getShortTermInvestment();
      }
    }, error => {
      error.error.error.map((e: any) => {
        this.errorMessage = e.errorMessage;
        this.showError = true;
        this.yahooCodeToDelete = "";
      });
    });
  }

  closeSuccess() {
    this.showSuccess = false;
    this.successMessage = "";
  }

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }

}
