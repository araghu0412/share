import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-money-manager-loading-modal',
  templateUrl: './money-manager-loading-modal.component.html',
  styleUrls: ['./money-manager-loading-modal.component.css']
})
export class MoneyManagerLoadingModalComponent implements OnInit {

  @Input() headingDisplay!: string;

  constructor() { }

  ngOnInit(): void {
    if (!this.headingDisplay) {
      this.headingDisplay = "Your patience is greatly valued";
    }
  }

}
