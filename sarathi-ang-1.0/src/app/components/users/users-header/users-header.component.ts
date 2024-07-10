import { KeyValue } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LogoutStoreService } from 'src/app/common/store/logout/logout-store.service';
import { PreDataStoreService } from 'src/app/common/store/pre-data/pre-data-store.service';
import { UserDetailsStoreService } from 'src/app/common/store/user-details/user-details-store.service';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-users-header',
  templateUrl: './users-header.component.html',
  styleUrls: ['./users-header.component.css']
})
export class UsersHeaderComponent implements OnInit {

  homeRouterActive: boolean = false;
  servicesRouterActiveMap: Map<string, boolean> = new Map();
  showUserServicesMap: Map<string, boolean> = new Map();
  showUserSubServicesMap: Map<string, boolean> = new Map();
  showUserDropdownInfo: boolean = false;
  userName: string = "";
  userSubServicesList: Array<any> = [];
  userOptedCountryIdsList: Array<any> = [];
  userOptedServiceIdsByCountryMap: Map<string, any> = new Map();
  countryDetailsMap: Map<string, any> = new Map();
  servicesOfferedDetailsMap: Map<string, any> = new Map();
  userSubServicesObject: any = {};
  showUserSubServices: boolean = false;

  constructor(
    private userDetailsStoreService: UserDetailsStoreService,
    private preDataStoreService: PreDataStoreService,
    private logoutStoreService: LogoutStoreService,
    private router: Router,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.userName = this.userDetailsStoreService.getUserDetails().preferredName ? this.userDetailsStoreService.getUserDetails().preferredName : this.userDetailsStoreService.getUserDetails().firstName + " " + this.userDetailsStoreService.getUserDetails().lastName;

    for (let userOptedService of this.userDetailsStoreService.getUserDetails().userOptedServicesList) {
      this.userOptedServiceIdsByCountryMap.set(userOptedService.countryId, userOptedService);
    }

    this.userOptedCountryIdsList = this.userDetailsStoreService.getUserDetails().userOptedCountryIdsList;
    this.countryDetailsMap = this.preDataStoreService.getCountryDetailsMap();
    this.servicesOfferedDetailsMap = this.preDataStoreService.getServicesOfferedDetailsMap();

    for (let [key] of this.countryDetailsMap) {
      this.servicesRouterActiveMap.set(key, false);
      this.showUserServicesMap.set(key, false);
    }

    this.homeRouterActive = true;
    this.router.navigate(["/users/home"]);
  }

  // Preserve original property order
  originalOrder = (a: KeyValue<string, any>, b: KeyValue<string, any>): number => {
    return 0;
  }

  onUsersHomeClick() {
    this.activeRouter("homeRouter");
    this.router.navigate(["/users/home"]);
  }

  onUsersServicesClick(country: any) {
    this.showUserSubServices = false;
    this.showUserSubServicesMap = new Map();
    for (let [key, value] of this.showUserServicesMap) {
      if (key === country.countryId) {
        this.showUserServicesMap.set(key, !value);
        if (!this.showUserServicesMap.get(key)) {
          this.userSubServicesObject = {};
        }
      } else {
        this.showUserServicesMap.set(key, false);
      }
    }
  }

  activeRouter(selectedRouter: string) {
    if (selectedRouter === "homeRouter") {
      for (let [key] of this.servicesRouterActiveMap) {
        this.servicesRouterActiveMap.set(key, false);;
      }
      for (let [key] of this.showUserServicesMap) {
        this.showUserServicesMap.set(key, false);
      }
      this.userSubServicesObject = {};
      this.showUserSubServices = false;
      this.homeRouterActive = true;
    } else {
      this.homeRouterActive = false;
      for (let [key] of this.servicesRouterActiveMap) {
        if (key === selectedRouter) {
          this.servicesRouterActiveMap.set(key, true);
        } else {
          this.servicesRouterActiveMap.set(key, false);
        }
      }
    }
  }

  onUserDropdownInfoBlur() {
    this.showUserDropdownInfo = false;
  }

  onUserDropdownInfoClick() {
    this.showUserDropdownInfo = !this.showUserDropdownInfo;
    for (let [key] of this.showUserServicesMap) {
      this.showUserServicesMap.set(key, false);
    }
    this.userSubServicesObject = {};
    this.showUserSubServices = false;
    this.showUserSubServicesMap = new Map();
  }

  onUserProfileClick() {
    this.showUserDropdownInfo = false;
    this.homeRouterActive = false;
    this.router.navigate(["/users/profile"]);
  }

  onUserLogoutClick() {
    this.showUserDropdownInfo = false;
    this.http.get<any>(services.logout, {}).subscribe(response => {}, error => {});
    this.logoutStoreService.clearStoreAndLogout();
  }

  onServiceSelectClick(countryId: string, serviceCode: string) {
    this.showUserSubServicesMap = new Map();

    if ("home" === serviceCode) {
      let countryCode: string = this.preDataStoreService.getCountryDetailsMap().get(countryId).countryCode;
      this.router.navigate(["/users/" + countryCode + "/" + serviceCode.toLowerCase()]);
      this.activeRouter(this.countryDetailsMap.get(countryId).countryId);

      for (let [key, value] of this.showUserServicesMap) {
        if (value === true) {
          this.showUserServicesMap.set(key, false);
        }
      }

      this.userSubServicesObject = {};
      this.showUserSubServices = false;
      this.showUserSubServicesMap = new Map();
      return;
    }

    let userSubServicesList: Array<any> = [];
    this.preDataStoreService.getSubServicesByServiceCodeDetailsMap().get(serviceCode).subServicesDetailsList.forEach((subServicesDetail: string) => {
      userSubServicesList.push(this.preDataStoreService.getSubServicesDetailsMap().get(subServicesDetail));
    });

    if (this.userSubServicesObject.countryId === this.preDataStoreService.getCountryDetailsMap().get(countryId).countryId && this.userSubServicesObject.serviceCode === serviceCode) {
      this.userSubServicesObject = {};
      this.showUserSubServices = false;
      this.showUserSubServicesMap = new Map();
      return;
    }

    this.userSubServicesObject = {
      countryId: this.preDataStoreService.getCountryDetailsMap().get(countryId).countryId,
      countryCode: this.preDataStoreService.getCountryDetailsMap().get(countryId).countryCode,
      serviceCode: serviceCode,
      userSubServicesList: userSubServicesList
    };

    this.showUserSubServicesMap.set(serviceCode, true);
    this.showUserSubServices = true;
  }

  onSubServiceSelectClick(countryId: string, countryCode: string, serviceCode: string, subServiceName: string) { 

    this.userSubServicesObject = {};
    this.showUserSubServices = false;
    this.showUserSubServicesMap = new Map();
    for (let [key] of this.countryDetailsMap) {
      this.servicesRouterActiveMap.set(key, false);
      this.showUserServicesMap.set(key, false);
    }
    this.activeRouter(countryId);
    this.router.navigate(["/users/" + countryCode + "/" + serviceCode + "/" + subServiceName.toLowerCase()]);
  }
}
