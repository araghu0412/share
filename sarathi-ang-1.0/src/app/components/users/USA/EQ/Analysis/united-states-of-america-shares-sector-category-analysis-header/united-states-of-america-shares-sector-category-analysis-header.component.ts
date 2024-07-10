import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-united-states-of-america-shares-sector-category-analysis-header',
  templateUrl: './united-states-of-america-shares-sector-category-analysis-header.component.html',
  styleUrls: ['./united-states-of-america-shares-sector-category-analysis-header.component.css']
})
export class UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeaderComponent implements OnInit {

  @Input() isRefreshButtonDisabled!: boolean;
  @Input() isTypeDropdownDisabled!: boolean
  @Input() typeDropdownList!: Array<any>;
  @Input() typeDropdownPlaceholder!: string;
  @Input() isSettingsButtonDisabled!: boolean;
  @Input() oneAnalysisDropdownList!: Array<any>;
  @Input() showOneAnalysis!: boolean;
  @Output("onRefreshClick") onRefreshClickEventEmitter = new EventEmitter();
  @Output("onSectorAnalysisSelect") onSectorAnalysisSelectEventEmitter = new EventEmitter();
  @Output("onCategoryAnalysisSelect") onCategoryAnalysisSelectEventEmitter = new EventEmitter();
  @Output("onSettingsClick") onSettingsClickEventEmitter = new EventEmitter();
  @Output("onOneAnalysisDropdownSelect") onOneAnalysisDropdownSelectEventEmitter = new EventEmitter<any>();
  @Output("onAnalysisServiceDropdownSelect") onAnalysisServiceDropdownSelectEventEmitter = new EventEmitter<any>();
  @Output("onBackButtonClick") onBackButtonClickEventEmitter = new EventEmitter();

  resetTypeDropdown: boolean = false;
  resetOneAnalysisDropdown: boolean = false;
  resetAnalysisServiceDropdown: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  onRefreshClick() {
    this.onRefreshClickEventEmitter.emit();
  }

  onTypeDropdownSelect(dropdownSelected: any) {
    this.resetTypeDropdown = false;
    switch (dropdownSelected.value) {
      case "Sector":
        this.onSectorAnalysisSelectEventEmitter.emit();
        break;
      case "Category":
        this.onCategoryAnalysisSelectEventEmitter.emit();
        break;
    }
  }

  onSettingsClick() {
    this.onSettingsClickEventEmitter.emit();
  }

  onOneAnalysisDropdownSelect(selectedDropdown: any) {
    this.onOneAnalysisDropdownSelectEventEmitter.emit(selectedDropdown);
  }

  onAnalysisServiceDropdownSelect(selectedDropdown: any) {
    this.resetAnalysisServiceDropdown = false;
    this.onAnalysisServiceDropdownSelectEventEmitter.emit();
  }

  onBackButtonClick() {
    this.onBackButtonClickEventEmitter.emit("selectedDropdown");
  }
}
