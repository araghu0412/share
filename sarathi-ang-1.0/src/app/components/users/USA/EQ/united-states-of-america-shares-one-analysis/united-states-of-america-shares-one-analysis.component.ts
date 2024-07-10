import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UnitedStatesOfAmericaSharesCompleteAnalysisStoreService } from 'src/app/common/store/united-states-of-america-shares-complete-analysis/united-states-of-america-shares-complete-analysis-store.service';
import { UnitedStatesOfAmericaSharesDividendYieldAnalysisStoreService } from 'src/app/common/store/united-states-of-america-shares-dividend-yield-analysis/united-states-of-america-shares-dividend-yield-analysis-store.service';

@Component({
  selector: 'app-united-states-of-america-shares-one-analysis',
  templateUrl: './united-states-of-america-shares-one-analysis.component.html',
  styleUrls: ['./united-states-of-america-shares-one-analysis.component.css']
})
export class UnitedStatesOfAmericaSharesOneAnalysisComponent implements OnInit {

  loading: boolean = false;
  noRecords: boolean = false;
  isLongTermOnly: boolean = false;
  isValueInPercentage: boolean = false;
  chartOptions: any = {};
  total: any = {};
  analysisService: string = "";

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private unitedStatesOfAmericaSharesCompleteAnalysisStoreService: UnitedStatesOfAmericaSharesCompleteAnalysisStoreService,
    private unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService: UnitedStatesOfAmericaSharesDividendYieldAnalysisStoreService
  ) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.isLongTermOnly = params["isLongTermOnly"] === "Yes" ? true : false;
      this.isValueInPercentage = params["isValueInPercentage"] === "Yes" ? true : false;
      this.analysisService = params["analysisService"];
    });

    this.getOneAnalysisDetails();
  }

  onBackButtonClick() {
    this.router.navigate(["/users/USA/EQ/" + this.analysisService]);
  }

  getOneAnalysisDetails() {

    let name = "";
    let type = "";

    this.activatedRoute.queryParams.subscribe(params => {
      name = params["name"];
      type = params["type"];
    });

    this.loading = true;
    this.noRecords = false;

    let investedValueList = [];
    let currentValueList = [];
    let xAxisTitlesList = [];
    let chartTitle = name;

    let oneAnalysisMap = new Map();
    let numberOfScripts = 0;

    if (this.isLongTermOnly) {
      chartTitle = chartTitle + " and Long Term Only";
    }

    if (this.isValueInPercentage) {
      chartTitle = chartTitle + " (%)";
    }

    if (this.analysisService === "complete-analysis") {
      if (type === "sector" && !this.isLongTermOnly) {
        for (let [key, val] of this.unitedStatesOfAmericaSharesCompleteAnalysisStoreService.getUnitedStatesOfAmericaSharesOneSectorDetailsMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      } else if (type === "sector" && this.isLongTermOnly) {
        for (let [key, val] of this.unitedStatesOfAmericaSharesCompleteAnalysisStoreService.getUnitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      } else if (type === "category" && !this.isLongTermOnly) {
        for (let [key, val] of this.unitedStatesOfAmericaSharesCompleteAnalysisStoreService.getUnitedStatesOfAmericaSharesOneCategoryDetailsMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      } else if (type === "category" && this.isLongTermOnly) {
        for (let [key, val] of this.unitedStatesOfAmericaSharesCompleteAnalysisStoreService.getUnitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      }
    } else if (this.analysisService === "dividend-yield-analysis") {
      if (type === "sector" && !this.isLongTermOnly) {
        for (let [key, val] of this.unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService.getUnitedStatesOfAmericaSharesOneSectorDetailsMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      } else if (type === "sector" && this.isLongTermOnly) {
        for (let [key, val] of this.unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService.getUnitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      } else if (type === "category" && !this.isLongTermOnly) {
        for (let [key, val] of this.unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService.getUnitedStatesOfAmericaSharesOneCategoryDetailsMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      } else if (type === "category" && this.isLongTermOnly) {
        for (let [key, val] of this.unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService.getUnitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      }
    }

    // Decrementing number of scripts count to ignore total
    numberOfScripts--;

    for (let [key, val] of oneAnalysisMap) {
      if (key !== "total") {
        xAxisTitlesList.push(val.scriptName);

        if (!this.isValueInPercentage) {
          investedValueList.push(val.investedValue);
          currentValueList.push(val.currentValue);
        } else {
          investedValueList.push(val.investedValuePercentage);
          currentValueList.push(val.currentValuePercentage);
        }
      } else {
        this.total = {
          numberOfScripts: numberOfScripts,
          investedValue: val.investedValue,
          currentValue: val.currentValue,
          profitLoss: val.profitLoss,
          profitLossPercentage: val.profitLossPercentage
        }
      }
    }

    this.buildChartData(investedValueList, currentValueList, xAxisTitlesList, chartTitle);

    if (oneAnalysisMap.size === 0) {
      this.noRecords = true;
    }
    this.loading = false;
  }

  buildChartData(investedValueList: Array<any>, currentValueList:Array<any>, xAxisTitlesList:Array<any>, chartTitle: string) {
    this.chartOptions = {
      series: [
        {
          name: "Invested Value",
          data: investedValueList
        },
        {
          name: "Current Value",
          data: currentValueList
        }
      ],

      chart: {
        type: "bar",
        height: 500
      },

      title: {
        text: chartTitle,
        align: 'center'
      },

      states: {
        hover: {
          filter: {
            type: 'none'
          }
        },

        active: {
          filter: {
            type: 'none'
          }
        }
      },

      colors: ['#8B0000', '#1AA260'],

      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: "40%"
        }
      },

      dataLabels: {
        enabled: false
      },

      xaxis: {
        categories: xAxisTitlesList
      },

      fill: {
        colors: ['#8B0000', '#1AA260'],
        opacity: 1
      },
      tooltip: {
        enabled: true
      }
    };
  }

}
