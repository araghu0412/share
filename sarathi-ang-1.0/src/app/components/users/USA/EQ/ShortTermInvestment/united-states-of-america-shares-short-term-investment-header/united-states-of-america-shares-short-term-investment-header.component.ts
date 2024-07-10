import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-united-states-of-america-shares-short-term-investment-header',
  templateUrl: './united-states-of-america-shares-short-term-investment-header.component.html',
  styleUrls: ['./united-states-of-america-shares-short-term-investment-header.component.css']
})
export class UnitedStatesOfAmericaSharesShortTermInvestmentHeaderComponent implements OnInit {

  @Input() isRefreshButtonDisabled!: boolean;
  @Input() oneShareShortTermInvestment: boolean = false;
  @Output("onBackButtonClick") onBackButtonClickEventEmitter = new EventEmitter();
  @Output("onRefreshClick") onRefreshClickEventEmitter = new EventEmitter();
  @Output("onAddShortTermInvestmentClick") onAddShortTermInvestmentClickEventEmitter = new EventEmitter();
  
  constructor() { }

  ngOnInit(): void {
  }

  onBackButtonClick() {
    this.onBackButtonClickEventEmitter.emit();
  }

  onRefreshClick() {
    this.onRefreshClickEventEmitter.emit();
  }

  onAddShortTermInvestmentClick() {
    this.onAddShortTermInvestmentClickEventEmitter.emit();
  }

}
