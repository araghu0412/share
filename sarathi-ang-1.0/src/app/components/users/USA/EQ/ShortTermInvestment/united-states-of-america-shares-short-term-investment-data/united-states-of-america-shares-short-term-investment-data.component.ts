import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-united-states-of-america-shares-short-term-investment-data',
  templateUrl: './united-states-of-america-shares-short-term-investment-data.component.html',
  styleUrls: ['./united-states-of-america-shares-short-term-investment-data.component.css']
})
export class UnitedStatesOfAmericaSharesShortTermInvestmentDataComponent implements OnInit {

  @Input() loading!: boolean;
  @Input() noRecords!: boolean;
  @Input() oneShareShortTermInvestment: boolean = false;
  @Input() shortTermInvestmentDataList!: Array<any>;
  @Input() shortTermInvestmentTotal!: any;
  @Output("onShortTermInvestmentScriptClick") onShortTermInvestmentScriptClickEventEmitter = new EventEmitter<string>();
  @Output("onDeleteShortTermInvestmentClick") onDeleteShortTermInvestmentClickEventEmitter = new EventEmitter<any>();

  constructor() { }

  ngOnInit(): void {
  }

  onShortTermInvestmentScriptClick(yahooCode: string) {
    this.onShortTermInvestmentScriptClickEventEmitter.emit(yahooCode);
  }

  onDeleteShortTermInvestmentClick(yahooCode: string, shareName: string) {
    this.onDeleteShortTermInvestmentClickEventEmitter.emit({ yahooCode: yahooCode, shareName: shareName } );
  }

}
