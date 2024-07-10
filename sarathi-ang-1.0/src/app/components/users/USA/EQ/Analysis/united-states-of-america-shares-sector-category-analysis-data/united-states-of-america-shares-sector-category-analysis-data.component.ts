import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-united-states-of-america-shares-sector-category-analysis-data',
  templateUrl: './united-states-of-america-shares-sector-category-analysis-data.component.html',
  styleUrls: ['./united-states-of-america-shares-sector-category-analysis-data.component.css']
})
export class UnitedStatesOfAmericaSharesSectorCategoryAnalysisDataComponent implements OnInit {

  @Input() loading!: boolean;
  @Input() noRecords!: boolean;
  @Input() chartOptions!: any;
  @Input() total!: any;

  constructor() { }

  ngOnInit(): void {
  }

}
