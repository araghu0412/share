import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-short-term-investment-data',
  templateUrl: './bhaaratha-shares-short-term-investment-data.component.html',
  styleUrls: ['./bhaaratha-shares-short-term-investment-data.component.css']
})
export class BhaarathaSharesShortTermInvestmentDataComponent implements OnInit {

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

  onShortTermInvestmentScriptClick(bseCode: string) {
    this.onShortTermInvestmentScriptClickEventEmitter.emit(bseCode);
  }

  onDeleteShortTermInvestmentClick(bseCode: string, shareName: string) {
    this.onDeleteShortTermInvestmentClickEventEmitter.emit({ bseCode: bseCode, shareName: shareName } );
  }

}
