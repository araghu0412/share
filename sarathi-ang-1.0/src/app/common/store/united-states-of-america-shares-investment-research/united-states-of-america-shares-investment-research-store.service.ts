import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UnitedStatesOfAmericaSharesInvestmentResearchStoreService {

  unitedStatesOfAmericaSharesInvestmentResearchBehaviorSubjectList = new BehaviorSubject<Array<any>>([]);

  setUnitedStatesOfAmericaSharesInvestmentResearchList(unitedStatesOfAmericaSharesInvestmentResearchResponseList: Array<any>) {
    this.clearUnitedStatesOfAmericaSharesInvestmentResearchList();

    let unitedStatesOfAmericaSharesInvestmentResearchList: Array<any> = [];
    unitedStatesOfAmericaSharesInvestmentResearchResponseList.forEach(unitedStatesOfAmericaSharesInvestmentResearchResponse => {
      unitedStatesOfAmericaSharesInvestmentResearchList.push(unitedStatesOfAmericaSharesInvestmentResearchResponse);
    });

    this.unitedStatesOfAmericaSharesInvestmentResearchBehaviorSubjectList.next(unitedStatesOfAmericaSharesInvestmentResearchList);
  }

  getUnitedStatesOfAmericaSharesInvestmentResearchList(): Array<any> {
    let unitedStatesOfAmericaSharesInvestmentResearchList: Array<any> = [];

    this.unitedStatesOfAmericaSharesInvestmentResearchBehaviorSubjectList.subscribe(unitedStatesOfAmericaSharesInvestmentResearchBehaviorSubjectList => {
      unitedStatesOfAmericaSharesInvestmentResearchList = unitedStatesOfAmericaSharesInvestmentResearchBehaviorSubjectList;
    });

    return unitedStatesOfAmericaSharesInvestmentResearchList;
  }

  clearUnitedStatesOfAmericaSharesInvestmentResearchList() {
    this.unitedStatesOfAmericaSharesInvestmentResearchBehaviorSubjectList = new BehaviorSubject<Array<any>>([]);
  }

  constructor() { }
}
