import { KeyValue } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import services from 'src/assets/services/services.json';
import { dataEncryption } from 'src/app/common/utils/utils';
import { PreDataStoreService } from 'src/app/common/store/pre-data/pre-data-store.service';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {

  // loading
  registerUserReady: boolean = false;
  registerUserError: boolean = false;

  countryDetailsMap: Map<string, any> = new Map();
  servicesOfferedDetailsMap: Map<string, any> = new Map();
  genderMap: Map<string, any> = new Map();
  countriesAndServicesOfferedMap: Map<string, any> = new Map();
  servicesOfferedByCountryMap: Map<string, any> = new Map();
  userOptedServicesMap: Map<string, any> = new Map();
  genderRadioList: Array<any> = [];
  resetGenderRadio: boolean = false;
  countriesCheckBoxList: Array<any> = [];
  resetCountriesCheckBox: boolean = false;
  countriesAndServicesOfferedList: Array<any> = [];
  firstName: string = "";
  middleName: string = "";
  lastName: string = "";
  preferredName: string = "";
  gender: string = "";
  email: string = "";
  password: string = "";
  pin: string = "";
  userOptedCountryIdsList: Array<any> = [];
  userOptedServicesList: Array<any> = [];
  resetServicesOfferedCheckBox: boolean = false;

  // Error field flags
  errorFlagFirstName: boolean = false;
  errorFlagMiddleName: boolean = false;
  errorFlagLastName: boolean = false;
  errorFlagPreferredName: boolean = false;
  errorFlagGender: boolean = false;
  errorFlagEmail: boolean = false;
  errorFlagPassword: boolean = false;
  errorFlagPin: boolean = false;
  errorFlagCountry: boolean = false;
  errorFlagService: boolean = false;
  // Error field messages
  errorMessageFirstName: string = "";
  errorMessageMiddleName: string = "";
  errorMessageLastName: string = "";
  errorMessagePreferredName: string = "";
  errorMessageGender: string = "";
  errorMessageEmail: string = "";
  errorMessagePassword: string = "";
  errorMessagePin: string = "";
  errorMessageCountry: string = "";
  errorMessageService: string = "";

  // Error/Success Modal
  showErrorRegisterUserPreData: boolean = false;
  showSuccess: boolean = false;
  errorMessageRegisterUserPreData: string = "";
  successMessage: string = "";

  constructor(
    private http: HttpClient,
    private router: Router,
    private preDataStoreService: PreDataStoreService
  ) { }

  ngOnInit(): void {
    this.preDataStoreService.getCountryDetailsMap().forEach((val: any, key: any) => {
      this.countryDetailsMap.set(key, val);
      this.countriesCheckBoxList.push({
        id: key,
        value: val.countryName,
        isChecked: false
      });
    });

    this.preDataStoreService.getGenderDetailsMap().forEach((val: any, key: any) => {
      this.genderRadioList.push({
        id: key,
        value: val.genderName
      });
    });

    this.servicesOfferedDetailsMap = this.preDataStoreService.getServicesOfferedDetailsMap();

    this.countriesAndServicesOfferedMap = this.preDataStoreService.getCountriesAndServicesOfferedDetailsMap();

    this.registerUserReady = true;
    this.registerUserError = false;
  }

  // Preserve original property order
  originalOrder = (a: KeyValue<string, any>, b: KeyValue<string, any>): number => {
    return 0;
  }

  onCountryCheckBoxSelect(countries: any) {
    // Resetting the error
    this.errorFlagCountry = false;
    this.errorMessageCountry = "";
    this.errorFlagService = false;
    this.errorMessageService = "";

    this.resetCountriesCheckBox = false;
    this.userOptedCountryIdsList = [];
    // Required to display services offered when country is selected
    this.servicesOfferedByCountryMap = new Map();

    // Adding logic to reset userOptedServicesList
    if (countries.length === 0) {
      this.userOptedServicesList = [];
    } else {
      let updatedUserOptedServicesList: Array<any> = [];
      this.userOptedServicesList.forEach(userOptedServices => {
        countries.forEach((country: any) => {
          if (country.id === userOptedServices.countryId) {
            updatedUserOptedServicesList.push(userOptedServices);
          }
        });
      });
      this.userOptedServicesList = updatedUserOptedServicesList;
    }

    // Adding this to set the service check box list
    let userOptedServicesByCountryMap: Map<string, Set<string>> = new Map();
    this.userOptedServicesList.forEach(userOptedServices => {
      let userOptedServiceIdsByCountrySet: Set<string> = new Set();
      userOptedServices.userOptedServiceIdsList.forEach((userOptedServiceId: string) => {
        userOptedServiceIdsByCountrySet.add(userOptedServiceId);
      });
      userOptedServicesByCountryMap.set(userOptedServices.countryId, userOptedServiceIdsByCountrySet);
    });

    countries.map((country: any) => {
      this.userOptedCountryIdsList.push(country.id);
      let countryDetail = this.countryDetailsMap.get(country.id);
      let serviceOfferedIdsByCountryList =
        this.countriesAndServicesOfferedMap.get(
          country.id
        ).servicesOfferedDetailsIdsList;
      let servicesOfferedByCountryCheckBoxList: Array<any> = [];

      // Fetching service offered by country
      serviceOfferedIdsByCountryList.map((serviceId: any) => {
        let servicesOfferedByCountryCheckBox: any = {
          id: serviceId,
          value: this.servicesOfferedDetailsMap.get(serviceId).serviceName,
          isChecked: false
        }

        let userOptedServiceIdsByCountrySet = userOptedServicesByCountryMap.get(country.id);
        if (userOptedServiceIdsByCountrySet !== undefined) {
          if (userOptedServiceIdsByCountrySet.has(serviceId)) {
            servicesOfferedByCountryCheckBox.isChecked = true;
          }
        }
        servicesOfferedByCountryCheckBoxList.push(servicesOfferedByCountryCheckBox);
      });

      // For services by country
      this.servicesOfferedByCountryMap.set(country.id, {
        countryId: countryDetail.countryId,
        countryName: countryDetail.countryName,
        countryCode: countryDetail.countryCode,
        servicesOfferedCheckBoxList: servicesOfferedByCountryCheckBoxList,
      });
    });
  }

  onGenderSelect(selectedGender: any) {
    // Resetting error flag and messages
    this.errorFlagGender = false;
    this.errorMessageGender = "";
    this.resetGenderRadio = false;
    this.gender = selectedGender.id;
  }

  onServiceCheckBoxSelect(serviceSelected: Array<any>, country: any) {
    // Resetting error flags and message
    this.errorFlagService = false;
    this.errorMessageService = "";

    // Deleting to set the values newly
    this.userOptedServicesMap.delete(country.countryId);
    this.userOptedServicesList = [];

    let serviceIdsSelectedList: Array<any> = [];

    // Create the country newly and add the services selected
    serviceSelected.map((service) => {
      serviceIdsSelectedList.push(service.id);
    });

    if (serviceIdsSelectedList.length !== 0) {
      this.userOptedServicesMap.set(country.countryId, {
        countryId: country.countryId,
        userOptedServiceIdsList: serviceIdsSelectedList,
      });
    }

    // Adding userOptedServicesList to field
    for (let [key, value] of this.userOptedServicesMap) {
      this.userOptedServicesList.push({
        countryId: key,
        userOptedServiceIdsList: value.userOptedServiceIdsList,
      });
    }
  }

  // Incase of the error, change the border from red to original
  inputOnFocus(id: string) {
    (document.getElementById(id) as HTMLElement).style.border = "";

    // Resetting error flags and error messages
    if (id === "register-user-first-name") {
      this.errorFlagFirstName = false;
      this.errorMessageFirstName = "";
    } else if (id === "register-user-middle-name") {
      this.errorFlagMiddleName = false;
      this.errorMessageMiddleName = "";
    } else if (id === "register-user-last-name") {
      this.errorFlagLastName = false;
      this.errorMessageLastName = "";
    } else if (id === "register-user-preferred-name") {
      this.errorFlagPreferredName = false;
      this.errorMessagePreferredName = "";
    } else if (id === "register-user-email") {
      this.errorFlagEmail = false;
      this.errorMessageEmail = "";
    } else if (id === "register-user-password") {
      this.errorFlagPassword = false;
      this.errorMessagePassword = "";
    } else if (id === "register-user-pin") {
      this.errorFlagPin = false;
      this.errorMessagePin = "";
    }
  }

  userAvailability() {
    this.http.get(services.userAvailability + "/" + this.email).subscribe(response => {}, error => {
      if (error.status !== 0) {
        error.error.error.map((e: any) => {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagEmail = true;
          this.errorMessageEmail = e.errorMessage;
        });
      }
    });
  }

  onClear() {
    this.firstName = "";
    this.middleName = "";
    this.lastName = "";
    this.preferredName = "";
    this.gender = "";
    this.resetGenderRadio = true;
    this.email = "";
    this.password = "";
    this.pin = "";
    this.userOptedCountryIdsList = [];
    this.userOptedServicesList = [];
    this.userOptedServicesMap = new Map();
    this.servicesOfferedByCountryMap = new Map();

    this.countriesCheckBoxList = [];
    this.preDataStoreService.getCountryDetailsMap().forEach((val: any, key: any) => {
      this.countryDetailsMap.set(key, val);
      this.countriesCheckBoxList.push({
        id: key,
        value: val.countryName,
        isChecked: false
      });
    });

    this.resetCountriesCheckBox = true;
    this.resetServicesOfferedCheckBox = true;
    // Error field flags
    this.errorFlagFirstName = false;
    this.errorFlagMiddleName = false;
    this.errorFlagLastName = false;
    this.errorFlagPreferredName = false;
    this.errorFlagGender = false;
    this.errorFlagEmail = false;
    this.errorFlagPassword = false;
    this.errorFlagPin = false;
    this.errorFlagCountry = false;
    this.errorFlagService = false;
    // Error field messages
    this.errorMessageFirstName = "";
    this.errorMessageMiddleName = "";
    this.errorMessageLastName = "";
    this.errorMessagePreferredName = "";
    this.errorMessageGender = "";
    this.errorMessageEmail = "";
    this.errorMessagePassword = "";
    this.errorMessagePin = "";
    this.errorMessageCountry = "";
    this.errorMessageService = "";

    // In case of error, change input border color
    (document.getElementById("register-user-first-name") as HTMLElement).style.border = "";
    (document.getElementById("register-user-middle-name") as HTMLElement).style.border = "";
    (document.getElementById("register-user-last-name") as HTMLElement).style.border = "";
    (document.getElementById("register-user-preferred-name") as HTMLElement).style.border = "";
    (document.getElementById("register-user-email") as HTMLElement).style.border = "";
    (document.getElementById("register-user-password") as HTMLElement).style.border = "";
    (document.getElementById("register-user-pin") as HTMLElement).style.border = "";
  }

  onSubmit() {
    let encryptedPassword = dataEncryption(this.password);
    let encryptedPin = dataEncryption(this.pin);

    const formData = {
      firstName: this.firstName,
      middleName: this.middleName,
      lastName: this.lastName,
      preferredName: this.preferredName,
      gender: this.gender,
      email: this.email,
      password: encryptedPassword,
      pin: encryptedPin,
      userOptedCountryIdsList: this.userOptedCountryIdsList,
      userOptedServicesList: this.userOptedServicesList,
    };

    this.http.post<any>(services.registerUser, formData).subscribe(response => {
      this.successMessage = response.message;
      this.showSuccess = true;

      this.firstName = "";
      this.middleName = "";
      this.lastName = "";
      this.preferredName = "";
      this.gender = "";
      this.resetGenderRadio = true;
      this.email = "";
      this.password = "";
      this.pin = "";
      this.userOptedCountryIdsList = [];
      this.userOptedServicesList = [];
      this.userOptedServicesMap = new Map();
      this.servicesOfferedByCountryMap = new Map();
      this.resetCountriesCheckBox = true;
      this.resetServicesOfferedCheckBox = true;
    }, error => {
      error.error.error.map((e: any) => {
        if (e.uiField === "register-user-email") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagEmail = true;
          this.errorMessageEmail = e.errorMessage;
        } else if (e.uiField === "register-user-password") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagPassword = true;
          this.errorMessagePassword = e.errorMessage;
        } else if (e.uiField === "register-user-pin") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagPin = true;
          this.errorMessagePin = e.errorMessage;
        } else if (e.uiField === "register-user-first-name") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagFirstName = true;
          this.errorMessageFirstName = e.errorMessage;
        } else if (e.uiField === "register-user-middle-name") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagMiddleName = true;
          this.errorMessageMiddleName = e.errorMessage;
        } else if (e.uiField === "register-user-last-name") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagLastName = true;
          this.errorMessageLastName = e.errorMessage;
        } else if (e.uiField === "register-user-preferred-name") {
          (document.getElementById(e.uiField) as HTMLElement).style.border =
            "0.1rem solid red";
          this.errorFlagPreferredName = true;
          this.errorMessagePreferredName = e.errorMessage;
        } else if (e.uiField === "register-user-gender") {
          this.errorFlagGender = true;
          this.errorMessageGender = e.errorMessage;
        } else if (e.uiField === "register-user-opt-countries") {
          this.errorFlagCountry = true;
          this.errorMessageCountry = e.errorMessage;
        } else if (e.uiField === "register-user-opt-services") {
          this.errorFlagService = true;
          this.errorMessageService = e.errorMessage;
        }
      })
    });
  }

  closeErrorRegisterUserPreData() {
    this.showErrorRegisterUserPreData = false;
    this.errorMessageRegisterUserPreData = "";
    this.router.navigate(["/login"]);
  }

  closeSuccess() {
    this.showSuccess = false;
    this.successMessage = "";
    this.router.navigate(["/login"]);
  }

  getKeys(map: Map<string, any>) {
    return Array.from(map.values());
  }
}
