import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-united-states-of-america-shares-one-analysis-header',
  templateUrl: './united-states-of-america-shares-one-analysis-header.component.html',
  styleUrls: ['./united-states-of-america-shares-one-analysis-header.component.css']
})
export class UnitedStatesOfAmericaSharesOneAnalysisHeaderComponent implements OnInit {

  @Output("onBackButtonClick") onBackButtonClickEventEmitter = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  onBackButtonClick() {
    this.onBackButtonClickEventEmitter.emit();
  }
}
