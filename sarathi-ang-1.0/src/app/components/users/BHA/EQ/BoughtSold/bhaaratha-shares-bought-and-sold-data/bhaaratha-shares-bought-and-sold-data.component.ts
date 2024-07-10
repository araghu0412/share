import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bhaaratha-shares-bought-and-sold-data',
  templateUrl: './bhaaratha-shares-bought-and-sold-data.component.html',
  styleUrls: ['./bhaaratha-shares-bought-and-sold-data.component.css']
})
export class BhaarathaSharesBoughtAndSoldDataComponent implements OnInit {

  @Input() type!: string;
  @Input() loading!: boolean;
  @Input() noRecords!: boolean;
  @Input() bhaarathaSharesList!: Array<any>;
  @Input() bhaarathaSharesTotal!: any;

  constructor(
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  onBhaarathaSharesScriptClick(event: any, bseCode: string, nseCode: string, moneycontrolCode: string, yahooBseCode: string, yahooNseCode: string) {
    this.router.navigate(["/users/BHA/EQ/one-share"], { queryParams: { type: this.type,  bseCode: bseCode, nseCode: encodeURIComponent(nseCode), moneycontrolCode: moneycontrolCode, yahooBseCode: encodeURIComponent(yahooBseCode), yahooNseCode: encodeURIComponent(yahooNseCode) } });
  }

}
