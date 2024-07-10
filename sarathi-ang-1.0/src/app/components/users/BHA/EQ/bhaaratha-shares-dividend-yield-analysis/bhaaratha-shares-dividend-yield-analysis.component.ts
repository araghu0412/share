import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BhaarathaSharesDividendYieldAnalysisStoreService } from 'src/app/common/store/bhaaratha-shares-dividend-yield-analysis/bhaaratha-shares-dividend-yield-analysis-store.service';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-bhaaratha-shares-dividend-yield-analysis',
  templateUrl: './bhaaratha-shares-dividend-yield-analysis.component.html',
  styleUrls: ['./bhaaratha-shares-dividend-yield-analysis.component.css']
})
export class BhaarathaSharesDividendYieldAnalysisComponent implements OnInit {

  isRefreshButtonDisabled: boolean = false;
  isTypeDropdownDisabled: boolean = false;
  typeDropdownList: Array<any> = [
    {
      id: "sector",
      value: "Sector"
    },
    {
      id: "category",
      value: "Category"
    }
  ];
  typeDropdownPlaceholder: string = "Sector";
  loading: boolean = false;
  isLongTermOnly: boolean = false;
  isValueInPercentage: boolean = false;
  noRecords: boolean = false;
  isSectorAnalysis: boolean = false;
  isCategoryAnalysis: boolean = false;
  chartOptions: any = {};
  longTermRadioList: Array<any> = [
    {
      id: "Y",
      value: "Yes"
    },
    {
      id: "N",
      value: "No"
    }
  ];
  longTermRadioDefault: any = {};
  valueInPercentageRadioList: Array<any> = [
    {
      id: "Y",
      value: "Yes"
    },
    {
      id: "N",
      value: "No"
    }
  ];
  valueInPercentageRadioDefault: any = {};
  showSettings: boolean = false;
  isSettingsButtonDisabled: boolean = false;
  total: any = {};
  oneAnalysisDropdownList: Array<any> = [];
  showOneAnalysis: boolean = false;

  constructor(
    private bhaarathaSharesDividendYieldAnalysisStoreService : BhaarathaSharesDividendYieldAnalysisStoreService,
    private http: HttpClient,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.longTermRadioDefault = this.longTermRadioList[0];
    this.valueInPercentageRadioDefault = this.valueInPercentageRadioList[1];
    this.isLongTermOnly = this.longTermRadioDefault.value === "Yes" ? true : false;
    this.isValueInPercentage = this.valueInPercentageRadioDefault.value === "Yes" ? true : false;
    // First time is sector analysis
    this.isSectorAnalysis = true;
    this.getAnalysisDetails();
  }

