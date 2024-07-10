import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BhaarathaSharesCompleteAnalysisStoreService } from 'src/app/common/store/bhaaratha-shares-complete-analysis/bhaaratha-shares-complete-analysis-store.service';
import { BhaarathaSharesDividendYieldAnalysisStoreService } from 'src/app/common/store/bhaaratha-shares-dividend-yield-analysis/bhaaratha-shares-dividend-yield-analysis-store.service';

@Component({
  selector: 'app-bhaaratha-shares-one-analysis',
  templateUrl: './bhaaratha-shares-one-analysis.component.html',
  styleUrls: ['./bhaaratha-shares-one-analysis.component.css']
})
export class BhaarathaSharesOneAnalysisComponent implements OnInit {

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
    private bhaarathaSharesCompleteAnalysisStoreService: BhaarathaSharesCompleteAnalysisStoreService,
    private bhaarathaSharesDividendYieldAnalysisStoreService: BhaarathaSharesDividendYieldAnalysisStoreService
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
    this.router.navigate(["/users/BHA/EQ/" + this.analysisService]);
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
        for (let [key, val] of this.bhaarathaSharesCompleteAnalysisStoreService.getBhaarathaSharesOneSectorDetailsMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      } else if (type === "sector" && this.isLongTermOnly) {
        for (let [key, val] of this.bhaarathaSharesCompleteAnalysisStoreService.getBhaarathaSharesOneSectorDetailsLongTermOnlyMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      } else if (type === "category" && !this.isLongTermOnly) {
        for (let [key, val] of this.bhaarathaSharesCompleteAnalysisStoreService.getBhaarathaSharesOneCategoryDetailsMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      } else if (type === "category" && this.isLongTermOnly) {
        for (let [key, val] of this.bhaarathaSharesCompleteAnalysisStoreService.getBhaarathaSharesOneCategoryDetailsLongTermOnlyMap()) {
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
        for (let [key, val] of this.bhaarathaSharesDividendYieldAnalysisStoreService.getBhaarathaSharesOneSectorDetailsMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      } else if (type === "sector" && this.isLongTermOnly) {
        for (let [key, val] of this.bhaarathaSharesDividendYieldAnalysisStoreService.getBhaarathaSharesOneSectorDetailsLongTermOnlyMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      } else if (type === "category" && !this.isLongTermOnly) {
        for (let [key, val] of this.bhaarathaSharesDividendYieldAnalysisStoreService.getBhaarathaSharesOneCategoryDetailsMap()) {
          if (key === name) {
            Object.entries(val).forEach(([k, v]) => {
              oneAnalysisMap.set(k, v);
              numberOfScripts++;
            });
          }
        }
      } else if (type === "category" && this.isLongTermOnly) {
        for (let [key, val] of this.bhaarathaSharesDividendYieldAnalysisStoreService.getBhaarathaSharesOneCategoryDetailsLongTermOnlyMap()) {
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
