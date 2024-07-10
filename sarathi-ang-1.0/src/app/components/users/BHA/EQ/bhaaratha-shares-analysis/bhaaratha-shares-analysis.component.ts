import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bhaaratha-shares-analysis',
  templateUrl: './bhaaratha-shares-analysis.component.html',
  styleUrls: ['./bhaaratha-shares-analysis.component.css']
})
export class BhaarathaSharesAnalysisComponent implements OnInit {

  resetAnalysisServicesDropdown: boolean = false;
  analysisServicesDropdownList: Array<any> = [
    {
      id: "complete-analysis",
      value: "Complete Analysis"
    },
    {
      id: "dividend-yield-analysis",
      value: "Dividend Yield Analysis"
    },
    {
      id: "investment-research",
      value: "Investment Research"
    }
  ];

  constructor(
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  onAnalysisServicesDropdownSelect(selectedAnalysisService: any) {
    this.router.navigate(["/users/BHA/EQ/" + selectedAnalysisService.id]);
  }
}