  getAnalysisDetails() {

    this.loading = true;
    this.isRefreshButtonDisabled = true;
    this.isTypeDropdownDisabled = true;
    this.noRecords = false;
    this.isSettingsButtonDisabled = true;
    this.showOneAnalysis = false;

    if(this.bhaarathaSharesDividendYieldAnalysisStoreService.getBhaarathaSharesSectorMap().size === 0) {
      this.http.get(services.bhaarathaSharesDividendYieldAnalysis).subscribe((response: any) => {
        if (response.status === 204) {
          this.noRecords = true;
          this.loading = false;
          this.isRefreshButtonDisabled = false;
          this.isTypeDropdownDisabled = false;
          this.isSettingsButtonDisabled = false;
        } else {
          this.bhaarathaSharesDividendYieldAnalysisStoreService.setBhaarathaSharesSectorMap(response.bhaarathaSharesSectorMap);
          this.bhaarathaSharesDividendYieldAnalysisStoreService.setBhaarathaSharesSectorLongTermOnlyMap(response.bhaarathaSharesSectorLongTermOnlyMap);
          this.bhaarathaSharesDividendYieldAnalysisStoreService.setBhaarathaSharesCategoryMap(response.bhaarathaSharesCategoryMap);
          this.bhaarathaSharesDividendYieldAnalysisStoreService.setBhaarathaSharesCategoryLongTermOnlyMap(response.bhaarathaSharesCategoryLongTermOnlyMap);
          this.bhaarathaSharesDividendYieldAnalysisStoreService.setBhaarathaSharesOneSectorDetailsMap(response.bhaarathaSharesOneSectorDetailsMap);
          this.bhaarathaSharesDividendYieldAnalysisStoreService.setBhaarathaSharesOneSectorDetailsLongTermOnlyMap(response.bhaarathaSharesOneSectorDetailsLongTermOnlyMap);
          this.bhaarathaSharesDividendYieldAnalysisStoreService.setBhaarathaSharesOneCategoryDetailsMap(response.bhaarathaSharesOneCategoryDetailsMap);
          this.bhaarathaSharesDividendYieldAnalysisStoreService.setBhaarathaSharesOneCategoryDetailsLongTermOnlyMap(response.bhaarathaSharesOneCategoryDetailsLongTermOnlyMap);

          this.getAnalysisData();

          this.noRecords = false;
          this.loading = false;
          this.isRefreshButtonDisabled = false;
          this.isTypeDropdownDisabled = false;
          this.isSettingsButtonDisabled = false;
        }
      }, error => {
        console.log(error);
      });
    } else {
      this.getAnalysisData();

      this.noRecords = false;
      this.loading = false;
      this.isRefreshButtonDisabled = false;
      this.isTypeDropdownDisabled = false;
      this.isSettingsButtonDisabled = false;
    }
  }

