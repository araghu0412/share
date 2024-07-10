import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-united-states-of-america-shares-bought-sold-header',
  templateUrl: './united-states-of-america-shares-bought-sold-header.component.html',
  styleUrls: ['./united-states-of-america-shares-bought-sold-header.component.css']
})
export class UnitedStatesOfAmericaSharesBoughtSoldHeaderComponent implements OnInit {

  @Input() type!: string;
  @Input() isSearchFieldDisabled!: boolean;
  @Input() isSearchButtonDisabled!: boolean;
  @Input() isRefreshButtonDisabled!: boolean;
  @Input() isHeaderDropdownDisabled!: boolean;
  @Input() headerDropdownList!: Array<any>;
  @Input() headerDropdownPlaceholder!: string;
  @Input() isLongTermOnly: boolean = false;
  @Input() isSettingsButtonDisabled: boolean = true;
  @Output("onSearchClick") searchClickEventEmitter = new EventEmitter<any>();
  @Output("onRefreshClick") refreshClickEventEmitter = new EventEmitter<any>();
  @Output("onConsolidatedSelect") consolidatedSelectEventEmitter = new EventEmitter<any>();
  @Output("onNonConsolidatedSelect") nonConsolidatedSelectEventEmitter = new EventEmitter<any>();
  @Output("onLongTermClick") longTermEventEmitter = new EventEmitter<any>();
  @Output("onSettingsClick") settingsClickEventEmitter = new EventEmitter<any>();

  searchText: string = "";
  isNonConsolidated: boolean = false;
  resetHeaderDropdown: boolean = false;

  constructor(
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  onSearchClick() {
    this.searchClickEventEmitter.emit({ searchText: this.searchText, isNonConsolidated: this.isNonConsolidated, isLongTermOnly: this.isLongTermOnly });
  }

  onRefreshClick() {
    this.refreshClickEventEmitter.emit({ searchText: this.searchText, isNonConsolidated: this.isNonConsolidated, isLongTermOnly: this.isLongTermOnly });
  }

  onHeaderDropdownSelect(dropdownSelected: any) {
    this.resetHeaderDropdown = false;
    switch (dropdownSelected.value) {
      case "Consolidated":
        this.isNonConsolidated = false;
        this.consolidatedSelectEventEmitter.emit({ searchText: this.searchText, isNonConsolidated: this.isNonConsolidated, isLongTermOnly: this.isLongTermOnly });
        break;
      case "Non-Consolidated":
        this.isNonConsolidated = true;
        this.nonConsolidatedSelectEventEmitter.emit({ searchText: this.searchText, isNonConsolidated: this.isNonConsolidated, isLongTermOnly: this.isLongTermOnly });
        break;
    }
  }

  onLongTermClick() {
    this.isLongTermOnly = true;
    this.longTermEventEmitter.emit({ searchText: this.searchText, isNonConsolidated: this.isNonConsolidated, isLongTermOnly: this.isLongTermOnly })
  }

  onSharesBoughtClick() {
    this.router.navigate(["/users/USA/EQ/bought-shares"]);
  }

  onSharesSoldClick() {
    this.router.navigate(["/users/USA/EQ/sold-shares"]);
  }

  onSettingsClick() {
    this.settingsClickEventEmitter.emit();
  }
}
