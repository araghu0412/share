import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-united-states-of-america-shares-home',
  templateUrl: './united-states-of-america-shares-home.component.html',
  styleUrls: ['./united-states-of-america-shares-home.component.css']
})
export class UnitedStatesOfAmericaSharesHomeComponent implements OnInit {

  otherServicesDropdownResetDropdown: boolean = false;
  otherServicesDropdownList: Array<any> = [];

  constructor(
    private router: Router
  ) { }

  ngOnInit(): void {
    this.otherServicesDropdownList = [
      {
        id: "short-term-investment",
        value: "Short Term Investment"
      }
    ];
  }

  onOtherServicesDropdownSelect(otherServicesDropdownSelected: any) {
    this.otherServicesDropdownResetDropdown = false;
    this.router.navigate(["/users/USA/EQ/" + otherServicesDropdownSelected.id]);
  }
}
