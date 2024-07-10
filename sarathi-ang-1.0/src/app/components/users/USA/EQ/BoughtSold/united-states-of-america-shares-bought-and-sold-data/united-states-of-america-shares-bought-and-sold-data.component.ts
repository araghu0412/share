import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-united-states-of-america-shares-bought-and-sold-data',
  templateUrl: './united-states-of-america-shares-bought-and-sold-data.component.html',
  styleUrls: ['./united-states-of-america-shares-bought-and-sold-data.component.css']
})
export class UnitedStatesOfAmericaSharesBoughtAndSoldDataComponent implements OnInit {

  @Input() type!: string;
  @Input() loading!: boolean;
  @Input() noRecords!: boolean;
  @Input() unitedStatesOfAmericaSharesList!: Array<any>;
  @Input() unitedStatesOfAmericaSharesTotal!: any;

  constructor(
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  onUnitedStatesOfAmericaSharesScriptClick(event: any, scriptCode: string) {
    this.router.navigate(["/users/USA/EQ/one-share"], { queryParams: { type: this.type,  scriptCode } });
  }
}
