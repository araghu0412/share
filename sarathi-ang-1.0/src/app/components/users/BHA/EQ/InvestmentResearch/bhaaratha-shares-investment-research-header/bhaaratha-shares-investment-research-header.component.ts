import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-investment-research-header',
  templateUrl: './bhaaratha-shares-investment-research-header.component.html',
  styleUrls: ['./bhaaratha-shares-investment-research-header.component.css']
})
export class BhaarathaSharesInvestmentResearchHeaderComponent implements OnInit {

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
