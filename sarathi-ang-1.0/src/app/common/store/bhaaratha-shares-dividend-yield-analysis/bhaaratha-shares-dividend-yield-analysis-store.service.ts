import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BhaarathaSharesDividendYieldAnalysisStoreService {

  bhaarathaSharesSectorBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  bhaarathaSharesSectorLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  bhaarathaSharesCategoryBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  bhaarathaSharesCategoryLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  bhaarathaSharesOneSectorDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  bhaarathaSharesOneSectorDetailsLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  bhaarathaSharesOneCategoryDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  bhaarathaSharesOneCategoryDetailsLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());

  // Setters
  setBhaarathaSharesSectorMap(bhaarathaSharesSectorMapResponse: Map<string, any>) {
    let bhaarathaSharesSectorMap: Map<string, any> = new Map();

      Object.entries(bhaarathaSharesSectorMapResponse).forEach((entry: any) => {
        bhaarathaSharesSectorMap.set(entry[0], entry[1]);
      });

      this.bhaarathaSharesSectorBehaviorSubjectMap.next(bhaarathaSharesSectorMap);
  }

  setBhaarathaSharesSectorLongTermOnlyMap(bhaarathaSharesSectorLongTermOnlyMapResponse: Map<string, any>) {
    let bhaarathaSharesSectorLongTermOnlyMap: Map<string, any> = new Map();

    Object.entries(bhaarathaSharesSectorLongTermOnlyMapResponse).forEach((entry: any) => {
      bhaarathaSharesSectorLongTermOnlyMap.set(entry[0], entry[1]);
    });

    this.bhaarathaSharesSectorLongTermOnlyBehaviorSubjectMap.next(bhaarathaSharesSectorLongTermOnlyMap);
  }

  setBhaarathaSharesCategoryMap(bhaarathaSharesCategoryMapResponse: Map<string, any>) {
    let bhaarathaSharesCategoryMap: Map<string, any> = new Map();

    Object.entries(bhaarathaSharesCategoryMapResponse).forEach((entry: any) => {
      bhaarathaSharesCategoryMap.set(entry[0], entry[1]);
    });

    this.bhaarathaSharesCategoryBehaviorSubjectMap.next(bhaarathaSharesCategoryMap);
  }

  setBhaarathaSharesCategoryLongTermOnlyMap(bhaarathaSharesCategoryLongTermOnlyMapResponse: Map<string, any>) {
    let bhaarathaSharesCategoryLongTermOnlyMap: Map<string, any> = new Map();

    Object.entries(bhaarathaSharesCategoryLongTermOnlyMapResponse).forEach((entry: any) => {
      bhaarathaSharesCategoryLongTermOnlyMap.set(entry[0], entry[1]);
    });

    this.bhaarathaSharesCategoryLongTermOnlyBehaviorSubjectMap.next(bhaarathaSharesCategoryLongTermOnlyMap);
  }

  setBhaarathaSharesOneSectorDetailsMap(bhaarathaSharesOneSectorDetailsMapResponse: Map<string, any>) {
    let bhaarathaSharesOneSectorDetailsMap: Map<string, any> = new Map();

    Object.entries(bhaarathaSharesOneSectorDetailsMapResponse).forEach((entry: any) => {
      bhaarathaSharesOneSectorDetailsMap.set(entry[0], entry[1]);
    });

    this.bhaarathaSharesOneSectorDetailsBehaviorSubjectMap.next(bhaarathaSharesOneSectorDetailsMap);
  }

  setBhaarathaSharesOneSectorDetailsLongTermOnlyMap(bhaarathaSharesOneSectorDetailsLongTermOnlyMapResponse: Map<string, any>) {
    let bhaarathaSharesOneSectorDetailsLongTermOnlyMap: Map<string, any> = new Map();

    Object.entries(bhaarathaSharesOneSectorDetailsLongTermOnlyMapResponse).forEach((entry: any) => {
      bhaarathaSharesOneSectorDetailsLongTermOnlyMap.set(entry[0], entry[1]);
    });

    this.bhaarathaSharesOneSectorDetailsLongTermOnlyBehaviorSubjectMap.next(bhaarathaSharesOneSectorDetailsLongTermOnlyMap);
  }

  setBhaarathaSharesOneCategoryDetailsMap(bhaarathaSharesOneCategoryDetailsMapResponse: Map<string, any>) {
    let bhaarathaSharesOneCategoryDetailsMap: Map<string, any> = new Map();

    Object.entries(bhaarathaSharesOneCategoryDetailsMapResponse).forEach((entry: any) => {
      bhaarathaSharesOneCategoryDetailsMap.set(entry[0], entry[1]);
    });

    this.bhaarathaSharesOneCategoryDetailsBehaviorSubjectMap.next(bhaarathaSharesOneCategoryDetailsMap);
  }

  setBhaarathaSharesOneCategoryDetailsLongTermOnlyMap(bhaarathaSharesOneCategoryDetailsLongTermOnlyMapResponse: Map<string, any>) {
    let bhaarathaSharesOneCategoryDetailsLongTermOnlyMap: Map<string, any> = new Map();

    Object.entries(bhaarathaSharesOneCategoryDetailsLongTermOnlyMapResponse).forEach((entry: any) => {
      bhaarathaSharesOneCategoryDetailsLongTermOnlyMap.set(entry[0], entry[1]);
    });

    this.bhaarathaSharesOneCategoryDetailsLongTermOnlyBehaviorSubjectMap.next(bhaarathaSharesOneCategoryDetailsLongTermOnlyMap);
  }

  // Getters
  getBhaarathaSharesSectorMap(): Map<string, any> {
    let bhaarathaSharesSectorMap: Map<string, any> = new Map();

    this.bhaarathaSharesSectorBehaviorSubjectMap.subscribe(bhaarathaSharesSectorBehaviorSubjectMap => {
      bhaarathaSharesSectorMap = bhaarathaSharesSectorBehaviorSubjectMap;
    });

    return bhaarathaSharesSectorMap;
  }

  getBhaarathaSharesSectorLongTermOnlyMap(): Map<string, any> {
    let bhaarathaSharesSectorLongTermOnlyMap: Map<string, any> = new Map();

    this.bhaarathaSharesSectorLongTermOnlyBehaviorSubjectMap.subscribe(bhaarathaSharesSectorLongTermOnlyBehaviorSubjectMap => {
      bhaarathaSharesSectorLongTermOnlyMap = bhaarathaSharesSectorLongTermOnlyBehaviorSubjectMap;
    });

    return bhaarathaSharesSectorLongTermOnlyMap;
  }

  getBhaarathaSharesCategoryMap(): Map<string, any> {
    let bhaarathaSharesCategoryMap: Map<string, any> = new Map();

    this.bhaarathaSharesCategoryBehaviorSubjectMap.subscribe(bhaarathaSharesCategoryBehaviorSubjectMap => {
      bhaarathaSharesCategoryMap = bhaarathaSharesCategoryBehaviorSubjectMap;
    });

    return bhaarathaSharesCategoryMap;
  }

  getBhaarathaSharesCategoryLongTermOnlyMap(): Map<string, any> {
    let bhaarathaSharesCategoryLongTermOnlyMap: Map<string, any> = new Map();

    this.bhaarathaSharesCategoryLongTermOnlyBehaviorSubjectMap.subscribe(bhaarathaSharesCategoryLongTermOnlyBehaviorSubjectMap => {
      bhaarathaSharesCategoryLongTermOnlyMap = bhaarathaSharesCategoryLongTermOnlyBehaviorSubjectMap;
    });

    return bhaarathaSharesCategoryLongTermOnlyMap;
  }

  getBhaarathaSharesOneSectorDetailsMap(): Map<string, any> {
    let bhaarathaSharesOneSectorDetailsMap: Map<string, any> = new Map();

    this.bhaarathaSharesOneSectorDetailsBehaviorSubjectMap.subscribe(bhaarathaSharesOneSectorDetailsBehaviorSubjectMap => {
      bhaarathaSharesOneSectorDetailsMap = bhaarathaSharesOneSectorDetailsBehaviorSubjectMap;
    });

    return bhaarathaSharesOneSectorDetailsMap;
  }

  getBhaarathaSharesOneSectorDetailsLongTermOnlyMap(): Map<string, any> {
    let bhaarathaSharesOneSectorDetailsLongTermOnlyMap: Map<string, any> = new Map();

    this.bhaarathaSharesOneSectorDetailsLongTermOnlyBehaviorSubjectMap.subscribe(bhaarathaSharesOneSectorDetailsLongTermOnlyBehaviorSubjectMap => {
      bhaarathaSharesOneSectorDetailsLongTermOnlyMap = bhaarathaSharesOneSectorDetailsLongTermOnlyBehaviorSubjectMap;
    });

    return bhaarathaSharesOneSectorDetailsLongTermOnlyMap;
  }

  getBhaarathaSharesOneCategoryDetailsMap(): Map<string, any> {
    let bhaarathaSharesOneCategoryDetailsMap: Map<string, any> = new Map();

    this.bhaarathaSharesOneCategoryDetailsBehaviorSubjectMap.subscribe(bhaarathaSharesOneCategoryDetailsBehaviorSubjectMap => {
      bhaarathaSharesOneCategoryDetailsMap = bhaarathaSharesOneCategoryDetailsBehaviorSubjectMap;
    });

    return bhaarathaSharesOneCategoryDetailsMap;
  }

  getBhaarathaSharesOneCategoryDetailsLongTermOnlyMap(): Map<string, any> {
    let bhaarathaSharesOneCategoryDetailsLongTermOnlyMap: Map<string, any> = new Map();

    this.bhaarathaSharesOneCategoryDetailsLongTermOnlyBehaviorSubjectMap.subscribe(bhaarathaSharesOneCategoryDetailsLongTermOnlyBehaviorSubjectMap => {
      bhaarathaSharesOneCategoryDetailsLongTermOnlyMap = bhaarathaSharesOneCategoryDetailsLongTermOnlyBehaviorSubjectMap;
    });

    return bhaarathaSharesOneCategoryDetailsLongTermOnlyMap;
  }

  clearBhaarathaSharesSectorMap() {
    this.bhaarathaSharesSectorBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearBhaarathaSharesSectorLongTermOnlyMap() {
    this.bhaarathaSharesSectorLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearBhaarathaSharesCategoryMap() {
    this.bhaarathaSharesCategoryBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearBhaarathaSharesCategoryLongTermOnlyMap() {
    this.bhaarathaSharesCategoryLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearBhaarathaSharesOneSectorDetailsMap() {
    this.bhaarathaSharesOneSectorDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearBhaarathaSharesOneSectorDetailsLongTermOnlyMap() {
    this.bhaarathaSharesOneSectorDetailsLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearBhaarathaSharesOneCategoryDetailsMap() {
    this.bhaarathaSharesOneCategoryDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearBhaarathaSharesOneCategoryDetailsLongTermOnlyMap() {
    this.bhaarathaSharesOneCategoryDetailsLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  constructor() { }
}
