import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-united-states-of-america-shares-one-share-short-term-investment',
  templateUrl: './united-states-of-america-shares-one-share-short-term-investment.component.html',
  styleUrls: ['./united-states-of-america-shares-one-share-short-term-investment.component.css']
})
export class UnitedStatesOfAmericaSharesOneShareShortTermInvestmentComponent implements OnInit {

  isRefreshButtonDisabled: boolean = false;
  loading: boolean = false;
  noRecords: boolean = false;
  shortTermInvestmentDataList: Array<any> = [];
  shortTermInvestmentTotal: any = {};
  yahooCode: string = "";
  showError: boolean = false;
  errorMessage: string = "";

  constructor(
    private http: HttpClient,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.yahooCode = params["yahooCode"];
    });
    this.getShortTermInvestment();
  }

  onBackButtonClick() {
    this.router.navigate(["/users/USA/EQ/short-term-investment"]);
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

    this.http.get(services.unitedStatesOfAmericaSharesShortTermInvestment + "?yahooCode=" + this.yahooCode).subscribe((response: any) => {
      if (response === null) {
        this.noRecords = true;
        this.loading = false;
        this.isRefreshButtonDisabled = false;
      } else {
        this.shortTermInvestmentDataList = response.shortTermInvestmentScriptsList;
        this.shortTermInvestmentTotal = response.shortTermInvestmentTotal
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

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }

}
