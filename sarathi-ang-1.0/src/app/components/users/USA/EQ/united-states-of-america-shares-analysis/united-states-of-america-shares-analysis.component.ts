import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-united-states-of-america-shares-analysis',
  templateUrl: './united-states-of-america-shares-analysis.component.html',
  styleUrls: ['./united-states-of-america-shares-analysis.component.css']
})
export class UnitedStatesOfAmericaSharesAnalysisComponent implements OnInit {

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
    this.router.navigate(["/users/USA/EQ/" + selectedAnalysisService.id]);
  }
}
