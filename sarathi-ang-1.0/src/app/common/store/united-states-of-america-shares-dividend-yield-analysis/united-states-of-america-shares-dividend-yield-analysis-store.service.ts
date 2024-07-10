import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UnitedStatesOfAmericaSharesDividendYieldAnalysisStoreService {

  unitedStatesOfAmericaSharesSectorBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  unitedStatesOfAmericaSharesSectorLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  unitedStatesOfAmericaSharesCategoryBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  unitedStatesOfAmericaSharesCategoryLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  unitedStatesOfAmericaSharesOneSectorDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  unitedStatesOfAmericaSharesOneCategoryDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());

  // Setters
  setUnitedStatesOfAmericaSharesSectorMap(unitedStatesOfAmericaSharesSectorMapResponse: Map<string, any>) {
    let unitedStatesOfAmericaSharesSectorMap: Map<string, any> = new Map();

      Object.entries(unitedStatesOfAmericaSharesSectorMapResponse).forEach((entry: any) => {
        unitedStatesOfAmericaSharesSectorMap.set(entry[0], entry[1]);
      });

      this.unitedStatesOfAmericaSharesSectorBehaviorSubjectMap.next(unitedStatesOfAmericaSharesSectorMap);
  }

  setUnitedStatesOfAmericaSharesSectorLongTermOnlyMap(unitedStatesOfAmericaSharesSectorLongTermOnlyMapResponse: Map<string, any>) {
    let unitedStatesOfAmericaSharesSectorLongTermOnlyMap: Map<string, any> = new Map();

    Object.entries(unitedStatesOfAmericaSharesSectorLongTermOnlyMapResponse).forEach((entry: any) => {
      unitedStatesOfAmericaSharesSectorLongTermOnlyMap.set(entry[0], entry[1]);
    });

    this.unitedStatesOfAmericaSharesSectorLongTermOnlyBehaviorSubjectMap.next(unitedStatesOfAmericaSharesSectorLongTermOnlyMap);
  }

  setUnitedStatesOfAmericaSharesCategoryMap(unitedStatesOfAmericaSharesCategoryMapResponse: Map<string, any>) {
    let unitedStatesOfAmericaSharesCategoryMap: Map<string, any> = new Map();

    Object.entries(unitedStatesOfAmericaSharesCategoryMapResponse).forEach((entry: any) => {
      unitedStatesOfAmericaSharesCategoryMap.set(entry[0], entry[1]);
    });

    this.unitedStatesOfAmericaSharesCategoryBehaviorSubjectMap.next(unitedStatesOfAmericaSharesCategoryMap);
  }

  setUnitedStatesOfAmericaSharesCategoryLongTermOnlyMap(unitedStatesOfAmericaSharesCategoryLongTermOnlyMapResponse: Map<string, any>) {
    let unitedStatesOfAmericaSharesCategoryLongTermOnlyMap: Map<string, any> = new Map();

    Object.entries(unitedStatesOfAmericaSharesCategoryLongTermOnlyMapResponse).forEach((entry: any) => {
      unitedStatesOfAmericaSharesCategoryLongTermOnlyMap.set(entry[0], entry[1]);
    });

    this.unitedStatesOfAmericaSharesCategoryLongTermOnlyBehaviorSubjectMap.next(unitedStatesOfAmericaSharesCategoryLongTermOnlyMap);
  }

  setUnitedStatesOfAmericaSharesOneSectorDetailsMap(unitedStatesOfAmericaSharesOneSectorDetailsMapResponse: Map<string, any>) {
    let unitedStatesOfAmericaSharesOneSectorDetailsMap: Map<string, any> = new Map();

    Object.entries(unitedStatesOfAmericaSharesOneSectorDetailsMapResponse).forEach((entry: any) => {
      unitedStatesOfAmericaSharesOneSectorDetailsMap.set(entry[0], entry[1]);
    });

    this.unitedStatesOfAmericaSharesOneSectorDetailsBehaviorSubjectMap.next(unitedStatesOfAmericaSharesOneSectorDetailsMap);
  }

  setUnitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap(unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMapResponse: Map<string, any>) {
    let unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap: Map<string, any> = new Map();

    Object.entries(unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMapResponse).forEach((entry: any) => {
      unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap.set(entry[0], entry[1]);
    });

    this.unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyBehaviorSubjectMap.next(unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap);
  }

  setUnitedStatesOfAmericaSharesOneCategoryDetailsMap(unitedStatesOfAmericaSharesOneCategoryDetailsMapResponse: Map<string, any>) {
    let unitedStatesOfAmericaSharesOneCategoryDetailsMap: Map<string, any> = new Map();

    Object.entries(unitedStatesOfAmericaSharesOneCategoryDetailsMapResponse).forEach((entry: any) => {
      unitedStatesOfAmericaSharesOneCategoryDetailsMap.set(entry[0], entry[1]);
    });

    this.unitedStatesOfAmericaSharesOneCategoryDetailsBehaviorSubjectMap.next(unitedStatesOfAmericaSharesOneCategoryDetailsMap);
  }

  setUnitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap(unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMapResponse: Map<string, any>) {
    let unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap: Map<string, any> = new Map();

    Object.entries(unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMapResponse).forEach((entry: any) => {
      unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap.set(entry[0], entry[1]);
    });

    this.unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyBehaviorSubjectMap.next(unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap);
  }

  // Getters
  getUnitedStatesOfAmericaSharesSectorMap(): Map<string, any> {
    let unitedStatesOfAmericaSharesSectorMap: Map<string, any> = new Map();

    this.unitedStatesOfAmericaSharesSectorBehaviorSubjectMap.subscribe(unitedStatesOfAmericaSharesSectorBehaviorSubjectMap => {
      unitedStatesOfAmericaSharesSectorMap = unitedStatesOfAmericaSharesSectorBehaviorSubjectMap;
    });

    return unitedStatesOfAmericaSharesSectorMap;
  }

  getUnitedStatesOfAmericaSharesSectorLongTermOnlyMap(): Map<string, any> {
    let unitedStatesOfAmericaSharesSectorLongTermOnlyMap: Map<string, any> = new Map();

    this.unitedStatesOfAmericaSharesSectorLongTermOnlyBehaviorSubjectMap.subscribe(unitedStatesOfAmericaSharesSectorLongTermOnlyBehaviorSubjectMap => {
      unitedStatesOfAmericaSharesSectorLongTermOnlyMap = unitedStatesOfAmericaSharesSectorLongTermOnlyBehaviorSubjectMap;
    });

    return unitedStatesOfAmericaSharesSectorLongTermOnlyMap;
  }

  getUnitedStatesOfAmericaSharesCategoryMap(): Map<string, any> {
    let unitedStatesOfAmericaSharesCategoryMap: Map<string, any> = new Map();

    this.unitedStatesOfAmericaSharesCategoryBehaviorSubjectMap.subscribe(unitedStatesOfAmericaSharesCategoryBehaviorSubjectMap => {
      unitedStatesOfAmericaSharesCategoryMap = unitedStatesOfAmericaSharesCategoryBehaviorSubjectMap;
    });

    return unitedStatesOfAmericaSharesCategoryMap;
  }

  getUnitedStatesOfAmericaSharesCategoryLongTermOnlyMap(): Map<string, any> {
    let unitedStatesOfAmericaSharesCategoryLongTermOnlyMap: Map<string, any> = new Map();

    this.unitedStatesOfAmericaSharesCategoryLongTermOnlyBehaviorSubjectMap.subscribe(unitedStatesOfAmericaSharesCategoryLongTermOnlyBehaviorSubjectMap => {
      unitedStatesOfAmericaSharesCategoryLongTermOnlyMap = unitedStatesOfAmericaSharesCategoryLongTermOnlyBehaviorSubjectMap;
    });

    return unitedStatesOfAmericaSharesCategoryLongTermOnlyMap;
  }

  getUnitedStatesOfAmericaSharesOneSectorDetailsMap(): Map<string, any> {
    let unitedStatesOfAmericaSharesOneSectorDetailsMap: Map<string, any> = new Map();

    this.unitedStatesOfAmericaSharesOneSectorDetailsBehaviorSubjectMap.subscribe(unitedStatesOfAmericaSharesOneSectorDetailsBehaviorSubjectMap => {
      unitedStatesOfAmericaSharesOneSectorDetailsMap = unitedStatesOfAmericaSharesOneSectorDetailsBehaviorSubjectMap;
    });

    return unitedStatesOfAmericaSharesOneSectorDetailsMap;
  }

  getUnitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap(): Map<string, any> {
    let unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap: Map<string, any> = new Map();

    this.unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyBehaviorSubjectMap.subscribe(unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyBehaviorSubjectMap => {
      unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap = unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyBehaviorSubjectMap;
    });

    return unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap;
  }

  getUnitedStatesOfAmericaSharesOneCategoryDetailsMap(): Map<string, any> {
    let unitedStatesOfAmericaSharesOneCategoryDetailsMap: Map<string, any> = new Map();

    this.unitedStatesOfAmericaSharesOneCategoryDetailsBehaviorSubjectMap.subscribe(unitedStatesOfAmericaSharesOneCategoryDetailsBehaviorSubjectMap => {
      unitedStatesOfAmericaSharesOneCategoryDetailsMap = unitedStatesOfAmericaSharesOneCategoryDetailsBehaviorSubjectMap;
    });

    return unitedStatesOfAmericaSharesOneCategoryDetailsMap;
  }

  getUnitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap(): Map<string, any> {
    let unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap: Map<string, any> = new Map();

    this.unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyBehaviorSubjectMap.subscribe(unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyBehaviorSubjectMap => {
      unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap = unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyBehaviorSubjectMap;
    });

    return unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap;
  }

  clearUnitedStatesOfAmericaSharesSectorMap() {
    this.unitedStatesOfAmericaSharesSectorBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearUnitedStatesOfAmericaSharesSectorLongTermOnlyMap() {
    this.unitedStatesOfAmericaSharesSectorLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearUnitedStatesOfAmericaSharesCategoryMap() {
    this.unitedStatesOfAmericaSharesCategoryBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearUnitedStatesOfAmericaSharesCategoryLongTermOnlyMap() {
    this.unitedStatesOfAmericaSharesCategoryLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearUnitedStatesOfAmericaSharesOneSectorDetailsMap() {
    this.unitedStatesOfAmericaSharesOneSectorDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearUnitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap() {
    this.unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearUnitedStatesOfAmericaSharesOneCategoryDetailsMap() {
    this.unitedStatesOfAmericaSharesOneCategoryDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  clearUnitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap() {
    this.unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());
  }

  constructor() { }
}
