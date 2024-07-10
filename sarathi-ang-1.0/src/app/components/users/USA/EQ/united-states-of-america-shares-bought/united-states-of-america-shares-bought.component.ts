import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-united-states-of-america-shares-bought',
  templateUrl: './united-states-of-america-shares-bought.component.html',
  styleUrls: ['./united-states-of-america-shares-bought.component.css']
})
export class UnitedStatesOfAmericaSharesBoughtComponent implements OnInit {

  type: string = "bought";
  isSearchFieldDisabled: boolean = false;
  isSearchButtonDisabled: boolean = false;
  isRefreshButtonDisabled: boolean = false;
  isHeaderDropdownDisabled: boolean = false;
  headerDropdownList: Array<any> = [
    {
      id: "1",
      value: "Consolidated"
    },
    {
      id: "2",
      value: "Non-Consolidated"
    }
  ];
  headerDropdownPlaceholder: string = "Consolidated";
  isNonConsolidated: boolean = false;
  isLongTermOnly: boolean = false;
  searchText: string = "";
  loading: boolean = true;
  noRecords: boolean = false;
  unitedStatesOfAmericaSharesList: Array<any> = [];
  unitedStatesOfAmericaSharesTotal: any = {};
  isSettingsButtonDisabled: boolean = false;
  // Error/Success Modal
  showError: boolean = false;
  errorMessage: string = "";
  // Bought shares settings
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
  showSettings: boolean = false;

  constructor(
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.longTermRadioDefault = this.longTermRadioList[1];
    this.isLongTermOnly = this.longTermRadioDefault.value === "Yes" ? true : false;
    this.getBoughtShares();
  }

  getBoughtShares() {
    this.loading = true;
    this.isSearchFieldDisabled = true;
    this.isRefreshButtonDisabled = true;
    this.isHeaderDropdownDisabled = true;
    this.noRecords = false;
    this.isSettingsButtonDisabled = true;

    this.http.get(services.unitedStatesOfAmericaSharesShares + "?type=bought&searchTerm=" + this.searchText + "&isNonConsolidated=" + this.isNonConsolidated + "&isLongTermOnly=" + this.isLongTermOnly).subscribe((response: any) => {
      if (response === null) {
        this.noRecords = true;
        this.loading = false;
        //
        this.isSearchFieldDisabled = false;
        this.isRefreshButtonDisabled = false;
        this.isHeaderDropdownDisabled = false;
        this.isSettingsButtonDisabled = false;
      } else {
        this.unitedStatesOfAmericaSharesList = response.unitedStatesOfAmericaSharesList;
        this.unitedStatesOfAmericaSharesTotal = response.unitedStatesOfAmericaSharesTotal
        this.loading = false;
        //
        this.isSearchFieldDisabled = false;
        this.isRefreshButtonDisabled = false;
        this.isHeaderDropdownDisabled = false;
        this.loading = false;
        this.isSettingsButtonDisabled = false;
      }
    }, error => {
      error.response.data.error.map((e: any) => {
        this.errorMessage = e.errorMessage;
        this.showError = true;
      });
    });
  }

  onSearchClick(unitedStatesOfAmericaShares: any) {
    this.searchText = unitedStatesOfAmericaShares.searchText;
    this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
    this.isLongTermOnly = unitedStatesOfAmericaShares.isLongTermOnly;
    this.getBoughtShares();
  }

  onRefreshClick(unitedStatesOfAmericaShares: any) {
    this.searchText = unitedStatesOfAmericaShares.searchText;
    this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
    this.isLongTermOnly = unitedStatesOfAmericaShares.isLongTermOnly;
    this.getBoughtShares();
  }

  onConsolidatedSelect(unitedStatesOfAmericaShares: any) {
    this.searchText = unitedStatesOfAmericaShares.searchText;
    this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
    this.isLongTermOnly = unitedStatesOfAmericaShares.isLongTermOnly;
    this.getBoughtShares();
  }

  onNonConsolidatedSelect(unitedStatesOfAmericaShares: any) {
    this.searchText = unitedStatesOfAmericaShares.searchText;
    this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
    this.isLongTermOnly = unitedStatesOfAmericaShares.isLongTermOnly;
    this.getBoughtShares();
  }

  onSettingsClick() {
    this.showSettings = true;
  }

  onCloseSettingsClick() {
    this.showSettings = false;
  }

  onConfirmSettingsClick(longTermRadio: any) {
    this.isLongTermOnly = longTermRadio.value === "Yes" ? true : false;
    this.getBoughtShares();
    this.showSettings = false;
    this.longTermRadioDefault = longTermRadio;
  }

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }
}
