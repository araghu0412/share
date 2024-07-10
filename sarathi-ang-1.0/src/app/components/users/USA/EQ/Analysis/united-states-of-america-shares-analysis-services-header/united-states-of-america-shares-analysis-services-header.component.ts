import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-united-states-of-america-shares-analysis-services-header',
  templateUrl: './united-states-of-america-shares-analysis-services-header.component.html',
  styleUrls: ['./united-states-of-america-shares-analysis-services-header.component.css']
})
export class UnitedStatesOfAmericaSharesAnalysisServicesHeaderComponent implements OnInit {

  @Input() resetAnalysisServicesDropdown!: boolean;
  @Input() analysisServicesDropdownList!: Array<any>;
  @Output("onAnalysisServicesDropdownSelect") onAnalysisServicesDropdownSelectEventEmitter = new EventEmitter<any>();

  constructor() { }

  ngOnInit(): void {
  }

  onAnalysisServicesDropdownSelect(selectedAnalysisServices: any) {
    this.onAnalysisServicesDropdownSelectEventEmitter.emit(selectedAnalysisServices);
  }
}
