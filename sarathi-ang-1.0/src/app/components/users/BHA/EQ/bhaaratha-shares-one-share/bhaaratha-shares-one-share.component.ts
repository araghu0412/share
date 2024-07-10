import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-bhaaratha-shares-one-share',
  templateUrl: './bhaaratha-shares-one-share.component.html',
  styleUrls: ['./bhaaratha-shares-one-share.component.css']
})
export class BhaarathaSharesOneShareComponent implements OnInit {

  type: string = "";
  bseCode: string = "";
  nseCode: string = "";
  moneycontrolCode: string = "";
  yahooBseCode: string = "";
  yahooNseCode: string ="";
  loading: boolean = false;
  noRecords: boolean = false;
  isRefreshButtonDisabled: boolean = false;
  pageRefreshed: boolean = false;
  bhaarathaSharesList: Array<any> = [];
  bhaarathaSharesTotal: any = {};
  bseOneShareDetails: any = {};
  nseOneShareDetails: any = {};
  // Error/Success Modal
  showError: boolean = false;
  errorMessage: string = "";

  constructor(
    private activatedRoute: ActivatedRoute,
    private http: HttpClient,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.type = params["type"];
      this.bseCode = params["bseCode"];
      this.nseCode = params["nseCode"];
      this.moneycontrolCode = params["moneycontrolCode"];
      this.yahooBseCode = params["yahooBseCode"];
      this.yahooNseCode = params["yahooNseCode"];
    });

    this.getOneShareDetails();
  }

  getOneShareDetails() {
    this.loading = true;
    this.noRecords = false;
    this.isRefreshButtonDisabled = true;

    this.http.get(services.bhaarathaSharesOneShareDetails + "?type=" + this.type + "&bseCode=" + this.bseCode + "&nseCode=" + this.nseCode + "&moneycontrolCode=" + this.moneycontrolCode + "&yahooBseCode=" + this.yahooBseCode + "&yahooNseCode=" + this.yahooNseCode).subscribe((response: any) => {
      if (response === null) {
        this.noRecords = true;
        this.loading = false;
      } else {
        this.bhaarathaSharesList = response.bhaarathaSharesResponse.bhaarathaSharesList;
        this.bhaarathaSharesTotal = response.bhaarathaSharesResponse.bhaarathaSharesTotal;
        this.bseOneShareDetails = response.bseOneShareDetails;
        this.nseOneShareDetails = response.nseOneShareDetails;
        this.isRefreshButtonDisabled = false;
        this.loading = false;
        this.pageRefreshed = false;
      }
    }, error => {
      this.errorMessage = "";
      error.error.error.map((e: any) => {
        this.errorMessage = this.errorMessage + e.errorMessage +"<br>";
        this.showError = true;
      });
    });
  }

  onRefreshClick() {
    this.pageRefreshed = true;
    this.getOneShareDetails();
  }

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }

}
