import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { PreDataStoreService } from 'src/app/common/store/pre-data/pre-data-store.service';
import { UserDetailsStoreService } from 'src/app/common/store/user-details/user-details-store.service';
import services from 'src/assets/services/services.json';
import { dataEncryption } from 'src/app/common/utils/utils';
import { LogoutStoreService } from 'src/app/common/store/logout/logout-store.service';
import { KeyValue } from '@angular/common';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  userName: string = "";
  firstName: string = "";
  middleName: string = "";
  lastName: string = "";
  preferredName: string = "";
  gender: string = "";
  email: string = "";
  // Show Update
  showPasswordUpdateContent: boolean = false;
  showPinUpdateContent: boolean = false;
  showCountriesAndServicesUpdateContent: boolean = false;
  // Password update fields
  oldPassword: string = "";
  newPassword: string = "";
  confirmNewPassword: string = "";
  // Password update fields;
  oldPin: string = "";
  newPin: string = "";
  confirmNewPin: string = "";
  // User Services fields
  countriesCheckBoxList: Array<any> = [];
  resetCountriesCheckBox: boolean = false;
  servicesOfferedByCountryMap: Map<string, any> = new Map();
  resetServicesOfferedCheckBox: boolean = false;
  userOptedCountryIdsList: Array<any> = [];
  userOptedServicesList: Array<any> = [];
  userOptedServicesMap: Map<string, any> = new Map();
  userOptedCountryIdsSet: Set<any> = new Set();
  logoutFlag: boolean = false;
  // Error field flags
  errorFlagOldPassword: boolean = false;
  errorFlagNewPassword: boolean = false;
  errorFlagConfirmNewPassword: boolean = false;
  errorFlagOldPin: boolean = false;
  errorFlagNewPin: boolean = false;
  errorFlagConfirmNewPin: boolean = false;
  errorFlagCountry: boolean = false;
  errorFlagService: boolean = false;
  // Error field messages
  errorMessageOldPassword: string = "";
  errorMessageNewPassword: string = "";
  errorMessageConfirmNewPassword: string = "";
  errorMessageOldPin: string = "";
  errorMessageNewPin: string = "";
  errorMessageConfirmNewPin: string = "";
  errorMessageCountry: string = "";
  errorMessageService: string = "";
  // Modal
  showError: boolean = false;
  errorMessage: string = "";
  successMessage: string = "";
  showSuccess: boolean = false;
  showConfirmation: boolean = false;
  confirmationMessage: string = "";
  showLoadingModal: boolean = false;

  constructor(
    private userDetailsStoreService: UserDetailsStoreService,
    private preDataStoreService : PreDataStoreService,
    private http: HttpClient,
    private logoutStoreService : LogoutStoreService
  ) { }

  ngOnInit(): void {
    this.getUserDetails();
    this.getDefaultUserOptedCountriesAndServices();
  }

  getUserDetails() {
    let userObject = this.userDetailsStoreService.getUserDetails();

    this.userName = userObject.preferredName ? userObject.preferredName : userObject.firstName + " " + userObject.lastName;
  
    this.firstName = userObject.firstName;
    this.middleName = userObject.middleName;
    this.lastName = userObject.lastName;
    this.preferredName = userObject.preferredName;
    this.gender = this.preDataStoreService.getGenderDetailsMap().get(userObject.gender).genderName;
    this.email = userObject.email;
  }

  getDefaultUserOptedCountriesAndServices() {
    this.countriesCheckBoxList = [];
    this.userOptedCountryIdsList = [];
    this.userOptedServicesList = [];

    let userOptedCountryIdsSet = new Set();
    for (let userOptedCountryId of this.userDetailsStoreService.getUserDetails().userOptedCountryIdsList) {
      userOptedCountryIdsSet.add(userOptedCountryId);
    }

    let userOptedServiceIdsByCountryMap = new Map();
    for (let userOptedServices of this.userDetailsStoreService.getUserDetails().userOptedServicesList) {
      let userOptedServicesIdsSet: Set<string> = new Set();
      for (let userOptedServiceId of userOptedServices.userOptedServiceIdsList) {
          userOptedServicesIdsSet.add(userOptedServiceId);
      }
      userOptedServiceIdsByCountryMap.set(userOptedServices.countryId, userOptedServicesIdsSet);
    }

    // Required to display services offered when country is selected
    this.servicesOfferedByCountryMap = new Map();

    for(let [key, val] of this.preDataStoreService.getCountryDetailsMap()) {

      let countriesCheckBox = {
        id: key,
        value: val.countryName,
        isChecked: false
      };

      if (userOptedCountryIdsSet.has(key)) {
        this.userOptedCountryIdsList.push(key);
        countriesCheckBox.isChecked = true;

        // Services offered by country
        let servicesOfferedDetailsIdsByCountryList = this.preDataStoreService.getCountriesAndServicesOfferedDetailsMap().get(key).servicesOfferedDetailsIdsList;

        let userOptedServiceIdsList: Array<any> = [];
        let servicesOfferedByCountryCheckBoxList: Array<any> = [];

        servicesOfferedDetailsIdsByCountryList.map((serviceId: any) => {
          let servicesOfferedByCountryCheckBox = {
            id: serviceId,
            value: this.preDataStoreService.getServicesOfferedDetailsMap().get(serviceId).serviceName,
            isChecked: false
          };

          if (userOptedServiceIdsByCountryMap.get(key).has(serviceId)) {
            userOptedServiceIdsList.push(serviceId);
            servicesOfferedByCountryCheckBox.isChecked = true;
          }
          servicesOfferedByCountryCheckBoxList.push(servicesOfferedByCountryCheckBox);
        });

        if (userOptedServiceIdsList.length !== 0) {
          this.userOptedServicesMap.set(key, {
            countryId: key,
            userOptedServiceIdsList: userOptedServiceIdsList,
          });
        }

        this.servicesOfferedByCountryMap.set(key, {
          countryId: val.countryId,
          countryName: val.countryName,
          countryCode: val.countryCode,
          servicesOfferedCheckBoxList: servicesOfferedByCountryCheckBoxList,
        });
      }

      this.countriesCheckBoxList.push(countriesCheckBox);

    }

    // Adding userOptedServicesList to field
    for (let [key, value] of this.userOptedServicesMap) {
      this.userOptedServicesList.push({
        countryId: key,
        userOptedServiceIdsList: value.userOptedServiceIdsList,
      });
    }
  }

  onPasswordUpdateCollapsibleClick() {
    this.onPasswordUpdateClear();
    this.onPinUpdateClear();
    this.onCountriesAndServicesUpdateClear();

    this.showPinUpdateContent = false;
    this.showCountriesAndServicesUpdateContent = false;
    this.showPasswordUpdateContent = !this.showPasswordUpdateContent;
  }

  onPinUpdateCollapsibleClick() {
    this.onPasswordUpdateClear();
    this.onPinUpdateClear();
    this.onCountriesAndServicesUpdateClear();

    this.showPasswordUpdateContent = false;
    this.showCountriesAndServicesUpdateContent = false;
    this.showPinUpdateContent = !this.showPinUpdateContent;
  }

  onCountriesAndServicesUpdateCollapsibleClick() {
    this.onPasswordUpdateClear();
    this.onPinUpdateClear();
    this.onCountriesAndServicesUpdateClear();

    this.showPasswordUpdateContent = false;
    this.showPinUpdateContent = false;
    this.showCountriesAndServicesUpdateContent = !this.showCountriesAndServicesUpdateContent
  }

  onPasswordUpdateClear() {
    this.oldPassword = "";
    this.newPassword = "";
    this.confirmNewPassword = "";
    this.errorFlagOldPassword = false;
    this.errorFlagNewPassword = false;
    this.errorFlagConfirmNewPassword = false;
    this.errorMessageOldPassword = "";
    this.errorMessageNewPassword = "";
    this.errorMessageConfirmNewPassword = "";

    // In case of error, change input border color
    if (document.getElementById("user-profile-old-password")) {
      (document.getElementById("user-profile-old-password") as HTMLElement).style.border = "";
    }
    if (document.getElementById("user-profile-new-password")) {
      (document.getElementById("user-profile-new-password") as HTMLElement).style.border = "";
    }
    if (document.getElementById("user-profile-confirm-new-password")) {
      (document.getElementById("user-profile-confirm-new-password") as HTMLElement).style.border = "";
    }
  }

  onPinUpdateClear() {
    this.oldPin = "";
    this.newPin = "";
    this.confirmNewPin = "";
    this.errorFlagOldPin = false;
    this.errorFlagNewPin = false;
    this.errorFlagConfirmNewPin = false;
    this.errorMessageOldPin = "";
    this.errorMessageNewPin = "";
    this.errorMessageConfirmNewPin = "";

    // In case of error, change input border color
    if (document.getElementById("user-profile-old-pin")) {
      (document.getElementById("user-profile-old-pin") as HTMLElement).style.border = "";
    }
    if (document.getElementById("user-profile-new-pin")) {
      (document.getElementById("user-profile-new-pin") as HTMLElement).style.border = "";
    }
    if (document.getElementById("user-profile-confirm-new-pin")) {
      (document.getElementById("user-profile-confirm-new-pin") as HTMLElement).style.border = "";
    }
  }

  onCountriesAndServicesUpdateClear() {
    this.errorFlagCountry = false;
    this.errorFlagService = false;
    this.errorMessageCountry = "";
    this.errorMessageService = "";
    this.getDefaultUserOptedCountriesAndServices();
    this.resetCountriesCheckBox = true;
    this.resetServicesOfferedCheckBox = true;
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

    this.resetServicesOfferedCheckBox = false;
    this.resetCountriesCheckBox = false;
    this.userOptedCountryIdsList = [];
    this.userOptedCountryIdsSet = new Set();

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

    // Required to display services offered when country is selected
    this.servicesOfferedByCountryMap = new Map();

    countries.map((country: any) => {
      this.userOptedCountryIdsList.push(country.id);
      this.userOptedCountryIdsSet.add(country.id);
      let countryDetail = this.preDataStoreService.getCountryDetailsMap().get(country.id);
      let serviceOfferedIdsByCountryList = this.preDataStoreService.getCountriesAndServicesOfferedDetailsMap().get(country.id).servicesOfferedDetailsIdsList;
      let servicesOfferedByCountryCheckBoxList: Array<any> = [];

      // Fetching service offered by country
      serviceOfferedIdsByCountryList.map((serviceId: any) => {
        let servicesOfferedByCountryCheckBox: any = {
          id: serviceId,
          value: this.preDataStoreService.getServicesOfferedDetailsMap().get(serviceId).serviceName,
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

  onServiceCheckBoxSelect(serviceSelected: any, country: any) {
    // Resetting error flags and message
    this.errorFlagService = false;
    this.errorMessageService = "";

    // this.resetServicesOfferedCheckBox = false;

    // Deleting to set the values newly
    this.userOptedServicesMap.delete(country.countryId);
    this.userOptedServicesList = [];

    let servicesSelectedList: Array<any> = [];

    // Create the country newly and add the services selected
    serviceSelected.map((service: any) => {
      servicesSelectedList.push(service.id);
    });

    if (servicesSelectedList.length !== 0) {
      this.userOptedServicesMap.set(country.countryId, {
        countryId: country.countryId,
        userOptedServiceIdsList: servicesSelectedList,
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

  onPasswordUpdateSubmit () {
    this.showLoadingModal = true;
    let encryptedOldPassword = dataEncryption(this.oldPassword);
    let encryptedNewPassword = dataEncryption(this.newPassword);
    let encryptedConfirmNewPassword = dataEncryption(this.confirmNewPassword);

    const formData = new FormData();
    formData.append("oldPassword", encryptedOldPassword);
    formData.append("newPassword", encryptedNewPassword);
    formData.append("confirmNewPassword", encryptedConfirmNewPassword);

    this.http.post<any>(services.updatePassword, formData).subscribe(response => {
      this.successMessage = response.message;
      this.showSuccess = response.status;
      this.showLoadingModal = false;
    }, error => {
      error.error.error.map((e: any) => {
        if (e.uiField === "user-profile-old-password") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagOldPassword = true;
          this.errorMessageOldPassword = e.errorMessage;
        } else if (e.uiField === "user-profile-new-password") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagNewPassword = true;
          this.errorMessageNewPassword = e.errorMessage;
        } else if (e.uiField === "user-profile-confirm-new-password") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagConfirmNewPassword = true;
          this.errorMessageConfirmNewPassword = e.errorMessage;
        } else {
          this.errorMessage = e.errorMessage;
          this.showError = true;
        }
      });

      this.showLoadingModal = false;
    });
  }

  onPinUpdateSubmit() {
    this.showLoadingModal = true;
    let encryptedOldPin = dataEncryption(this.oldPin);
    let encryptedNewPin = dataEncryption(this.newPin);
    let encryptedConfirmNewPin = dataEncryption(this.confirmNewPin);

    const formData = new FormData();
    formData.append("oldPin", encryptedOldPin);
    formData.append("newPin", encryptedNewPin);
    formData.append("confirmNewPin", encryptedConfirmNewPin);

    this.http.post<any>(services.updatePin, formData).subscribe(response => {
      this.successMessage = response.message;
      this.showSuccess = response.status;
      this.showLoadingModal = false;
    }, error => {
      error.error.error.map((e: any) => {
        if (e.uiField === "user-profile-old-pin") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagOldPin = true;
          this.errorMessageOldPin = e.errorMessage;
        } else if (e.uiField === "user-profile-new-pin") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagNewPin = true;
          this.errorMessageNewPin = e.errorMessage;
        } else if (e.uiField === "user-profile-confirm-new-pin") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagConfirmNewPin = true;
          this.errorMessageConfirmNewPin = e.errorMessage;
        } else {
          this.errorMessage = e.errorMessage;
          this.showError = true;
        }
      });

      this.showLoadingModal = false;
    });
  }

  onCountriesAndServicesUpdateSubmit() {
    this.showLoadingModal = true;

    const formData = {
      userOptedCountryIdsList: this.userOptedCountryIdsList,
      userOptedServicesList: this.userOptedServicesList,
      confirmed: false
    };

    this.http.post<any>(services.updateCountriesAndServices, formData).subscribe(response => {
      if (response.confirmationRequired) {
        this.confirmationMessage = response.message;
        this.showConfirmation = response.confirmationRequired;
        this.showLoadingModal = false;
      } else if (response.sameData) {
        this.successMessage = response.message;
        this.showSuccess = response.sameData;
        this.showLoadingModal = false;
      } else if (response.dataAdded) {
        this.successMessage = response.message;
        this.showSuccess = response.dataAdded;
        this.userDetailsStoreService.setUserDetails(response.userDetails);
        this.logoutFlag = true;
        this.showLoadingModal = false;
      }
    }, error => {
      error.error.error.map((e: any) => {
        if (e.uiField === "user-profile-opt-countries") {
          this.errorFlagCountry = true;
          this.errorMessageCountry = e.errorMessage;
        } else if (e.uiField === "user-profile-opt-services") {
          this.errorFlagService = true;
          this.errorMessageService = e.errorMessage;
        }
      });

      this.showLoadingModal = false;
    });
  }

  passwordUpdateInputOnFocus(id: any) {
    (document.getElementById(id) as HTMLElement).style.border = "";
    // Resetting error flags and error messages
    if (id === "user-profile-old-password") {
      this.errorFlagOldPassword = false;
      this.errorMessageOldPassword = "";
    } else if (id === "user-profile-new-password") {
      this.errorFlagNewPassword = false;
      this.errorMessageNewPassword = "";
    } else if (id === "user-profile-confirm-new-password") {
      this.errorFlagConfirmNewPassword = false;
      this.errorMessageConfirmNewPassword = "";
    }
  }

  pinUpdateInputOnFocus(id: any) {
    (document.getElementById(id) as HTMLElement).style.border = "";
    // Resetting error flags and error messages
    if (id === "user-profile-old-pin") {
      this.errorFlagOldPin = false;
      this.errorMessageOldPin = "";
    } else if (id === "user-profile-new-pin") {
      this.errorFlagNewPin = false;
      this.errorMessageNewPin = "";
    } else if (id === "user-profile-confirm-new-pin") {
      this.errorFlagConfirmNewPin = false;
      this.errorMessageConfirmNewPin = "";
    }
  }

  closeError() {
    this.errorMessage = "";
    this.showError = false;
  }

  closeSuccess() {
    this.onPasswordUpdateClear();
    this.onPinUpdateClear();
    this.onCountriesAndServicesUpdateClear();

    this.showPasswordUpdateContent = false;
    this.showPinUpdateContent = false;
    this.showCountriesAndServicesUpdateContent = false;

    this.successMessage = "";
    this.showSuccess = false;
    if (this.logoutFlag) {
      this.http.get<any>(services.logout, {}).subscribe(response => {}, error => {});
      this.logoutStoreService.clearStoreAndLogout();
    }
  }

  onCloseConfirmation() {  
    this.confirmationMessage = "";
    this.showConfirmation = false;
  }

  onConfirmationContinue() {
    this.showLoadingModal = true;

    this.confirmationMessage = "";
    this.showConfirmation = false;

    const formData = {
      userOptedCountryIdsList: this.userOptedCountryIdsList,
      userOptedServicesList: this.userOptedServicesList,
      confirmed: true
    };

    this.http.post<any>(services.updateCountriesAndServices, formData).subscribe(response => {
      this.successMessage = response.message;
      this.showSuccess = response.dataAdded;
      this.userDetailsStoreService.setUserDetails(response.userDetails);
      this.logoutFlag = true;
      this.showLoadingModal = false;
    }, error => {
      console.log(error);
      this.showLoadingModal = false;
    });
  }
}
