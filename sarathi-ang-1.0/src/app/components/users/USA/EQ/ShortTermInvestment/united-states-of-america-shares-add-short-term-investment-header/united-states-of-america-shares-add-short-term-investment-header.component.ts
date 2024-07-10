import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-united-states-of-america-shares-add-short-term-investment-header',
  templateUrl: './united-states-of-america-shares-add-short-term-investment-header.component.html',
  styleUrls: ['./united-states-of-america-shares-add-short-term-investment-header.component.css']
})
export class UnitedStatesOfAmericaSharesAddShortTermInvestmentHeaderComponent implements OnInit {

  @Output("onBackButtonClick") onBackButtonClickEventEmitter = new EventEmitter();
  constructor() { }

  ngOnInit(): void {
  }

  onBackButtonClick() {
    this.onBackButtonClickEventEmitter.emit();
  }

}
