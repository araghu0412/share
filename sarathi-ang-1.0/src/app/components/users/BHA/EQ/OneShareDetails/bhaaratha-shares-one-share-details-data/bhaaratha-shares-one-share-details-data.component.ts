import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-one-share-details-data',
  templateUrl: './bhaaratha-shares-one-share-details-data.component.html',
  styleUrls: ['./bhaaratha-shares-one-share-details-data.component.css']
})
export class BhaarathaSharesOneShareDetailsDataComponent implements OnInit {

  @Input() loading!: boolean;
  @Input() noRecords!: boolean;
  @Input() pageRefreshed!: boolean;
  @Input() bhaarathaSharesList!: Array<any>;
  @Input() bhaarathaSharesTotal!: any;
  @Input() bseOneShareDetails!: any;
  @Input() nseOneShareDetails!: any;

  stockExchangeRadioList: Array<any> = [];
  stockExchangeRadioDefault: any;
  stockExchangeSelected: string = "";

  constructor() { }

  ngOnInit(): void {
    this.getStockExchangeRadioList();
    this.getStockExchangeRadioDefault();
    this.stockExchangeSelected = "BSE";
  }

  ngOnChanges(): void {
    if (this.pageRefreshed) {
      this.getStockExchangeRadioList();
      this.getStockExchangeRadioDefault();
      this.stockExchangeSelected = "BSE";
    }
  }

  getStockExchangeRadioList() {
    this.stockExchangeRadioList = [
      {
        id: "bse",
        value: "BSE"
      },
      {
        id: "nse",
        value: "NSE"
      }
    ];
  }

  getStockExchangeRadioDefault() {
    this.stockExchangeRadioDefault = this.stockExchangeRadioList[0];
  }

  onStockExchangeSelect(stockExchange: any) {
    this.stockExchangeSelected = stockExchange.value;
  }

}
