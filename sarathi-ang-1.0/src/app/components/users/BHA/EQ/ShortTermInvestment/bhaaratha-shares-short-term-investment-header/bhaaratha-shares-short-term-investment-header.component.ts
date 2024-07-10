import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-short-term-investment-header',
  templateUrl: './bhaaratha-shares-short-term-investment-header.component.html',
  styleUrls: ['./bhaaratha-shares-short-term-investment-header.component.css']
})
export class BhaarathaSharesShortTermInvestmentHeaderComponent implements OnInit {

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
