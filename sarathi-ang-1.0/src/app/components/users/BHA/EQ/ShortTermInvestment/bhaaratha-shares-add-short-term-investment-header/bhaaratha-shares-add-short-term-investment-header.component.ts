import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-add-short-term-investment-header',
  templateUrl: './bhaaratha-shares-add-short-term-investment-header.component.html',
  styleUrls: ['./bhaaratha-shares-add-short-term-investment-header.component.css']
})
export class BhaarathaSharesAddShortTermInvestmentHeaderComponent implements OnInit {

  @Output("onBackButtonClick") onBackButtonClickEventEmitter = new EventEmitter();
  constructor() { }

  ngOnInit(): void {
  }

  onBackButtonClick() {
    this.onBackButtonClickEventEmitter.emit();
  }

}
