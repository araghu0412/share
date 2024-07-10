import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-money-manager-min-max-bar',
  templateUrl: './money-manager-min-max-bar.component.html',
  styleUrls: ['./money-manager-min-max-bar.component.css']
})
export class MoneyManagerMinMaxBarComponent implements OnInit {

  @Input() lowValue!: string;
  @Input() highValue!: string;
  @Input() highLowValue!: string;

  constructor() { }

  ngOnInit(): void {
  }

}
