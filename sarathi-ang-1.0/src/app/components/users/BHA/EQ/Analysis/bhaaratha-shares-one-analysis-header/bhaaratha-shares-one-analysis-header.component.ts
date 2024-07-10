import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-one-analysis-header',
  templateUrl: './bhaaratha-shares-one-analysis-header.component.html',
  styleUrls: ['./bhaaratha-shares-one-analysis-header.component.css']
})
export class BhaarathaSharesOneAnalysisHeaderComponent implements OnInit {

  @Output("onBackButtonClick") onBackButtonClickEventEmitter = new EventEmitter();
  
  constructor() { }

  ngOnInit(): void {
  }

  onBackButtonClick() {
    this.onBackButtonClickEventEmitter.emit();
  }

}
