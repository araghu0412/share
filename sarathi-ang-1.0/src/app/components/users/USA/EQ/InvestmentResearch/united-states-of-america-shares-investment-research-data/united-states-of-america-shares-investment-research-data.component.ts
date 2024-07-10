import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-united-states-of-america-shares-investment-research-data',
  templateUrl: './united-states-of-america-shares-investment-research-data.component.html',
  styleUrls: ['./united-states-of-america-shares-investment-research-data.component.css']
})
export class UnitedStatesOfAmericaSharesInvestmentResearchDataComponent implements OnInit {

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
