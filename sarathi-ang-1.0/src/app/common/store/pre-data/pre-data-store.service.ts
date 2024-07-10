import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PreDataStoreService {

  countryDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());

  servicesOfferedDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());

  countriesAndServicesOfferedDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());

  genderDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());

  subServicesDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());

  subServicesByServiceCodeDetailsBehaviorSubjectMap = new BehaviorSubject<Map<string, any>>(new Map());

  // Setters
  setCountryDetailsMap(countryDetailsMapResponse: Map<string, any>) {
    let countryDetailsMap: Map<string, any> = new Map();

      Object.entries(countryDetailsMapResponse).forEach((entry: any) => {
        countryDetailsMap.set(entry[0], entry[1]);
      });

      this.countryDetailsBehaviorSubjectMap.next(countryDetailsMap);
  }

  setServicesOfferedDetailsMap(servicesOfferedDetailsMapResponse: Map<string, any>) {
    let servicesOfferedDetailsMap: Map<string, any> = new Map();

      Object.entries(servicesOfferedDetailsMapResponse).forEach((entry: any) => {
        servicesOfferedDetailsMap.set(entry[0], entry[1]);
      });

      this.servicesOfferedDetailsBehaviorSubjectMap.next(servicesOfferedDetailsMap);
  }

  setCountriesAndServicesOfferedDetailsMap(countriesAndServicesOfferedDetailsMapResponse: Map<string, any>) {
    let countriesAndServicesOfferedDetailsMap: Map<string, any> = new Map();

      Object.entries(countriesAndServicesOfferedDetailsMapResponse).forEach((entry: any) => {
        countriesAndServicesOfferedDetailsMap.set(entry[0], entry[1]);
      });

      this.countriesAndServicesOfferedDetailsBehaviorSubjectMap.next(countriesAndServicesOfferedDetailsMap);
  }

  setGenderDetailsMap(genderDetailsMapResponse: Map<string, any>) {
    let genderDetailsMap: Map<string, any> = new Map();

      Object.entries(genderDetailsMapResponse).forEach((entry: any) => {
        genderDetailsMap.set(entry[0], entry[1]);
      });

      this.genderDetailsBehaviorSubjectMap.next(genderDetailsMap);
  }

  setSubServicesDetailsMap(subServicesDetailsMapResponse: Map<string, any>) {
    let subServicesDetailsMap: Map<string, any> = new Map();

      Object.entries(subServicesDetailsMapResponse).forEach((entry: any) => {
        subServicesDetailsMap.set(entry[0], entry[1]);
      });

      this.subServicesDetailsBehaviorSubjectMap.next(subServicesDetailsMap);
  }

  setSubServicesByServiceCodeDetailsMap(subServicesByServiceCodeDetailsMapResponse: Map<string, any>) {
    let subServicesByServiceCodeDetailsMap: Map<string, any> = new Map();

      Object.entries(subServicesByServiceCodeDetailsMapResponse).forEach((entry: any) => {
        subServicesByServiceCodeDetailsMap.set(entry[0], entry[1]);
      });

      this.subServicesByServiceCodeDetailsBehaviorSubjectMap.next(subServicesByServiceCodeDetailsMap);
  }

  // Getters
  getCountryDetailsMap(): Map<string, any> {
    let countryDetailsMap: Map<string, any> = new Map();

    this.countryDetailsBehaviorSubjectMap.subscribe(countryDetailsBehaviorSubjectMap => {
      countryDetailsMap = countryDetailsBehaviorSubjectMap;
    });

    return countryDetailsMap;
  }

  getServicesOfferedDetailsMap(): Map<string, any> {
    let servicesOfferedDetailsMap: Map<string, any> = new Map();

    this.servicesOfferedDetailsBehaviorSubjectMap.subscribe(servicesOfferedDetailsBehaviorSubjectMap => {
      servicesOfferedDetailsMap = servicesOfferedDetailsBehaviorSubjectMap;
    });

    return servicesOfferedDetailsMap;
  }

  getCountriesAndServicesOfferedDetailsMap(): Map<string, any> {
    let countriesAndServicesOfferedDetailsMap: Map<string, any> = new Map();

    this.countriesAndServicesOfferedDetailsBehaviorSubjectMap.subscribe(countriesAndServicesOfferedDetailsBehaviorSubjectMap => {
      countriesAndServicesOfferedDetailsMap = countriesAndServicesOfferedDetailsBehaviorSubjectMap;
    });

    return countriesAndServicesOfferedDetailsMap;
  }

  getGenderDetailsMap(): Map<string, any> {
    let genderDetailsMap: Map<string, any> = new Map();

    this.genderDetailsBehaviorSubjectMap.subscribe(genderDetailsBehaviorSubjectMap => {
      genderDetailsMap = genderDetailsBehaviorSubjectMap;
    });

    return genderDetailsMap;
  }

  getSubServicesDetailsMap(): Map<string, any> {
    let subServicesDetailsMap: Map<string, any> = new Map();

    this.subServicesDetailsBehaviorSubjectMap.subscribe(subServicesDetailsBehaviorSubjectMap => {
      subServicesDetailsMap = subServicesDetailsBehaviorSubjectMap;
    });

    return subServicesDetailsMap;
  }

  getSubServicesByServiceCodeDetailsMap(): Map<string, any> {
    let subServicesByServiceCodeDetailsMap: Map<string, any> = new Map();

    this.subServicesByServiceCodeDetailsBehaviorSubjectMap.subscribe(subServicesByServiceCodeDetailsBehaviorSubjectMap => {
      subServicesByServiceCodeDetailsMap = subServicesByServiceCodeDetailsBehaviorSubjectMap;
    });

    return subServicesByServiceCodeDetailsMap;
  }

  constructor() { }
}
