import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bhaaratha-shares-home',
  templateUrl: './bhaaratha-shares-home.component.html',
  styleUrls: ['./bhaaratha-shares-home.component.css']
})
export class BhaarathaSharesHomeComponent implements OnInit {

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
      },
      {
        id: "bulk-upload",
        value: "Bulk Upload"
      }
    ];
  }

  onOtherServicesDropdownSelect(otherServicesDropdownSelected: any) {
    this.otherServicesDropdownResetDropdown = false;
    this.router.navigate(["/users/BHA/EQ/" + otherServicesDropdownSelected.id]);
  }
}
