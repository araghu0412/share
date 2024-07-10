import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-sector-category-analysis-data',
  templateUrl: './bhaaratha-shares-sector-category-analysis-data.component.html',
  styleUrls: ['./bhaaratha-shares-sector-category-analysis-data.component.css']
})
export class BhaarathaSharesSectorCategoryAnalysisDataComponent implements OnInit {

  @Input() loading!: boolean;
  @Input() noRecords!: boolean;
  @Input() chartOptions!: any;
  @Input() total!: any;

  constructor() { }

  ngOnInit(): void {
  }

}
