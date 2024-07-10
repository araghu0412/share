import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BhaarathaSharesInvestmentResearchStoreService {

  bhaarathaSharesInvestmentResearchBehaviorSubjectList = new BehaviorSubject<Array<any>>([]);

  setBhaarathaSharesInvestmentResearchList(bhaarathaSharesInvestmentResearchResponseList: Array<any>) {
    this.clearBhaarathaSharesInvestmentResearchList();

    let bhaarathaSharesInvestmentResearchList: Array<any> = [];
    bhaarathaSharesInvestmentResearchResponseList.forEach(bhaarathaSharesInvestmentResearchResponse => {
      bhaarathaSharesInvestmentResearchList.push(bhaarathaSharesInvestmentResearchResponse);
    });

    this.bhaarathaSharesInvestmentResearchBehaviorSubjectList.next(bhaarathaSharesInvestmentResearchList);
  }

  getBhaarathaSharesInvestmentResearchList(): Array<any> {
    let bhaarathaSharesInvestmentResearchList: Array<any> = [];

    this.bhaarathaSharesInvestmentResearchBehaviorSubjectList.subscribe(bhaarathaSharesInvestmentResearchBehaviorSubjectList => {
      bhaarathaSharesInvestmentResearchList = bhaarathaSharesInvestmentResearchBehaviorSubjectList;
    });

    return bhaarathaSharesInvestmentResearchList;
  }

  clearBhaarathaSharesInvestmentResearchList() {
    this.bhaarathaSharesInvestmentResearchBehaviorSubjectList = new BehaviorSubject<Array<any>>([]);
  }

  constructor() { }
}
