import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-united-states-of-america-shares-investment-research-header',
  templateUrl: './united-states-of-america-shares-investment-research-header.component.html',
  styleUrls: ['./united-states-of-america-shares-investment-research-header.component.css']
})
export class UnitedStatesOfAmericaSharesInvestmentResearchHeaderComponent implements OnInit {

  @Input() isRefreshButtonDisabled!: boolean;
  @Output("onRefreshClick") onRefreshClickEventEmitter = new EventEmitter();
  @Output("onBackButtonClick") onBackButtonClickEventEmitter = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  onRefreshClick() {
    this.onRefreshClickEventEmitter.emit();
  }

  onBackButtonClick() {
    this.onBackButtonClickEventEmitter.emit("onBackButtonClick");
  }
}
