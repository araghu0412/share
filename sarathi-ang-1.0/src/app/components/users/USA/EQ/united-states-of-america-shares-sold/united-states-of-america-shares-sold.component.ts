import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-united-states-of-america-shares-sold',
  templateUrl: './united-states-of-america-shares-sold.component.html',
  styleUrls: ['./united-states-of-america-shares-sold.component.css']
})
export class UnitedStatesOfAmericaSharesSoldComponent implements OnInit {

  type: string = "sold";
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
  searchText: string = "";
  loading: boolean = true;
  noRecords: boolean = false;
  unitedStatesOfAmericaSharesList: Array<any> = [];
  unitedStatesOfAmericaSharesTotal: any = {};
  // Error/Success Modal
  showError: boolean = false;
  errorMessage: string = "";

  constructor(
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.getSoldShares();
  }

  getSoldShares() {
    this.loading = true;
    this.isSearchFieldDisabled = true;
    this.isRefreshButtonDisabled = true;
    this.noRecords = false;

    this.http.get(services.unitedStatesOfAmericaSharesShares + "?type=sold&searchTerm=" + this.searchText + "&isNonConsolidated=" + this.isNonConsolidated).subscribe((response: any) => {
      if (response === null) {
        this.noRecords = true;
        this.loading = false;
        //
        this.isSearchFieldDisabled = false;
        this.isRefreshButtonDisabled = false;
      } else {
        this.unitedStatesOfAmericaSharesList = response.unitedStatesOfAmericaSharesList;
        this.unitedStatesOfAmericaSharesTotal = response.unitedStatesOfAmericaSharesTotal
        this.loading = false;
        //
        this.isSearchFieldDisabled = false;
        this.isRefreshButtonDisabled = false;
        this.loading = false;
      }
    }, error => {
      error.error.error.map((e: any) => {
        this.errorMessage = e.errorMessage;
        this.showError = true;
      });
    });
  }

  onSearchClick(unitedStatesOfAmericaShares: any) {
    this.searchText = unitedStatesOfAmericaShares.searchText;
    this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
    this.getSoldShares();
  }

  onRefreshClick(unitedStatesOfAmericaShares: any) {
    this.searchText = unitedStatesOfAmericaShares.searchText;
    this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
    this.getSoldShares();
  }

  onConsolidatedSelect(unitedStatesOfAmericaShares: any) {
    this.searchText = unitedStatesOfAmericaShares.searchText;
    this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
    this.getSoldShares();
  }

  onNonConsolidatedSelect(unitedStatesOfAmericaShares: any) {
    this.searchText = unitedStatesOfAmericaShares.searchText;
    this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
    this.getSoldShares();
  }

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }
}
