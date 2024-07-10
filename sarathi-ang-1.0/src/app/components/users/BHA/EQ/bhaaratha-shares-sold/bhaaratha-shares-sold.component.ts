import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-bhaaratha-shares-sold',
  templateUrl: './bhaaratha-shares-sold.component.html',
  styleUrls: ['./bhaaratha-shares-sold.component.css']
})
export class BhaarathaSharesSoldComponent implements OnInit {

  type: string = "sold";
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
  searchText: string = "";
  loading: boolean = true;
  noRecords: boolean = false;
  bhaarathaSharesList: Array<any> = [];
  bhaarathaSharesTotal: Object = {};
  // Error/Success Modal
  showError: boolean  = false;
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

    this.http.get(services.bhaarathaSharesShares + "?type=sold&searchTerm=" + this.searchText + "&isNonConsolidated=" + this.isNonConsolidated).subscribe((response: any) => {
      if (response === null) {
        this.noRecords = true;
        this.loading = false;
        //
        this.isSearchFieldDisabled = false;
        this.isRefreshButtonDisabled = false;
      } else {
        this.bhaarathaSharesList = response.bhaarathaSharesList;
        this.bhaarathaSharesTotal = response.bhaarathaSharesTotal
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

  onSearchClick(bhaarathaShares: any) {
    this.searchText = bhaarathaShares.searchText;
    this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
    this.getSoldShares();
  }

  onRefreshClick(bhaarathaShares: any) {
    this.searchText = bhaarathaShares.searchText;
    this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
    this.getSoldShares();
  }

  onConsolidatedSelect(bhaarathaShares: any) {
    this.searchText = bhaarathaShares.searchText;
    this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
    this.getSoldShares();
  }

  onNonConsolidatedSelect(bhaarathaShares: any) {
    this.searchText = bhaarathaShares.searchText;
    this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
    this.getSoldShares();
  }

  closeError() {
    this.showError = false;
    this.errorMessage = "";
  }
}
