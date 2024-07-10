import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-one-share-details-bse-nse-data',
  templateUrl: './bhaaratha-shares-one-share-details-bse-nse-data.component.html',
  styleUrls: ['./bhaaratha-shares-one-share-details-bse-nse-data.component.css']
})
export class BhaarathaSharesOneShareDetailsBseNseDataComponent implements OnInit {

  @Input() oneShareDetails!: any;

  constructor() { }

  ngOnInit(): void {
  }

}
