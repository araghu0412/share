import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-analysis-services-header',
  templateUrl: './bhaaratha-shares-analysis-services-header.component.html',
  styleUrls: ['./bhaaratha-shares-analysis-services-header.component.css']
})
export class BhaarathaSharesAnalysisServicesHeaderComponent implements OnInit {

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
