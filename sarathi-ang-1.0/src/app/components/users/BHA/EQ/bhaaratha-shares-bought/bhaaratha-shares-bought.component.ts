import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-bhaaratha-shares-bought',
  templateUrl: './bhaaratha-shares-bought.component.html',
  styleUrls: ['./bhaaratha-shares-bought.component.css']
})
export class BhaarathaSharesBoughtComponent implements OnInit {

  type: string = "bought";
  isSearchFieldDisabled: boolean = false;
  isSearchButtonDisabled: boolean = false;
  isRefreshButtonDisabled: boolean = false;
  isHeaderDropdownDisabled: boolean = false;
  headerDropdownList: Array<any> =
  [
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
  bhaarathaSharesList: Array<any> = [];
  bhaarathaSharesTotal: Object = {};
  isSettingsButtonDisabled: boolean = false;
  // Error/Success Modal
  showError: boolean  = false;
  errorMessage: string = "";
  // Bought shares settings
  longTermRadioList: Array<any> =
  [
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

    this.http.get(services.bhaarathaSharesShares + "?type=bought&searchTerm=" + this.searchText + "&isNonConsolidated=" + this.isNonConsolidated + "&isLongTermOnly=" + this.isLongTermOnly).subscribe((response: any) => {
      if (response === null) {
        this.noRecords = true;
        this.loading = false;
        //
        this.isSearchFieldDisabled = false;
        this.isRefreshButtonDisabled = false;
        this.isHeaderDropdownDisabled = false;
        this.isSettingsButtonDisabled = false;
      } else {
        this.bhaarathaSharesList = response.bhaarathaSharesList;
        this.bhaarathaSharesTotal = response.bhaarathaSharesTotal
        this.loading = false;
        //
        this.isSearchFieldDisabled = false;
        this.isRefreshButtonDisabled = false;
        this.isHeaderDropdownDisabled = false;
        this.loading = false;
        this.isSettingsButtonDisabled = false;
      }
    }, error => {
      error.error.error.map((e: any) => {
        this.errorMessage = e.errorMessage;
        this.showError = true;
      });
    });
  }

  onSearchClick(bhaarathaShares: any) {
    this.searchText = bhaarathaShares.searchText;
    this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
    this.isLongTermOnly = bhaarathaShares.isLongTermOnly;
    this.getBoughtShares();
  }

  onRefreshClick(bhaarathaShares: any) {
    this.searchText = bhaarathaShares.searchText;
    this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
    this.isLongTermOnly = bhaarathaShares.isLongTermOnly;
    this.getBoughtShares();
  }

  onConsolidatedSelect(bhaarathaShares: any) {
    this.searchText = bhaarathaShares.searchText;
    this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
    this.isLongTermOnly = bhaarathaShares.isLongTermOnly;
    this.getBoughtShares();
  }

  onNonConsolidatedSelect(bhaarathaShares: any) {
    this.searchText = bhaarathaShares.searchText;
    this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
    this.isLongTermOnly = bhaarathaShares.isLongTermOnly;
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
