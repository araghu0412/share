import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bhaaratha-shares-one-share-details-header',
  templateUrl: './bhaaratha-shares-one-share-details-header.component.html',
  styleUrls: ['./bhaaratha-shares-one-share-details-header.component.css']
})
export class BhaarathaSharesOneShareDetailsHeaderComponent implements OnInit {

  @Input() type!: string;
  @Input() isRefreshButtonDisabled!: boolean;
  @Output("onRefreshClick") onRefreshClickEventEmitter = new EventEmitter<any>();

  constructor(
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  onBackButtonClick() {
    this.router.navigate(["/users/BHA/EQ/" + this.type]);
  }

  onRefreshClick() {
    this.onRefreshClickEventEmitter.emit();
  }

}
