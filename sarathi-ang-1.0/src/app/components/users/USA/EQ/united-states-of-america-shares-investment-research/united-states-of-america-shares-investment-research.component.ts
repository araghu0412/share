import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UnitedStatesOfAmericaSharesInvestmentResearchStoreService } from 'src/app/common/store/united-states-of-america-shares-investment-research/united-states-of-america-shares-investment-research-store.service';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-united-states-of-america-shares-investment-research',
  templateUrl: './united-states-of-america-shares-investment-research.component.html',
  styleUrls: ['./united-states-of-america-shares-investment-research.component.css']
})
export class UnitedStatesOfAmericaSharesInvestmentResearchComponent implements OnInit {

  loading: boolean = false;
  isRefreshButtonDisabled: boolean = false;
  noRecords: boolean = false;
  defaultInvestmentResearchList: Array<any> = [];
  investmentResearchList: Array<any> = [];
  sortInvestmentResearchMap: Map<string, any> = new Map();
  sortingColumn: string = "";
  sortingType: string = "";

  constructor(
    private unitedStatesOfAmericaSharesInvestmentResearchStoreService: UnitedStatesOfAmericaSharesInvestmentResearchStoreService,
    private http: HttpClient,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.defaultSortInvestmentResearchMap()
    this.getInvestmentResearch();
  }

  defaultSortInvestmentResearchMap() {
    this.sortInvestmentResearchMap.set("scriptName", "");
    this.sortInvestmentResearchMap.set("dailyRange", "");
    this.sortInvestmentResearchMap.set("52WRange", "");
    this.sortInvestmentResearchMap.set("ttmDividendYield", "");
  }

  getInvestmentResearch() {
    this.loading = true;
    this.isRefreshButtonDisabled = true;
    this.noRecords = false;

    if (this.unitedStatesOfAmericaSharesInvestmentResearchStoreService.getUnitedStatesOfAmericaSharesInvestmentResearchList().length === 0) {
      this.http.get(services.unitedStatesOfAmericaSharesInvestmentResearch).subscribe((response:any) => {
        if (response.status === 204) {
          this.noRecords = true;
          this.loading = false;
          this.isRefreshButtonDisabled = false;
        } else {
          this.unitedStatesOfAmericaSharesInvestmentResearchStoreService.setUnitedStatesOfAmericaSharesInvestmentResearchList(response.unitedStatesOfAmericaSharesInvestmentResearchList);
          this.noRecords = false;
          this.loading = false;
          this.isRefreshButtonDisabled = false;
        }

        this.defaultInvestmentResearchList = this.unitedStatesOfAmericaSharesInvestmentResearchStoreService.getUnitedStatesOfAmericaSharesInvestmentResearchList();
        this.buildInvestmentResearchList();
      }, error => {
        console.log(error);
      });
    } else {
      this.defaultInvestmentResearchList = this.unitedStatesOfAmericaSharesInvestmentResearchStoreService.getUnitedStatesOfAmericaSharesInvestmentResearchList();
      this.buildInvestmentResearchList();

      this.noRecords = false;
      this.loading = false;
      this.isRefreshButtonDisabled = false;
    }
  }

  buildInvestmentResearchList() {
    this.investmentResearchList = [];
    this.defaultInvestmentResearchList.forEach(defaultInvestmentResearch => {
      this.investmentResearchList.push(defaultInvestmentResearch);
    });
  }

  sortInvestmentResearch(column: string) {
    this.loading = true;
    this.isRefreshButtonDisabled = true;
    
    this.sortingColumn = column;
    this.sortingType = this.sortInvestmentResearchMap.get(column);

    this.defaultSortInvestmentResearchMap();

    if (this.sortingType === "") {
      this.sortingType = "ASC";
    } else if (this.sortingType === "ASC") {
      this.sortingType = "DESC";
    } else if (this.sortingType === "DESC") {
      this.sortingType = "ASC";
    }

    this.sortInvestmentResearchMap.set(this.sortingColumn, this.sortingType);

    if (this.sortingColumn === "scriptName") {
      if (this.sortingType === "ASC") {
        this.investmentResearchList.sort((a, b) => {
          return a.scriptName.localeCompare(b.scriptName);
        });
      } else if (this.sortingType === "DESC") {
        this.investmentResearchList.sort((a, b) => {
          return b.scriptName.localeCompare(a.scriptName);
        });
      }
    } else if (this.sortingColumn === "dailyRange") {
      if (this.sortingType === "ASC") {
        this.investmentResearchList.sort((a, b) => {
          return a.dailyRange - b.dailyRange;
        });
      } else if (this.sortingType === "DESC") {
        this.investmentResearchList.sort((a, b) => {
          return b.dailyRange - a.dailyRange;
        });
      }
    } else if (this.sortingColumn === "52WRange") {
      if (this.sortingType === "ASC") {
        this.investmentResearchList.sort((a, b) => {
          return a.fiftyTwoWeekRange - b.fiftyTwoWeekRange;
        });
      } else if (this.sortingType === "DESC") {
        this.investmentResearchList.sort((a, b) => {
          return b.fiftyTwoWeekRange - a.fiftyTwoWeekRange;
        });
      }
    } else if (this.sortingColumn === "ttmDividendYield") {
      if (this.sortingType === "ASC") {
        this.investmentResearchList.sort((a, b) => {
          return a.ttmDividendYield - b.ttmDividendYield;
        });
      } else if (this.sortingType === "DESC") {
        this.investmentResearchList.sort((a, b) => {
          return b.ttmDividendYield - a.ttmDividendYield;
        });
      }
    }

    this.loading = false;
    this.isRefreshButtonDisabled = false;
  }

  onRefreshClick() {
    this.defaultSortInvestmentResearchMap();
    this.sortingColumn = "";
    this.sortingType = "";
    this.unitedStatesOfAmericaSharesInvestmentResearchStoreService.clearUnitedStatesOfAmericaSharesInvestmentResearchList();
    this.getInvestmentResearch();
  }

  onBackButtonClick() {
    this.router.navigate(["/users/USA/EQ/analysis"]);
  }
}
