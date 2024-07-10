import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-investment-research-data',
  templateUrl: './bhaaratha-shares-investment-research-data.component.html',
  styleUrls: ['./bhaaratha-shares-investment-research-data.component.css']
})
export class BhaarathaSharesInvestmentResearchDataComponent implements OnInit {

  @Input() loading!: boolean;
  @Input() noRecords!: boolean;
  @Input() investmentResearchList!: Array<any>;
  @Input() sortingColumn!: string;
  @Input() sortingType!: string;
  @Output("sortInvestmentResearch") sortInvestmentResearchEventEmitter = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }

  sortInvestmentResearch(column: string) {
    this.sortInvestmentResearchEventEmitter.emit(column);
  }
}