  getAnalysisData() {
    let investedValueList: Array<any> = [];
    let currentValueList: Array<any> = [];
    let xAxisTitlesList: Array<any> = [];
    let chartTitle: string = "";
    this.oneAnalysisDropdownList = [];

    if (this.isSectorAnalysis && !this.isLongTermOnly) {
      for (let [key, val] of this.bhaarathaSharesDividendYieldAnalysisStoreService.getBhaarathaSharesSectorMap()) {
        if (key !== "total") {
          this.oneAnalysisDropdownList.push({ id: val.sectorName, value: val.sectorName});
          xAxisTitlesList.push(val.sectorName);
          if (!this.isValueInPercentage) {
            chartTitle = "Sector";
            investedValueList.push(val.investedValue);
            currentValueList.push(val.currentValue);
          } else {
            chartTitle = "Sector (%)";
            investedValueList.push(val.investedValuePercentage);
            currentValueList.push(val.currentValuePercentage);
          }
        } else {
          this.total = {
            numberOfScripts: val.numberOfScripts,
            investedValue: val.investedValue,
            currentValue: val.currentValue,
            profitLoss: val.profitLoss,
            profitLossPercentage: val.profitLossPercentage
          }
        }
      }
    } else if (this.isSectorAnalysis && this.isLongTermOnly) {
      for (let [key, val] of this.bhaarathaSharesDividendYieldAnalysisStoreService.getBhaarathaSharesSectorLongTermOnlyMap()) {
        if (key !== "total") {
          this.oneAnalysisDropdownList.push({ id: val.sectorName, value: val.sectorName});
          xAxisTitlesList.push(val.sectorName);
          if (!this.isValueInPercentage) {
            chartTitle = "Sector and Long Term Only";
            investedValueList.push(val.investedValue);
            currentValueList.push(val.currentValue);
          } else {
            chartTitle = "Sector and Long Term Only (%)";
            investedValueList.push(val.investedValuePercentage);
            currentValueList.push(val.currentValuePercentage);
          }
        } else {
          this.total = {
            numberOfScripts: val.numberOfScripts,
            investedValue: val.investedValue,
            currentValue: val.currentValue,
            profitLoss: val.profitLoss,
            profitLossPercentage: val.profitLossPercentage
          }
        }
      }
    } else if (this.isCategoryAnalysis && !this.isLongTermOnly) {
      for (let [key, val] of this.bhaarathaSharesDividendYieldAnalysisStoreService.getBhaarathaSharesCategoryMap()) {
        if (key !== "total") {
          this.oneAnalysisDropdownList.push({ id: val.categoryName, value: val.categoryName});
          xAxisTitlesList.push(val.categoryName);
          if (!this.isValueInPercentage) {
            chartTitle = "Category";
            investedValueList.push(val.investedValue);
            currentValueList.push(val.currentValue);
          } else {
            chartTitle = "Category (%)";
            investedValueList.push(val.investedValuePercentage);
            currentValueList.push(val.currentValuePercentage);
          }
        } else {
          this.total = {
            numberOfScripts: val.numberOfScripts,
            investedValue: val.investedValue,
            currentValue: val.currentValue,
            profitLoss: val.profitLoss,
            profitLossPercentage: val.profitLossPercentage
          }
        }
      }
    } else if (this.isCategoryAnalysis && this.isLongTermOnly) {
      for (let [key, val] of this.bhaarathaSharesDividendYieldAnalysisStoreService.getBhaarathaSharesCategoryLongTermOnlyMap()) {
        if (key !== "total") {
          this.oneAnalysisDropdownList.push({ id: val.categoryName, value: val.categoryName});
          xAxisTitlesList.push(val.categoryName);
          if (!this.isValueInPercentage) {
            chartTitle = "Category and Long Term Only";
            investedValueList.push(val.investedValue);
            currentValueList.push(val.currentValue);
          } else {
            chartTitle = "Category and Long Term Only (%)";
            investedValueList.push(val.investedValuePercentage);
            currentValueList.push(val.currentValuePercentage);
          }
        } else {
          this.total = {
            numberOfScripts: val.numberOfScripts,
            investedValue: val.investedValue,
            currentValue: val.currentValue,
            profitLoss: val.profitLoss,
            profitLossPercentage: val.profitLossPercentage
          }
        }
      }
    }

    this.buildChartData(investedValueList, currentValueList, xAxisTitlesList, chartTitle);
    this.showOneAnalysis = true;
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

  onRefreshClick() {
    // Clear store
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesSectorMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesSectorLongTermOnlyMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesCategoryMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesCategoryLongTermOnlyMap();;
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesOneSectorDetailsMap;
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesOneSectorDetailsLongTermOnlyMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesOneCategoryDetailsMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesOneCategoryDetailsLongTermOnlyMap();

    this.getAnalysisDetails();
  }

  onSectorAnalysisSelect() {      
    if (!this.isSectorAnalysis) {
      this.isSectorAnalysis = true;
      this.isCategoryAnalysis = false;
    }

    this.getAnalysisDetails();
  }

  onCategoryAnalysisSelect() {
    if (!this.isCategoryAnalysis) {
      this.isCategoryAnalysis = true;
      this.isSectorAnalysis = false;
    }

    this.getAnalysisDetails();
  }

  onSettingsClick() {
    this.showSettings = true;
  }

  onCloseSettingsClick() {
    this.showSettings = false;
  }

  onConfirmSettingsClick(settings: any) {
    this.isLongTermOnly = settings.longTermRadio.value === "Yes" ? true : false;
    this.isValueInPercentage = settings.valueInPercentageRadio.value === "Yes" ? true : false;
    this.getAnalysisDetails();
    this.showSettings = false;
    this.longTermRadioDefault = settings.longTermRadio;
    this.valueInPercentageRadioDefault = settings.valueInPercentageRadio;
  }

  onOneAnalysisDropdownSelect(selectedType: any) {
    let type = "";
    if (this.isSectorAnalysis) {
      type = "sector";
    } else if (this.isCategoryAnalysis) {
      type = "category";
    }

    let isLongTermOnly = this.isLongTermOnly ? "Yes" : "No";
    let isValueInPercentage = this.isValueInPercentage ? "Yes" : "No";

    this.router.navigate(["/users/BHA/EQ/one-analysis"], { queryParams: { type: type,  name: selectedType.value, isLongTermOnly: isLongTermOnly, isValueInPercentage: isValueInPercentage, analysisService: "dividend-yield-analysis" } });
  }

  onbackButtonClick() {
    this.router.navigate(["/users/BHA/EQ/analysis"]);
  }

}
